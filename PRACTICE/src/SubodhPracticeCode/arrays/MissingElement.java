/*WAP to find the missing number from the array
size of array is n
elements of array is 0-n
Find the missing element.

Input - 
n = 9
array = [8,3,4,1,2,6,0,7,9]

Output - 5*/

package com.example.arrays;

import java.util.Scanner;

public class MissingElement {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the array size:");
		int size=sc.nextInt();
		int[] array=new int[size];
		
		System.out.println("Enter the array elements between 0 to 9:");
		for(int i=0; i<array.length; i++ ) {
			array[i]=sc.nextInt();
		}
		//Calculate the sum of numbers from 0 to n
        //int expectedSum = size * (size + 1) / 2; //Sum up to n+1 term
        //Sum of first n terms: sn = n/2(2a + (n-1)d) (Sequence & Series)
        
		int expectedSum=0;
		for(int i=0; i<size+1; i++) {
			expectedSum=expectedSum+i;
		}
		sc.close();
		
		// Calculate the sum of elements in the array
		int actualSum=0;
		for(int i=0; i<array.length; i++) {
			actualSum=actualSum+array[i];
		}
		
		int missingNumber = expectedSum - actualSum;
		System.out.println("The missing element is: "+missingNumber);
	}
}
