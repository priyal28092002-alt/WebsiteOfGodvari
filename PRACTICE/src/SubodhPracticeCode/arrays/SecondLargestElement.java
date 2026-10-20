//Program to find the second largest element

package com.example.arrays;

import java.util.Scanner;

public class SecondLargestElement {
	public static int secondlargest( int[] array){
		int largest=Integer.MIN_VALUE; 
		int secondlargest=Integer.MIN_VALUE;

		for( int i = 0 ; i < array.length ; i++ ){
			if( array[i] > largest){
				secondlargest=largest;
				largest = array[i];
			}else if(array[i]>secondlargest && array[i]!=largest) {
				secondlargest=array[i];
			}
		}
		return secondlargest; 
	}

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
	    System.out.println("Enter the array size: ");
	    
		int size=sc.nextInt();
		
		int[] array= new int[size];
		
		System.out.println("Enter the array elements: ");
		for(int i=0; i<array.length; i++) {
			array[i]=sc.nextInt();
		}
		 
		System.out.println("The second largest elemnet is:"+secondlargest(array));
		
		sc.close();
	}
}