/*How to create string object using String class?

1. using String Literal (String Constant Pool)

String variable = "String Literal";
Eg - String s1 = "apple";

2. using new keyword (Heap Area as well as String Constant Pool)

String variable = new String("String Literal");
Eg -  String s1 = new String("apple"); */

package com.example.String;

public class StringObject {
	public static void main(String[] args) {
		String s1 = "apple";
		String s2 = "apple";

		System.out.println(s1 == s2); // true

		String s3 = new String("apple");
		String s4 = new String("apple");

		System.out.println(s3 == s4); // false
		System.out.println(s1 == s3.intern()); // true
		// used to check string constant pool
	}
}