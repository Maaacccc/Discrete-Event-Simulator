package cs2030.simulator;

import cs2030.util.PQ;
import java.util.List;

public class Simulate4 {
    private final int numOfServers;
    private final PQ<Event> pq;
    private final Shop shop;
    private final int customerNum;

    public Simulate4(int numOfServers, List<Double> arrivalTimes) {
        this.numOfServers = numOfServers;
        Shop shop = new Shop();
        PQ<Event> pq = new PQ<>(new EventComparator());
        for (int i = 0; i < numOfServers; i++) {
            shop = shop.add(new Server((i + 1)));
        }
        for (int i = 0; i < arrivalTimes.size(); i++) {
            Customer customer = new Customer((i + 1), arrivalTimes.get(i));
            Event arriveEvent = new ArriveEvent(customer, arrivalTimes.get(i));
            pq = pq.add(arriveEvent);
        }
        this.pq = pq;
        this.shop = shop;
        this.customerNum = arrivalTimes.size();
    }

    public String run() {
        String returnString = "";
        PQ<Event> queue = pq;
        Shop shop = this.shop;
        Statistic stats = Statistic.initializeStatistics(customerNum);
        while (!queue.isEmpty()) {
            Event firstEvent = queue.poll().first();
            if (!firstEvent.getType().equals("PENDING") &&
                    !firstEvent.getType().equals("REST")) {
                returnString += firstEvent.toString() + "\n";
            }
            queue = queue.poll().second();
            if (firstEvent.getType().equals("SERVE")) {
                stats = stats.addWaitingTime(firstEvent.getTime() -
                        firstEvent.getCustomer().getArrivalTime());
            } else if (firstEvent.getType().equals("DONE")) {
                stats = stats.addCustomerServed();
            }
            Event nextEvent = firstEvent.execute(shop).first()
                    .orElse(new EventStub(new Customer(0, 0), 0));
            shop = firstEvent.execute(shop).second();
            if (!nextEvent.equals(new EventStub(new Customer(0, 0), 0))) {
                queue = queue.add(nextEvent);
            }
        }
        return returnString + stats;
    }

    @Override
    public String toString() {
        return String.format("Queue: %s; Shop: %s", pq.toString(), shop.toString());
    }
}

