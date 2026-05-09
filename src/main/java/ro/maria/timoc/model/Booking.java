package ro.maria.timoc.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Booking {
    private int id;
    private LocalDateTime bookingDate;
    private Customer customer;
    private Train train;
    private int noOfTickets;

    public Booking() {
    }

    public Booking(int id, LocalDateTime bookingDate, Customer customer, Train train, int noOfTickets) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.customer = customer;
        this.train = train;
        this.noOfTickets = noOfTickets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }
}
