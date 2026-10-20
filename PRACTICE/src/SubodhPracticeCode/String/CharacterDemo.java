package com.example.String;

public class CharacterDemo {
    public static void main(String[] args) {
        // Demonstrate Character class methods

        char ch = 'A';

        // isLetter() method
        System.out.println("Is '" + ch + "' a letter? " + Character.isLetter(ch));

        // isDigit() method
        System.out.println("Is '" + ch + "' a digit? " + Character.isDigit(ch));

        // isUpperCase() and isLowerCase() methods
        System.out.println("Is '" + ch + "' uppercase? " + Character.isUpperCase(ch));
        System.out.println("Is '" + ch + "' lowercase? " + Character.isLowerCase(ch));

        // toLowerCase() and toUpperCase() methods
        System.out.println("Lowercase of '" + ch + "': " + Character.toLowerCase(ch));
        System.out.println("Uppercase of 'A': " + Character.toUpperCase('a'));

        // isWhitespace() method
        char space = ' ';
        System.out.println("Is space a whitespace character? " + Character.isWhitespace(space));

        // forDigit() and digit() methods
        int digit = 5;
        System.out.println("Character for digit 5 (radix 10): " + Character.forDigit(digit, 10));
        System.out.println("Numeric value of '5': " + Character.digit('5', 10));

        // Get type of character
        System.out.println("Type of 'A': " + Character.getType(ch));

        // Check if a character is defined
        System.out.println("Is '" + ch + "' defined? " + Character.isDefined(ch));

        // Additional examples
        char letterOrDigit = '7';
        System.out.println("Is '" + letterOrDigit + "' a letter or digit? " + Character.isLetterOrDigit(letterOrDigit));

        char unicodeChar = '\u0905';
        System.out.println("Unicode character example: " + unicodeChar);
        System.out.println("Is '" + unicodeChar + "' alphabetic? " + Character.isAlphabetic(unicodeChar));

        char titleCaseSample = '\u01C5';
        System.out.println("Is title case character? " + Character.isTitleCase(titleCaseSample));

        char mirroredChar = '(';
        System.out.println("Is '" + mirroredChar + "' mirrored? " + Character.isMirrored(mirroredChar));

        char identifierStart = '_';
        char identifierPart = '9';
        System.out.println("Can '_' start Java identifier? " + Character.isJavaIdentifierStart(identifierStart));
        System.out.println("Can '9' be Java identifier part? " + Character.isJavaIdentifierPart(identifierPart));

        char isoControl = '\n';
        System.out.println("Is newline an ISO control character? " + Character.isISOControl(isoControl));

        char hexDigit = 'F';
        System.out.println("Numeric value of 'F' in radix 16: " + Character.digit(hexDigit, 16));
        System.out.println("Character for number 15 in radix 16: " + Character.forDigit(15, 16));

        char lowSurrogate = '\uDC00';
        char highSurrogate = '\uD800';
        System.out.println("Is low surrogate? " + Character.isLowSurrogate(lowSurrogate));
        System.out.println("Is high surrogate? " + Character.isHighSurrogate(highSurrogate));

        char sample = 'b';
        System.out.println("Compare 'A' and 'b': " + Character.compare('A', sample));
        System.out.println("Uppercase of 'b': " + Character.toUpperCase(sample));
    }
}