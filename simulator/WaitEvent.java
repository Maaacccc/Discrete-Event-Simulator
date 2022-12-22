package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.Pair;
import java.util.Optional;

class WaitEvent implements Event {
    private final Customer customer;
    private final double time;
    private final int serverIndex;
    private final Server server;

    WaitEvent(Customer customer, double time, int serverIndex, Server server) {
        this.customer = customer;
        this.time = time;
        this.serverIndex = serverIndex;
        this.server = server;
    }

    @Override
    public String getType() {
        return "WAIT";
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
        ImList<Server> updatedList = shop.getServerList();
        server = server.update(time, getCustomer());
        updatedList = updatedList.set(serverIndex, server);
        return Pair.of(Optional.<Event>of(new PendingEvent(customer, time, serverIndex, server)),
                new Shop(updatedList));
    }

    double getWaitingTime() {
        return this.time - this.customer.getArrivalTime();
    }

    @Override
    public String toString() {
        return String.format("%.3f %s waits at %s", time, customer.getId(), server.getId());
    }
}
