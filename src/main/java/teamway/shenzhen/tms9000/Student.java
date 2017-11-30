package teamway.shenzhen.tms9000;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.Test;

public class Student {
	/*
	 * 内存使用率 /ComputerInfo/src/test/resources/config/diskName.properties
	 */
	public static void main(String[] args) throws Exception {
		
		Properties p = new Properties();
		InputStream in = Object.class.getResourceAsStream("/config/macaddress.properties");
		p.load(in);
		System.out.println(p.getProperty("macaddress"));
		 
		

	}
}
