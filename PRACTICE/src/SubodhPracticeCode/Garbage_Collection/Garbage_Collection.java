package com.example.Garbage_Collection;

public class Garbage_Collection {
	int i = 4;
	public static void main(String[] args) {
		System.out.println("Garbage Collection Example");
		
		//Anonymous Object
		System.out.println(new Garbage_Collection());
		
		Garbage_Collection obj = new Garbage_Collection();
		//Nullifying the reference
		obj = null;
		
		Garbage_Collection ref1 = new Garbage_Collection();
		Garbage_Collection ref2 = new Garbage_Collection();
		//Assigning the reference of one object var to another
		ref1 = ref2;
		
		System.gc();
	}
	
	public void finalize() {
		System.out.println("Object is garbage collected.");
	}
}
