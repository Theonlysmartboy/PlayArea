import java.util.Scanner;
public class NDigitNumberSolver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of digits: ");
        int digits = scanner.nextInt();
        System.out.print("Enter required sum of digits: ");
        int requiredSum = scanner.nextInt();
        System.out.print("Enter how much the reversed number exceeds the original: ");
        int requiredDiff = scanner.nextInt();
        int start = (int) Math.pow(10, digits - 1);
        int end = (int) Math.pow(10, digits) - 1;
        boolean found = false;
        System.out.println("\nMatching numbers:");
        for (int num = start; num <= end; num++) {
            if (sumOfDigits(num) == requiredSum) {
                int reversed = reverseNumber(num);
                if (reversed - num == requiredDiff) {
                    System.out.println("Original: " + num + " | Reversed: " + reversed);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No matching numbers found.");
        }
        scanner.close();
    }
    private static int sumOfDigits(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
    private static int reverseNumber(int num) {
        int reversed = 0;
        while (num > 0) {
            reversed = reversed * 10 + (num % 10);
            num /= 10;
        }
        return reversed;
    }
}
