import java.util.Scanner;

public class Baggage {

    private String baggageId;
    private double weight;
    private String dimensions;
    private String type;
    private boolean isOverweight;
    private double extraFee;


    public Baggage(String baggageId, double weight, String dimensions, String type) {
        this.baggageId = baggageId;
        this.weight = weight;
        this.dimensions = dimensions;
        this.type = type;
        this.isOverweight = false;
        this.extraFee = 0.0;
    }

    public String getBaggageId() {
        return baggageId;
    }

    public void setBaggageId(String baggageId) {
        this.baggageId = baggageId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        checkWeightLimit();
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isOverweight() {
        return isOverweight;
    }

    public double getExtraFee() {
        return extraFee;
    }


    public void checkWeightLimit() {
        double weightLimit = 23.0;
        double overweightFeePerKg = 15.0;

        if (weight > weightLimit) {
            isOverweight = true;
            extraFee = (weight - weightLimit) * overweightFeePerKg;

        } else if(weight <= 0) {
            System.out.println("Invalid weight");
        } else {
            isOverweight = false;
            extraFee = 0.0;
        }
    }

    public void displayBaggageDetails() {
        System.out.println("Baggage Details:");
        System.out.println("Baggage ID: " + baggageId);
        System.out.println("Weight: " + weight + " kg");
        System.out.println("Dimensions: " + dimensions);
        System.out.println("Type: " + type);
        System.out.println("Overweight: " + (isOverweight ? "Yes" : "No"));
        if (isOverweight) {
            System.out.println("Extra Fee: $" + extraFee);
        }
    }

    // Static method to create baggage
    public static Baggage createBaggage(Scanner scanner) {
        System.out.println("Enter Baggage ID: ");
        String baggageId = scanner.nextLine();

        System.out.println("Enter Weight of the baggage (in kg): ");
        double weight = scanner.nextDouble();
        scanner.nextLine(); // Consume leftover newline

        System.out.println("Enter Dimensions of the baggage (e.g., 50x40x30 cm): ");
        String dimensions = scanner.nextLine();

        System.out.println("Enter Type of the baggage (e.g., Carry-on, Checked, Fragile): ");
        String type = scanner.nextLine();

        // Create and return a new Baggage object
        Baggage baggage = new Baggage(baggageId, weight, dimensions, type);
        baggage.checkWeightLimit(); // Check for overweight status
        return baggage;
    }
}

