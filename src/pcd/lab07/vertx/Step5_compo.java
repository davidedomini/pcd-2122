package pcd.lab07.vertx;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

class TestCompo extends AbstractVerticle {
	
	public void start() {
		FileSystem fs = vertx.fileSystem();    		

		Promise<Buffer> p1 = Promise.promise();
		Promise<Buffer> p2 = Promise.promise();

		Future<Buffer> f1 = p1.future();
		Future<Buffer> f2 = p2.future();

		//Al posto che passargli la continuazione e dirgli di crearsi la sua promise gliela passo già io e gli dico di avvertirmi su quella
		fs.readFile("build.gradle.kts", p1);
		
		fs.readFile("settings.gradle", p2);
				
		CompositeFuture
		.all(f1,f2)
		.onSuccess((CompositeFuture res) -> {
			log("COMPOSITE => \n"+res.result().list());			
		}); 
	}

	private void log(String msg) {
		System.out.println("[REACTIVE AGENT] " + msg);
	}
}

public class Step5_compo {

	public static void main(String[] args) {
		Vertx  vertx = Vertx.vertx();
		vertx.deployVerticle(new TestCompo());
	}
}

