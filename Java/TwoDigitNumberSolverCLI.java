import java.util.Scanner;
public class TwoDigitNumberSolverCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the sum of digits: ");
        int sumDigits = scanner.nextInt();
        System.out.print("Enter how much the reversed number exceeds the original: ");
        int diff = scanner.nextInt();
        System.out.println("\nMatching two-digit numbers:");
        boolean found = false;
        for (int num = 10; num <= 99; num++) {
            int tens = num / 10;
            int units = num % 10;
            if ((tens + units == sumDigits) && ((units * 10 + tens) - num == diff)) {
                System.out.println("Original number: " + num + ", Reversed number: " + (units * 10 + tens));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching numbers found.");
        }
        scanner.close();
    }
}
