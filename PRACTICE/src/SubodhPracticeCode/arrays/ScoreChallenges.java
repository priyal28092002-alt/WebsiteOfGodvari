/*Alice and Bob each created one problem for Jspiders. 
 * A reviewer rates the two challenges, awarding points on a scale from 1 to 100 for three categories: 
 * problem clarity, originality, and difficulty. 
 * The rating for Alice's challenge is the triplet a = (a[0], a[1], a[2]), 
 * and the rating for Bob's challenge is the triplet b = (b[0], b[1], b[2]). 
 * The task is to find their comparison points by comparing a[0] with b[0], a[1] with b[1], and a[2] with b[2]. 
 * If a[i] > b[i], then Alice is awarded 1 point. If a[i] < b[i], then Bob is awarded 1 point. 
 * If a[i] = b[i], then neither person receives a point. 
 * Comparison points is the total points a person earned. Given a and b, determine their respective comparison points. 
 * Example a = [1, 2, 3] b = [3, 2, 1] For elements *0*, Bob is awarded a point because a[0] . 
 * For the equal elements a[1] and b[1], no points are earned. Finally, for elements 2, a[2] > b[2] so Alice receives a point. 
 * The return array is [1, 1] with Alice's score first and Bob's second.*/

package com.example.arrays;

import java.util.Scanner;

public class ScoreChallenges {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int size = sc.nextInt();

		int[] alice = new int[size];
		int[] bob = new int[size];

		for (int i = 0; i < alice.length; i++) {
			alice[i] = sc.nextInt();
		}

		for (int j = 0; j < bob.length; j++) {
			bob[j] = sc.nextInt();
		}

		int[] score = new int[2];

		for (int i = 0; i < alice.length; i++) {
			if (alice[i] > bob[i]) {
				score[0] = 1;
			} else if (alice[i] < bob[i]) {
				score[1] = 1;
			} else if (alice[i] == bob[i]) {

			}
		}

		/*
		 * for(int i=0; i<score.length; i++){ System.out.print(score[i]+" "); }
		 */
		System.out.println(score[0] + " " + score[1]);

		sc.close();
	}
}