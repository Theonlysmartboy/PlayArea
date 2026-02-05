// Prints two-digit numbers that meet a fixed set of conditions.
public class TwoDigitNumberSolver {
    public static void main(String[] args) {
        // Try every two-digit number.
        for (int num = 10; num <= 99; num++) {
            // Extract digits.
            int tens = num / 10;
            int units = num % 10;
            // Condition 1: sum of digits is 10
            if (tens + units != 10) {
                continue;
            }
            // Condition 2: reversed number exceeds original by 18
            int reversed = units * 10 + tens;
            if (reversed - num == 18) {
                System.out.println("Original number: " + num + ", Reversed number: " + reversed);
            }
        }
    }
}
