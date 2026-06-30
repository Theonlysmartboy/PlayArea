import java.util.Scanner;

public class MeanCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=================================");
        System.out.println("       MEAN CALCULATOR");
        System.out.println("=================================");
        System.out.println("Enter numbers separated by commas");
        System.out.println("Example: 12, 15, 18, 10, 20, 25");
        System.out.println("---------------------------------");
        System.out.print("Your numbers: ");
        
        // Read the entire line
        String input = scanner.nextLine().trim();
        
        try {
            // Split by comma
            String[] parts = input.split(",");
            
            // Convert to numbers and calculate
            double sum = 0;
            int count = 0;
            StringBuilder numbersList = new StringBuilder();
            
            for (String part : parts) {
                // Trim whitespace from each part
                String trimmed = part.trim();
                if (!trimmed.isEmpty()) {
                    double number = Double.parseDouble(trimmed);
                    sum += number;
                    count++;
                    
                    // Build the display string
                    if (numbersList.length() > 0) {
                        numbersList.append(", ");
                    }
                    numbersList.append(trimmed);
                }
            }
            
            if (count == 0) {
                System.out.println("\nError: No valid numbers entered.");
            } else {
                double mean = sum / count;
                
                // Display results
                System.out.println("\n=================================");
                System.out.println("           RESULTS");
                System.out.println("=================================");
                System.out.println("Numbers entered: " + numbersList.toString());
                System.out.println("Count of numbers: " + count);
                System.out.println("Sum: " + (sum % 1 == 0 ? String.valueOf((long)sum) : String.valueOf(sum)));
                System.out.println("---------------------------------");
                System.out.printf("MEAN = %.2f\n", mean);
                System.out.println("=================================");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("\nError: Invalid input. Please enter only numbers separated by commas.");
            System.out.println("Example: 12, 15, 18, 10, 20, 25");
        }
        
        scanner.close();
    }
}