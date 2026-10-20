//i/p -> maharashtra o/p : m 1 a 4..

package com.example.String;

public class CountUniqueLetters {
	public static void main(String[] args) {
		String s = "Maharashtra";

		boolean[] b = new boolean[s.length()];

		for (int i = 0; i < s.length(); i++) {
			if (b[i] == false) {
				int count = 1;
				for (int j = i + 1; j < s.length(); j++) {
					if (s.charAt(i) == s.charAt(j)) {
						count++;
						b[j] = true;
					}
				}
				System.out.println(s.charAt(i) + " " + count);
			}
		}
	}
}