package com.example.MultiThreading;

import java.io.FileOutputStream;
import java.io.PrintWriter;

class A{
	String data = "Hello";
	String path = "src/com/example/MultiThreading/Output.txt";
	
	public void printIntoFile() throws Exception{
		  try (FileOutputStream fos = new FileOutputStream(path);
		       PrintWriter pw = new PrintWriter(fos)) {

		   for(int i = 0; i < data.length(); i++) {
		    char ch = data.charAt(i);
		    pw.write(ch);

		    Thread.sleep(1000);

		    System.out.println("Data written into the file: " + ch);
		   }
		   pw.flush();
		  }catch (Exception e) {
            e.printStackTrace();
          }
	}
	
	public void modifyData(String data) {
		this.data = data;
	}
}

class B extends Thread {
	A ref;

	public B(A ref) {
		this.ref = ref;
	}

	@Override
	public void run() {
		try {
			ref.printIntoFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class C extends Thread {
	A ref;

	public C(A ref) {
		this.ref = ref;
	}

	@Override
	public void run() {
		ref.modifyData("Java");
	}
}

public class DataInconsistency {
	public static void main(String[] args) throws InterruptedException {
		A ref = new A();
		B t1 = new B(ref);
		C t2 = new C(ref);
		
		t1.start();

		Thread.sleep(5000);
		
		t2.start();
	}
}
