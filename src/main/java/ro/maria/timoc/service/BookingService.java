package ro.maria.timoc.service;

import ro.maria.timoc.dao.BookingDao;
import ro.maria.timoc.model.Booking;
import ro.maria.timoc.model.Train;

public class BookingService {
    private final BookingDao bookingDao;
    private final EmailService emailService;

    public BookingService(BookingDao bookingDao, EmailService emailService) {
        this.bookingDao = bookingDao;
        this.emailService = emailService;
    }

    public void bookTickets(Booking booking) {

        Train train = booking.getTrain();
        int bookedSeats =bookingDao.getBookedSeatsForTrain(train.getId());
        int availableSeats =train.getNoOfSeats() - bookedSeats;

        if (booking.getNoOfTickets() <= 0) {
            System.out.println("Number of tickets must be greater than 0.");
            return;
        }

        if (booking.getNoOfTickets() > availableSeats) {
            System.out.println("Booking failed.");
            System.out.println("Not enough available seats.");
            System.out.println("Available seats: " + availableSeats);
            return;
        }

        bookingDao.insertBooking(booking);
        emailService.sendBookingConfirmation(booking);
    }
}
