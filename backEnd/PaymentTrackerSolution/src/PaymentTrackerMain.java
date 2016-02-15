import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import sk.malis.libor.paytrack.PaymentTrackerWorkManager;
import sk.malis.libor.paytrack.Verifier;
import sk.malis.libor.paytrack.dataholderclasses.Payment;
import sk.malis.libor.paytrack.dataholderclasses.UsdRate;



public class PaymentTrackerMain {
	
	private static void loadFromInputFile(File input, String inputFilePath, Scanner fScan, Scanner lScan, String first, String second, Verifier ver, ArrayList<Payment> pList){
		input = new File(inputFilePath);
		if(input.exists()){
			try {
				fScan = new Scanner(input);
			while (fScan.hasNextLine()) {
				lScan = new Scanner(fScan.nextLine());
				try{
					first = lScan.next();
					second = lScan.next();
					lScan.close();
					if(ver.isCorrectCurrencyName(first)&&ver.isCorrectPaymentValue(second)){
						pList.add(new Payment(first, Long.parseLong(second)));
					}
				}catch (NoSuchElementException nse){
					System.out.println("Incorrect input format in file - missing value or currency");
				}
			}
			fScan.close();
			} catch (FileNotFoundException e) {
				System.out.println("Somehow file with inputs was not found:");
				e.printStackTrace();
			}
		}else{
			System.out.println("Inputs containing file specified by path does not exists. No data was loaded.");
		}
	}
	
	private static void loadFromRateFile(File rate, String rateFilePath, Scanner fScan, Scanner lScan, String first, String second, Verifier verif, ArrayList<UsdRate> rateList){
		rate = new File(rateFilePath);
		if(rate.exists()){
			try {
				fScan = new Scanner(rate);
			while (fScan.hasNextLine()) {
				lScan = new Scanner(fScan.nextLine());
				try{
					first = lScan.next();
					second = lScan.next();
					lScan.close();
					if(verif.isCorrectCurrencyName(first)&&verif.isCorrectRateValue(second)){
						rateList.add(new UsdRate(first, Double.parseDouble(second)));
					}
				}catch (NoSuchElementException nse){
					System.out.println("Incorrect rate format in file - missing rate or currency");
				}
			}
			fScan.close();
			} catch (FileNotFoundException e) {
				System.out.println("Somehow file with rates was not found:");
				e.printStackTrace();
			}
		
	}else{
		System.out.println("Rates containing file specified by path does not exists. No data was loaded.");
		}
	}
	
	private static void addPayTrackWorkManagerJobsAndListOut(PaymentTrackerWorkManager payTrkWrkMng, ArrayList<Payment> pays, ArrayList<UsdRate> rates){
		UsdRate[] ratesz = new UsdRate[0];
		Payment[] payz = new Payment[0];
		payTrkWrkMng.addUSDRates(rates.toArray(ratesz));
		String[] currencies = new String[pays.size()];
		for(int u=0; u<currencies.length; u++){
			currencies[u] = pays.get(u).getCurrName();
		}
		payTrkWrkMng.addCurrencies(currencies);
		payTrkWrkMng.startListingOutAllCurrenciesAndValues();
		payTrkWrkMng.applyPayments(pays.toArray(payz));
	}
	
	public static void main(String[] args) {
		if(args.length>0&&"start".equals(args[0])){
			Verifier verif = new Verifier();
			ArrayList<Payment> payList = new ArrayList<Payment>();
			ArrayList<UsdRate> rateList = new ArrayList<UsdRate>();
			boolean finished = false;
			PaymentTrackerWorkManager payTrackWorkManage = new PaymentTrackerWorkManager(verif);
			if(args.length == 5){
				File inputs = null;
				File rates = null;
				Scanner fileScan = null;
				Scanner lineScan = null;
				String fstStr = null;
				String sndStr = null;
				if("-input".equals(args[1])){
					loadFromInputFile(inputs, args[2], fileScan, lineScan, fstStr, sndStr, verif, payList);
					if("-rates".equals(args[3])){
					loadFromRateFile(rates, args[4], fileScan, lineScan, fstStr, sndStr, verif, rateList);
					
				}else{
					System.out.println("Incorrect parameter '-rates' starting with only loading inputs");
					}
					addPayTrackWorkManagerJobsAndListOut(payTrackWorkManage, payList, rateList);
				}
			else if("-rates".equals(args[1])){
					loadFromRateFile(rates, args[2], fileScan, lineScan, fstStr, sndStr, verif, rateList);
					if("-input".equals(args[3])){
						loadFromInputFile(inputs, args[4], fileScan, lineScan, fstStr, sndStr, verif, payList);
					}else{
						System.out.println("Incorrect parameter '-input' starting with only loading rates");
					}
					addPayTrackWorkManagerJobsAndListOut(payTrackWorkManage, payList, rateList);
				}else{
					System.out.println("Incorrect start parameter formats, starting without parameters");
				}
			}else if(args.length == 3){
				if("-input".equals(args[1])){
					File inputs = null;
					Scanner fileScan = null;
					Scanner lineScan = null;
					String fstStr = null;
					String sndStr = null;
					loadFromInputFile(inputs, args[2], fileScan, lineScan, fstStr, sndStr, verif, payList);
					Payment[] payz = new Payment[0];
					String[] currencies = new String[payList.size()];
					for(int u=0; u<currencies.length; u++){
						currencies[u] = payList.get(u).getCurrName();
					}
					payTrackWorkManage.addCurrencies(currencies);
					payTrackWorkManage.applyPayments(payList.toArray(payz));
					}else if("-rates".equals(args[1])){
						File rates = null;
						Scanner fileScan = null;
						Scanner lineScan = null;
						String fstStr = null;
						String sndStr = null;
						loadFromRateFile(rates, args[2], fileScan, lineScan, fstStr, sndStr, verif, rateList);
						UsdRate[] ratesz = new UsdRate[0];
						payTrackWorkManage.addUSDRates(rateList.toArray(ratesz));
					}
					else{
						System.out.println("Incorrect start parameter, starting without parameter.");
					}
				payTrackWorkManage.startListingOutAllCurrenciesAndValues();
			}else if(args.length == 1){
				payTrackWorkManage.startListingOutAllCurrenciesAndValues();
			}else{
				System.out.println("Incorrect start number parameters, starting without parameters.");
				payTrackWorkManage.startListingOutAllCurrenciesAndValues();
			}
			Scanner consoleScan = new Scanner(System.in);
			Scanner lineScanner;
			String firstStr;
			String secondStr;
			String thirdStr;
			while ((!finished)&&consoleScan.hasNextLine()) {
				lineScanner = new Scanner(consoleScan.nextLine());
				firstStr = lineScanner.next();
				
				try {
					if("-rate".equals(firstStr)){
						secondStr = lineScanner.next();
						thirdStr = lineScanner.next();
						if((verif.isCorrectCurrencyName(secondStr))&&(verif.isCorrectRateValue(thirdStr))){
							UsdRate rate = new UsdRate(secondStr, Double.parseDouble(thirdStr));
							payTrackWorkManage.addUSDRate(rate);
							lineScanner.close();
						}else{
							System.out.println("Incorrect user input - currency name or rate value is not in correct format");
						}
					}else if (!"quit".equals(firstStr)){
						secondStr = lineScanner.next();
						if((verif.isCorrectCurrencyName(firstStr))&&(verif.isCorrectPaymentValue(secondStr))){
							Payment pay = new Payment(firstStr, Long.parseLong(secondStr));
							payTrackWorkManage.addCurency(pay.getCurrName());
							payTrackWorkManage.applyPayment(pay);
							lineScanner.close();
						}else{
							System.out.println("Incorrect user input - currency name or value is not in correct format");
						}
					}else{
						finished = true;
						payTrackWorkManage.programCancellationByUser();
						consoleScan.close();
					}
				} catch (NoSuchElementException nsee) {
					System.out.println("Incorrect user input - not enough parameters");
				}
		}
		
		}
		
		
	}

}
