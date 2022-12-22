package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.Pair;
import java.util.Optional;

class ServeEvent implements Event {
    private final Customer customer;
    private final double time;
    private final int serverIndex;
    private final Server server;

    ServeEvent(Customer customer, double time, int serverIndex, Server server) {
        this.customer = customer;
        this.time = time;
        this.serverIndex = serverIndex;
        this.server = server;
    }

    @Override
    public String getType() {
        return "SERVE";
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
        /**
         * Proceed from SERVE to next event DONE. Updates the server that is currently
         * serving the customer. Will add it to queue if it is not added ie previous event
         * is ARRIVE. If it is already added, will update the queue.
         * @return next event
         */
        Server server = shop.getServerList().get(serverIndex);
        ImList<Server> updatedList = shop.getServerList();
//        if (server.isAvailable()) {
            server = server.update(getTime(), customer);
//        } else {
//            server = server.update();
//        }
        updatedList = updatedList.set(serverIndex, server);
        return Pair.of(Optional.<Event>of(new DoneEvent(getCustomer(),
                server.getNextAvailableTime(), serverIndex, server)),
                new Shop(updatedList));
    }

    double getWaitingTime() {
        return this.time - this.customer.getArrivalTime();
    }

    @Override
    public String toString() {
        return String.format("%.3f %s serves by %s", time, customer.getId(), server.getId());
    }
}
