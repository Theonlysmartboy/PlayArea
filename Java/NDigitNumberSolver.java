import java.util.Scanner;

// Generalized solver for numbers with N digits based on digit-sum and reverse-difference rules.
public class NDigitNumberSolver {
    public static void main(String[] args) {
        // Read inputs from the user.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of digits: ");
        int digits = scanner.nextInt();
        System.out.print("Enter required sum of digits: ");
        int requiredSum = scanner.nextInt();
        System.out.print("Enter how much the reversed number exceeds the original: ");
        int requiredDiff = scanner.nextInt();
        // Compute the smallest and largest N-digit numbers.
        int start = (int) Math.pow(10, digits - 1);
        int end = (int) Math.pow(10, digits) - 1;
        boolean found = false;
        System.out.println("\nMatching numbers:");
        for (int num = start; num <= end; num++) {
            // Check the sum-of-digits rule first to skip non-matches quickly.
            if (sumOfDigits(num) == requiredSum) {
                int reversed = reverseNumber(num);
                // Then check the reverse-difference rule.
                if (reversed - num == requiredDiff) {
                    System.out.println("Original: " + num + " | Reversed: " + reversed);
                    found = true;
                }
            }
        }
        // Report if nothing matched.
        if (!found) {
            System.out.println("No matching numbers found.");
        }
        // Clean up input.
        scanner.close();
    }

    // Adds up all digits of a non-negative integer.
    private static int sumOfDigits(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    // Builds the reversed digits of a non-negative integer.
    private static int reverseNumber(int num) {
        int reversed = 0;
        while (num > 0) {
            reversed = reversed * 10 + (num % 10);
            num /= 10;
        }
        return reversed;
    }
}
