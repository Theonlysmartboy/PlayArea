import java.util.Scanner;

public class AgeCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input
        System.out.print("Enter the sum of their ages: ");
        double sum = sc.nextDouble();

        System.out.print("Enter the ratio of elder brother's age to Njeri's age: ");
        double ratio = sc.nextDouble(); // e.g., 2 for "Njeri is twice as young"

        // Calculate ages
        double njeriAge = sum / (ratio + 1);      // n = sum / (ratio + 1)
        double brotherAge = sum - njeriAge;       // b = sum - n

        // Njeri's age in 10 years
        double njeriFuture = njeriAge + 10;

        // Output
        System.out.println("Current age of Njeri: " + njeriAge);
        System.out.println("Current age of elder brother: " + brotherAge);
        System.out.println("Njeri's age in 10 years: " + njeriFuture);
    }
}