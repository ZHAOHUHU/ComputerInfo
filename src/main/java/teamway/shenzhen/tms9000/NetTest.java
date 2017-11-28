package teamway.shenzhen.tms9000;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NetTest {
	 private int a;
	  
	 public void setVal(){
	  Timer timer = new Timer();
	  //每隔一秒生成一个[1,100)内的随机整数，赋给成员a
	  timer.schedule(new TimerTask() { 
	   @Override
	   public void run() {
	    Random rand = new Random();
	    System.out.println(rand.nextInt());
	    setA(rand.nextInt(100));
	   }
	  },0, 1000);
	 }
	  
	 public void setA(int a) {
	  this.a = a;
	 }
	 public int getA() {
	  return a;
	 }
	 public static void main(String[] args) throws Exception {
		 NetTest me = new NetTest();
	  me.setVal();
	  Thread.sleep(1000);
	  int i = me.getA();
	  System.out.println("数据是"+i);
	 }
}
