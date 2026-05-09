CREATE DATABASE train_ticket;
use train_ticket;

CREATE TABLE station (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(100) NOT NULL
);

CREATE TABLE train (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(100) NOT NULL,
                       noOfSeats INT NOT NULL
);

CREATE TABLE customer (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(100) NOT NULL,
                          address VARCHAR(255)
);

CREATE TABLE trainroute (
                            id INT PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE route_station (
                               route_id INT NOT NULL,
                               station_id INT NOT NULL,
                               stationOrder INT NOT NULL,

                               PRIMARY KEY (route_id, station_id),
                               FOREIGN KEY (route_id) REFERENCES trainroute(id),
                               FOREIGN KEY (station_id) REFERENCES station(id)
);

CREATE TABLE train_schedule (
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                train_id INT NOT NULL,
                                route_id INT NOT NULL,
                                departureTime VARCHAR(20) NOT NULL,
                                arrivalTime VARCHAR(20) NOT NULL,

                                FOREIGN KEY (train_id) REFERENCES train(id),
                                FOREIGN KEY (route_id) REFERENCES trainroute(id)
);

CREATE TABLE booking (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         bookingDate DATETIME NOT NULL,
                         customer_id INT NOT NULL,
                         train_id INT NOT NULL,
                         noOfTickets INT NOT NULL,

                         FOREIGN KEY (customer_id) REFERENCES customer(id),
                         FOREIGN KEY (train_id) REFERENCES train(id)
);

ALTER TABLE train
    ADD delayMinutes INT DEFAULT 0;