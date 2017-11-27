package com.zhao.test;

import static org.junit.Assert.*;

import org.junit.Test;

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
         System.out.println("内存的利用率是："+d);
         System.out.println("硬盘的利用率是："+disk);
	}
	/*
	 * cpu使用率
	 */
	@Test
	public void test1() {
		ComputerInfo info = new ComputerInfo();
		double d = info.getCpu();
		//System.out.println("CPU的利用率是："+d);
//		double timeNet = info.getTimeNet("BC-AE-C5-6E-B8-FA");
//		System.out.println("网络带宽的利用率是："+timeNet);
	info.getNet("E0-05-C5-F1-6D-C0");
	}

}
