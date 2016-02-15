package sk.malis.libor.paytrack.dataholderclasses;

public class Payment {

	private final String currName;
	private final long value;
	
	public Payment (String s, long val){
		this.currName = s;
		this.value = val;
	}

	public String getCurrName() {
		return currName;
	}

	public long getValue() {
		return value;
	}
	
}
