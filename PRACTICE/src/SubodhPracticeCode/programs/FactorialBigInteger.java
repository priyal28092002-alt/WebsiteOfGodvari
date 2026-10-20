//Program to calculate the factorial of a number

package com.example.programs;

import java.math.BigInteger; //BigInteger Class is present inside java.math package
import java.util.Scanner;

public class FactorialBigInteger {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the number: ");
		int n = sc.nextInt();

		BigInteger fact = BigInteger.valueOf(1);
		//BigInteger fact = BigInteger.ONE;
		//BigInteger fact = BigInteger.ZERO;
		//Can also pass 0 or 1 only in this form
		
		// BigInteger Class is used to store the data that is
		// beyond the range of int and long data type

		for (int i = 1; i <= n; i++) {
			fact = fact.multiply(BigInteger.valueOf(i)); // Uses inbuilt method to multiply
			//& accepts the formal argument of BigInteger data type
		}
		System.out.println("The factorial of the number is: " + fact);

		sc.close();
	}
}
