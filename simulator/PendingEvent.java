package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.Pair;
import java.util.Optional;

class PendingEvent implements Event {
    private final Customer customer;
    private final double time;
    private final int serverIndex;
    private final Server server;

    PendingEvent(Customer customer, double time, int serverIndex, Server server) {
        this.customer = customer;
        this.time = time;
        this.serverIndex = serverIndex;
        this.server = server;
    }

    @Override
    public String getType() {
        return "PENDING";
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
        Server server = shop.getServerList().get(serverIndex);
        if (!(server.getQueueList().size() > 0)) {
            return Pair.of(Optional.<Event>of(
                    new ServeEvent(customer, this.time, serverIndex, server)), shop);
        } else if (server.isAvailable() &&
                customer.getId() == server.getQueueList().get(0).getId() &&
                (this.getTime() >= server.getNextAvailableTime())){
            return Pair.of(Optional.<Event>of(
                    new ServeEvent(customer, this.time, serverIndex, server)), shop);
        } else {
            return Pair.of(Optional.<Event>of(
                    new PendingEvent(customer, server.getNextAvailableTime(),
                            serverIndex, server)), shop);
        }
    }

    double getWaitingTime() {
        return this.time - this.customer.getArrivalTime();
    }

    @Override
    public String toString() {
        return String.format("%.3f %s pending at %s", time, customer.getId(), server.getId());
    }
}
