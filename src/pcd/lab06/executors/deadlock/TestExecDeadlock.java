package pcd.lab06.executors.deadlock;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// #ESAME
// Sono una sw e devo fare una progettazione con executor di un certo insieme di task con un fixed theread pool con cpu + 1 thread
// I task eseguono due sezioni: A e B
//Arriva una richiesta di estensione, dobbiamo fare in modo che tutti i task eseguano la parte B solo dopo che tutti hanno
//Eseguito la parte A (un punto di sync)
//Come risolviamo ?


//Potremmo dire: usiamo una barriera ciclica di sincronizzazione inizializzata al numero di task che devo aspettare
// (in questo codice 100, nTasks)

//Che succede ? DEADLOCK, perchè se ad esempio ho 16 core logici e quindi un pool con 17 thread quando faccio la wait
// sulla barriera non blocco solo il task ma i thread dell'executor quindi i primi 17 task eseguiranno A ma poi ho finito i
//thread e quindi gli altri task non riusciranno ad eseguire A e ciao ciao

//Come potremmo risolverlo bene?
//Per esempio decomponendo i task in MyTaskA e MyTaskB e mandandoli in esecuzione nell'ordine giusto

//Oppure usando il fork join pattern



public class TestExecDeadlock {

	public static void main(String[] args) throws Exception {

		int nTasks = 100; 
		int nThreads = Runtime.getRuntime().availableProcessors() + 1;
		
		ExecutorService exec = Executors.newFixedThreadPool(nThreads);
		CyclicBarrier barrier = new CyclicBarrier(nTasks);
				
		for (int i = 0; i < nTasks; i++) {
			exec.execute(new MyTask("task-" + i, barrier));
		}		
		
		exec.shutdown();
		exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);		
	}
}


