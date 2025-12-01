import java.util.Scanner;
public class Matrix {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the rows and column");
        int M = sc.nextInt();
        int N = sc.nextInt();
        if (M <= 2 || M >= 10 || N <= 2 || N >= 10) {
            System.out.println("SIZE IS OUT OF RANGE. INVALID ENTRY");
            return;
        }

        int A[][] = new int[M][N];

        // Input
        for (int i = 1; i < M; i++)
            for (int j = 1; j < N; j++)
                A[i][j] = (int)(Math.random()*10+1);
            for (int x = 1; x < A.length; x++) {
    for ( int y = 1; y < A.length; y++) {
        System.out.print(A[x][y] + " ");
    }
    System.out.println();
}


        // Rotate rows upward
        int R[][] = new int[M][N];
        for (int i = 1; i < M - 1; i++)
            R[i] = A[i + 1];
        R[M - 1] = A[0];

        // Display
        System.out.println("FORMED MATRIX AFTER ROTATING:");
        int max = R[0][0], r = 0, c = 0;

        for (int i = 1; i < M; i++) {
            for (int j = 1; j < N; j++) {
                System.out.print(R[i][j] + "\t");
                if (R[i][j] > max) {
                    max = R[i][j];
                    r = i;
                    c = j;
                }
            }
            System.out.println();
        }

        System.out.println("Highest element: " + max +
                           "   ( Row: " + r + "   and   Column: " + c + " )");
    }
}
