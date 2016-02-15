package sk.malis.libor.paytrack;
public class Verifier{
		
		public boolean isCorrectCurrencyName (String k){
			boolean result = true;
			if((k==null)||(k.length()!=3)){
				result = false;
			}
			if(result){
				for (int u=0; u<k.length(); u++){
					if(result){
						if(k.charAt(u)==Character.toLowerCase(k.charAt(u))){
							result = false;
						}
					}else{
						break;
					}
				}
			}
			return result;
		}
		

		public boolean isCorrectRateValue(String s){
			boolean result = true;
			if(s!=null&&(!"".equals(s))){
				try {
					Double.parseDouble(s);
				} catch (NumberFormatException e) {
					result = false;
				}
			}
			else{result = false;}
			return result;
		}
		
		public boolean isCorrectPaymentValue(String s){
			boolean result = true;
			if(s!=null&&(!"".equals(s))){
				try {
					Long.parseLong(s);
				} catch (NumberFormatException e) {
					result = false;
				}
			}
			else{result = false;}
			return result;
		}
		
	} 