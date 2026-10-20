//Program to check if the number is a Niven number(Harshad's Number) or not.
//A Harshad number is a number which is divisible by sum of its digits.

package com.example.programs;
import java.util.Scanner;

public class NivenNumber {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number.");
		int num=sc.nextInt();
		int temp=num;
		int sum=0;
		
		while(num!=0) {
			int digit=num%10;
			sum=sum+digit;
			num=num/10;
		}
		
		if((temp%sum)==0) {
			System.out.println("Its a Niven number");
		}else {
			System.out.println("Its not a Niven number");
		}
		sc.close();
	}
}