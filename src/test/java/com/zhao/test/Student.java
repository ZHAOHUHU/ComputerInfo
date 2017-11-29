package com.zhao.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import teamway.shenzhen.tms9000.Computer;
import teamway.shenzhen.tms9000.ComputerInfo;

public class Student {
	/*
	 * 内存使用率
	 */
	@Test
	public void test() {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(5);
		list.remove(0);
		int i = list.size();
		System.out.println(i);

	}
}
