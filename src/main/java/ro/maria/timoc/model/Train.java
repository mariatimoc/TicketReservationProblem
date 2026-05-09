package ro.maria.timoc.model;

public class Train {
    private int id;
    private String name;
    private int noOfSeats;
    private int delayMinutes;

    public Train() {
    }

    public Train(int id, String name, int noOfSeats) {
        this.id = id;
        this.name = name;
        this.noOfSeats = noOfSeats;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public int getDelayMinutes() {
        return delayMinutes;
    }

    public void setDelayMinutes(int delayMinutes) {
        this.delayMinutes = delayMinutes;
    }
}
