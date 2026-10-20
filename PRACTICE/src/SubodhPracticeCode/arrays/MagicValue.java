//Magic Value - > (Sum of good integers - Sum of bad integers)
//Good numbers are the numbers who's position remains same after sorting
//Bad integers are the numbers who has been sorted

package com.example.arrays;

import java.util.Arrays;
import java.util.Scanner;

public class MagicValue {
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
		
		/*for(int i=0; i<arraya.length; i++) {
			
			int min=i; //Assuming that the ith element is the smallest element
			for(int j=i+1; j<arraya.length; j++) {
				if(arraya[j]<arraya[min]) {
					min=j;
				}
			}
			int temp=arraya[i];
			arraya[i]=arraya[min];
			arraya[min]=temp;
		}*/
		
        int[] arrayb=arraya.clone(); //For creating a duplicate array using clone method
		
		Arrays.sort(arraya);
		
		int sumGoodInt=0;
		int sumBadInt=0;
		
		for (int i = 0; i < arrayb.length; i++) {
			if(arraya[i]==arrayb[i]) {
				sumGoodInt+=arraya[i];
			}else {
				sumBadInt+=arrayb[i];
			}
		}
		
		int magicValue=sumGoodInt - sumBadInt;
		System.out.println("Magic Value:"+magicValue);
	}
}
