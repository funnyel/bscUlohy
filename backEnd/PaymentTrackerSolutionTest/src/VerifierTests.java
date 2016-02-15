import static org.junit.Assert.*;

import org.junit.Test;

import sk.malis.libor.paytrack.Verifier;


public class VerifierTests {
	
	Verifier verif = new Verifier();

	@Test
	public void testCorrectRateValue() {
		boolean result = true;
		String rateValue = "651.545";
		result = verif.isCorrectRateValue(rateValue);
		assertTrue(result);
	}
	
	@Test
	public void testCorrectRateValue2() {
		boolean result = true;
		String rateValue = "-651.545";
		result = verif.isCorrectRateValue(rateValue);
		assertTrue(result);
	}

	@Test
	public void testNotCorrectRateValue() {
		boolean result = true;
		String rateValue = "651.AE5";
		result = verif.isCorrectRateValue(rateValue);
		assertFalse(result);
	}
	
	@Test
	public void testNotCorrectRateValue2() {
		boolean result = true;
		String rateValue = "651,435";
		result = verif.isCorrectRateValue(rateValue);
		assertFalse(result);
	}
	
	@Test
	public void testCorrectPaymentValue() {
		boolean result = true;
		String payValue = "651435";
		result = verif.isCorrectPaymentValue(payValue);
		assertTrue(result);
	}
	
	@Test
	public void testCorrectPaymentValue2() {
		boolean result = true;
		String payValue = "-651435";
		result = verif.isCorrectPaymentValue(payValue);
		assertTrue(result);
	}
	
	@Test
	public void testNotCorrectPaymentValue() {
		boolean result = true;
		String payValue = "651[35";
		result = verif.isCorrectPaymentValue(payValue);
		assertFalse(result);
	}
	
	@Test
	public void testNotCorrectPaymentValue2() {
		boolean result = true;
		String payValue = "651,35";
		result = verif.isCorrectPaymentValue(payValue);
		assertFalse(result);
	}
	

	@Test
	public void testCorrectCurrencyName() {
		boolean result = true;
		String currName = "GER";
		result = verif.isCorrectCurrencyName(currName);
		assertTrue(result);
	}
	
	@Test
	public void testNotCorrectCurrencyName() {
		boolean result = true;
		String currName = "GERZ";
		result = verif.isCorrectCurrencyName(currName);
		assertFalse(result);
	}
	
	@Test
	public void testNotCorrectCurrencyName2() {
		boolean result = true;
		String currName = "GE";
		result = verif.isCorrectCurrencyName(currName);
		assertFalse(result);
	}
	
	@Test
	public void testNotCorrectManyCurrencyNames() {
		boolean currentResult = true;
		int counter = 0;
		String[] incorrects = {"GEz", "GeZ", "gEZ", "", null, "gEz", "gez", "G.Z", "G!Z", "G/Z", "G@Z", "G5Z", "G%Z", "G#Z", "G,Z", "G{Z", "G Z", "G	Z", "G\nZ" };
		for(int u=0; u<incorrects.length; u++){
			currentResult = verif.isCorrectCurrencyName(incorrects[u]);
			if(!currentResult){
				counter++;
				currentResult = true;
			}else{
				continue;
			}
		}
		
		assertEquals(incorrects.length, counter);
	}

}
