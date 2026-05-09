package ro.maria.timoc.service;

import ro.maria.timoc.dao.BookingDao;
import ro.maria.timoc.dao.RouteDao;
import ro.maria.timoc.dao.TrainDao;
import ro.maria.timoc.model.Train;
import ro.maria.timoc.model.TrainRoute;

import java.util.List;

public class AdminService {
    private final TrainDao trainDao;
    private final RouteDao routeDao;
    private final BookingDao bookingDao;
    private final EmailService emailService;

    public AdminService() {
        this.trainDao = new TrainDao();
        this.routeDao = new RouteDao();
        this.bookingDao = new BookingDao();
        this.emailService = new EmailService();
    }

    public void addTrain(Train train) {
        trainDao.insert(train);
    }

    public void updateTrain(Train train) {
        trainDao.update(train);
    }

    public void deleteTrain(int trainId) {
        trainDao.deleteById(trainId);
    }

    public void addRoute(TrainRoute route) {
        routeDao.insert(route);
    }

    public void updateRoute(TrainRoute route) {
        routeDao.update(route);
    }

    public void deleteRoute(int routeId) {
        routeDao.deleteById(routeId);
    }

    public void showBookingsForTrain(int trainId) {
        bookingDao.showBookingsForTrain(trainId);
    }

    public void setTrainDelay(int trainId, int delayMinutes) {

        Train train = trainDao.findById(trainId);

        if (train == null) {
            System.out.println("Train not found.");
            return;
        }

        train.setDelayMinutes(delayMinutes);
        trainDao.update(train);

        List<String> emails =
                bookingDao.getCustomerEmailsByTrainId(trainId);

        for (String email : emails) {
            emailService.sendDelayNotification(
                    email,
                    train.getName(),
                    delayMinutes
            );
        }
    }
}
