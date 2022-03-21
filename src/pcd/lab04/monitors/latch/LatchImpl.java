package pcd.lab04.monitors.latch;

public class LatchImpl implements Latch {

    private int count;

    public LatchImpl(final int count){
        this.count = count;
    }

    @Override
    public synchronized void countDown() {
        this.count = this.count - 1;
        if(this.count == 0){
            notifyAll();
        }
    }

    @Override
    public synchronized void await() throws InterruptedException {
        while(this.count > 0){
            wait();
        }
    }
}
