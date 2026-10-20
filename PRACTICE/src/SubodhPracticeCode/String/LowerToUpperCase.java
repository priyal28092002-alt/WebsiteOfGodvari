//Program to convert all the lower case characters into upper case characters.
//String  -> Array -> String
//Original String -> Character Array -> Modified String
/*Since string is immutable, for every new String an object is created 
which reduces the efficiency as, objects will be created in total 
equal to the no of characters in String*/

package com.example.String;

public class LowerToUpperCase {
	public static void main(String[] args) {
		String str="jaVA@ proGramming123";
		
		char[] ch = str.toCharArray();
		
		for(int i=0; i<ch.length; i++) {
			if(Character.isLowerCase(ch[i])) //ch[i]>='a' && ch[i]<='z'
			{
				ch[i]=(char)(ch[i]-32);
			}
		}
		
		String upperCase=new String(ch);
		System.out.println(upperCase);
	}
}
