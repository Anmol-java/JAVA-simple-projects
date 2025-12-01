import java.util.ArrayList;

public class GoldBasch {
    
    // Function to check if a number is prime
    public static boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    // Function to find and print the Goldbach pair for the given even number
    public static void GoldBasch(int n) {
        if (n <= 2 || n % 2 != 0) {
            System.out.println("Number must be even and greater than 2.");
            return;
        }

        // Check for all pairs of prime numbers that sum up to n
        for (int i = 2; i <= n / 2; i++) {
            if (isPrime(i) && isPrime(n - i)) {
                System.out.println(n + " = " + i + " + " + (n - i));
                return;
            }
        }

        // If no pair found (theoretically shouldn't happen for even numbers > 2)
        System.out.println("No prime pair found for " + n);
    }

    public static void main(String[] args) {
        // Test with some even numbers
        int[] testNumbers = {4, 6, 8, 10, 12, 20, 28};
        
        for (int num : testNumbers) {
            GoldBasch(num);
        }
    }
}
