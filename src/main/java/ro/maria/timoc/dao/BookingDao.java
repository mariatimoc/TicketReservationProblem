package ro.maria.timoc.dao;

import ro.maria.timoc.connection.ConnectionFactory;
import ro.maria.timoc.model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDao {

    public void insertBooking(Booking booking) {
        String query =
                "INSERT INTO booking " + "(bookingDate, customer_id, train_id, noOfTickets) " + "VALUES (?, ?, ?, ?)";
        try (
                Connection connection = ConnectionFactory.getConnection();

                PreparedStatement statement= connection.prepareStatement(query)) {
                    statement.setObject(1, booking.getBookingDate());
                    statement.setInt(2, booking.getCustomer().getId());
                    statement.setInt(3, booking.getTrain().getId());
                    statement.setInt(4, booking.getNoOfTickets());
                    statement.executeUpdate();

                    System.out.println("Booking inserted successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getBookedSeatsForTrain(int trainId) {

        String query ="SELECT SUM(noOfTickets) AS total " + "FROM booking WHERE train_id = ?";

        try (
                Connection connection=ConnectionFactory.getConnection();
                PreparedStatement statement=connection.prepareStatement(query)
        ) {

            statement.setInt(1, trainId);
            ResultSet resultSet =statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    public void showBookingsForTrain(int trainId) {

        String query =
                "SELECT b.id, b.bookingDate, b.noOfTickets, c.name, c.email " +
                        "FROM booking b " +
                        "JOIN customer c ON b.customer_id = c.id " +
                        "WHERE b.train_id = ?";

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {

            statement.setInt(1, trainId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Booking ID: " + resultSet.getInt("id") +
                                ", Customer: " + resultSet.getString("name") +
                                ", Email: " + resultSet.getString("email") +
                                ", Tickets: " + resultSet.getInt("noOfTickets") +
                                ", Date: " + resultSet.getObject("bookingDate")
                );
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }


    public List<String> getCustomerEmailsByTrainId(int trainId) {

        List<String> emails = new ArrayList<>();

        String query = "SELECT c.email " + "FROM booking b " + "JOIN customer c ON b.customer_id = c.id " + "WHERE b.train_id = ?";

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {

            statement.setInt(1, trainId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                emails.add(resultSet.getString("email"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return emails;
    }
}