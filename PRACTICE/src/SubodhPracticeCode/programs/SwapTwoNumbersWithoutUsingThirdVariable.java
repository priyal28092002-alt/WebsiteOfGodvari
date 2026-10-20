//Program to swap two numbers using third variable

package com.example.programs;

public class SwapTwoNumbersWithoutUsingThirdVariable {
	public static void main(String[] args) {
		int a=5, b=7;
		System.out.println("Before swapping:");
		System.out.println("a = "+a);
		System.out.println("b = "+b);
		
		a=a+b;
		b=a-b;
		a=a-b;
		
		System.out.println("\nAfter swapping:");
		System.out.println("a = "+a);
		System.out.println("b = "+b);
	}
}
