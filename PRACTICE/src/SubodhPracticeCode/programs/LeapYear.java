//Program to check if its a leap year

package com.example.programs;

public class LeapYear {
	public static void year(int year) {
		if(year%4==0 && (year%100!=0 || year%400==0)) {
			System.out.println("Its a leap year");
		}else {
			System.out.println("Its not a leap yeat");
		}
	}
	
	public static void main(String[] args) {
		year(1900);
	}
}
