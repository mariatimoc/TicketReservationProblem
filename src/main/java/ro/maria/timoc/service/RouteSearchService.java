package ro.maria.timoc.service;

import ro.maria.timoc.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteSearchService {

    public boolean findDirectRoutes(String fromStation, String toStation) {

        String query =
                "SELECT ts.id, t.name, ts.departureTime, ts.arrivalTime " +
                        "FROM train_schedule ts " +
                        "JOIN train t ON ts.train_id = t.id " +
                        "JOIN route_station rs1 ON ts.route_id = rs1.route_id " +
                        "JOIN station s1 ON rs1.station_id = s1.id " +
                        "JOIN route_station rs2 ON ts.route_id = rs2.route_id " +
                        "JOIN station s2 ON rs2.station_id = s2.id " +
                        "WHERE s1.name = ? " +
                        "AND s2.name = ? " +
                        "AND rs1.stationOrder < rs2.stationOrder";

        boolean found = false;

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {

            statement.setString(1, fromStation);
            statement.setString(2, toStation);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                found = true;

                System.out.println("Direct route found:");
                System.out.println("Train: " + resultSet.getString("name"));
                System.out.println("Departure: " + resultSet.getString("departureTime"));
                System.out.println("Arrival: " + resultSet.getString("arrivalTime"));
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return found;
    }



    public void searchRoutes(String fromStation, String toStation) {

        boolean directFound =
                findDirectRoutes(fromStation, toStation);

        if (directFound) {
            return;
        }

        System.out.println("No direct route found. Searching route with change...");

        boolean changeFound =
                findRoutesWithChange(fromStation, toStation);

        if (!changeFound) {
            System.out.println("No possible connection found.");
        }
    }


    public boolean findRoutesWithChange(String fromStation, String toStation) {

        return searchRoutesWithCommonStation(
                fromStation,
                toStation
        );
    }


    private boolean searchRoutesWithCommonStation(
            String fromStation,
            String toStation
    ) {

        String select =
                "SELECT " +
                        "t1.name AS firstTrain, " +
                        "t2.name AS secondTrain, " +
                        "changeStation.name AS changeStation, " +
                        "ts1.departureTime AS firstDeparture, " +
                        "ts1.arrivalTime AS firstArrival, " +
                        "ts2.departureTime AS secondDeparture, " +
                        "ts2.arrivalTime AS secondArrival ";

        String from =
                "FROM train_schedule ts1 " +
                        "JOIN train t1 ON ts1.train_id = t1.id " +
                        "JOIN route_station rsFrom ON ts1.route_id = rsFrom.route_id " +
                        "JOIN station fromS ON rsFrom.station_id = fromS.id " +
                        "JOIN route_station rsChange1 ON ts1.route_id = rsChange1.route_id " +
                        "JOIN station changeStation ON rsChange1.station_id = changeStation.id ";

        String joins =
                "JOIN train_schedule ts2 ON ts1.id <> ts2.id " +
                        "JOIN train t2 ON ts2.train_id = t2.id " +
                        "JOIN route_station rsChange2 ON ts2.route_id = rsChange2.route_id " +
                        "JOIN route_station rsTo ON ts2.route_id = rsTo.route_id " +
                        "JOIN station toS ON rsTo.station_id = toS.id ";

        String where =
                "WHERE fromS.name = ? " +
                        "AND toS.name = ? " +
                        "AND rsFrom.stationOrder < rsChange1.stationOrder " +
                        "AND rsChange1.station_id = rsChange2.station_id " +
                        "AND rsChange2.stationOrder < rsTo.stationOrder";

        String query =
                select + from + joins + where;

        boolean found = false;

        try (
                Connection connection =
                        ConnectionFactory.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setString(1, fromStation);
            statement.setString(2, toStation);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                found = true;

                printChangeRoute(resultSet);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return found;
    }


    private void printChangeRoute(ResultSet resultSet)
            throws SQLException {

        System.out.println("Route with change found:");

        System.out.println("First train: " + resultSet.getString("firstTrain")
        );

        System.out.println("Change at: " + resultSet.getString("changeStation")
        );

        System.out.println("Second train: " + resultSet.getString("secondTrain")
        );

        System.out.println("First departure: " + resultSet.getString("firstDeparture")
        );

        System.out.println("First arrival: " + resultSet.getString("firstArrival")
        );

        System.out.println("Second departure: " + resultSet.getString("secondDeparture")
        );

        System.out.println("Second arrival: " + resultSet.getString("secondArrival")
        );


    }

}