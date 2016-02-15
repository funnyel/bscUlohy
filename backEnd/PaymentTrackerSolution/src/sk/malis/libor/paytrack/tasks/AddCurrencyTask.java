package sk.malis.libor.paytrack.tasks;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import sk.malis.libor.paytrack.Verifier;


public class AddCurrencyTask implements Runnable {

	private final String currencyName;
	private final ConcurrentHashMap<String, AtomicLong> currencyMap;
	private final Verifier ver;
	
	public AddCurrencyTask (String name, ConcurrentHashMap<String, AtomicLong> curMap, Verifier v){
		super();
		this.currencyName = name;
		this.currencyMap = curMap;
		this.ver = v;
	}
	
	@Override
	public void run() {
		try {
			if(Thread.interrupted()){
				throw new InterruptedException();
				}else{
					if(this.ver != null && this.currencyMap != null){
						if(this.ver.isCorrectCurrencyName(this.currencyName)){
							this.currencyMap.putIfAbsent(this.currencyName, new AtomicLong(0));
							}	
						}	
				}
		}
		 catch (InterruptedException ie) {
			System.out.println("Task interrupted by user cancel");
			ie.printStackTrace();
			Thread.currentThread().interrupt();
		}
		

	}
}
