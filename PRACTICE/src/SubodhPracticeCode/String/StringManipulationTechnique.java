package com.example.String;

/**
 * CORE CONCEPT: String Manipulation and Transformation
 * 
 * Use when: Case conversion, character removal/replacement, toggling
 * Time Complexity: O(n), Space: O(n) due to immutability
 * 
 * Pattern Recognition:
 * - Case conversions (upper/lower/toggle)
 * - Remove/replace characters
 * - Extract digits/letters
 * - Swap characters
 * - String formatting
 * 
 * Key Insight: String immutable → use StringBuilder for multiple operations
 * 
 * Applications:
 * - Toggle case
 * - Remove vowels/consonants
 * - Remove spaces
 * - Extract digits and sum
 * - Replace characters
 */
public class StringManipulationTechnique {
    
    public static void main(String[] args) {
        String s = "Hello World 123";
        
        System.out.println("Original: " + s);
        System.out.println("Uppercase: " + toUpperCase(s));
        System.out.println("Lowercase: " + toLowerCase(s));
        System.out.println("Toggle case: " + toggleCase(s));
        
        System.out.println("\n\nCapitalization:");
        System.out.println("First letter: " + capitalizeFirst("hello world"));
        System.out.println("Each word: " + capitalizeEachWord("hello world java"));
        
        System.out.println("\n\nRemoval operations:");
        System.out.println("Remove vowels: " + removeVowels(s));
        System.out.println("Remove spaces: " + removeSpaces(s));
        System.out.println("Remove digits: " + removeDigits(s));
        System.out.println("Keep only letters: " + keepOnlyLetters(s));
        System.out.println("Keep only digits: " + keepOnlyDigits(s));
        
        System.out.println("\n\nDigit operations:");
        System.out.println("Sum of digits: " + sumOfDigits(s));
        System.out.println("Extract number: " + extractNumber("abc123def456"));
        
        System.out.println("\n\nReplacement:");
        System.out.println("Replace spaces with underscore: " 
                          + replaceSpaces("Hello World", "_"));
        System.out.println("Remove duplicates: " + removeDuplicates("programming"));
        
        System.out.println("\n\nSwapping:");
        System.out.println("Swap case: " + swapCase("HeLLo WoRLd"));
        System.out.println("Reverse vowels: " + reverseVowels("hello"));
    }
    
    /**
     * Convert to uppercase without using built-in
     * Concept: ASCII difference between 'a' and 'A' is 32
     */
    public static String toUpperCase(String s) {
        if (s == null) return null;
        
        StringBuilder result = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                result.append((char)(c - 32));
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Convert to lowercase without using built-in
     */
    public static String toLowerCase(String s) {
        if (s == null) return null;
        
        StringBuilder result = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                result.append((char)(c + 32));
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Toggle case of each character
     * Concept: Swap upper to lower and vice versa
     */
    public static String toggleCase(String s) {
        if (s == null) return null;
        
        StringBuilder result = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                result.append((char)(c - 32));
            } else if (c >= 'A' && c <= 'Z') {
                result.append((char)(c + 32));
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Capitalize first letter
     */
    public static String capitalizeFirst(String s) {
        if (s == null || s.isEmpty()) return s;
        
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }
    
    /**
     * Capitalize first letter of each word
     */
    public static String capitalizeEachWord(String s) {
        if (s == null || s.isEmpty()) return s;
        
        String[] words = s.split(" ");
        StringBuilder result = new StringBuilder();
        
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase())
                      .append(" ");
            }
        }
        
        return result.toString().trim();
    }
    
    /**
     * Remove vowels from string
     * Concept: Skip vowels while building result
     */
    public static String removeVowels(String s) {
        if (s == null) return null;
        
        StringBuilder result = new StringBuilder();
        String vowels = "aeiouAEIOU";
        
        for (char c : s.toCharArray()) {
            if (vowels.indexOf(c) == -1) {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Remove all spaces
     */
    public static String removeSpaces(String s) {
        if (s == null) return null;
        
        return s.replaceAll("\\s+", "");
    }
    
    /**
     * Remove all digits
     */
    public static String removeDigits(String s) {
        if (s == null) return null;
        
        StringBuilder result = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Keep only letters
     */
    public static String keepOnlyLetters(String s) {
        if (s == null) return null;
        
        StringBuilder result = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Keep only digits
     */
    public static String keepOnlyDigits(String s) {
        if (s == null) return null;
        
        StringBuilder result = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Sum of all digits in string
     * Concept: Extract digits and sum them
     */
    public static int sumOfDigits(String s) {
        if (s == null) return 0;
        
        int sum = 0;
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                sum += c - '0';
            }
        }
        
        return sum;
    }
    
    /**
     * Extract complete number from string
     * Example: "abc123def456" -> [123, 456]
     */
    public static String extractNumber(String s) {
        if (s == null) return "";
        
        return s.replaceAll("[^0-9]", " ").trim();
    }
    
    /**
     * Replace spaces with given character/string
     */
    public static String replaceSpaces(String s, String replacement) {
        if (s == null) return null;
        
        return s.replaceAll(" ", replacement);
    }
    
    /**
     * Remove duplicate characters (keep first occurrence)
     * Concept: Use boolean array to track seen characters
     */
    public static String removeDuplicates(String s) {
        if (s == null || s.length() <= 1) return s;
        
        boolean[] seen = new boolean[256];
        StringBuilder result = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (!seen[c]) {
                result.append(c);
                seen[c] = true;
            }
        }
        
        return result.toString();
    }
    
    /**
     * Swap case of all characters
     */
    public static String swapCase(String s) {
        if (s == null) return null;
        
        StringBuilder result = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append(Character.toLowerCase(c));
            } else if (Character.isLowerCase(c)) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Reverse only vowels in string
     * Concept: Two pointer, swap vowels only
     */
    public static String reverseVowels(String s) {
        if (s == null || s.length() <= 1) return s;
        
        char[] chars = s.toCharArray();
        String vowels = "aeiouAEIOU";
        int left = 0;
        int right = chars.length - 1;
        
        while (left < right) {
            while (left < right && vowels.indexOf(chars[left]) == -1) {
                left++;
            }
            
            while (left < right && vowels.indexOf(chars[right]) == -1) {
                right--;
            }
            
            if (left < right) {
                char temp = chars[left];
                chars[left] = chars[right];
                chars[right] = temp;
                left++;
                right--;
            }
        }
        
        return new String(chars);
    }
    
    /**
     * Remove last N characters
     */
    public static String removeLastN(String s, int n) {
        if (s == null || n <= 0) return s;
        if (n >= s.length()) return "";
        
        return s.substring(0, s.length() - n);
    }
    
    /**
     * Remove first N characters
     */
    public static String removeFirstN(String s, int n) {
        if (s == null || n <= 0) return s;
        if (n >= s.length()) return "";
        
        return s.substring(n);
    }
    
    /**
     * Trim spaces from both ends without built-in
     */
    public static String trimSpaces(String s) {
        if (s == null || s.isEmpty()) return s;
        
        int start = 0;
        int end = s.length() - 1;
        
        while (start <= end && s.charAt(start) == ' ') {
            start++;
        }
        
        while (end >= start && s.charAt(end) == ' ') {
            end--;
        }
        
        return s.substring(start, end + 1);
    }
}
