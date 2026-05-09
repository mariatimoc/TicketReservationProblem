package ro.maria.timoc.service;

import ro.maria.timoc.model.Booking;

public class EmailService {
    public void sendBookingConfirmation(Booking booking) {

        System.out.println("Email sent to: " +booking.getCustomer().getEmail());
        System.out.println("Booking confirmed for train: " +booking.getTrain().getName());
        System.out.println("Number of tickets: " +booking.getNoOfTickets());
    }

    public void sendDelayNotification(String email,String trainName,int delayMinutes) {

        System.out.println("Email sent to: " +email);
        System.out.println("Train "+ trainName+ " has a delay of " + delayMinutes + " minutes.");
    }
}
