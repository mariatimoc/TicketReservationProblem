package ro.maria.timoc.dao;


import ro.maria.timoc.connection.ConnectionFactory;
import ro.maria.timoc.model.TrainSchedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScheduleDao {

    public void insertSchedule(TrainSchedule schedule) {

        String query =
                "INSERT INTO train_schedule " + "(train_id, route_id, departureTime, arrivalTime) " + "VALUES (?, ?, ?, ?)";
        try (
                Connection connection= ConnectionFactory.getConnection();
                PreparedStatement statement=connection.prepareStatement(query)
        ) {

            statement.setInt(1, schedule.getTrain().getId());
            statement.setInt(2, schedule.getRoute().getId());
            statement.setString(3, schedule.getDepartureTime());
            statement.setString(4, schedule.getArrivalTime());

            statement.executeUpdate();
            System.out.println("Schedule inserted successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}