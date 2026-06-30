import java.util.Scanner;

public class SimpleMeanCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter numbers separated by commas: ");
        String[] parts = scanner.nextLine().split(",");
        
        double sum = 0;
        int count = 0;
        
        for (String part : parts) {
            sum += Double.parseDouble(part.trim());
            count++;
        }
        
        System.out.printf("Mean = %.2f\n", sum / count);
        scanner.close();
    }
}