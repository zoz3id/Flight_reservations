import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

import java.util.List;
import java.util.ArrayList;


public class AdmitBooking extends booking {

    private Payment payment;
    private Flight selectedFlight; // Selected flight
    private String seatClass;// Selected seat class
    private String selectedSeatName; // Store the booked seat name

    public String getSelectedSeatName() {
        return selectedSeatName; // Return the selected seat name
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    public void setSelectedFlight(Flight selectedFlight) {
        this.selectedFlight = selectedFlight;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    // Constructor to initialize dependencies
    public AdmitBooking(Payment payment, Flight selectedFlight) {
        this.payment = payment;
        this.selectedFlight = selectedFlight;
    }


    @Override
    public void passengerBook() {
        Scanner scanner = new Scanner(System.in);

        // Ensure payment is verified
        if (!payment.getPaymentStatus()) {
            System.out.println("Payment not verified. Please complete the payment before booking a seat.");
            return;
        }

        if (selectedFlight == null) {
            System.out.println("No flight selected. Cannot proceed with booking.");
            return;
        }




        // Seat selection
        String seatName = "";
        while (true) {
            System.out.print("Enter the seat name you want to book: ");
            seatName = scanner.nextLine().trim();

            if (selectedFlight.bookSeat(seatName, seatClass)) {
                System.out.println("Seat " + seatName + " booked successfully!");
                selectedFlight.saveSeatsToFile(); // Save booking
                selectedSeatName = seatName; // Store the seat name
                break;
            } else {
                System.out.println("Seat not available or already booked. Please choose another seat.");
            }
        }
    }

    // Method to cancel booking
    public void cancelBooking(String loggedInPassengerId, String loggedInPassengerPass) {
        Scanner scanner = new Scanner(System.in);

        // Load all bookings using loadBookings method
        List<String> bookings = loadBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings available to cancel.");
            return;
        }

        boolean bookingFound = false;

        // Search for the booking based on the provided user ID and password
        for (int i = 0; i < bookings.size(); i++) {
            String[] details = bookings.get(i).split("\\|");

            if (details[0].equals(loggedInPassengerId) && details[1].equals(loggedInPassengerPass)) {
                System.out.println("Booking found for user: " + loggedInPassengerId);

                // Ask for flight details (flight number, seat class, and seat name)
                System.out.print("Enter the flight number for the booking to cancel: ");
                int flightNumber = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.print("Enter the seat class for the booking to cancel (Economy, Economy Plus, First Class): ");
                String seatClass = scanner.nextLine().trim();
                System.out.print("Enter the seat name for the booking to cancel: ");
                String seatName = scanner.nextLine().trim();

                // Check if this booking matches the input details
                if (Integer.parseInt(details[2]) == flightNumber && details[3].equalsIgnoreCase(seatClass) && details[4].equalsIgnoreCase(seatName)) {
                    // Confirm cancellation
                    System.out.print("Are you sure you want to cancel the booking for seat " + seatName + "? (yes/no): ");
                    String confirmation = scanner.nextLine().trim();

                    if (confirmation.equalsIgnoreCase("yes")) {
                        // Remove the booking
                        bookings.remove(i);

                        // Save the updated bookings to the file
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bookings.txt"))) {
                            for (String updatedBooking : bookings) {
                                writer.write(updatedBooking + "\n");
                            }
                        } catch (IOException e) {
                            System.out.println("Error writing updated bookings file: " + e.getMessage());
                            return;
                        }

                        // Update seat availability and save changes
                        selectedFlight.markSeatAsAvailable(seatClass, seatName);
                        selectedFlight.saveSeatsToFile();

                        // Clear the seat selection
                        selectedSeatName = null;

                        System.out.println("Booking for seat " + seatName + " has been successfully canceled.");
                    } else {
                        System.out.println("Booking cancellation aborted.");
                    }
                    bookingFound = true;
                    break;
                }
            }
        }

        // If no matching booking was found
        if (!bookingFound) {
            System.out.println("No matching booking found for the provided details.");
        }
    }


}

