/*
 * Write a method to return the largest element in the given 
 * integer array
 * Test Case 1: 

	Input :[1,6,9,0,25,16]
	Output : 25

    Test Case 2: 

	Input : [ 4, 78, 98, 87, 120 ]
	Output : 120

    Test Case 3: 

	Input : [ 5, -24, 46,- 20 ,98]
	Output : 98

 */

package com.example.arrays;

import java.util.Scanner;

public class LargestElement {
	
	// method to find largest 
	public static int largest( int[] array){
		//int largest=Integer.MIN_VALUE; //Initialize i with 0 within for loop 
		
		int largest = array[0] ; 

		for( int i = 1 ; i < array.length ; i++ ){
			if( array[i] > largest){
				largest = array[i] ; 
			}
		}
		return largest; 
	}

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
	    System.out.println("Enter the array size: ");
	    
		int size=sc.nextInt();
		
		int[] array= new int[size];
		
		System.out.print("Enter the array elements: ");
		for(int i=0; i<array.length; i++) {
			array[i]=sc.nextInt();
		}
		 
		System.out.println("The largest elemnet is:"+largest(array));
		
		sc.close();
	}
}