package ro.maria.timoc.dao;

import ro.maria.timoc.connection.ConnectionFactory;
import ro.maria.timoc.model.TrainRoute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RouteDao extends AbstractDao<TrainRoute> {
    public void addStationToRoute(int routeId, int stationId, int stationOrder) {
        String query = "INSERT INTO route_station (route_id, station_id, stationOrder) VALUES (?, ?, ?)";

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, routeId);
            statement.setInt(2, stationId);
            statement.setInt(3, stationOrder);
            statement.executeUpdate();

            System.out.println("Station added to route successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
