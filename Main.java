//Importing needed classes/libraries
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    // Creating lists to hold different types of food
    private static ArrayList<Food> proteins = new ArrayList<>();
    private static ArrayList<Food> carbohydrates = new ArrayList<>();
    private static ArrayList<Food> fatsAndOils = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Getting user inputs
        System.out.println("Enter your age: ");
        int userAge = scanner.nextInt();
        System.out.println("Enter sex('M' or 'F'): ");
        char userSex = scanner.next().charAt(0);
        scanner.nextLine();
        System.out.println("Enter your goal (cut/bulk/maintain): ");
        String userGoal = scanner.nextLine();
        System.out.println("Enter your activity level (Sedentary/Lightly Active/Highly Active): ");
        String activityLevel = scanner.nextLine();
        System.out.println("Enter your weight in kilograms: ");
        double userWeight = scanner.nextDouble();
        System.out.println("Enter your height in centimeters: ");
        double userHeight = scanner.nextDouble();
        double weeklyFatLossTarget = 0;
        double MonthlyGainTarget = 0;

        if (userGoal.equals("cut")) {
            System.out.println("Enter your weekly fat loss target(Between 0.005(0.5%) and 0.0075(0.75%)): ");
            weeklyFatLossTarget = scanner.nextDouble(); }
        else if (userGoal.equals("bulk")) {
            System.out.println("\nFor monthly gain rate: ");
            System.out.println("Beginner: 2% (Totally new to training)-Enter 0.02");
            System.out.println("Novice: 1.5% (Still able to progress most training loads in the gym on a week to week basis)-Enter 0.015");
            System.out.println("Intermediate: 1% (Able to progress most training loads in the gym on a month to month basis)- Enter 0.01");
            System.out.println("Advanced: 0.5% (Progress is evident only when viewed over multiple months or a year)- Enter 0.005\n");
            System.out.println("Enter your monthly muscle gain target: ");
            MonthlyGainTarget = scanner.nextDouble(); }
            
        User this_user = new User(userAge, userSex, userGoal, activityLevel, userWeight, userHeight, weeklyFatLossTarget, MonthlyGainTarget);
        double intake = this_user.calculateCalories();

        // Reading food data from CSV file
        readFoodFromCSV("db.csv");

        // Get and print a random food combination
        getRandomFoodCombination(intake, this_user);
    }

    // A Method to read food data from CSV file and create instances of Food
    private static void readFoodFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Splitting the line into food name and food group
                String[] parts = line.split(",\\s*");
                String foodName = parts[0].replace("'", "").trim();
                String foodGroup = parts[1].replace("'", "").trim();

                // Creating food instances based on the food group
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

    //A  Method to get and print a random food combination
    private static void getRandomFoodCombination(double intake, User this_user) {
        Random random = new Random();

        // Getting a random food from each category
        Proteins randomProtein = (Proteins) proteins.get(random.nextInt(proteins.size()));
        Carbohydrates randomCarb = (Carbohydrates) carbohydrates.get(random.nextInt(carbohydrates.size()));
        FatsAndOils randomFat = (FatsAndOils) fatsAndOils.get(random.nextInt(fatsAndOils.size()));

        // Printing the random food combination
        if (this_user.userGoal.equals("bulk")) {
        System.out.println("Random food combination (calories):");
        System.out.println("Protein: " + Proteins.calculateproteinBulkIntake(this_user.userWeight) + " " + randomProtein.getName());
        System.out.println("Fat/Oil: " + FatsAndOils.calculateFatBulkIntake(this_user.calculateTDEE(this_user.activityLevel)) + " " + randomFat.getName());
        System.out.println("Carbohydrate: " + Carbohydrates.calculateCarbIntake(this_user.calculateTDEE(this_user.activityLevel), Proteins.calculateproteinBulkIntake(this_user.userWeight), FatsAndOils.calculateFatBulkIntake(this_user.calculateTDEE(this_user.activityLevel))) + " " + randomCarb.getName()); }

        else if (this_user.userGoal.equals("cut")) {
        System.out.println("Random food combination (calories):");
        System.out.println("Protein: " + Proteins.calculateproteinCutIntake(this_user.userWeight) + " " + randomProtein.getName());
        System.out.println("Fat/Oil: " + FatsAndOils.calculateFatCutIntake(this_user.calculateTDEE(this_user.activityLevel)) + " " + randomFat.getName()); 
        System.out.println("Carbohydrate: " + Carbohydrates.calculateCarbIntake(this_user.calculateTDEE(this_user.activityLevel), Proteins.calculateproteinCutIntake(this_user.userWeight), FatsAndOils.calculateFatCutIntake(this_user.calculateTDEE(this_user.activityLevel))) + " " + randomCarb.getName()); }

        else {
        System.out.println("Random food combination (calories):");
        System.out.println("Protein: " + Proteins.calculateproteinMaintainIntake(this_user.userWeight) + " " + randomProtein.getName());
        System.out.println("Fat/Oil: " + FatsAndOils.calculateFatMaintainIntake(this_user.calculateTDEE(this_user.activityLevel)) + " " + randomFat.getName()); 
        System.out.println("Carbohydrate: " + Carbohydrates.calculateCarbIntake(this_user.calculateTDEE(this_user.activityLevel), Proteins.calculateproteinMaintainIntake(this_user.userWeight), FatsAndOils.calculateFatMaintainIntake(this_user.calculateTDEE(this_user.activityLevel))) + " " + randomCarb.getName());}
    }
}
