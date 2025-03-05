import java.io.*;
import java.util.*;

public class Flight {
    //attributes
    private int numOfFlight;
    private String leavingAirport;
    private String arrivalAirport;
    private String leavingTime;
    private String arrivalTime;
    private List<Seat> seats;
    ArrayList<Ticket> tickets = new ArrayList<>();

    // Constructor
    public Flight(int numOfFlight, String leavingAirport, String arrivalAirport, String leavingTime, String arrivalTime) {
        this.numOfFlight = numOfFlight;
        this.leavingAirport = leavingAirport;
        this.arrivalAirport = arrivalAirport;
        this.leavingTime = leavingTime;
        this.arrivalTime = arrivalTime;
        this.seats=generateSeats();
        this.tickets=Ticket.generate_ids(tickets,156);

    }
    // Getters and Setters

    public int getNumOfFlight() {
        return numOfFlight;
    }

    public void setNumOfFlight(int numOfFlight) {
        this.numOfFlight = numOfFlight;
    }

    public String getLeavingAirport() {
        return leavingAirport;
    }

    public void setLeavingAirport(String leavingAirport) {
        this.leavingAirport = leavingAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(String leavingTime) {
        this.leavingTime = leavingTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }


    // save default flights to
    public static void saveDefaultFlightsToFile() {
        Flight[] flights = {
                new Flight(101, "Cairo", "Dubai", "2024-12-10 10:00 AM", "2024-12-10 02:00 PM"),
                new Flight(102, "London", "New York", "2024-12-12 01:00 PM", "2024-12-12 05:00 PM"),
                new Flight(103, "Paris", "Berlin", "2024-12-13 03:00 PM", "2024-12-13 06:00 PM")
        };

        File file = new File("flights.txt");

        // if file not exist make it
        if (!file.exists() || file.length() == 0) {
            try (PrintWriter writer = new PrintWriter(new FileWriter("flights.txt", true))) {
                // headers
                writer.println("FlightNumber|LeavingAirport|ArrivalAirport|LeavingTime|ArrivalTime");

                // write all flights in file
                for (Flight flight : flights) {
                    writer.println(flight.getNumOfFlight() + "|" + flight.getLeavingAirport() + "|" + flight.getArrivalAirport() + "|" +
                            flight.getLeavingTime() + "|" + flight.getArrivalTime());
                }

                System.out.println("Default flights saved to flights.txt");
            } catch (IOException e) {
                System.out.println("An error occurred while saving default flights to file: " + e.getMessage());
            }
        } else {
            System.out.println("The file already contains data. Skipping default data saving.");
        }

    }

    //    function to add flights by admin
    public static void enterFlightData() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of flights you want to add: ");
        int numberOfFlights = scanner.nextInt();
        scanner.nextLine();

        Flight[] flights = new Flight[numberOfFlights];

        // add details of each flight
        for (int i = 0; i < numberOfFlights; i++) {
            System.out.println("Enter details for flight " + (i + 1) + ":");

            System.out.print("Flight Number: ");
            int flightNumber = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Leaving Airport: ");
            String leavingAirport = scanner.nextLine();

            System.out.print("Arrival Airport: ");
            String arrivalAirport = scanner.nextLine();

            System.out.print("Leaving Time: ");
            String leavingTime = scanner.nextLine();

            System.out.print("Arrival Time: ");
            String arrivalTime = scanner.nextLine();

            // make object of flight
            flights[i] = new Flight(flightNumber, leavingAirport, arrivalAirport, leavingTime, arrivalTime);
        }

        // save new flights
        try (PrintWriter writer = new PrintWriter(new FileWriter("flights.txt", true))) {
            for (Flight flight : flights) {
                // make sure data be saved
                String flightData = flight.getNumOfFlight() + "|" + flight.getLeavingAirport() + "|" + flight.getArrivalAirport() + "|" +
                        flight.getLeavingTime() + "|" + flight.getArrivalTime() ;
                writer.println(flightData);
                System.out.println("Writing to file: " + flightData);  // display data
            }
            System.out.println("Flights saved to flights.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving flights to file: " + e.getMessage());
        }
    }
    public static void editFlight() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the flight number you want to edit: ");
        int flightNumber = scanner.nextInt();
        scanner.nextLine();  // newline

        // Read flights from file
        List<String> flightData = new ArrayList<>();
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("flights.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                flightData.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading flight data: " + e.getMessage());
            return;
        }

        // Search for the flight to edit
        for (int i = 1; i < flightData.size(); i++) {  // Start from 1 to skip the header
            String[] flight = flightData.get(i).split("\\|");
            if (Integer.parseInt(flight[0]) == flightNumber) {
                found = true;
                System.out.println("Current details of the flight:");
                System.out.println("Flight Number: " + flight[0]);
                System.out.println("Leaving Airport: " + flight[1]);
                System.out.println("Arrival Airport: " + flight[2]);
                System.out.println("Leaving Time: " + flight[3]);
                System.out.println("Arrival Time: " + flight[4]);

                // Ask user to update each field
                System.out.print("Enter new leaving airport: ");
                flight[1] = scanner.nextLine();

                System.out.print("Enter new arrival airport: ");
                flight[2] = scanner.nextLine();

                System.out.print("Enter new leaving time: ");
                flight[3] = scanner.nextLine();

                System.out.print("Enter new arrival time: ");
                flight[4] = scanner.nextLine();

                // Update the flight data in the list
                flightData.set(i, String.join("|", flight));
                break;
            }
        }

        if (found) {
            // Write the updated data back to the file
            try (PrintWriter writer = new PrintWriter(new FileWriter("flights.txt"))) {
                for (String data : flightData) {
                    writer.println(data);
                }
                System.out.println("Flight details updated successfully.");
            } catch (IOException e) {
                System.out.println("Error writing updated flight data: " + e.getMessage());
            }
        } else {
            System.out.println("Flight not found.");
        }
    }
    public static void deleteFlight() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the flight number you want to delete: ");
        int flightNumber = scanner.nextInt();
        scanner.nextLine();  //  newline

        List<String> flightData = new ArrayList<>();
        boolean found = false;

        // Read the current flights from the file
        try (BufferedReader reader = new BufferedReader(new FileReader("flights.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                flightData.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading flight data: " + e.getMessage());
            return;
        }

        // Search and delete the flight
        for (int i = 1; i < flightData.size(); i++) {  // Start from 1 to skip the header
            String[] flight = flightData.get(i).split("\\|");
            if (Integer.parseInt(flight[0]) == flightNumber) {
                found = true;
                flightData.remove(i);  // Remove the flight
                break;
            }
        }

        if (found) {
            // Write the updated list of flights
            try (PrintWriter writer = new PrintWriter(new FileWriter("flights.txt"))) {
                for (String data : flightData) {
                    writer.println(data);
                }
                System.out.println("Flight deleted successfully.");
            } catch (IOException e) {
                System.out.println("Error writing updated flight data: " + e.getMessage());
            }
        } else {
            System.out.println("Flight not found.");
        }
    }


    // function to search flights
    public static void searchFlights() {
        Scanner scanner = new Scanner(System.in);

        while (true) {  // Loop for multiple searches
            // Search options
            System.out.println("Search by: ");
            System.out.println("1. Leaving Airport");
            System.out.println("2. Arrival Airport");
            System.out.println("3. Leaving and Arrival Airports");
            System.out.println("4. Leaving Time");
            System.out.println("5. Arrival Time");
            System.out.println("6. All Criteria");
            System.out.print("Choose the number for search option: ");
            int searchOption = scanner.nextInt();
            scanner.nextLine();  //  newline

            // Variables to search
            String from = "", to = "", leavingTime = "", arrivalTime = "";

            // Switch case
            switch (searchOption) {
                case 1:
                    System.out.println("Enter the Leaving Airport: ");
                    from = scanner.nextLine();
                    break;
                case 2:
                    System.out.println("Enter the Arrival Airport: ");
                    to = scanner.nextLine();
                    break;
                case 3:
                    System.out.println("Enter the Leaving Airport: ");
                    from = scanner.nextLine();
                    System.out.println("Enter the Arrival Airport: ");
                    to = scanner.nextLine();
                    break;
                case 4:
                    System.out.println("Enter the Leaving Time (e.g., 2024-12-10 10:00 AM or just the date, e.g., 2024-12-10): ");
                    leavingTime = scanner.nextLine();
                    break;
                case 5:
                    System.out.println("Enter the Arrival Time (e.g., 2024-12-10 10:00 AM or just the date, e.g., 2024-12-10): ");
                    arrivalTime = scanner.nextLine();
                    break;
                case 6:
                    System.out.println("Enter the Leaving Airport: ");
                    from = scanner.nextLine();
                    System.out.println("Enter the Arrival Airport: ");
                    to = scanner.nextLine();
                    System.out.println("Enter the Leaving Time (e.g., 2024-12-10 10:00 AM or just the date, e.g., 2024-12-10): ");
                    leavingTime = scanner.nextLine();
                    System.out.println("Enter the Arrival Time (e.g., 2024-12-10 10:00 AM or just the date, e.g., 2024-12-10): ");
                    arrivalTime = scanner.nextLine();
                    break;
                default:
                    System.out.println("Invalid option, exiting search.");
                    return;
            }

            // Search logic
            try (Scanner fileScanner = new Scanner(new File("flights.txt"))) {
                boolean flightFound = false; // Flag to check if any flight is found

                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine().trim();
                    if (line.isEmpty()) continue;

                    String[] flightData = line.split("\\|");
                    if (flightData.length != 5) continue;
                    if (flightData[0].equals("FlightNumber")) continue;  // Skip headers

                    boolean matches = true;

                    if (!from.isEmpty() && !flightData[1].equalsIgnoreCase(from)) {
                        matches = false;
                    }
                    if (!to.isEmpty() && !flightData[2].equalsIgnoreCase(to)) {
                        matches = false;
                    }
                    if (!leavingTime.isEmpty()) {
                        if (leavingTime.length() == 10) {
                            if (!flightData[3].startsWith(leavingTime)) matches = false;
                        } else {
                            if (!flightData[3].equalsIgnoreCase(leavingTime)) matches = false;
                        }
                    }
                    if (!arrivalTime.isEmpty()) {
                        if (arrivalTime.length() == 10) {
                            if (!flightData[4].startsWith(arrivalTime)) matches = false;
                        } else {
                            if (!flightData[4].equalsIgnoreCase(arrivalTime)) matches = false;
                        }
                    }

                    if (matches) {
                        System.out.println("Flight Number: " + flightData[0] +
                                ", Leaving Airport: " + flightData[1] +
                                ", Arrival Airport: " + flightData[2] +
                                ", Leaving Time: " + flightData[3] +
                                ", Arrival Time: " + flightData[4] );
                        flightFound = true;
                    }
                }

                if (!flightFound) {
                    System.out.println("No flights found matching the search criteria.");
                }

            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }

            // Asking the user if they want to search again
            System.out.println("Do you want to search again? (yes/no)");
            String answer = scanner.nextLine();
            if (!answer.equalsIgnoreCase("yes")) {
                break; // Exit loop if the user does not want to search again
            }
        }

        System.out.println("Thank you for using the flight search system.");
    }
    private List<Seat> generateSeats() {
        List<Seat> seatList = new ArrayList<>();
        String[] seatClasses = {"First Class", "Economy Plus", "Economy"};

        // Seat configuration: {startRow, endRow, rows, columns}
        Object[][] seatConfigs = {
                {1, 3, new char[]{'A', 'B', 'E', 'F'}, "First Class"},
                {7, 21, new char[]{'A', 'B', 'C', 'D', 'E', 'F'}, "Economy Plus"},
                {22, 38, new char[]{'A', 'B', 'C', 'D', 'E', 'F'}, "Economy"}
        };

        for (Object[] config : seatConfigs) {
            int startRow = (int) config[0];
            int endRow = (int) config[1];
            char[] rowLetters = (char[]) config[2];
            String seatClass = (String) config[3];

            for (int row = startRow; row <= endRow; row++) {
                for (char col : rowLetters) {
                    String seatName = row + String.valueOf(col);
                    seatList.add(new Seat(seatName, seatClass));
                }
            }
        }

        return seatList;
    }

    // Method to load seat data from the file
    public void loadSeatsFromFile() {
        File file = new File("seats_" + numOfFlight + ".txt");

        if (!file.exists()) {
            System.out.println("No saved seat data found for flight " + numOfFlight + ". Using default seat data.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Skip the header line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String seatName = parts[0];
                String seatClass = parts[1];
                boolean isAvailable = parts[2].equalsIgnoreCase("Available");

                for (Seat seat : seats) {
                    if (seat.getSeatName().equalsIgnoreCase(seatName) &&
                            seat.getSeatClass().equalsIgnoreCase(seatClass)) {
                        seat.setAvailable(isAvailable); // Update the seat's availability
                        break;
                    }
                }
            }
            System.out.println("Seat data loaded for flight " + numOfFlight);
        } catch (IOException e) {
            System.err.println("Error loading seat data: " + e.getMessage());
        }
    }


    // Display Available Seats
    public void displayAvailableSeats(String seatClass) {
        System.out.println("Available seats in " + seatClass + ":");
        boolean anyAvailable = false;
        int index;


        Ticket.saveTicketsToFile(tickets,numOfFlight);    //create file
        index=Ticket.status_check(seatClass,tickets,numOfFlight);

        if(index==-1){
            System.out.println("No Tickets Available");
        }

        else{
            tickets.get(index).calculate_fare(seatClass);


            //tickets.get(index).ticket_booked();
            //assigning seat details to ticket

            tickets.get(index).setBooked_seat_class(seatClass);
            Main.tp=tickets.get(index).getFare();

        }
        for (Seat seat : seats) {
            if (seat.getSeatClass().equalsIgnoreCase(seatClass) && seat.isAvailable()) {
                System.out.print(seat.getSeatName() + " ");
                anyAvailable = true;

            }
        }
        if(anyAvailable){

            System.out.println("\nThe Ticket Price is : "+tickets.get(index).getFare());

        }

        if (!anyAvailable) {
            System.out.println("No available seats in " + seatClass + ".");
        } else {
            System.out.println();
        }
    }

    // Book a Seat
    public boolean bookSeat(String seatName, String seatClass) {

        for (Seat seat : seats) {
            if (seat.getSeatName().equalsIgnoreCase(seatName) &&
                    seat.getSeatClass().equalsIgnoreCase(seatClass) &&
                    seat.isAvailable()) {
                seat.bookSeat();



                return true;
            }
        }
        return false;
    }

    // Method to mark a seat as available
    public void markSeatAsAvailable(String seatClass, String seatName) {
        // Find the seat based on seatClass and seatName
        for (Seat seat : seats) {
            if (seat.getSeatClass().equalsIgnoreCase(seatClass) && seat.getSeatName().equalsIgnoreCase(seatName)) {
                // Mark the seat as available
                seat.setAvailable(true);
                System.out.println("Seat " + seatName + " in class " + seatClass + " has been marked as available.");
                return;  // Exit once the seat is found and marked as available
            }
        }
        System.out.println("Seat " + seatName + " in class " + seatClass + " not found.");
    }


    // Save Seats to File
    public void saveSeatsToFile() {
        File file = new File("seats_" + numOfFlight + ".txt");

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("SeatName|SeatClass|Availability");
            for (Seat seat : seats) {
                writer.printf("%s|%s|%s%n",
                        seat.getSeatName(),
                        seat.getSeatClass(),
                        seat.isAvailable() ? "Available" : "Booked");
            }
            System.out.println("Seat data saved for flight " + numOfFlight);
        } catch (IOException e) {
            System.err.println("Error saving seat data: " + e.getMessage());
        }
    }


    // Flight Selection and Booking Process
    public static Flight flightSelect(Flight[] flights) {
        Scanner scanner = new Scanner(System.in);

        // Ensure the user inputs a valid flight number

        Flight selectedFlight = null;
        while (selectedFlight == null) {
            System.out.print("Enter the flight number you want to book: ");
            int flightNumber = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            // Try to find the flight with the given number
            selectedFlight = Arrays.stream(flights)
                    .filter(flight -> flight.getNumOfFlight() == flightNumber) // Use getter method here
                    .findFirst()
                    .orElse(null);

            if (selectedFlight == null) {
                System.out.println("Flight not found! Please enter a valid flight number.");
            } else {
                // Load saved seat data from the file
                selectedFlight.loadSeatsFromFile();
            }
        }

        // Predefined seat configurations
        int firstClassRows = 4, firstClassColumns = 3;       // Total = 12 seats
        int economyPlusRows = 7, economyPlusColumns = 6;    // Total = 42 seats
        int economyRows = 16, economyColumns = 6;          // Total = 96 seats

        // Calculate total and booked seats for each class
        int firstClassTotalSeats = firstClassRows * firstClassColumns;
        long firstClassBookedSeats = selectedFlight.seats.stream()
                .filter(seat -> seat.getSeatClass().equalsIgnoreCase("First Class") && !seat.isAvailable())
                .count();
        int firstClassAvailableSeats = firstClassTotalSeats - (int) firstClassBookedSeats;

        int economyPlusTotalSeats = economyPlusRows * economyPlusColumns;
        long economyPlusBookedSeats = selectedFlight.seats.stream()
                .filter(seat -> seat.getSeatClass().equalsIgnoreCase("Economy Plus") && !seat.isAvailable())
                .count();
        int economyPlusAvailableSeats = economyPlusTotalSeats - (int) economyPlusBookedSeats;

        int economyTotalSeats = economyRows * economyColumns;
        long economyBookedSeats = selectedFlight.seats.stream()
                .filter(seat -> seat.getSeatClass().equalsIgnoreCase("Economy") && !seat.isAvailable())
                .count();
        int economyAvailableSeats = economyTotalSeats - (int) economyBookedSeats;

        // Display total and available seats for each class
        System.out.println("Available seats for flight " + selectedFlight.getNumOfFlight() + ":");
        System.out.println("First Class: " + firstClassAvailableSeats + " available");
        System.out.println("Economy Plus: " + economyPlusAvailableSeats + " available");
        System.out.println("Economy: " + economyAvailableSeats + " available");



        return selectedFlight;

    }






}