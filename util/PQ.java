package cs2030.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class PQ<T> implements Iterable<T> {
    private final PriorityQueue<T> pq;

    public PQ(Comparator<? super T> com) {
        this.pq = new PriorityQueue<>(com);
    }

    private PQ(PriorityQueue<T> pq) {
        this.pq = new PriorityQueue<>(pq);
    }

    public PQ(PQ<T> pq) {
        this(pq.pq);
    }

    public PQ<T> add(T elem) {
        PQ<T> newPQ = new PQ<T>(this);
        newPQ.pq.add(elem);
        return newPQ;
    }

    public Pair<T, PQ<T>> poll() {
        PQ<T> newPQ = new PQ<T>(this);
        T t = newPQ.pq.poll();
        return Pair.<T, PQ<T>>of(t, newPQ);
    }

    int size() {
        return this.pq.size();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return this.pq.iterator();
    }

    @Override
    public String toString() {
        return this.pq.toString();
    }


}
