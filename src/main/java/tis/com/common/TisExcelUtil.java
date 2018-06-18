package tis.com.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TisExcelUtil {
	
	public static String bigDecimalToCurrency(Object decimalObject) {
		if(decimalObject != null) {
			DecimalFormat currencyFormat = new DecimalFormat("###,###");
			return currencyFormat.format(((BigDecimal)decimalObject).longValue());
		} else {
			return "0";
		}
	}
}
