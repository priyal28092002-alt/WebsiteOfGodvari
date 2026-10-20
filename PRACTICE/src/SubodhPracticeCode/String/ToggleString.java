//Toggle String
/*A Toggle String is a String where the case of each character is flipped.
 * All upper case letters become lower case, and all lower case letters become upper case.
 * For example, if the input String is: HelloWorld*, the output of toggling its characters would be "hELLOwORLD".
 * 
 * Input = HelloWorld
Output = hELLOwORLD*/

package com.example.String;

public class ToggleString {
	public static void main(String[] args) {
        String str="HelloWorld";
		
		char[] ch = str.toCharArray();
		
		for(int i=0; i<ch.length; i++) {
			if(Character.isLowerCase(ch[i])) //ch[i]>='a' && ch[i]<='z'
			{
				ch[i]=(char)(ch[i]-32);
			}else if(Character.isUpperCase(ch[i])) {
				ch[i]=(char)(ch[i]+32);
			}
		}
		
		String upperCase=new String(ch);
		System.out.println(upperCase);
	}
}
