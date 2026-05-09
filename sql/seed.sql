INSERT INTO station (name) VALUES
                               ('Cluj-Napoca'),
                               ('Alba Iulia'),
                               ('Sibiu'),
                               ('Brasov'),
                               ('Bucuresti');

INSERT INTO train (name, noOfSeats, delayMinutes) VALUES
                                                      ('IR 100', 100, 0),
                                                      ('IR 200', 80, 0),
                                                      ('R 300', 60, 0);

INSERT INTO customer (name, email, address) VALUES
                                                ('Maria Pop', 'maria@mail.com', 'Cluj'),
                                                ('Ana Ionescu', 'ana@mail.com', 'Brasov');

INSERT INTO trainroute () VALUES ();
INSERT INTO trainroute () VALUES ();

INSERT INTO route_station (route_id, station_id, stationOrder) VALUES
                                                                   (1, 1, 1),
                                                                   (1, 2, 2),
                                                                   (1, 3, 3),
                                                                   (2, 3, 1),
                                                                   (2, 4, 2),
                                                                   (2, 5, 3);

INSERT INTO train_schedule (train_id, route_id, departureTime, arrivalTime) VALUES
                                                                                (1, 1, '08:00', '11:00'),
                                                                                (2, 2, '11:30', '15:30');