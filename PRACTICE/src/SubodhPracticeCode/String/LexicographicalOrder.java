//Program to print all contents into alphabetical order

package com.example.String;

public class LexicographicalOrder {
	public static void main(String[] args) {
		String []a= {"orange","guava","apple","mango","kiwi","grape"};
		for(int i=0;i<a.length;i++) {
			for(int j=0;j<a.length-1;j++) {
				if(a[j].compareTo(a[j+1])>0){
					String temp=a[j];
					a[j]=a[j+1];
					a[j+1]=temp;
				}
			}
		}
		for(String s:a) {
			System.out.println(s);
		}
	}
}