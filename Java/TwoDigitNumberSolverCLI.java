import java.util.Scanner;

// Command-line program that finds two-digit numbers matching user-provided rules.
public class TwoDigitNumberSolverCLI {
    public static void main(String[] args) {
        // Set up input from the user.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the sum of digits: ");
        int sumDigits = scanner.nextInt();
        System.out.print("Enter how much the reversed number exceeds the original: ");
        int diff = scanner.nextInt();

        // Search through all two-digit numbers and print any that match.
        System.out.println("\nMatching two-digit numbers:");
        boolean found = false;
        for (int num = 10; num <= 99; num++) {
            // Split the number into tens and units digits.
            int tens = num / 10;
            int units = num % 10;
            // Check both rules: digit sum and reversed difference.
            if ((tens + units == sumDigits) && ((units * 10 + tens) - num == diff)) {
                System.out.println("Original number: " + num + ", Reversed number: " + (units * 10 + tens));
                found = true;
            }
        }
        // Let the user know if nothing matched.
        if (!found) {
            System.out.println("No matching numbers found.");
        }
        // Always close the scanner when done with input.
        scanner.close();
    }
}
