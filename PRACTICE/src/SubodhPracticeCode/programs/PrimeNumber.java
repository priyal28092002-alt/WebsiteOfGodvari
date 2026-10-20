//Program to check if a number is prime or not

package com.example.programs;

import java.util.Scanner;

public class PrimeNumber {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number");
		int num = sc.nextInt();
		int count = 0;

		for (int i = 1; i <= num; i++) {
			if (num % i == 0) {
				count++;
			}
		}
		if (count == 2) {
			System.out.println("Its a prime number.");
		} else {
			System.out.println("Its not a prime number.");
		}

		/*
		 * int count=2; for(int i=2;i<=num/2;i++) { if(num%i==0) { count++; } }
		 * if(count==2) { System.out.println("Its a prime number."); }else {
		 * System.out.println("Its not a prime number."); }
		 */

		sc.close();
	}
}
