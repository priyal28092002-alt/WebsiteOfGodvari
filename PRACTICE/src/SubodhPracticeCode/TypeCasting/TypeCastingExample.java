package com.example.TypeCasting;

public class TypeCastingExample {
    public static void main(String[] args) {
        // 1. Primitive to Non-primitive (Wrapper) - Boxing
        System.out.println("Primitive to Non-primitive (Boxing):");
        
        int primitiveInt = 10;
        Integer wrapperInt = Integer.valueOf(primitiveInt); // Manual boxing
        Integer autoBoxedInt = primitiveInt; // Auto-boxing
        System.out.println("int to Integer: " + wrapperInt);
        System.out.println("int to Integer (auto-boxing): " + autoBoxedInt);
        
        double primitiveDouble = 15.75;
        Double wrapperDouble = Double.valueOf(primitiveDouble);
        System.out.println("double to Double: " + wrapperDouble);
        
        char primitiveChar = 'A';
        Character wrapperChar = Character.valueOf(primitiveChar);
        System.out.println("char to Character: " + wrapperChar);
        
        System.out.println("\n--------------------------------\n");
        
        // 2. Non-primitive (Wrapper) to Primitive - Unboxing
        System.out.println("Non-primitive to Primitive (Unboxing):");
        
        Integer wrapperNum = 25;
        int primitiveNum = wrapperNum.intValue(); // Manual unboxing
        int autoUnboxedNum = wrapperNum; // Auto-unboxing
        System.out.println("Integer to int: " + primitiveNum);
        System.out.println("Integer to int (auto-unboxing): " + autoUnboxedNum);
        
        Double wrapperDbl = 20.25;
        double primitiveDbl = wrapperDbl.doubleValue();
        System.out.println("Double to double: " + primitiveDbl);
        
        Boolean wrapperBool = true;
        boolean primitiveBool = wrapperBool.booleanValue();
        System.out.println("Boolean to boolean: " + primitiveBool);
        
        System.out.println("\n--------------------------------\n");
        
        // 3. String to Primitive (Using parse methods)
        System.out.println("String to Primitive:");
        
        String strNumber = "123";
        int strToInt = Integer.parseInt(strNumber);
        double strToDouble = Double.parseDouble("45.67");
        System.out.println("String to int: " + strToInt);
        System.out.println("String to double: " + strToDouble);
        
        System.out.println("\n--------------------------------\n");
        
        // 4. Primitive to String
        System.out.println("Primitive to String:");
        
        int number = 456;
        String intToString1 = String.valueOf(number);
        String intToString2 = Integer.toString(number);
        System.out.println("int to String (using String.valueOf): " + intToString1);
        System.out.println("int to String (using Integer.toString): " + intToString2);
        
        double decimal = 78.90;
        String doubleToString = String.valueOf(decimal);
        System.out.println("double to String: " + doubleToString);
        
        System.out.println("\n--------------------------------\n");
        
        // 5. Between different primitive types
        System.out.println("Between Primitive Types:");
        
        int integerNum = 100;
        double intToDouble = (double) integerNum; // Widening (implicit)
        System.out.println("int to double (widening): " + intToDouble);
        
        double bigNum = 123.456;
        int doubleToInt = (int) bigNum; // Narrowing (explicit)
        System.out.println("double to int (narrowing): " + doubleToInt);
    }
}