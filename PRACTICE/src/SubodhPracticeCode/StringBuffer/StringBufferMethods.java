//StringBuffer methods + methods present in the String Class

package com.example.StringBuffer;

public class StringBufferMethods {
	public static void main(String[] args) {
		StringBuffer sb=new StringBuffer("JSpiders");
		
		sb.append(" Thane Training Institute");
		
		System.out.println(sb);
		sb.insert(15,"Java ");
		System.out.println(sb);
		
		sb.delete(20, 28);
		System.out.println(sb);
		
		sb.deleteCharAt(20);
		System.out.println(sb);
		
		sb.replace(15, 19, "Full Stack");
		System.out.println(sb);
		
		sb.reverse();
		System.out.println(sb);
	}
}
