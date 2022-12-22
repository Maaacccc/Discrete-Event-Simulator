package cs2030.simulator;

public class Statistic {
    private final double totWaitingTime;
    private final int totCustomerNum;
    private final int totCustomerServed;

    private Statistic(int totCustomerNum) {
        this.totWaitingTime = 0;
        this.totCustomerNum = totCustomerNum;
        this.totCustomerServed = 0;
    }

    private Statistic(double totWaitingTime, int totCustomerNum, int totCustomerServed) {
        this.totWaitingTime = totWaitingTime;
        this.totCustomerNum = totCustomerNum;
        this.totCustomerServed = totCustomerServed;
    }

    public static Statistic initializeStatistics(int totCustomerNum) {
        return new Statistic(totCustomerNum);
    }

    public Statistic addCustomerServed() {
        return new Statistic(this.totWaitingTime, this.totCustomerNum,
                this.totCustomerServed + 1);
    }

    public Statistic addWaitingTime (double time) {
        return new Statistic(this.totWaitingTime + time,
                this.totCustomerNum, this.totCustomerServed);
    }

    private double averageWaitingTime() {
        return this.totWaitingTime / this.totCustomerServed;
    }

    private int CustomersLeftNum() {
        return this.totCustomerNum - this.totCustomerServed;
    }

    @Override
    public String toString() {
        if (totCustomerServed == 0) {
            return String.format("[%.3f %d %d]", 0.0, this.totCustomerServed, CustomersLeftNum());
        } else {
            return String.format("[%.3f %d %d]", averageWaitingTime(),
                    this.totCustomerServed, CustomersLeftNum());
        }
    }
}
