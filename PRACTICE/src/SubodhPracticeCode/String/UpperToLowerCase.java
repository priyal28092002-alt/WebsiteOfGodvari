//Program to convert all the upper case characters into lower case characters.

//String  -> Array -> String
//Original String -> Character Array -> Modified String
/*Since string is immutable, for every new String an object is created 
which reduces the efficiency as, objects will be created in total 
equal to the no of characters in String*/

package com.example.String;

public class UpperToLowerCase {
	public static void main(String[] args) {
		String str="JaVA@ ProGramminG123";
		
		char[] ch = str.toCharArray();
		
		for(int i=0; i<ch.length; i++) {
			if(Character.isUpperCase(ch[i])) //ch[i]>='A' && ch[i]<='Z'
			{
				ch[i]=(char)(ch[i]+32);
			}
		}
		
		String lowerCase=new String(ch);
		System.out.println(lowerCase);
	}
}