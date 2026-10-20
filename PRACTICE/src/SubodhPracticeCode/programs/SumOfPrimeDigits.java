//Program to find the sum of all the prime digits of a number

package com.example.programs;
import java.util.Scanner;

public class SumOfPrimeDigits {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number.");
		int num=sc.nextInt();
		int sum=0;
		
		while(num!=0) {
			int count = 0;
			int digit=num%10;
			for(int i=1;i<=digit;i++) {
				if(digit%i==0) {
					count++;
				}
			}
			if(count==2) {
				sum=sum+digit;
			}
			num=num/10;
		}
		
		System.out.println(sum);
		sc.close();
	}
}

/*public class SumOfPrimeDigits {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number.");
		int num=sc.nextInt();
		int sum=0;
		
		while(num!=0) {
			int digit=num%10;
			if(digit==2 || digit==3 || digit==5 || digit==7) {
				sum=sum+digit;
			}
			num=num/10;
		}
		
		System.out.println(sum);
		sc.close();
	}
}*/