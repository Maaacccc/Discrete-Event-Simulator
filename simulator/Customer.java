package cs2030.simulator;

import cs2030.util.Lazy;
import java.util.function.Supplier;

public class Customer {
    private final int id;
    private final double arrivalTime;
    private final Lazy<Double> lazyServingTime;

    public Customer(int id, double arrivalTime, Supplier<Double> servingTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.lazyServingTime = new Lazy<Double>(servingTime);
    }

    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.lazyServingTime = new Lazy<Double>(() -> 1.0);
    }

    int getId() {
        return this.id;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public Lazy<Double> getLazyServingTime() {
        return this.lazyServingTime;
    }

    @Override
    public String toString() {
        return String.format("%s", this.id);
    }
}
