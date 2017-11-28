package com.zhao.test;

import static org.junit.Assert.*;

import org.junit.Test;

import teamway.shenzhen.tms9000.Computer;
import teamway.shenzhen.tms9000.ComputerInfo;

public class Student {
	/*
	 * 内存使用率
	 */
	@Test
	public void test() {
		ComputerInfo info = new ComputerInfo();
		double d = info.getMenmory();
		double disk = info.getDisk("d:/");
		System.out.println("内存的利用率是：" + d);
		System.out.println("硬盘的利用率是：" + disk);
	}

	/*
	 * cpu使用率
	 */
	@Test
	public void test1() throws Exception {
		ComputerInfo info = new ComputerInfo();
		Computer computer = new Computer();
		String string = info.getname();
		System.out.println(string);
		// System.out.println("CPU的利用率是："+d);
		// double timeNet = info.getTimeNet("BC-AE-C5-6E-B8-FA");
		// System.out.println("网络带宽的利用率是："+timeNet);
		info.getTimeNet("BC-AE-C5-6E-B8-FA");
		Thread.sleep(2000);
		double d = info.getNet("BC-AE-C5-6E-B8-FA");
		System.out.println("带宽是"+d);
		double net = computer.getNet();
		System.out.println("线程里面的值"+net);

	}

}
