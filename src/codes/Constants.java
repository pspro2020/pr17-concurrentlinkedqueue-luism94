package codes;

import java.time.format.DateTimeFormatter;

public class Constants {

	static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
	static final String HOPPER_INTERRUPTED = "Time %s: --- Hopper %d interrupted while dropping items...";
	static final String OPEN_HOPPER = "Time %s: Hopper %d drops an item";
	static final String MAIN_INTERRUPTED = "Time %s: ERROR! MAIN THREAD INTERRUPTED";
	static final int HOPPER_CAPACITY = 5;
	static final int NUM_OF_HOPPERS = 5;
	static final int HOPPER_TIME_SPAN = 2;
	static final int HOPPER_TIME_MIN = 1;
	static final int SEPARATOR_TIME_SPAN = 2;
	static final int SEPARATOR_TIME_MIN = 1;
	static final String HOPPER_FINISH = "Time %s: Hopper %d is empty";
	static final String SEPARATOR_INTERRUPTED = "Time %s: --- %s interrupted while working...";
	static final String SEPARATOR_FINISH = "Time %s: %s finished working";
	static final String SEPARATOR_CLASSIFY = "Time %s: %s starts to classify item %d from hopper %d";
	static final String HOPPER_INTERRUPTED_AT_START = "Time %s: --- Hopper %d interrupted while waiting the others to start working...";
	static final String SEPARATOR_INTERRUPTED_AT_START = "Time %s: --- %s interrupted while waiting the hoppers to start working...";
	static final int SEPARATOR_START_WAITING_TIME = 3;
	static final String HOPPER_START = "Time %s: Hopper %d has started";
	static final String SEPARATOR_START = "Time %s: Separator has started";

}
