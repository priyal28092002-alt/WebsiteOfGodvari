//Program to swap two numbers using third variable

package com.example.programs;

public class SwapTwoNumbersUsingThirdVariable {
	public static void main(String[] args) {
		int a=5, b=7;
		System.out.println("Before swapping:");
		System.out.println("a = "+a);
		System.out.println("b = "+b);
		
		int c=a;
		a=b;
		b=c;
		
		System.out.println("\nAfter swapping:");
		System.out.println("a = "+a);
		System.out.println("b = "+b);
	}
}
