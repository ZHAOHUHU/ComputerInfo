package teamway.shenzhen.tms9000;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NetTest {
	public static void main(String[] args) {
		double f=456.31654897486;
		 BigDecimal bg = new BigDecimal(f);  
         double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
         System.out.println(f1);   
	}
	
	 
}
