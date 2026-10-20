/*Program to check if a number is a perfect number or not.
A perfect number is a positive integer that 
is the sum of its positive factors, excluding the number itself*/


package com.example.programs;

import java.util.Scanner;

public class PerfectNumber {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Please enter the number");
		int num=sc.nextInt();
		int sum=0;
		
		for(int i=1; i<num; i++) {
			if(num%i==0) {
				sum=sum+i;
			}
		}
		if(num==sum) {
			System.out.println("True. Its a perfect number.");
		}else {
			System.out.println("False. Its not a perfect number.");
		}
		
		/*for(int i=1; i<=num/2; i++) {
			if(num%i==0) {
				sum=sum+i;
			}
		}
		if(num==sum) {
			System.out.println("True. Its a perfect number.");
		}else {
			System.out.println("False. Its not a perfect number.");
		}*/
		
		sc.close();
	}
}
