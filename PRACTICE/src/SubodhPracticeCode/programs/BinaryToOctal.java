//Program to convert binary into octal number
//1.Binary->Decimal
//2.Decimal->Octal

package com.example.programs;

import java.util.Scanner;

public class BinaryToOctal {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the binary number:");
		int binary=sc.nextInt();
		int temp=binary;
		int decimal=0, power=0;
		
		//TODO:Binary->Decimal
		while(binary!=0) {
			int digit=binary%10;
			decimal=decimal + digit*pow(2,power);
			power++;
			binary=binary/10;
		}
		
		//TODO:Decimal->Octal
		String octal="";
		while(decimal!=0) {
			int remainder=decimal%8;
			octal=remainder+octal;
			decimal=decimal/8;
		}
		
		System.out.println("Octal of "+temp+" is "+octal);
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
