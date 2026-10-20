//Techniques to copy the elements of one array into another array

package com.example.arrays;

import java.util.Arrays;
import java.util.Scanner;

public class CopyArray {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the array size:");
		int size=sc.nextInt();
		int[] arraya=new int[size];
		
		System.out.println("Enter the array elements:");
		for(int i=0; i<arraya.length; i++ ) {
			arraya[i]=sc.nextInt();
		}
		sc.close();
		
		//Using for loop
		int[] arrayb=new int[arraya.length]; //For creating a duplicate array
		for (int i = 0; i < arrayb.length; i++) {
			arrayb[i]=arraya[i];
		}
		
		//using copyOf method of Arrays class
		int[] arrayc=Arrays.copyOf(arraya, arraya.length);
		
		//Using arraycopy method of System class
		int[] arrayd=new int[arraya.length];
		System.arraycopy(arraya, 0, arrayd, 0, arraya.length); //Return type is void
		
		//Using non static clone method of Object class
		int[] arraye=arraya.clone();;
		
		System.out.println("Duplicate Array: ");
		System.out.println("Array A:");
		printarray(arraya);
		System.out.println("Array B:");
		printarray(arrayb);
		System.out.println("Array C:");
		printarray(arrayc);
		System.out.println("Array D:");
		printarray(arrayd);
		System.out.println("Array E:");
		printarray(arraye);
	}
	
	public static void printarray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
}
