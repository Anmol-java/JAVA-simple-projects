import java.util.*;
class Unique {
static boolean unique(int x){
boolean seen[] = new boolean[10];
while(x>0){
int d = x%10;
if(seen[d]) return false;
seen[d] = true;
x/=10;
}
return true;
}
public static void main(String args[]){
Scanner sc = new Scanner(System.in);
int m = sc.nextInt();
int n = sc.nextInt();
System.out.println("THE UNIQUE-DIGIT INTEGERS ARE:");
int count = 0;
boolean none=true;
for(int i=m;i<=n;i++){
if(unique(i)){
System.out.print(i+", ");
count++;
none=false;
}
}
if(none)
System.out.println("NIL");
System.out.println("\nFREQUENCY OF UNIQUE-DIGIT INTEGERS IS: " +
count);
}
}