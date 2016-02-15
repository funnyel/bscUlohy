package sk.malis.libor.paytrack;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import sk.malis.libor.paytrack.dataholderclasses.Payment;
import sk.malis.libor.paytrack.dataholderclasses.UsdRate;
import sk.malis.libor.paytrack.tasks.AddCurrencyTask;
import sk.malis.libor.paytrack.tasks.ApplyPaymentTask;
import sk.malis.libor.paytrack.tasks.ListOutAllCurrenciesAndAmountsTask;



public class PaymentTrackerWorkManager {

	private ExecutorService executorService;
	private ConcurrentHashMap<String, AtomicLong> currencyHashMap;
	private Verifier verif;
	private HashMap<String, Double> uSDRates;
	private ListOutAllCurrenciesAndAmountsTask listOutAll;
	private Thread listingThread;
	
	public PaymentTrackerWorkManager( Verifier v) {
		super();
		this.executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.currencyHashMap = new ConcurrentHashMap<String, AtomicLong>();
		this.verif = v;
		this.uSDRates = new HashMap<String, Double>();
		this.listOutAll = new ListOutAllCurrenciesAndAmountsTask(this.currencyHashMap, this.uSDRates);
	}
	
	public void addCurency (String s){
		AddCurrencyTask act = new AddCurrencyTask(s, this.currencyHashMap, this.verif);
		this.executorService.execute(act);
	}
	
	public void addCurrencies (String[] currs){
		for (int i = 0; i < currs.length; i++) {
			this.addCurency(currs[i]);
		}
	}
	
	public void applyPayment (Payment p){
		ApplyPaymentTask apt = new ApplyPaymentTask(p, this.currencyHashMap, this.verif);
		this.executorService.execute(apt);
	}
	
	public void applyPayments(Payment[] pays){
		for(int r=0; r<pays.length; r++){
			this.applyPayment(pays[r]);
		}
	}
	
	public void addUSDRates (UsdRate[] rates){
		for (int e=0; e<rates.length; e++){
			this.addUSDRate(rates[e]);
		}
	}
	
	public void addUSDRate(UsdRate rate){
		if(this.verif.isCorrectCurrencyName(rate.getCurrency())){
			if(!this.uSDRates.containsKey(rate.getCurrency())){
				this.uSDRates.put(rate.getCurrency(), new Double(rate.getRate()));
			}
		}
	}
	
	public void startListingOutAllCurrenciesAndValues(){
		this.listingThread = new Thread(this.listOutAll);
		this.listingThread.start();
	}
	
	public void programCancellationByUser(){
		this.listOutAll.setUserFinished();
		this.executorService.shutdownNow();
		while(!this.executorService.isTerminated()){
			//wait until currently running tasks is finished 
			//(max number of tasks waiting for to finnish is the number of cores)....
		}
		this.listingThread.interrupt();
	}
}
