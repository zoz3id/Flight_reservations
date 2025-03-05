import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Ticket {

    private String ticket_number;
    private double fare;
    private boolean ticket_status = true;
    private String booked_seat_num="";
    private String booked_seat_class="";

    // Constructors
    public Ticket(String ticket_number, float fare) {
        this.ticket_number = ticket_number;
        this.fare = fare;
    }

    public Ticket(String ticket_number) {
        this(ticket_number, 0);
    }

    public Ticket() {
        this("none");
    }

    // Setters
    public void setTicket_number(String ticket_number) {
        this.ticket_number = ticket_number;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }

    public void setTicketStatus(boolean status) {
        this.ticket_status = status;
    }

    public void setBooked_seat_num(String booked_seat_num) {
        this.booked_seat_num = booked_seat_num;
    }

    public void setBooked_seat_class(String booked_seat_class) {
        this.booked_seat_class = booked_seat_class;
    }

    // Getters
    public String getTicket_number() {
        return ticket_number;
    }

    public double getFare() {
        return fare;
    }

    public boolean getTicketStatus() {
        return ticket_status;
    }

    public void ticket_booked(){
        this.ticket_status=false;

    }

    public String getBooked_seat_num() {
        return booked_seat_num;
    }

    public String getBooked_seat_class() {
        return booked_seat_class;
    }

    public static ArrayList<Ticket> generate_ids(ArrayList<Ticket> t, int total) {
        if (t == null) {
            throw new IllegalArgumentException("The ticket list cannot be null.");
        }

        Random random = new Random(); // Random number generator
        for (int i = 0; i < total; i++) {
            int num = random.nextInt(900000) + 10000; // Generate 5-digit ticket ID
            String ticketId = Integer.toString(num);
            Ticket newTicket = new Ticket(ticketId); // Create a new ticket
            t.add(newTicket); // Add to the list

        }
        return t;
    }


    public static void saveTicketsToFile(ArrayList<Ticket> t,int NumofFlight) {
        if (t == null || t.isEmpty()) {
            System.out.println("No tickets to save.");
            return;
        }

        File file = new File("tickets"+NumofFlight+".txt");

        if (file.exists()) {

            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("TicketNumber|Status");
            for (Ticket ti : t) {
                writer.printf("%s|%s%n",
                        ti.getTicket_number(),
                        ti.getTicketStatus() ? "Available" : "Booked");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


    public void calculate_fare(String seatclass) {
        if (seatclass == null || seatclass.isEmpty()) {
            throw new IllegalArgumentException("Seat class cannot be null or empty.");
        }

        double price=0;
        switch (seatclass.toLowerCase()) {
            case "economy":
                price = 5000 + (5000 * 0.09);
                break;
            case "economy plus":
                price = 10000 + (10000 * 0.09);
                break;
            case "first class":
                price = 20000 + (20000 * 0.09);
                break;
            default:
                throw new IllegalArgumentException("Invalid seat class: " + seatclass);
        }
        this.fare=price;
    }


    public static int status_check(String seat_class,ArrayList<Ticket> t,int NumofFlight){

        String filee="tickets"+NumofFlight+".txt";
        String s="Available";
        String s2="Booked";

        try(BufferedReader reader = new BufferedReader(new FileReader(filee))){

            String line;
            int index=0;

            while((line=reader.readLine()) != null){

                if(line.equals("TicketNumber|Status")){
                    continue;
                }


                if(line.contains(s)){

                    t.get(index).ticket_booked();

                    break;

                }


                else if(line.contains(s2)){
                    t.get(index).ticket_booked();
                }
                index++;

            }


            try (PrintWriter writer = new PrintWriter(new FileWriter(filee))) {
                writer.println("TicketNumber|Status");
                for (Ticket ticket : t) {
                    writer.printf("%s|%s%n",
                            ticket.getTicket_number(),
                            ticket.getTicketStatus() ? "Available" : "Booked");
                }

            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
                return -1; // update isn't completed
            }
            return index;



        } catch (IOException e) {
            System.out.println("Error reading tickets file");;
        }

        return -1; //reading isn't completed

    }





}