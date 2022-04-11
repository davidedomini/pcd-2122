package pcd.lab07.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

class TestExecBlocking extends AbstractVerticle {

	public void start() {
		log("before");

		//La lambda che gli passiamo non è eseguita dall'event loop ma viene delegata ad un altro thread, quando questo ha concluso
		//executeBlocking ci restituisce una future...
		Future<Integer> res = this.getVertx().executeBlocking(promise -> {
			// Call some blocking API that takes a significant amount of time to return

			//Qui dentro non posso modificare variabili dichiarate fuori da questa lambda, se no creo corse critiche
			//Quindi per migliorare questo codice potrei spostarlo da un'altra parte in modo da avere incapsulamento

			log("blocking computation started");
			try {
				Thread.sleep(5000);
				
				/* notify promise completion */
				promise.complete(100);
			} catch (Exception ex) {
				
				/* notify failure */
				promise.fail("exception");
			}
		});

		log("after triggering a blocking computation...");

		//...E noi possiamo usare un onComplete
		res.onComplete((AsyncResult<Integer> r) -> {
			log("result: " + r.result());
		});
	}

	private void log(String msg) {
		System.out.println("[REACTIVE AGENT] [" + Thread.currentThread() + "] " + msg);
	}
}

public class Step6_withblocking {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new TestExecBlocking());
	}
}
