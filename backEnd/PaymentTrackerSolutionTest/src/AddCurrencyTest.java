import static org.junit.Assert.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import sk.malis.libor.paytrack.Verifier;
import sk.malis.libor.paytrack.tasks.AddCurrencyTask;



public class AddCurrencyTest {
	
	private Verifier ver = new Verifier();
	private ConcurrentHashMap<String, AtomicLong> payMap = null;

	@Test
	public void testCorrectRun() {
		payMap = new ConcurrentHashMap<String, AtomicLong>();
		String currency = "GBP";
		AddCurrencyTask act = new AddCurrencyTask(currency, payMap, ver);
		Thread t = new Thread(act);
		t.start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Interrupted before timeout elapsed for other thread to make work");
		}
		assertEquals(1, payMap.size());
	}
	
	@Test
	public void testNotCorrectRun() {
		payMap = new ConcurrentHashMap<String, AtomicLong>();
		String currency = "GvP";
		AddCurrencyTask act = new AddCurrencyTask(currency, payMap, ver);
		Thread t = new Thread(act);
		t.start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Interrupted before timeout elapsed for other thread to make work");
		}
		assertEquals(0, payMap.size());
	}

}
