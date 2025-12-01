# JAVA Simple Projects

This repository contains a collection of simple and authentic Java programs.  
These programs are designed for beginners who want to understand core Java concepts through small, clear examples.

## 📁 Included Programs
- **GoldBasch.java** – Program related to number theory (Goldbach’s conjecture, prime checks, etc.)
- **Keystrokes.java** – Demonstrates keyboard input handling in Java.
- **Matrix.java** – Basic matrix operations (input, display, etc.)
- **MatrixSort.java** – Sorting operations on matrices.
- **Unique.java** – Program to find unique elements in an array or string.

## 🎯 Purpose
These programs are meant to:
- Strengthen Java fundamentals  
- Provide reference code for students  
- Serve as simple examples for practice and learning  

## 🛠 How to Run
1. Install Java (JDK 8 or later).
2. Compile any file using:
   ```bash
   javac FileName.java
# JAVA Simple Projects — Repo Enhancements

This document adds everything you asked for:

* A `Dockerfile` to containerize your simple Java programs
* A recommended project folder structure
* A GitHub Actions CI workflow (build + optional Docker push)
* A GitHub profile `README` (for your GitHub profile repo)
* OpenShift deployment YAML (Deployment, Service, Route) and commands

Copy the sections you need into your repository files.

---

# 1) Recommended Project Structure

```
JAVA-simple-projects/
├── Dockerfile
├── .dockerignore
├── README.md                 # the README we already added
├── .github/
│   └── workflows/
│       └── ci.yml            # GitHub Actions workflow
├── openshift/
│   ├── deployment.yaml       # Deployment + Service + Route
│   └── build-and-deploy.sh   # optional helper script for oc commands
├── src/                      # put your .java files here
│   ├── GoldBasch.java
│   ├── Keystrokes.java
│   ├── Matrix.java
│   ├── MatrixSort.java
│   └── Unique.java
└── README.md
```

Place your `.java` files under `src/`.

---

# 2) Dockerfile (multi-stage, compiles .java and runs by MAIN_CLASS env)

Create `Dockerfile` at repo root:

```dockerfile
# Stage 1: compile
FROM openjdk:17-jdk-slim AS build
WORKDIR /usr/src/app
COPY src/ ./src
RUN mkdir -p bin
# compile all java files into bin
RUN javac -d bin $(find src -name "*.java") || true

# Stage 2: runtime
FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=build /usr/src/app/bin /app/bin
ENV MAIN_CLASS=Matrix
# default command runs the class named by MAIN_CLASS
CMD ["sh", "-c", "java -cp /app/bin $MAIN_CLASS"]
```

Notes:

* The Dockerfile compiles all `.java` files (no build tool required). Put sources in `src/`.
* Set the `MAIN_CLASS` at runtime (e.g. `docker run -e MAIN_CLASS=GoldBasch ...`).

Add `.dockerignore` to reduce context size:

```
target/
*.class
*.jar
.git
.gitignore
```

---

# 3) GitHub Actions workflow (`.github/workflows/ci.yml`)

This workflow does:

1. Check out code
2. Use Java 17
3. Compile `.java` files
4. Optionally build & push Docker image when `DOCKERHUB_USERNAME` secret exists

```yaml
name: CI

on:
  push:
    branches: [ main, master ]
  pull_request:
    branches: [ main, master ]

jobs:
  build-java:
    runs-on: ubuntu-latest
    steps:
      - name: Check out code
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '17'

      - name: Compile Java sources
        run: |
          mkdir -p bin
          find src -name "*.java" -print
          javac -d bin $(find src -name "*.java")

      - name: List compiled classes
        run: ls -R bin || true

  docker-build-and-push:
    needs: build-java
    runs-on: ubuntu-latest
    if: secrets.DOCKERHUB_USERNAME != '' && secrets.DOCKERHUB_TOKEN != ''
    steps:
      - name: Check out code
        uses: actions/checkout@v4

      - name: Build and push Docker image
        uses: docker/build-push-action@v5
        with:
          context: .
          push: true
          tags: ${{ secrets.DOCKERHUB_USERNAME }}/java-simple-projects:latest

```

How to configure secrets:

* Go to your repository → Settings → Secrets and variables → Actions
* Add `DOCKERHUB_USERNAME` and `DOCKERHUB_TOKEN` (Docker Hub access token)

After pushing, the workflow will compile the code and — if secrets are set — build & push the Docker image.

---

# 4) GitHub profile README (for `github.com/<your-username>/<your-username>` repo)

Create a repository named exactly like your username and add the following `README.md` there.

```markdown
# Hi, I'm Anmol 👋

I'm learning Java and sharing simple, authentic Java programs to practice core concepts.

## 🔭 Current Projects
- **JAVA-simple-projects** — small Java programs demonstrating arrays, matrices, input handling, sorting, and basic algorithms.

## 🛠️ Skills
Java · Git · GitHub · Docker · OpenShift basics

## 📫 How to reach me
- GitHub: https://github.com/Anmol-java

Feel free to browse my repos, open issues, or send improvements via PRs.
```

---

# 5) OpenShift deployment

Below is a general `deployment.yaml` you can apply in OpenShift after pushing your image to a registry. Replace `<IMAGE>` with your image (Docker Hub or internal registry).

Save as `openshift/deployment.yaml`:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: java-simple-app
  labels:
    app: java-simple-app
spec:
  replicas: 1
  selector:
    matchLabels:
      app: java-simple-app
  template:
    metadata:
      labels:
        app: java-simple-app
    spec:
      containers:
        - name: java-simple-app
          image: <IMAGE>
          imagePullPolicy: Always
          env:
            - name: MAIN_CLASS
              value: Matrix
          ports:
            - containerPort: 8080
              name: http
---
apiVersion: v1
kind: Service
metadata:
  name: java-simple-app
spec:
  selector:
    app: java-simple-app
  ports:
    - name: http
      protocol: TCP
      port: 8080
      targetPort: 8080
  type: ClusterIP
---
apiVersion: route.openshift.io/v1
kind: Route
metadata:
  name: java-simple-app
spec:
  to:
    kind: Service
    name: java-simple-app
  port:
    targetPort: http
  tls:
    termination: edge
```

**Notes:**

* The `image` field should be `yourdockerhubuser/java-simple-projects:latest` or the internal registry address.
* The container port is `8080` for example purposes. If your Java programs are console-only, they won't serve HTTP — you can still run them but exposing a Route won't make sense unless your app listens on a port.

## OpenShift helper script (optional)

`openshift/build-and-deploy.sh` — example commands to push to internal registry and deploy:

```bash
#!/usr/bin/env bash
set -e

PROJECT=my-docker-project
APP_NAME=java-simple-app
IMAGE_TAG=latest

# Login to cluster (oc must be configured already)
# oc login <api-url>

oc project $PROJECT || oc new-project $PROJECT

# Build using local docker and push to internal registry example (adjust domain)
# 1) Build locally
docker build -t ${APP_NAME}:${IMAGE_TAG} .

# 2) Tag for cluster internal registry (replace <cluster-domain>)
REGISTRY=default-route-openshift-image-registry.apps.<cluster-domain>
docker tag ${APP_NAME}:${IMAGE_TAG} ${REGISTRY}/${PROJECT}/${APP_NAME}:${IMAGE_TAG}

echo "Logging into registry..."
docker login -u $(oc whoami) -p $(oc whoami -t) ${REGISTRY}

docker push ${REGISTRY}/${PROJECT}/${APP_NAME}:${IMAGE_TAG}

# 3) Apply deployment pointing to internal image
sed "s#<IMAGE>#${REGISTRY}/${PROJECT}/${APP_NAME}:${IMAGE_TAG}#g" openshift/deployment.yaml | oc apply -f -

# 4) Expose route (if not created by YAML)
# oc expose svc/${APP_NAME}

oc rollout status deploy/${APP_NAME}

```

Replace `<cluster-domain>` with your OpenShift cluster domain.

---

# Quick usage examples

### Build and run locally

```
# from repo root
docker build -t java-simple-projects:local .
# run Matrix (default)
docker run --rm java-simple-projects:local
# run a specific class
docker run --rm -e MAIN_CLASS=GoldBasch java-simple-projects:local
```

### Deploy to OpenShift (assuming image pushed to Docker Hub)

```
# Create project
oc new-project my-docker-project
# Deploy using image from Docker Hub
oc new-app yourdockerhubuser/java-simple-projects:latest --name=java-simple-app
# Expose it
oc expose svc/java-simple-app
oc get route
```

---

# Final tips

* If your apps are console programs (read stdin, print to stdout) and not HTTP services, consider creating a small HTTP wrapper (e.g., a tiny Spark/Jetty app) if you want them reachable via a browser/route.
* If you later add a build tool (Maven/Gradle), update the Dockerfile and CI to use the build tool (packaging into a jar).

---

