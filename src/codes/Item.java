package codes;

public class Item {

	private int hopperNumber;
	private int itemNumber;

	public Item(int numTolva, int numElemento) {
		this.hopperNumber = numTolva;
		this.itemNumber = numElemento;
	}

	public int getHopperNumber() {
		return hopperNumber;
	}

	public int getItemNumber() {
		return itemNumber;
	}
}
