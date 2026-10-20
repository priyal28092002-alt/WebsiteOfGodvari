//Program to convert Octal into Binary number
//1.Octal->Decimal
//2.Decimal->Binary

package com.example.programs;

import java.util.Scanner;

public class OctalToBinary {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the octal number:");
		int octal=sc.nextInt();
		int temp=octal;
		int decimal=0, power=0;
		
		//TODO:Octal->Decimal
		while(octal!=0) {
			int digit=octal%10;
			decimal=decimal + digit*pow(8,power);
			octal=octal/10;
			power++;
		}
		
		//TODO:Decimal->Binary
		String binary="";
		while(decimal!=0) {
			int remainder=decimal%2;
			binary=remainder+binary;
			decimal=decimal/2;
		}
		
		System.out.println("Binary of "+temp+" is "+binary);
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
