import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Payment {

    private  static  double totalCost;
    private String paymentMethod;
    private boolean paymentStatus;
    private List<String> additionalServices;
    private Random random;
    private int paymentId;

    // Constructor
    public Payment() {
        this.additionalServices = new ArrayList<>();
        this.totalCost = 0.0;
        this.paymentStatus = false;
        this.random = new Random();
        this.paymentId = 100000 + random.nextInt(900000); // Generate a unique ID
    }


    public static void Totalcost(double ticketPrice, double baggagePrice, List<String> services) {
        double TCost=ticketPrice;
        double BCost=baggagePrice;
        double servicesCost = calculateServicesCost(services);

        totalCost = TCost +BCost+servicesCost ;
    }

    // Static method to calculate the cost of additional services
    public static double calculateServicesCost(List<String> services) {
        double servicesCost = 0.0;
        for (String service : services) {
            if (service.equalsIgnoreCase("Basic Internet Packages")) {
                servicesCost += 500.0;
            } else if (service.equalsIgnoreCase("Insurance")) {
                servicesCost += 2000.0;

            }
            else if (service.equalsIgnoreCase("Streaming Add-ons")) {
                servicesCost += 2500.0;

            }
            else if (service.equalsIgnoreCase("all of them")) {
                servicesCost += 5000.0;
            }
        }
        return servicesCost;
    }

    // Add an additional service
    public void addAdditionalService(String service) {
        additionalServices.add(service);
    }

    // Make a payment
    public void makePayment(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        System.out.println("Processing payment via " + paymentMethod + "...");
        if (paymentMethod.equalsIgnoreCase("Credit Card") ||
                paymentMethod.equalsIgnoreCase("Debit Card") ||
                paymentMethod.equalsIgnoreCase("Digital Wallet")) {
            this.paymentStatus = true;

            System.out.println("Payment Successful.");

        } else {
            this.paymentStatus = false;
            System.out.println("Payment Failed.");
        }
    }


    public double getTotalCost() {
        return totalCost;
    }

    public boolean getPaymentStatus() {
        return paymentStatus;
    }

    public List<String> getAdditionalServices() {
        return additionalServices;
    }

    public int getPaymentId() {
        return paymentId;
    }

    // Save payment statuses to file
    public static void saveStatus(ArrayList<Payment> payments) {
        File file = new File("Payment_status.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("Payment ID | Status");
            for (Payment payment : payments) {
                writer.printf("%d | %s%n",
                        payment.getPaymentId(),
                        payment.getPaymentStatus() ? "Successful" : "Failed");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
