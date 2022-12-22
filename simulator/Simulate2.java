package cs2030.simulator;

import cs2030.util.PQ;
import java.util.List;

public class Simulate2 {
    private final int numOfServers;
    private final PQ<Event> pq;
    private final Shop shop;

    public Simulate2(int numOfServers, List<Double> arrivalTimes) {
        this.numOfServers = numOfServers;
        Shop shop = new Shop();
        PQ<Event> pq = new PQ<>(new EventComparator());
        for (int i = 0; i < numOfServers; i++) {
            shop = shop.add(new Server((i + 1)));
        }
        for (int i = 0; i < arrivalTimes.size(); i++) {
            Customer customer = new Customer((i + 1), arrivalTimes.get(i));
            Event newEventStub = new EventStub(customer, arrivalTimes.get(i));
            pq = pq.add(newEventStub);
        }
        this.pq = pq;
        this.shop = shop;
    }

    public String run() {
        String returnString = "";
        PQ<Event> temPQ = new PQ<Event>(pq);
        while (!temPQ.isEmpty()) {
            returnString += temPQ.poll().first() + "\n";
            temPQ = temPQ.poll().second();
        }
        return returnString + "-- End of Simulation --";
    }

    @Override
    public String toString() {
        return String.format("Queue: %s; Shop: %s", pq.toString(), shop.toString());
    }
}
