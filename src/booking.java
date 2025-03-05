import java.io.*;
import java.util.ArrayList;
import java.util.List;
public abstract class booking {
    public abstract void passengerBook();
    // Method to save booking details to a file
    public static void saveBooking(String loggedInUserId, String password, int flightNumber, String seatClass, String seatName) {
        // Define file name where bookings will be saved
        String fileName = "bookings.txt";

        // Create a Date object to store current date and time
        // Create the file object to check its length
        File file = new File(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            // Check if the file is empty and write the header if needed
            if (file.length() == 0) {  // File is empty
                writer.write("LoggedInUserId|Password|FlightNumber|SeatClass|SeatName\n");  // Write the header with a newline
            }
            // Format the data to be written to the file
            String bookingDetails = String.format("%s|%s|%s|%s|%s\n",
                    loggedInUserId, password, flightNumber, seatClass, seatName);

            // Write booking details to the file
            writer.write(bookingDetails);
            System.out.println("Booking saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving booking: " + e.getMessage());
        }
    }


    public static void displayInfo(String loggedInPassengerId, String loggedInPassengerPass,String filePath) throws IOException {
        // Load all bookings from the file
        List<String> bookings = loadBookings();
        if (bookings == null || bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        boolean bookingFound = false;

        // Iterate through the bookings and display only the ones for the logged-in user
        for (String booking : bookings) {
            String[] details = booking.split("\\|");

            // Check if the booking belongs to the logged-in user by comparing the ID and password
            if (details[0].equals(loggedInPassengerId) && details[1].equals(loggedInPassengerPass)) {
                bookingFound = true;
                // Load existing users from file
                List<User> users = User.readUserData(filePath);
                System.out.println("Booking for Flight " + details[2]);
                System.out.println("Seat Class: " + details[3]);
                System.out.println("Seat Name: " + details[4]);
                for(User newuser:users){
                    if(loggedInPassengerId.equals(newuser.getid())){
                        System.out.println("Passenger ID: "+loggedInPassengerId);
                        System.out.print("Name:"+newuser.getfirstname()+" "+newuser.getlastname());
                    }
                }

            }
        }

        if (!bookingFound) {
            System.out.println("No bookings found for the provided user ID and password.");
        }
    }


    // Method to load booking details from the file
    public static List<String> loadBookings() {
        String fileName = "bookings.txt";
        List<String> bookings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine(); // Read the header line and skip it
            if (line == null) {
                System.out.println("No bookings found.");
                return bookings; // Return an empty list instead of null
            }

            System.out.println("Bookings:");
            while ((line = reader.readLine()) != null) {
                bookings.add(line); // Add each booking line to the list
                String[] bookingDetails = line.split("\\|"); // Split each line into details

                // Print the details in a formatted manner
//                System.out.printf("User ID: %s, Flight Number: %s, Seat Class: %s, Seat Name: %s%n",
//                        bookingDetails[0], bookingDetails[2], bookingDetails[3], bookingDetails[4]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Booking file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading booking file: " + e.getMessage());
        }

        return bookings; // Return the list of bookings
    }


    public abstract void cancelBooking(String loggedInPassengerId, String loggedInPassengerPass);

}
