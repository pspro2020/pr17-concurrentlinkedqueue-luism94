package codes;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		CountDownLatch hopperCounter = new CountDownLatch(Constants.NUM_OF_HOPPERS);
		Separator separator = new Separator();
		Thread[] hopperMachines = new Thread[Constants.NUM_OF_HOPPERS];
		Thread separatorMachine = new Thread(separator, "Separator Machine");
		Hopper[] hopperArray = new Hopper[Constants.NUM_OF_HOPPERS];

		// Creo los hilos secundarios que van a controlar las tolvas
		for (int i = 0; i < Constants.NUM_OF_HOPPERS; i++) {
			hopperArray[i] = new Hopper(i, separator, hopperCounter);
			hopperArray[i].fillHopper();
		}
		
		for (int i = 0; i < Constants.NUM_OF_HOPPERS; i++) {
			hopperMachines[i] = new Thread(hopperArray[i]);
		}
		
		// Se inician las tolvas
		for (int i = 0; i < Constants.NUM_OF_HOPPERS; i++) {
			hopperMachines[i].start();
		}

		try {
			// Despues de 3 segundos de que se inicien las tolvas la separadora se inicia
			TimeUnit.SECONDS.sleep(Constants.SEPARATOR_START_WAITING_TIME);
			System.out.println(
					String.format(Constants.SEPARATOR_START, LocalDateTime.now().format(Constants.TIME_FORMATTER)));
			separatorMachine.start();
		} catch (InterruptedException e) {
			System.out.println(
					String.format(Constants.MAIN_INTERRUPTED, LocalDateTime.now().format(Constants.TIME_FORMATTER)));
			return;
		}
	}
}
