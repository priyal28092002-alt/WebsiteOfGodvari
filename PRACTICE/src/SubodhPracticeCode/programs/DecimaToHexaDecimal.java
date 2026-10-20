//Program to convert decimal into hexadecimal(base 16)

package com.example.programs;

import java.util.Scanner;

public class DecimaToHexaDecimal {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the decimal number");
		int n=sc.nextInt();
		String hexadecimal="";
		
		while(n!=0) {
			int remainder=n%16;
			
			if(remainder<10) {
				hexadecimal=remainder+hexadecimal;
			}else {
				hexadecimal=(char)(remainder+55)+hexadecimal;
			}
			
			/*if(remainder==10) {
			hexadecimal=remainder+hexadecimal;
		}else if(remainder==11) {
			hexadecimal=remainder+hexadecimal;
		}else if(remainder==12) {
			hexadecimal=remainder+hexadecimal;
		}else if(remainder==13) {
			hexadecimal=remainder+hexadecimal;
		}else if(remainder==14) {
			hexadecimal=remainder+hexadecimal;
		}else if(remainder==15) {
			hexadecimal=remainder+hexadecimal;
		}else {
		    hexadecimal=remainder+hexadecimal;
		}*/
		
			/*String temp="";
			switch(remainder) {
			case 10: temp="A";
			         break;
			case 11: temp="B";
			         break;
			case 12: temp="C";
			         break;
			case 13: temp="D";
			         break;
			case 14: temp="E";
			         break;
			case 15: temp="F";
			         break;
			}*/
			
			/*if(remainder==10) {
				temp="A";
			}else if(remainder==11) {
				temp="B";
			}else if(remainder==12) {
				temp="C";
			}else if(remainder==13) {
				temp="D";
			}else if(remainder==14) {
				temp="E";
			}else if(remainder==15) {
				temp="F";
			}
			
			if(temp!="") {
				hexadecimal=temp+hexadecimal;
			}else {
				hexadecimal=remainder+hexadecimal;
			}*/
			n=n/16;
		}
		
		System.out.println("Hexadecimal of "+n+" is "+hexadecimal);
		sc.close();
	}
}
