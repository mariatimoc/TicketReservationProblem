# Train Ticketing Application

## Description

This project is a Java console application for managing train ticket bookings.

The application uses predefined trains, stations, routes and schedules stored in a MySQL database. A customer can book one or multiple tickets for a train. Before confirming the booking, the application checks the existing bookings in order to prevent overbooking.

The application also supports route searching between two stations. A connection can be direct or can require one train change.

For the administrator, the application supports adding, updating and deleting trains, showing bookings for a train, and setting train delays. When a delay is added, the customers who booked tickets for that train are notified by email.

Emails are simulated in the console using `System.out.println`.

---

## Technologies Used

- Java
- JDBC
- MySQL
- Maven
- Console-based user interface

---

## Solution Description

The application is implemented as a Java console application using JDBC and a MySQL database.

The solution is divided into several layers:

- `model` package: contains the entity classes used by the application, such as `Train`, `Customer`, `Booking`, `Station`, `TrainRoute` and `TrainSchedule`.
- `dao` package: contains the classes responsible for database operations.
- `service` package: contains the business logic of the application.
- `connection` package: contains the database connection logic.
- `Main` class: contains the console menu and connects the user input with the application services.

The application uses predefined data stored in the database for trains, stations, routes and schedules. A user can book tickets for a train, and the application checks the existing bookings before confirming the reservation, in order to prevent overbooking.

Route search is done using the order of stations inside a route. The application first searches for a direct route between the departure station and the arrival station. If no direct route is found, it searches for a route with one train change.

Emails are simulated using console output. After a successful booking, the customer receives a simulated confirmation email. When an administrator sets a delay for a train, all customers who booked tickets for that train receive a simulated delay notification.

---


## Console Menu

```text
TRAIN TICKETING APPLICATION
1. Book tickets
2. Search route between two stations
3. Add train
4. Update train
5. Delete train
6. Show bookings for train
7. Set train delay and notify customers
0. Exit
Choose option:
```

---

## Input and Output Examples

### 1. Book Tickets - Successful Booking

Input:

```text
Choose option: 1

--- Book tickets ---
Customer id: 1
Train id: 1
Number of tickets: 2
```

Output:

```text
Booking inserted successfully.
Email sent to: maria@mail.com
Booking confirmed for train: IR 100
Number of tickets: 2
```

---

### 2. Book Tickets - Overbooking

Input:

```text
Choose option: 1

--- Book tickets ---
Customer id: 1
Train id: 1
Number of tickets: 200
```

Output:

```text
Booking failed.
Not enough available seats.
Available seats: 98
```

---

### 3. Search Route With Train Change

Input:

```text
Choose option: 2

Search route
From station: Cluj-Napoca
To station: Sibiu
```

Output:

```text
No direct route found. Searching route with change...
Route with change found:
First train: IR 100
Change at: Alba Iulia
Second train: IR 200
First departure: 08:00
First arrival: 11:00
Second departure: 11:30
Second arrival: 15:30
```

---

### 4. Search Route That Does Not Exist

Input:

```text
Choose option: 2

Search route
From station: Bucuresti
To station: Cluj-Napoca
```

Output:

```text
No direct route found. Searching route with change...
No possible connection found.
```

---

### 5. Add Train

Input:

```text
Choose option: 3

Add train
Train name: IR 400
Number of seats: 120
```

Output:

```text
Train inserted successfully.
```

---

### 6. Update Train

Input:

```text
Choose option: 4

--- Update train ---
Train id: 1
New train name: IR 100 Updated
New number of seats: 150
```

Output:

```text
Train updated successfully.
```


---

### 7. Delete Train

Input:

```text
Choose option: 5

Delete train
Train id: 3
```

Output:

```text
Train deleted successfully.
```


---

### 8. Show Bookings For Train

Input:

```text
Choose option: 6

Show bookings for train
Train id: 1
```

Output:

```text
Booking ID: 2, Customer: Maria Pop, Email: maria@mail.com, Tickets: 2, Date: 2026-05-09T23:09:47
```

---

### 9. Set Train Delay and Notify Customers

Input:

```text
Choose option: 7

Set train delay
Train id: 1
Delay minutes: 30
```

Output:

```text
Train updated successfully.
Email sent to: maria@mail.com
Train IR 100 Updated has a delay of 30 minutes.
```

---

