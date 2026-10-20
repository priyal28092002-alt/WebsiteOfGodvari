//Program to check if a number is palindrome or not 
//i.e. reverse of the number is equal to the original number

package com.example.programs;

import java.util.Scanner;

public class Palindrome {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number.");
		int num = sc.nextInt();
		int temp = num;
		int reverse = 0;

		while (num != 0) {
			int digit = num % 10;
			reverse = (reverse * 10) + digit;
			num = num / 10;
		}
		if (reverse == temp) {
			System.out.println("The number is a palindrome");
		} else {
			System.out.println("The numbers is not a palindrome");
		}
		sc.close();
	}
}