package pcd.lab04.monitors.barrier;

import java.util.ArrayList;
import java.util.List;

public class TestBarrier {

	public static void main(String[] args) {
		
		int nWorkers = 10;
		
		Barrier barrier = new FakeBarrier(nWorkers);
		Barrier realBarrier = new BarrierImpl(nWorkers);
		
		List<Worker> workers = new ArrayList<Worker>();
		for (int i = 0; i < nWorkers; i++) {
			workers.add(new Worker("Worker-"+i, realBarrier));
		}

		for (Worker w: workers) {
			w.start();
		}
		
	}
}
