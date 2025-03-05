import java.io.*;
import java.util.*;

public class Main {

    static  double tp;
    static  double bp;
    public static void main(String[] args) throws IOException {
        //admin and user files
        String userFilePath = "users.txt";
        String adminFilePath = "admins.txt";
        // Load existing users and admins from files
        List<User> users = User.readUserData(userFilePath);
        List<Admin> admins = Admin.readAdminData(adminFilePath);

        Scanner scanner = new Scanner(System.in);
        Payment payment = new Payment();
        // Ensure default flights are saved to the file if not already present1
        Flight.saveDefaultFlightsToFile();
        AdmitBooking admitBooking = null;
        String loggedInPassengerId; // ال id بتاع ال user لو عمل login
        String loggedInPassengerPass;// ال password بتاع ال admin لو عمل login
        while (true) {
            System.out.println("1. User Sign Up");
            System.out.println("2. User Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choicee = scanner.nextInt();
            scanner.nextLine();

            if (choicee == 1) {
                User newUser = User.registerUser(scanner,userFilePath);
                    newUser.saveUserData(userFilePath);
                users.add(newUser);
                System.out.println("User registered successfully!");
            }
            else if (choicee == 2) {
                User user = User.loginUser(scanner, users);
                if (user != null) {
                loggedInPassengerId =user.getid();
                loggedInPassengerPass = user.getpassword();

                // Load flights from the file
                List<Flight> flights = loadFlightsFromFile();
                if (flights.isEmpty()) {
                    System.out.println("No flights available in the system.");
                    continue;
                }

                boolean ans = true;
                while (ans) {
                    System.out.println("\nWelcome to the Flight Booking System!");
                    System.out.println("1. View Available Flights");
                    System.out.println("2. Search Flights");
                    System.out.println("3. Book a Seat");
                    System.out.println("4. Cancel Booking");
                    System.out.println("5. View My Bookings");
                    System.out.println("6. View My Data");
                    System.out.println("7. Exit");
                    System.out.print("Enter your choice: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.println("\nAvailable Flights:");
                            for (Flight flight : flights) {
                                System.out.printf("Flight %d: %s to %s, Departure: %s, Arrival: %s%n",
                                        flight.getNumOfFlight(),
                                        flight.getLeavingAirport(),
                                        flight.getArrivalAirport(),
                                        flight.getLeavingTime(),
                                        flight.getArrivalTime());
                            }
                            break;

                        case 2:
                            Flight.searchFlights();
                            break;

                        case 3:
                            System.out.println("Booking a seat for Passenger ID: " + loggedInPassengerId);
                            Flight selectedFlight = Flight.flightSelect(flights.toArray(new Flight[0]));
                            // Ensure the user inputs a valid seat class
                            String seatClass = "";
                            while (true) {
                                System.out.print("Enter the seat class (Economy, Economy Plus, First Class): ");
                                seatClass = scanner.nextLine().trim();

                                if (seatClass.equalsIgnoreCase("Economy") || seatClass.equalsIgnoreCase("Economy Plus") || seatClass.equalsIgnoreCase("First Class")) {
                                    break;  // Exit the loop if the seat class is valid
                                } else {
                                    System.out.println("Invalid seat class. Please enter a valid class (Economy, Economy Plus, First Class).");
                                }
                            }

                            // Show available seats for the selected class
                            selectedFlight.displayAvailableSeats(seatClass);

                            //3mk zeyad eid
                            //  هسال يوزر لو هيعمل حموله زياده
                            System.out.println("Do you want to add baggage? (yes/no): ");
                            String response = scanner.nextLine().trim().toLowerCase();
                            if (response.equals("yes")) {
                                Baggage baggage = Baggage.createBaggage(scanner);
                                baggage.checkWeightLimit();
                                if (baggage.isOverweight()) {
                                    bp = baggage.getExtraFee();
                                    System.out.printf("Your baggage is overweight. An extra fee  will apply.%n" + bp);

                                }
                                System.out.println("Baggage added successfully.");
                            } else {
                                System.out.println("No baggage added.");
                            }

                            System.out.println("Add additional service (Basic Internet Packages, Insurance, Streaming Add-ons, all of them) OR 'Not' to stop:");
                            while (true) {
                                String service = scanner.nextLine();
                                if (service.equalsIgnoreCase("Not")) {
                                    break;
                                }
                                // Add service to the payment object
                                payment.addAdditionalService(service);
                            }
                            Payment.Totalcost(tp, bp, payment.getAdditionalServices());
                            System.out.println("Total cost: " + payment.getTotalCost());
                            System.out.print("Enter payment method (Credit Card /   Debit Card/   Digital Wallet): ");
                            String paymentMethod = scanner.nextLine();
                            payment.makePayment(paymentMethod);
                            //Proceed with seat booking if payment is successful
                            if (payment.getPaymentStatus()) {
                                admitBooking = new AdmitBooking(payment, selectedFlight);
                                admitBooking.setSeatClass(seatClass);
                                admitBooking.passengerBook();// Call the seat booking method
                                // Save the booking details
                                int flightNumber = selectedFlight.getNumOfFlight();  // Get the selected flight's number
                                String seatName = admitBooking.getSelectedSeatName();
                                booking.saveBooking(loggedInPassengerId, loggedInPassengerPass, flightNumber, seatClass, seatName);

                            } else {
                                System.out.println("Payment failed. Cannot proceed with booking.");
                            }

                            break;
                        case 4:
                            if (admitBooking.getSelectedSeatName() != null) { // Check if a seat is selected
                                admitBooking.cancelBooking(loggedInPassengerId, loggedInPassengerPass);
                            } else {
                                System.out.println("No booking found to cancel.");
                            }
                            break;

                        case 5:
                            booking.displayInfo(loggedInPassengerId, loggedInPassengerPass,userFilePath);
                            break;
                        case 6:
                            user.viewUserData();
                            break;
                        case 7:
                            ans = false; // Exit the loop
                            break;


                        default:
                            System.out.println("Invalid choice! Please try again.");

                    }
                }
                }
            }
            else if (choicee == 3) {
                boolean adminActive=false;
                Admin admin = Admin.loginAdmin(scanner, admins);
                if (admin != null) {
                    // Admin functionality
                    adminActive = true;
                    while (adminActive) {
                        System.out.println("\nAdmin Menu:");
                        System.out.println("1. Enter Flight Data");
                        System.out.println("2. Edit Flight");
                        System.out.println("3. Delete Flight");
                        System.out.println("4. View All Flights");
                        System.out.println("5. View All Saved Airports' data");
                        System.out.println("6. Add A New Admin");
                        System.out.println("7. Logout");
                        System.out.print("Choose an option: ");

                        int adminChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        switch (adminChoice) {
                            case 1:
                                // Admin enters flight data
                                Flight.enterFlightData();
                                break;
                            case 2:
                                Flight.editFlight();
                                break;
                            case 3:
                                Flight.deleteFlight();
                                break;

                            case 4:
                                // Display flights (loaded from the file)
                                List<Flight> flights = loadFlightsFromFile();
                                if (flights.isEmpty()) {
                                    System.out.println("No flights available in the system.");
                                } else {
                                    System.out.println("\nAvailable Flights:");
                                    for (Flight flight : flights) {
                                        System.out.printf("Flight %d: %s to %s, Departure: %s, Arrival: %s%n",
                                                flight.getNumOfFlight(),
                                                flight.getLeavingAirport(),
                                                flight.getArrivalAirport(),
                                                flight.getLeavingTime(),
                                                flight.getArrivalTime());
                                    }
                                }
                                break;

                            case 5 :
                                Airport_info.show_airports("Airports(2).txt");
                                break;
                            case 6:
                                Admin.registerAdmin(scanner,adminFilePath);
                                break;

                            case 7:
                                System.out.println("Logging out of Admin Panel...");
                                adminActive = false;
                                break;

                            default:
                                System.out.println("Invalid choice! Please try again.");
                        }
                    }
                }
            }
            else if (choicee == 4) {


                System.out.println("Goodbye!");
                break;
            }
            else {
                System.out.println("Invalid option. Please try again.");
            }
        }

//        // Create a list of payments  to save the payment status later
//        List<Payment> payments = new ArrayList<>(); // Create a new list to hold payments
//        payments.add(payment); // Add the current payment to the list
//        // Save the payment status to a file
//        Payment.saveStatus(new ArrayList<>(payments));

    }
    // Method to load flights from the file
    private static List<Flight> loadFlightsFromFile() {
        List<Flight> flights = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("flights.txt"))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] flightData = line.split("\\|");
                if (flightData.length >= 5) {
                    int flightNumber = Integer.parseInt(flightData[0]);
                    String leavingAirport = flightData[1];
                    String arrivalAirport = flightData[2];
                    String leavingTime = flightData[3];
                    String arrivalTime = flightData[4];

                    flights.add(new Flight(flightNumber, leavingAirport, arrivalAirport, leavingTime, arrivalTime));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Flight data file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading flight data: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing flight data: " + e.getMessage());
        }

        return flights;


    }
}