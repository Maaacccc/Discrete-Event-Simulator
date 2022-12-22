package cs2030.simulator;

import cs2030.util.ImList;
import java.util.Optional;
import java.util.function.Supplier;

public class Server {
    private final int id;
    private final Optional<Customer> currCustomer;
    private final ImList<Customer> waitingCustomers;
    private final double nextAvailableTime;
    private final int maxQueueLength;
    private final Supplier<Double> restTime;
//    private final boolean isHuman;

    public Server(int id) {
        this.id = id;
        this.currCustomer = Optional.empty();
        this.waitingCustomers = ImList.<Customer>of();
        this.nextAvailableTime = 0.0;
        this.maxQueueLength = 1;
        this.restTime = () -> 0.0;
//        this.isHuman = true;
    }

    public Server(int id, int maxQueueLength) {
        this.id = id;
        this.currCustomer = Optional.empty();
        this.waitingCustomers = ImList.<Customer>of();
        this.nextAvailableTime = 0.0;
        this.maxQueueLength = maxQueueLength;
        this.restTime = () -> 0.0;
//        this.isHuman = true;
    }

    public Server(int id, int maxQueueLength, Supplier<Double> restTime) {
        this.id = id;
        this.currCustomer = Optional.empty();
        this.waitingCustomers = ImList.<Customer>of();
        this.nextAvailableTime = 0.0;
        this.maxQueueLength = maxQueueLength;
        this.restTime = restTime;
//        this.isHuman = false;
    }

    Server(int id, Optional<Customer> currCustomer, ImList<Customer> waitingCustomers,
           double nextAvailableTime, int maxQueueLength,
           Supplier<Double> restTime) {
        this.id = id;
        this.currCustomer = currCustomer;
        this.waitingCustomers = waitingCustomers;
        this.nextAvailableTime = nextAvailableTime;
        this.maxQueueLength = maxQueueLength;
        this.restTime = restTime;
//        this.isHuman = isHuman;
    }

    int getId() {
        return this.id;
    }

    boolean isAvailable() {
        return this.currCustomer.equals(Optional.empty());
    }

    boolean canQueue() {
        return this.waitingCustomers.size() < this.maxQueueLength;
    }

    double getNextAvailableTime() {
        return this.nextAvailableTime;
    }

    ImList<Customer> getQueueList() {
        return this.waitingCustomers;
    }

    public double getRestTime() {
        return this.restTime.get();
    }

    Server done() {
        if (!isAvailable()) {
            if (waitingCustomers.size() == 0) {
                return new Server(this.id, Optional.empty(),
                        ImList.<Customer>of(), this.nextAvailableTime + getRestTime(),
                        this.maxQueueLength, this.restTime);
            } else {
                return new Server(this.id, Optional.empty(),
                        waitingCustomers, this.nextAvailableTime + getRestTime(),
                        this.maxQueueLength, this.restTime);
            }
        } else {
            return this;
        }
    }

    /**
     * Add in a new customer to the queue.
     * @param time Customer's arrival time
     * @param customer the customer who is about to be served or wait
     * @return A server
     */
    public Server update(double time, Customer customer) {
        if (isAvailable() && time >= nextAvailableTime) {
            if (waitingCustomers.size() > 0) {
                return new Server(this.id, Optional.<Customer>of(customer),
                        this.waitingCustomers.remove(0).second(),
                        time + customer.getLazyServingTime().get(),
                        this.maxQueueLength, this.restTime);
            }
            return new Server(this.id, Optional.<Customer>of(customer),
                    ImList.<Customer>of(),
                    time + customer.getLazyServingTime().get(),
                    this.maxQueueLength, this.restTime);
        } else if (canQueue()) {
            return new Server(this.id, this.currCustomer,
                    this.waitingCustomers.add(customer), this.nextAvailableTime,
                    this.maxQueueLength, this.restTime);
        } else {
            return this;
        }
    }

    @Override
    public String toString() {
        return String.format("%s", id);
    }
}
