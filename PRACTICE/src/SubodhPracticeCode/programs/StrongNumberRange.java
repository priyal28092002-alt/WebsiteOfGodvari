////Program to check if a number is a Strong/Peterson number or not between a range
//A number is called a Strong number if the sum of the factorial 
//of its digits is equal to the number

package com.example.programs;

import java.util.Scanner;

public class StrongNumberRange {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number");

		System.out.println("Enter lower limit.");
		int lower = sc.nextInt();
		System.out.println("Enter lower limit.");
		int upper = sc.nextInt();

		for (int num = lower; num <= upper; num++) {
			int temp = num;
			int sum = 0;
			while (num != 0) {
				int digit = num % 10;
				sum += factorial(digit);
				num = num / 10;
			}
			if (sum == temp) {
				System.out.println("The number " + temp + " is a Strong number");
			}
			num = temp;
		}

		sc.close();
	}

	public static int factorial(int digit) {
		int fact = 1;
		for (int i = 1; i <= digit; i++) {
			fact *= i;
		}
		return fact;
	}
}