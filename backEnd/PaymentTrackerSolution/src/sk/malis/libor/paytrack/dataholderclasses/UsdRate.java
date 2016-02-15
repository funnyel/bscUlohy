package sk.malis.libor.paytrack.dataholderclasses;

public class UsdRate {

	private String currency;
	private double rate;
	
	public UsdRate (String cur, double rate2){
		this.currency = cur;
		this.rate = rate2;
	}

	public String getCurrency() {
		return currency;
	}

	public double getRate() {
		return rate;
	}
	
	
	
}
