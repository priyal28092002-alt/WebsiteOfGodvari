package com.example.Wrapper;

public class Boxing_Unboxing {
	public static void main(String[] args) {
		int i = 10; // Primitive data type
		
        Integer intObj = Integer.valueOf(i); // Boxing
        System.out.println("Boxed value: " + intObj);
        System.out.println("Boxed Value: " + intObj.toString());
        
        int j = intObj.intValue(); // Unboxing
        System.out.println("Unboxed value: " +j);
        
        String str = "123";
        Integer intObj2 = Integer.parseInt(str); // Boxing
        System.out.println("Boxed value: " + intObj2);
        
        int k = Integer.parseInt(str); // Unboxing
        System.out.println("Unboxed value: " + k);
        
	}
}
