package pcd.lab04.monitors.barrier;

public class BarrierImpl implements Barrier{

    private final int nPartecipants;
    private int nArrivedPartecipants;

    public BarrierImpl(final int nPartecipants){
        this.nPartecipants = nPartecipants;
        this.nArrivedPartecipants = 0;
    }

    @Override
    public synchronized void hitAndWaitAll() throws InterruptedException {
        this.nArrivedPartecipants = this.nArrivedPartecipants + 1;
        while(this.nArrivedPartecipants < this.nPartecipants){
            wait();
        }
        notifyAll();
    }
}
