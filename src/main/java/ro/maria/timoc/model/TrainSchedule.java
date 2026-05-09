package ro.maria.timoc.model;

public class TrainSchedule {
    private int id;
    private Train train;
    private TrainRoute route;
    private String departureTime;
    private String arrivalTime;

    public TrainSchedule() {
    }

    public TrainSchedule(int id, Train train, TrainRoute trainRoute, String departureTime, String arrivalTime) {
        this.id = id;
        this.train = train;
        this.route = trainRoute;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public int getId() {
        return id;
    }

    public Train getTrain() {
        return train;
    }

    public TrainRoute getRoute() {
        return route;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public void setTrainRoute(TrainRoute trainRoute) {
        this.route = trainRoute;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }


}
