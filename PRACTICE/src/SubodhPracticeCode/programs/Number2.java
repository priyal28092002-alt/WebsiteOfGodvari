/*
Given a number n, print following pattern without using any loop.
Input: n=16
Output: 16, 11, 6, 1, -4, 1, 6, 11, 16
Input: n = 10
Output: 10, 5, 0, 5, 10 */

package com.example.programs;

public class Number2 {
	public static void print(int n) {
		if(n>0) {
			System.out.print(n+" ");
			print(n-5);
		}
		System.out.print(n+" ");
	}
	public static void main(String[] args) {
		print(16);
	}
}
