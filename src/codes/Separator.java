package codes;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class Separator implements Runnable {

	private ConcurrentLinkedQueue<Item> separatorBelt = new ConcurrentLinkedQueue<Item>();
	private Random rndmGenerator = new Random();

	// Metodo que añade el elemento que una de las tolvas deja caer sobre la cinta
	// de la separadora
	public void getItemFromHopper(Item elemento) {
		separatorBelt.add(elemento);
	}

	// Metodo de la separadora que clasifica el siguiente elemento de su cinta
	// transportadora
	private void classifyItem() throws InterruptedException {
		System.out.println(String.format(Constants.SEPARATOR_CLASSIFY,
				LocalDateTime.now().format(Constants.TIME_FORMATTER), Thread.currentThread().getName(),
				separatorBelt.element().getItemNumber(), separatorBelt.element().getHopperNumber()));
		TimeUnit.SECONDS.sleep(rndmGenerator.nextInt(Constants.SEPARATOR_TIME_SPAN) + Constants.SEPARATOR_TIME_MIN);
		separatorBelt.remove();
	}

	// Instrucciones del hilo secundario que controla la separadora
	@Override
	public void run() {
		// Se ejecuta el hilo mientras no sea interrumpido y mientras su cinta no esta
		// vacia
		while (!Thread.currentThread().isInterrupted() && (separatorBelt.peek() != null)) {
			// Despues de que las tolvas se inicien a la vez la separadora espera 3 segundos
			// antes de empezar a trabajar
			try {
				TimeUnit.SECONDS.sleep(Constants.SEPARATOR_START_WAITING_TIME);
				classifyItem();
			} catch (InterruptedException e) {
				System.out.println(String.format(Constants.SEPARATOR_INTERRUPTED,
						LocalDateTime.now().format(Constants.TIME_FORMATTER), Thread.currentThread().getName()));
				return;
			}
		}

		System.out.println(String.format(Constants.SEPARATOR_FINISH,
				LocalDateTime.now().format(Constants.TIME_FORMATTER), Thread.currentThread().getName()));
	}
}
