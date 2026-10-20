//Program to print the reverse of a number

package com.example.programs;

import java.util.Scanner;

public class ReverseOfNumber {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number.");
		int num = sc.nextInt();
		int reverse = 0;

		while (num != 0) {
			int digit = num % 10;
			reverse = (reverse * 10) + digit;
			num = num / 10;
		}
		System.out.println(reverse);
		sc.close();
	}
}