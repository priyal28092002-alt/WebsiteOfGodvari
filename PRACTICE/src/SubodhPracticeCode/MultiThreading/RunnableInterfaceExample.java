package com.example.MultiThreading;

class Example{
	
}

public class RunnableInterfaceExample extends Example implements Runnable {
	@Override
	public void run() {
		System.out.println("Thread is running.");
		for (char ch = 'A'; ch <= 'E'; ch++) {
			System.out.println(ch);
		}
	}
	
	public static void main(String[] args) {
		RunnableInterfaceExample ref = new RunnableInterfaceExample();
		Thread t1 = new Thread(ref);
		t1.start();

		Thread t2 = new Thread(ref);
		t2.start();

		Thread t3 = new Thread(ref);
		t3.start();
	}
	
}
