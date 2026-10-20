package com.example.Wrapper;

public class Auto_Boxing_Unboxing {
	public static void main(String[] args) {
		int i = 10; // Primitive data type

		Integer intObj = i; // Auto-Boxing
		System.out.println("Boxed value: " + intObj);
		System.out.println("Boxed Value: " + intObj.toString());

		int j = intObj; // Auto-Unboxing
		System.out.println("Unboxed value: " + j);
		
		System.out.println(intObj + j); // Auto-Unboxing
	}
}
