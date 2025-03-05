public class Seat {
    private String seatName;
    private String seatClass;
    private boolean available;

    public Seat(String seatName, String seatClass) {
        this.seatName = seatName;
        this.seatClass = seatClass;
        this.available = true; // All seats start as available
    }

    public String getSeatName() {
        return seatName;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public boolean isAvailable() {
        return available;
    }

    public void bookSeat() {
        this.available = false;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
