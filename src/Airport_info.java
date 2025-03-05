import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class Airport_info implements Serializable{
    public String name;
    private String code;
    private String location;
    private static final long serialVersionUID = -1354677192917686879L;

    public Airport_info(String name, String code, String location) {
        this.location = location;
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public static void createAndSaveAirport(String name, String code, String location, String fileName) {
        Airport_info newAirport = new Airport_info(name, code, location);
        List<Airport_info> airports = loadAirports(fileName);
        airports.add(newAirport);


        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {  //Serialization process
            out.writeObject(airports);
            System.out.println("Airport added and saved successfully");
        } catch (IOException e) {
            System.out.println("Error saving airport to file: " + e.getMessage());
        }
    }


    public static List<Airport_info> loadAirports(String fileName) {
        List<Airport_info> airports = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                // If the file doesn't exist, create it
                file.createNewFile();
                if(file.createNewFile()) {
                    System.out.println("File created: " + fileName);
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
            airports.add(new Airport_info("Miami International Airport","MIA","usa"));
            airports.add(new Airport_info("Paris International Airport","CDG","france"));
            airports.add(new Airport_info("Berlin Brandenburg Airport","BER","germany"));
            airports.add(new Airport_info("New York International Airport","LGA","USA"));
            airports.add(new Airport_info("Dubai International Airport","DXB","uae"));
            airports.add(new Airport_info("Narita International Airport","NRT","japan"));
            airports.add(new Airport_info("London City Airport","LCY","england"));
            airports.add(new Airport_info("EgyptAir International Airport","MSR","egypt"));
            airports.add(new Airport_info("Hartsfield-Jackson Atlanta International Airport", "ATL", "usa"));
            airports.add(new Airport_info("Beijing Capital International Airport", "PEK", "china"));
            airports.add(new Airport_info("Los Angeles International Airport", "LAX", "usa"));
            airports.add(new Airport_info("Tokyo Haneda Airport", "HND", "japan"));
            airports.add(new Airport_info("Dubai International Airport", "DXB", "uae"));
            airports.add(new Airport_info("O'Hare International Airport", "ORD", "usa"));
            airports.add(new Airport_info("London Heathrow Airport", "LHR", "england"));
            airports.add(new Airport_info("Shanghai Pudong International Airport", "PVG", "china"));
            airports.add(new Airport_info("Paris Charles de Gaulle Airport", "CDG", "france"));
            airports.add(new Airport_info("Dallas/Fort Worth International Airport", "DFW", "usa"));
            airports.add(new Airport_info("Amsterdam Schiphol Airport", "AMS", "netherlands"));
            airports.add(new Airport_info("Hong Kong International Airport", "HKG", "china"));
            airports.add(new Airport_info("Guangzhou Baiyun International Airport", "CAN", "china"));
            airports.add(new Airport_info("Frankfurt Airport", "FRA", "germany"));
            airports.add(new Airport_info("Seoul Incheon International Airport", "ICN", "south korea"));
            airports.add(new Airport_info("Singapore Changi Airport", "SIN", "singapore"));
            airports.add(new Airport_info("Denver International Airport", "DEN", "usa"));
            airports.add(new Airport_info("Indira Gandhi International Airport", "DEL", "india"));
            airports.add(new Airport_info("Soekarno-Hatta International Airport", "CGK", "indonesia"));
            airports.add(new Airport_info("Istanbul Airport", "IST", "turkey"));
            airports.add(new Airport_info("Suvarnabhumi Airport", "BKK", "thailand"));
            airports.add(new Airport_info("Chengdu Shuangliu International Airport", "CTU", "china"));
            airports.add(new Airport_info("Kuala Lumpur International Airport", "KUL", "malaysia"));
            airports.add(new Airport_info("Madrid Barajas Airport", "MAD", "spain"));
            airports.add(new Airport_info("Toronto Pearson International Airport", "YYZ", "canada"));
            airports.add(new Airport_info("Sydney Kingsford Smith Airport", "SYD", "australia"));
            airports.add(new Airport_info("São Paulo Guarulhos International Airport", "GRU", "brazil"));
            airports.add(new Airport_info("Mexico City International Airport", "MEX", "mexico"));
            airports.add(new Airport_info("Cairo International Airport", "CAI", "egypt"));
            airports.add(new Airport_info("Munich Airport", "MUC", "germany"));
            airports.add(new Airport_info("Johannesburg OR Tambo International Airport", "JNB", "south africa"));
            airports.add(new Airport_info("Zurich Airport", "ZRH", "switzerland"));
            airports.add(new Airport_info("Doha Hamad International Airport", "DOH", "qatar"));
            airports.add(new Airport_info("Newark Liberty International Airport", "EWR", "usa"));
            airports.add(new Airport_info("San Francisco International Airport", "SFO", "usa"));
            airports.add(new Airport_info("Brussels Airport", "BRU", "belgium"));
            airports.add(new Airport_info("Vienna International Airport", "VIE", "austria"));
            airports.add(new Airport_info("Oslo Gardermoen Airport", "OSL", "norway"));
            airports.add(new Airport_info("Bangkok Don Mueang International Airport", "DMK", "thailand"));
            airports.add(new Airport_info("Helsinki-Vantaa Airport", "HEL", "finland"));
            airports.add(new Airport_info("Rome Fiumicino Airport", "FCO", "italy"));
            airports.add(new Airport_info("Moscow Sheremetyevo International Airport", "SVO", "russia"));
            airports.add(new Airport_info("Cape Town International Airport", "CPT", "south africa"));
            airports.add(new Airport_info("Abu Dhabi International Airport", "AUH", "uae"));
            airports.add(new Airport_info("Seattle-Tacoma International Airport", "SEA", "usa"));
            airports.add(new Airport_info("Boston Logan International Airport", "BOS", "usa"));
            airports.add(new Airport_info("Chicago Midway International Airport", "MDW", "usa"));
            airports.add(new Airport_info("Miami International Airport", "MIA", "usa"));
            return airports; // Return list with default airports
        }
        else {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {   //Deserialization process
                airports = (List<Airport_info>) in.readObject();
            } catch (FileNotFoundException e) {

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading airports from file: " + e.getMessage());
            }
            return airports; // Only return the airports from the file
        }
    }
    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
    public static void show_airports(String fileName){
        List<Airport_info> airports = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {   //Deserialization process
            airports = (List<Airport_info>) in.readObject();
        } catch (FileNotFoundException e) {

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading airports from file: " + e.getMessage());
        }
        for(int i = 0 ; i < airports.size() ; i ++){
            System.out.println( i + 1 + ")" + " " + airports.get(i).toString());
        }

    }
    public static void flightsfound_onelocation(String flightsfile,String fileName, List<Airport_info> chosenairports,int typeofairport  ){
        List<String> flightData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(flightsfile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                flightData.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading flight data: " + e.getMessage());
        }
        int count = 0;
        System.out.println("Available flights : ");
        System.out.println();
        for (int i = 1; i < flightData.size(); i++) {  // Start from 1 to skip the header
            String[] flight = flightData.get(i).split("\\|");
            for(int j = 0 ; j < chosenairports.size() ; j++){
                if(flight[typeofairport].equals(chosenairports.get(j).name)){
                    System.out.println("Flight Number: " + flight[0]);
                    System.out.println("Leaving Airport: " + flight[1]);
                    System.out.println("Arrival Airport: " + flight[2]);
                    System.out.println("Leaving Time: " + flight[3]);
                    System.out.println("Arrival Time: " + flight[4]);
                    System.out.println();
                    count += 1;
                }

            }
        }
        if (count == 0 ){
            System.out.println("Sorry...There's are no available flights.");
        }
    }
    public static void flightsfound_twoLocations(String flightsfile, String fileName, List<Airport_info> chosenDepartureAirports, List<Airport_info> chosenArrivalAirports, int departureAirportType, int arrivalAirportType) {
        List<String> flightData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(flightsfile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                flightData.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading flight data: " + e.getMessage());
        }

        int count = 0;
        System.out.println("Available flights : ");
        System.out.println();
        for (int i = 1; i < flightData.size(); i++) {  // Start from 1 to skip the header
            String[] flight = flightData.get(i).split("\\|");

            boolean departureMatch = false;
            boolean arrivalMatch = false;

            // Check if the departure airport is in the list of chosen departure airports
            for (int j = 0; j < chosenDepartureAirports.size(); j++) {
                if (flight[departureAirportType].equals(chosenDepartureAirports.get(j).name)) {
                    departureMatch = true;
                    break;
                }
            }

            // Check if the arrival airport is in the list of chosen arrival airports
            for (int j = 0; j < chosenArrivalAirports.size(); j++) {
                if (flight[arrivalAirportType].equals(chosenArrivalAirports.get(j).name)) {
                    arrivalMatch = true;
                    break;
                }
            }

            // If both departure and arrival match, print the flight details
            if (departureMatch && arrivalMatch) {
                System.out.println("Flight Number: " + flight[0]);
                System.out.println("Leaving Airport: " + flight[1]);
                System.out.println("Arrival Airport: " + flight[2]);
                System.out.println("Leaving Time: " + flight[3]);
                System.out.println("Arrival Time: " + flight[4]);
                System.out.println();
                count += 1;
            }
        }

        if (count == 0) {
            System.out.println("Sorry...There are no available flights.");
        }
    }

    public static void search_airports(String LeavingLocation, String ArrivalLocation, String fileName, String flightsfile ){
        List<Airport_info> airports = loadAirports(fileName);
        List<Airport_info> Rairports = new ArrayList<>();
        if(LeavingLocation.equalsIgnoreCase("none") && !ArrivalLocation.equalsIgnoreCase("none")) {
            int  counter1 = 0;
            for (Airport_info airport : airports) {
                if (airport.location.equalsIgnoreCase(ArrivalLocation)) {
                    Rairports.add(airport);
                    counter1 += 1;
                    flightsfound_onelocation(flightsfile,fileName,Rairports,2);

                }
            }
            if(counter1 == 0){
                System.out.println("Sorry,no flights available in that particular area.");
            }
        }
        else if(ArrivalLocation.equalsIgnoreCase("none") && !LeavingLocation.equalsIgnoreCase("none")){
            int counter2 = 0;
            for(Airport_info airport : airports) {
                if (airport.location.equalsIgnoreCase(LeavingLocation)) {
                    Rairports.add(airport);
                    counter2 += 1;
                    flightsfound_onelocation(flightsfile,fileName,Rairports,1);
                }
            }
            if(counter2 == 0){
                System.out.println("Sorry, no flights available in that particular area.");
            }
        }
        else{
            int counterArrival = 0;
            int counterLeaving = 0;
            List<Airport_info> Aairports = new ArrayList<>();
            List<Airport_info>Lairports = new ArrayList<>();
            for(Airport_info airport : airports){
                if (airport.location.equalsIgnoreCase(LeavingLocation)) {
                    Lairports.add(airport);
                    counterLeaving += 1;
                }
            }
            for(Airport_info airport : airports){
                if (airport.location.equalsIgnoreCase(ArrivalLocation)) {
                    Aairports.add(airport);
                    counterArrival += 1;
                }
            }
            if(counterLeaving == 0 || counterArrival == 0){
                System.out.println("Sorry, no flights available in that particular area.");
            }
            else{

                flightsfound_twoLocations(flightsfile , fileName , Lairports,  Aairports,1,  2 );
            }

        }
        System.out.println("Other recommended flights : ");
        System.out.println();

    }


}
