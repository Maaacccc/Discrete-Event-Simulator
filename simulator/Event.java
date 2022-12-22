package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

public interface Event {
    Pair<Optional<Event>, Shop> execute(Shop shop);

    boolean equals(Event other);

    double getTime();

    Customer getCustomer();

    int getCustomerId();

    String getType();


}


