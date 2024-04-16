import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    // Create lists to hold different types of food
    private static ArrayList<Food> proteins = new ArrayList<>();
    private static ArrayList<Food> carbohydrates = new ArrayList<>();
    private static ArrayList<Food> fatsAndOils = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get user inputs
        System.out.println("Enter your age: ");
        int userAge = scanner.nextInt();
        System.out.println("Enter sex('M' or 'F'): ");
        char userSex = scanner.next().charAt(0);
        System.out.println("Enter your goal (cut/bulk/maintain): ");
        String userGoal = scanner.next();
        System.out.println("Enter your activity level (Sedentary/Lightly Active/Highly Active): ");
        String activityLevel = scanner.next();
        System.out.println("Enter your weight in kilograms: ");
        double userWeight = scanner.nextDouble();
        System.out.println("Enter your height in centimeters: ");
        double userHeight = scanner.nextDouble();
        System.out.println("Enter your weekly fat loss target(Between 0.5% and 0.75%): ");
        double weeklyFatLossTarget = scanner.nextDouble();
        System.out.println("For monthly gain rate: ");
        System.out.println("Beginner: 2% (Totally new to training)");
        System.out.println("Novice: 1.5% (Still able to progress most training loads in the gym on a week to week basis)");
        System.out.println("Intermediate: 1% (Able to progress most training loads in the gym on a month to month basis)");
        System.out.println("Advanced: 0.5% (Progress is evident only when viewed over multiple months or a year)");
        System.out.println("Enter monthly gain target: ");
        double MonthlyGainTarget = scanner.nextDouble();

        // Read food data from CSV file
        readFoodFromCSV("path_to_csv_file.csv");

        // Get and print a random food combination
        getRandomFoodCombination();
    }

    // Method to read food data from CSV file and create instances of Food
    private static void readFoodFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into food name and food group
                String[] parts = line.split(",\\s*");
                String foodName = parts[0].replace("'", "").trim();
                String foodGroup = parts[1].replace("'", "").trim();

                // Create food instances based on the food group
                switch (foodGroup) {
                    case "Protein":
                        proteins.add(new Proteins(foodName, 0));  // Assuming 0 calories for now, adjust as needed
                        break;
                    case "Carbohydrates":
                        carbohydrates.add(new Carbohydrates(foodName, 0));  // Assuming 0 calories for now, adjust as needed
                        break;
                    case "Fats and Oils":
                        fatsAndOils.add(new FatsAndOils(foodName, 0));  // Assuming 0 calories for now, adjust as needed
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    // Method to get and print a random food combination
    private static void getRandomFoodCombination() {
        Random random = new Random();

        // Get a random food from each category
        Proteins randomProtein = (Proteins) proteins.get(random.nextInt(proteins.size()));
        Carbohydrates randomCarb = (Carbohydrates) carbohydrates.get(random.nextInt(carbohydrates.size()));
        FatsAndOils randomFat = (FatsAndOils) fatsAndOils.get(random.nextInt(fatsAndOils.size()));

        // Print the random food combination
        System.out.println("Random food combination:");
        System.out.println("Protein: " + randomProtein.getName());
        System.out.println("Carbohydrate: " + randomCarb.getName());
        System.out.println("Fat/Oil: " + randomFat.getName());
    }
}