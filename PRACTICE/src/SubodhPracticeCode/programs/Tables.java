//Program to print the table of a number from 1 till the range

package com.example.programs;
import java.util.Scanner;

public class Tables {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number");
		int table=sc.nextInt();
		
		for(int i=1; i<=10; i++) {
		for(int j=1; j<=table; j++) {
			System.out.print(j+" * "+i+"="+j*i+" | ");
		}
		System.out.print("\n");
		}
		
		/*for(int i=1; i<=table; i++) {
			for(int j=1; j<=10; j++) {
				System.out.println(i+" * "+j+"="+i*j);
			}
			System.out.print("\n");
		}*/
		sc.close();
	}
			
}
