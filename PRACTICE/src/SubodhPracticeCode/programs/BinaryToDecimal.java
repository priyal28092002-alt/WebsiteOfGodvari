//Program to convert the binary to decimal number

package com.example.programs;

import java.util.Scanner;

public class BinaryToDecimal {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the binary number:");
		int n=sc.nextInt();
		int temp=n;
		int sum=0, power=0;
		
		while(n!=0) {
			int digit=n%10;
			sum=sum + digit*pow(2,power);
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
