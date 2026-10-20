//Program to find the smallest digit present in the number

package com.example.programs;

import java.util.Scanner;

public class SmallestDigit {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number");
		int n=sc.nextInt();
		int smallestdigit=9; //Since the largest digit is 9 which will be compared with the digit to find the smallest one
		
		while(n!=0) {
			int digit=n%10;
			if(digit<smallestdigit) {
				smallestdigit=digit;
			}
			n=n/10;
		}
		
		System.out.println(smallestdigit);
		
		sc.close();
	}
}
