//Program to find the largest digit present in the number

package com.example.programs;

import java.util.Scanner;

public class LargestDigit {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number");
		int n=sc.nextInt();
		int largestdigit=0;
		
		while(n!=0) {
			int digit=n%10;
			if(digit>largestdigit) {
				largestdigit=digit;
			}
			n=n/10;
		}
		
		System.out.println(largestdigit);
		
		sc.close();
	}
}
