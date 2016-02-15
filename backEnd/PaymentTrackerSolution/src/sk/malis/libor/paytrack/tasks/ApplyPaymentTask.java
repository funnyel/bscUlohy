package sk.malis.libor.paytrack.tasks;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import sk.malis.libor.paytrack.Verifier;
import sk.malis.libor.paytrack.dataholderclasses.Payment;


public class ApplyPaymentTask implements Runnable {

	
	private final Payment pay;
	private final ConcurrentHashMap<String, AtomicLong> currencyMap;
	private final Verifier verificat;
	
	public ApplyPaymentTask(Payment p, ConcurrentHashMap<String, AtomicLong> currMap, Verifier ve){
	this.pay = p;
	this.currencyMap = currMap;
	this.verificat = ve;
	}
	
	@Override
	public void run() {
		
		try {
			
			if(Thread.interrupted()){
				throw new InterruptedException();
			}else{
				if(this.pay != null && this.verificat != null && this.currencyMap != null){
					if(this.verificat.isCorrectCurrencyName(this.pay.getCurrName())){
						if(this.currencyMap.containsKey(this.pay.getCurrName())){
							this.currencyMap.get(this.pay.getCurrName()).addAndGet(this.pay.getValue());
						}
					}	
				}
				
			}
		} catch (InterruptedException ie) {
			System.out.println("Queued payment canceled by user cancel commmand");
			ie.printStackTrace();
			Thread.currentThread().interrupt();
		}
		
		

	}

}
