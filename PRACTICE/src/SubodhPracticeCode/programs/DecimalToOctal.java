//Program to convert decimal into octal(base 8)

package com.example.programs;

import java.util.Scanner;

public class DecimalToOctal {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the decimal number");
		int n=sc.nextInt();
		String octal="";
		
		while(n!=0) {
			int remainder=n%8;
			octal=remainder+octal;
			n=n/8;
		}
		
		System.out.println("Octal of "+n+" is "+octal);
		sc.close();
	}
}
