//Program to print all the even digits from the number

package com.example.programs;

import java.util.Scanner;

public class EvenDigits {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number");
		int num=sc.nextInt();
		
		while(num!=0) {
			int digit=num%10;
			if(digit%2==0) {
				System.out.println(digit);
			}
			num=num/10;
		}
		
		sc.close();
	}
}
