//Program to convert Octal to decimal number

package com.example.programs;

import java.util.Scanner;

public class OctalToDecimal {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the octal number:");
		int n=sc.nextInt();
		int temp=n;
		int sum=0, power=0;
		
		while(n!=0) {
			int digit=n%10;
			sum=sum + digit*pow(8,power);
			power++;
			n=n/10;
		}
		System.out.println("The decimal value of "+temp+" is:"+sum);
		sc.close();
	}
	
	public static int pow(int base, int power) {
		int product=1;
		
		for(int i=1; i<=power;i++) {
			product=product*base;
		}
		return product;
	}
}
