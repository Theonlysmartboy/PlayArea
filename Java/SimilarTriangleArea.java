import java.util.Scanner;

public class SimilarTriangleArea {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=========================================");
        System.out.println("  SIMILAR TRIANGLE AREA CALCULATOR");
        System.out.println("=========================================");
        System.out.println("This calculates the area of one triangle");
        System.out.println("given the side ratio and the other area.");
        System.out.println("-----------------------------------------");
        
        try {
            // Get the side ratio
            System.out.print("Enter side ratio (small:large) as two numbers separated by a comma: ");
            System.out.println("\nExample: 2, 5");
            System.out.print("Your ratio: ");
            String ratioInput = scanner.nextLine();
            String[] ratioParts = ratioInput.split(",");
            
            double smallRatio = Double.parseDouble(ratioParts[0].trim());
            double largeRatio = Double.parseDouble(ratioParts[1].trim());
            
            // Get which triangle's area is known
            System.out.println("\nWhich triangle's area do you know?");
            System.out.println("1. Smaller triangle");
            System.out.println("2. Larger triangle");
            System.out.print("Enter 1 or 2: ");
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            // Get the known area
            System.out.print("\nEnter the known area in cm²: ");
            double knownArea = Double.parseDouble(scanner.nextLine().trim());
            
            double unknownArea;
            String whichTriangle;
            
            // Calculate the unknown area
            if (choice == 1) {
                // We know the smaller area, find the larger
                unknownArea = knownArea * Math.pow(largeRatio / smallRatio, 2);
                whichTriangle = "larger";
            } else {
                // We know the larger area, find the smaller
                unknownArea = knownArea * Math.pow(smallRatio / largeRatio, 2);
                whichTriangle = "smaller";
            }
            
            // Display results
            System.out.println("\n=========================================");
            System.out.println("              RESULTS");
            System.out.println("=========================================");
            System.out.printf("Side ratio (small : large)  =  %.0f : %.0f\n", smallRatio, largeRatio);
            System.out.printf("Area ratio (small : large)  =  %.0f : %.0f\n", 
                              Math.pow(smallRatio, 2), Math.pow(largeRatio, 2));
            System.out.println("-----------------------------------------");
            System.out.printf("Area of " + whichTriangle + " triangle  =  %.2f cm²\n", unknownArea);
            System.out.println("=========================================");
            
            // Show the calculation steps
            System.out.println("\n--- Calculation Steps ---");
            if (choice == 1) {
                System.out.printf("1. Area ratio = (%.0f/%.0f)² = %.4f\n", 
                                  largeRatio, smallRatio, Math.pow(largeRatio/smallRatio, 2));
                System.out.printf("2. Area(large) = %.2f × %.4f\n", knownArea, Math.pow(largeRatio/smallRatio, 2));
                System.out.printf("3. Area(large) = %.2f cm²\n", unknownArea);
            } else {
                System.out.printf("1. Area ratio = (%.0f/%.0f)² = %.4f\n", 
                                  smallRatio, largeRatio, Math.pow(smallRatio/largeRatio, 2));
                System.out.printf("2. Area(small) = %.2f × %.4f\n", knownArea, Math.pow(smallRatio/largeRatio, 2));
                System.out.printf("3. Area(small) = %.2f cm²\n", unknownArea);
            }
            
        } catch (NumberFormatException e) {
            System.out.println("\nError: Please enter valid numbers only.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\nError: Please enter the ratio as two numbers separated by a comma (e.g., 2, 5).");
        }
        
        scanner.close();
    }
}