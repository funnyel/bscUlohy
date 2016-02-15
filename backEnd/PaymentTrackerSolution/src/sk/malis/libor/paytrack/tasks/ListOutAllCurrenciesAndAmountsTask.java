package sk.malis.libor.paytrack.tasks;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ListOutAllCurrenciesAndAmountsTask implements Runnable {

	private final ConcurrentHashMap<String, AtomicLong> currencyValueMap;
	private final HashMap<String, Double> uSDRateMap;
	private boolean finished;
	private boolean hasRates;
	private final long minuteToMilis;
	
	public ListOutAllCurrenciesAndAmountsTask (ConcurrentHashMap<String, AtomicLong> curValMap, HashMap<String, Double> usdRatMap){
		this.currencyValueMap = curValMap;
		this.uSDRateMap = usdRatMap;
		this.finished = false;
		this.hasRates = false;
		this.minuteToMilis = 60000;
	}
	
	public void setUserFinished(){
		this.finished = true;
	}
	
	@Override
	public void run() {
		StringBuilder sb = new StringBuilder();
		while(!this.finished){
		if(this.currencyValueMap != null){
			if(this.uSDRateMap!=null&&this.uSDRateMap.size()>0){
				this.hasRates = true;
			}else{
			this.hasRates = false;
			System.out.println("UsdRates structure is null or empty, no rates data is stored and therefore no data is used, showing only list of currency-and-values!");	
			}
			String currentKey = "";
			long currentValue = 0;
			for(Enumeration<String> currMapKeys = this.currencyValueMap.keys(); currMapKeys.hasMoreElements();){
				currentKey = currMapKeys.nextElement();
				currentValue = ((AtomicLong)this.currencyValueMap.get(currentKey)).get();
				if(currentValue!=0){
					sb.append(currentKey);
					sb.append(' ');
					sb.append(currentValue);
					if(this.hasRates && this.uSDRateMap.containsKey(currentKey)){
						
						sb.append(" (USD ");
						sb.append((currentValue/this.uSDRateMap.get(currentKey).doubleValue()));
						sb.append(')');
					}
					sb.append('\n');
					System.out.print(sb.toString());
					currentKey = "";
					currentValue = 0;
					sb.setLength(0);
				}
			}
		}else{
			System.out.println("The currency-and-value data structure is null, no currency-and-value information is stored");
		}
		try {
			Thread.sleep(this.minuteToMilis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("The writing-out-all-currencies thread was interrupted because of(probably user program cancellation):\n");
			e.printStackTrace();
			this.finished = true;
			Thread.currentThread().interrupt();
		} catch (IllegalArgumentException en) {
			System.out.println("The inputed milliseconds was not correct: \n");
			en.printStackTrace();
		}
		this.hasRates = false;
		}
	}

}
