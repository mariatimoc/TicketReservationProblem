package ro.maria.timoc;

import ro.maria.timoc.connection.ConnectionFactory;
import ro.maria.timoc.dao.BookingDao;
import ro.maria.timoc.dao.CustomerDao;
import ro.maria.timoc.dao.TrainDao;
import ro.maria.timoc.model.Booking;
import ro.maria.timoc.model.Customer;
import ro.maria.timoc.model.Train;
import ro.maria.timoc.service.AdminService;
import ro.maria.timoc.service.BookingService;
import ro.maria.timoc.service.EmailService;
import ro.maria.timoc.service.RouteSearchService;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final TrainDao trainDao = new TrainDao();
    private static final CustomerDao customerDao = new CustomerDao();
    private static final BookingService bookingService = new BookingService(new BookingDao(), new EmailService());
    private static final RouteSearchService routeSearchService = new RouteSearchService();
    private static final AdminService adminService = new AdminService();

    public static void main(String[] args) {

        Connection connection = ConnectionFactory.getConnection();

        if (connection== null) {
            System.out.println("Database connection failed.");
            return;
        }

        System.out.println("Connected successfully.");

        int option;

        do {
            printMenu();
            option = readInt("Choose option: ");

            switch (option) {
                case 1:
                    bookTicketsMenu();
                    break;
                case 2:
                    searchRouteMenu();
                    break;
                case 3:
                    addTrainMenu();
                    break;
                case 4:
                    updateTrainMenu();
                    break;
                case 5:
                    deleteTrainMenu();
                    break;
                case 6:
                    showBookingsForTrainMenu();
                    break;
                case 7:
                    setTrainDelayMenu();
                    break;
                case 0:
                    System.out.println("Application closed.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }

        } while (option != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("TRAIN TICKETING APPLICATIO");
        System.out.println("1. Book tickets");
        System.out.println("2. Search route between two stations");
        System.out.println("3. Add train");
        System.out.println("4. Update train");
        System.out.println("5. Delete train");
        System.out.println("6. Show bookings for train");
        System.out.println("7. Set train delay and notify customers");
        System.out.println("0. Exit");
    }

    private static void bookTicketsMenu() {
        System.out.println();
        System.out.println("--- Book tickets ---");

        int customerId = readInt("Customer id: ");
        int trainId = readInt("Train id: ");
        int noOfTickets = readInt("Number of tickets: ");

        Customer customer=customerDao.findById(customerId);
        Train train = trainDao.findById(trainId);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        if (train == null) {
            System.out.println("Train not found.");
            return;
        }

        Booking booking = new Booking(0,
                LocalDateTime.now(),
                customer,
                train,
                noOfTickets
        );

        bookingService.bookTickets(booking);
    }

    private static void searchRouteMenu() {
        System.out.println();
        System.out.println("Search route");

        String fromStation = readString("From station: ");
        String toStation = readString("To station: ");
        routeSearchService.searchRoutes(fromStation, toStation);
    }

    private static void addTrainMenu() {
        System.out.println();
        System.out.println("Add train");

        String name = readString("Train name: ");
        int noOfSeats = readInt("Number of seats: ");

        Train train = new Train(0, name, noOfSeats);
        train.setDelayMinutes(0);

        adminService.addTrain(train);
    }

    private static void updateTrainMenu() {
        System.out.println();
        System.out.println("--- Update train ---");

        int trainId = readInt("Train id: ");
        Train train = trainDao.findById(trainId);

        if (train == null) {
            System.out.println("Train not found.");
            return;
        }

        String name = readString("New train name: ");
        int noOfSeats = readInt("New number of seats: ");

        train.setName(name);
        train.setNoOfSeats(noOfSeats);

        adminService.updateTrain(train);
    }

    private static void deleteTrainMenu() {
        System.out.println();
        System.out.println("Delete train");

        int trainId = readInt("Train id: ");
        adminService.deleteTrain(trainId);
    }

    private static void showBookingsForTrainMenu() {
        System.out.println();
        System.out.println("Show bookings for train");

        int trainId = readInt("Train id: ");
        adminService.showBookingsForTrain(trainId);
    }

    private static void setTrainDelayMenu() {
        System.out.println();
        System.out.println("Set train delay");

        int trainId = readInt("Train id: ");
        int delayMinutes = readInt("Delay minutes: ");

        adminService.setTrainDelay(trainId, delayMinutes);
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}
