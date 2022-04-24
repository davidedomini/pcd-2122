package pcd.lab07.vertx;

import java.util.Random;
import io.vertx.core.*;


class TestPromise extends AbstractVerticle {

	public Future<Double> getRandomValue(){
		Promise<Double> promise = Promise.promise();
		//questo pezzo di codice è quello che mettiamo di solito nella parte di librerira che vuole fornire API asincrone
		this.getVertx().setTimer(1000, res -> {
			log("timeout from the timer...");
			Random rand = new Random();
			double value = rand.nextDouble();
			if (value > 0.5) {
				log("...complete with success.");
				promise.complete(value);
			} else {
				log("...complete with failure.");
				promise.fail("Value below 0.5 " + value);
			}
		});
		log("Ritorno la future");
		return promise.future();
	}
	//public void start() {
		//log("pre");

//		//Creo una promise dicendogli il tipo di risultato che mi aspetto
//		//Al contrario di js non devo specificargli subito la callback
//		Promise<Double> promise = Promise.promise();
//
//		//questo pezzo di codice è quello che mettiamo di solito nella parte di librerira che vuole fornire API asincrone
//		this.getVertx().setTimer(1000, res -> {
//			log("timeout from the timer...");
//			Random rand = new Random();
//			double value = rand.nextDouble();
//			if (value > 0.5) {
//				log("...complete with success.");
//				promise.complete(value);
//			} else {
//				log("...complete with failure.");
//				promise.fail("Value below 0.5 " + value);
//			}
//		});
//
//		//Qui dalla promise lato libreria vado a prendermi la future da restituire al client
//		Future<Double> fut = promise.future();

		//Lato client uso la future
//		fut
//		.onSuccess((Double res) -> {
//			log("reacting to timeout - success: " + res);
//		})
//		.onFailure((Throwable t) -> {
//			log("reacting to timeout - failure: " + t.getMessage());
//		})
//		.onComplete((AsyncResult<Double> res) -> {
//			log("reacting to completion - " + res.succeeded());
//		});
//
//		log("post");
	//}

	private void log(String msg) {
		System.out.println("[REACTIVE AGENT] " + msg);
	}
}



public class Step4_promise {
	public static void main(String[] args) {
		
		Vertx vertx = Vertx.vertx();
		TestPromise t = new TestPromise();
		vertx.deployVerticle(t);

		t.getRandomValue()
			.onSuccess((Double res) -> {
				System.out.println("reacting to timeout - success: " + res);
			})
			.onFailure((Throwable th) -> {
				System.out.println("reacting to timeout - failure: " + th.getMessage());
			});
	}
}

