package cs2030.simulator;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        double timeDiff = e1.getTime() - e2.getTime();
        if (timeDiff > 0) {
            return 1;
        } else if (timeDiff < 0) {
            return -1;
        }
        return  e1.getCustomer().getId() - e2.getCustomer().getId();
    }
}
