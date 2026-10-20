//Program to check if an element is a Leader Number or not
/*Given an array of integers, find and print all the leaders of the array. A leader 
is defined as an element of the array which is greater than all the elements
following it. The rightmost element is always a leader.
For example:
In the array {8, 7, 4, 3, 5, 2}, leaders are 8, 7, 5 and 2.
Explanation:

8>7,8>4,8>3,8>5,8>2.
7>4,7>3,7>5,7>2.
5>2.
2 is the rightmost element.*/

package com.example.arrays;

import java.util.Scanner;

public class LeaderOfArray {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the array size:");
		int size = sc.nextInt();
		int[] array = new int[size];

		System.out.println("Enter the array elements:");
		for (int i = 0; i < array.length; i++) {
			array[i] = sc.nextInt();
		}
		sc.close();
        
		System.out.println("The leaders are:");
		for (int i = 0; i < array.length; i++) {
			boolean isleader=true;
	    	for (int j = i+1; j < array.length; j++) {
				if(array[j]>array[i]) {
					isleader=false;
					break;
				}
			}
	    	if(isleader) {
	    		System.out.print(array[i]+" ");
	    	}
		}
		
		/*for (int i = 0; i < array.length; i++) {
			boolean isleader=true;
	    	for (int j = i+1; j < array.length; j++) {
				if(array[i]>=j) {
					isleader=isleader && true;
				}else {
					isleader=isleader && false;
				}
			}
	    	if(isleader==true) {
	    		System.out.print(array[i]+" ");
	    	}
		}*/
	}
}
