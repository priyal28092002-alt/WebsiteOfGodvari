package com.example.MultiThreading;

public class Example1 extends Thread {
	@Override
	public void run() {
		System.out.println("Thread is running.");
		for (int i = 0; i < 5; i++) {
			System.out.println(i);
		}
	}

	public static void main(String[] args) {
		Example1 t1 = new Example1();
		t1.start();
		Example1 t2 = new Example1();
		t2.start();
		Example1 t3 = new Example1();
		t3.start();
	}
}
