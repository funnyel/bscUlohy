import static org.junit.Assert.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import sk.malis.libor.paytrack.Verifier;
import sk.malis.libor.paytrack.dataholderclasses.Payment;
import sk.malis.libor.paytrack.tasks.AddCurrencyTask;
import sk.malis.libor.paytrack.tasks.ApplyPaymentTask;


public class ApplyPaymentTest {

	Verifier ver = new Verifier();
	ConcurrentHashMap<String, AtomicLong> payMap;
	
	@Test
	public void testCorrectApply() {
		payMap = new ConcurrentHashMap<String, AtomicLong>();
		Payment pay = new Payment("GBP", 1000);
		AddCurrencyTask act = new AddCurrencyTask(pay.getCurrName(), payMap, ver);
		Thread t = new Thread(act);
		t.start();
		try {
			Thread.sleep(200);
			ApplyPaymentTask apt = new ApplyPaymentTask(pay, payMap, ver);
			Thread k = new Thread(apt);
			k.start();
			Thread.sleep(200);
			assertEquals(1000, payMap.get(pay.getCurrName()).get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Unit test thread interrupted somehow");
		} catch (Exception e){
			fail("Some other exception was thrown.");
		}
	}
	
	@Test
	public void testNotCorrectApply() {
		payMap = new ConcurrentHashMap<String, AtomicLong>();
		Payment pay = new Payment("GtP", 1000);
		AddCurrencyTask act = new AddCurrencyTask(pay.getCurrName(), payMap, ver);
		Thread t = new Thread(act);
		t.start();
		try {
			Thread.sleep(200);
			ApplyPaymentTask apt = new ApplyPaymentTask(pay, payMap, ver);
			Thread k = new Thread(apt);
			k.start();
			Thread.sleep(200);
			assertEquals(0, payMap.size());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Unit test thread interrupted somehow");
		} catch (Exception e){
			fail("Some other exception was thrown.");
		}
	}

}
