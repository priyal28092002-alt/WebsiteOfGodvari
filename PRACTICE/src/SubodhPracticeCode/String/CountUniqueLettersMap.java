//Program to count the frequency of letters in the String using Map

package com.example.String;

import java.util.LinkedHashMap;
import java.util.Map;

public class CountUniqueLettersMap {
	public static void main(String[] args) {
		String str="maharashtra";
		
		Map<Character, Integer> map=new LinkedHashMap<>();
		
		for(int i=0; i<str.length(); i++) {
			char ch=str.charAt(i);
			
			if(map.containsKey(ch)) {
				int value=map.get(ch);  //get() is use to get the values for the key
				map.put(ch, value+1);
			}else {
				map.put(ch, 1);
			}
		}
		
		System.out.println(map);
	}
}
