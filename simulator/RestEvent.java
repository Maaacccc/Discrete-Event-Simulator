package cs2030.simulator;

import cs2030.util.Pair;
import java.util.Optional;

class RestEvent implements Event {
    private final Customer customer;
    private final double time;
    private final int serverIndex;

    RestEvent(Customer customer, double time, int serverIndex) {
        this.customer = customer;
        this.time = time;
        this.serverIndex = serverIndex;
    }

    @Override
    public String getType() {
        return "REST";
    }

    @Override
    public int getCustomerId() {
        return customer.getId();
    }

    @Override
    public boolean equals(Event other) {
        if (this.getType().equals(other.getType()) &&
                this.getCustomerId() == other.getCustomerId() &&
                this.getTime() == other.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double getTime() {
        return this.time;
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        return Pair.of(Optional.<Event>empty(), shop);
    }

    double getWaitingTime() {
        return this.time - this.customer.getArrivalTime();
    }

    @Override
    public String toString() {
        return String.format("%.3f %s rests", time, customer.getId());
    }
}
