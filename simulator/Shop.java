package cs2030.simulator;

import cs2030.util.*;
import java.util.List;

public class Shop {
    private final ImList<Server> imListHuman;

    public Shop(List<Server> listHuman) {
        this.imListHuman = ImList.<Server>of(listHuman);
    }

    Shop(ImList<Server> imListHuman) {
        this.imListHuman = ImList.of(imListHuman);
    }

    public Shop() {
        this.imListHuman = ImList.<Server>of();
    }

    public Shop add(Server server) {
        ImList<Server> newImList = ImList.<Server>of(imListHuman);
        newImList = newImList.add(server);
        Shop newShop = new Shop(newImList);
        return newShop;
    }

    ImList<Server> getServerList() {
        return this.imListHuman;
    }

    @Override
    public String toString() {
        return this.imListHuman.toString();
    }
}
