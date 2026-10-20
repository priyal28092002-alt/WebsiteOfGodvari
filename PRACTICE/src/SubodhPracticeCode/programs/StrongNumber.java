//Program to check if a number is a Strong/Peterson number or not
//A number is called a Strong number if the sum of the factorial 
//of its digits is equal to the number

package com.example.programs;

import java.util.Scanner;

public class StrongNumber {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number.");
		int n=sc.nextInt();
		int temp=n, sum=0;
		
		while(n!=0) {
			int digit=n%10;
			sum=sum+factorial(digit);
			n=n/10;
		}
		
		if(sum==temp) {
			System.out.println("The number is a Strong number.");
		}else {
			System.err.println("The number is not a Strong number.");
		}
		
		sc.close();
	}
	
	public static int factorial(int digit) {
		int fact=1;
		for(int i=1; i<=digit;i++) {
			fact=fact*i;
		}
		return fact;
	}
}
