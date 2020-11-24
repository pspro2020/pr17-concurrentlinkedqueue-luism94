package codes;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Hopper implements Runnable {

	private Random rndmGenerator;
	private LinkedList<Item> itemList = new LinkedList<Item>();
	private int hopperNumber;
	private Separator separator;
	// Objeto CountDownLatch para que las tolvas puedan esperarse unas a las otras
	// despues de que se inicien
	private CountDownLatch hopperCounter;

	public Hopper(int hopperNumber, Separator separator, CountDownLatch hopperCounter) {
		this.hopperNumber = hopperNumber;
		this.separator = separator;
		this.hopperCounter = hopperCounter;
	}

	public void fillHopper() {
		for (int i = 0; i < Constants.HOPPER_CAPACITY; i++) {
			itemList.add(new Item(hopperNumber, i));
		}
	}

	private Item dropItem() throws InterruptedException {
		return itemList.removeFirst();
	}

	// Metodo para obligar a esperar a la separadora a que se inicien todas las
	// tolvas
	private void waitAllHoppersToStart() throws InterruptedException {
		hopperCounter.await();
	}

	// Metodo de las tolvas para avisar a la separadora de que se ha puesto en
	// marcha
	private void hopperStarted() {
		hopperCounter.countDown();
	}

	@Override
	public void run() {
		try {
			System.out.println(String.format(Constants.HOPPER_START,
					LocalDateTime.now().format(Constants.TIME_FORMATTER), hopperNumber));
			// Se avisa a la separadora de que la tolva se ha iniciado para que cuando pasen
			// 3 segundos desde que se inicia la ultima, la separadora pueda iniciar su
			// trabajo
			hopperStarted();
			// La tolva actual se queda esperando a que se inicien el resto de tolvas
			waitAllHoppersToStart();
		} catch (InterruptedException e1) {
			System.out.println(String.format(Constants.HOPPER_INTERRUPTED_AT_START,
					LocalDateTime.now().format(Constants.TIME_FORMATTER), hopperNumber));
			return;
		}

		// Todas las tolvas empiezan a trabajar a la vez a partir de este punto

		// Bloque que se repite hasta que la tolva deja caer su ultimo elemento o hasta
		// que el hilo secundario de la tolva es interrumpido
		while (!Thread.currentThread().isInterrupted() && !itemList.isEmpty()) {
			try {
				// La tolva deja caer uno de sus elementos a la cinta transportadora de la
				// separadora
				TimeUnit.SECONDS.sleep(rndmGenerator.nextInt(Constants.HOPPER_TIME_SPAN) + Constants.HOPPER_TIME_MIN);
				separator.getItemFromHopper(dropItem());
			} catch (InterruptedException e) {
				System.out.println(String.format(Constants.HOPPER_INTERRUPTED,
						LocalDateTime.now().format(Constants.TIME_FORMATTER), hopperNumber));
				return;
			}
		}
		// Cuando la tolva esta vacia termina su trabajo
		System.out.println(
				String.format(Constants.HOPPER_FINISH, LocalDateTime.now().format(Constants.TIME_FORMATTER), hopperNumber));
	}
}
