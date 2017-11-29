package com.zhao.test;

import static org.junit.Assert.*;

import org.junit.Test;

import teamway.shenzhen.tms9000.Computer;
import teamway.shenzhen.tms9000.ComputerImp;

public class CTest {

	
	@Test
	public void test1() throws Exception {
		ComputerImp c=new ComputerImp();
		Computer computer = c.getComputerInfo("E0-05-C5-F1-6D-C0", null);
		double net = computer.getNet();
		System.out.println("网速是"+net);
	}

}
