package cs2030.simulator;

import cs2030.util.PQ;
import cs2030.util.Pair;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class Simulate6 {
    private final int numOfServers;
    private final PQ<Event> pq;
    private final Shop shop;
    private final int customerNum;

    public Simulate6(int numOfServers, List<Pair<Double,
            Supplier<Double>>> inputTimes, int qMax) {
        this.numOfServers = numOfServers;
        Shop shop = new Shop();
        PQ<Event> pq = new PQ<>(new EventComparator());
        for (int i = 0; i < numOfServers; i++) {
            shop = shop.add(new Server((i + 1), qMax));
        }
        for (int i = 0; i < inputTimes.size(); i++) {
            Customer customer = new Customer((i + 1), inputTimes.get(i).first(),
                    inputTimes.get(i).second());
            Event arriveEvent = new ArriveEvent(customer, inputTimes.get(i).first());
            pq = pq.add(arriveEvent);
        }
        this.pq = pq;
        this.shop = shop;
        this.customerNum = inputTimes.size();
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
            Pair<Optional<Event>, Shop> nextPair = firstEvent.execute(shop);
            Event nextEvent = nextPair.first()
                    .orElse(new EventStub(new Customer(0, 0), 0));
            shop = nextPair.second();
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
