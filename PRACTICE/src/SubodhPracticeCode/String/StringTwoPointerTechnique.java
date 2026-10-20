package com.example.String;

/**
 * CORE CONCEPT: Two Pointer Technique for Strings
 * 
 * Use when: Palindrome checks, reversing, comparing from both ends
 * Time Complexity: O(n), Space: O(1) or O(n) depending on mutability
 * 
 * Pattern Recognition:
 * - Palindrome problems
 * - Reversing string/words
 * - Removing characters
 * - Valid palindrome after modifications
 * 
 * Key Insight: String is immutable in Java, use char[] or StringBuilder
 * 
 * Applications:
 * - Check palindrome
 * - Reverse string
 * - Valid palindrome (ignoring spaces/punctuation)
 * - Closest palindrome by removing one character
 */
public class StringTwoPointerTechnique {
    
    public static void main(String[] args) {
        // Palindrome check
        String s1 = "racecar";
        System.out.println("'" + s1 + "' is palindrome: " + isPalindrome(s1));
        
        String s2 = "hello";
        System.out.println("'" + s2 + "' is palindrome: " + isPalindrome(s2));
        
        // Valid palindrome (ignore non-alphanumeric)
        String s3 = "A man, a plan, a canal: Panama";
        System.out.println("\n'" + s3 + "'");
        System.out.println("Valid palindrome: " + isPalindromeIgnoreSpecial(s3));
        
        // Reverse string
        String s4 = "hello";
        System.out.println("\n\nOriginal: " + s4);
        System.out.println("Reversed: " + reverseString(s4));
        
        // Reverse words
        String s5 = "Hello World Java";
        System.out.println("\nOriginal: " + s5);
        System.out.println("Reversed words: " + reverseWords(s5));
        System.out.println("Reversed each word: " + reverseEachWord(s5));
        
        // Can form palindrome by removing at most one character
        String s6 = "abca";
        System.out.println("\n\n'" + s6 + "' can be palindrome by removing 1 char: " 
                          + validPalindromeRemoveOne(s6));
        
        // Remove adjacent duplicates
        String s7 = "abbaca";
        System.out.println("\n'" + s7 + "' after removing adjacent duplicates: " 
                          + removeAdjacentDuplicates(s7));
    }
    
    /**
     * Check if string is palindrome
     * Concept: Compare characters from both ends moving inward
     */
    public static boolean isPalindrome(String s) {
        if (s == null || s.length() <= 1) return true;
        
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Valid palindrome ignoring non-alphanumeric characters and case
     * Concept: Skip invalid characters, compare valid ones
     */
    public static boolean isPalindromeIgnoreSpecial(String s) {
        if (s == null) return true;
        
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            // Skip non-alphanumeric from left
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            
            // Skip non-alphanumeric from right
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            
            // Compare (case-insensitive)
            if (Character.toLowerCase(s.charAt(left)) != 
                Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Reverse entire string
     * Concept: Convert to char array, swap from both ends
     */
    public static String reverseString(String s) {
        if (s == null || s.length() <= 1) return s;
        
        char[] chars = s.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        
        return new String(chars);
    }
    
    /**
     * Reverse words in a sentence
     * Example: "Hello World" -> "World Hello"
     * 
     * Concept: Split, reverse array, join
     */
    public static String reverseWords(String s) {
        if (s == null || s.isEmpty()) return s;
        
        String[] words = s.trim().split("\\s+");
        
        int left = 0;
        int right = words.length - 1;
        
        while (left < right) {
            String temp = words[left];
            words[left] = words[right];
            words[right] = temp;
            left++;
            right--;
        }
        
        return String.join(" ", words);
    }
    
    /**
     * Reverse each word individually
     * Example: "Hello World" -> "olleH dlroW"
     */
    public static String reverseEachWord(String s) {
        if (s == null || s.isEmpty()) return s;
        
        String[] words = s.split(" ");
        StringBuilder result = new StringBuilder();
        
        for (String word : words) {
            result.append(reverseString(word)).append(" ");
        }
        
        return result.toString().trim();
    }
    
    /**
     * Valid palindrome by removing at most one character
     * Concept: When mismatch found, try removing left OR right character
     */
    public static boolean validPalindromeRemoveOne(String s) {
        if (s == null || s.length() <= 1) return true;
        
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                // Try removing left or right character
                return isPalindromeRange(s, left + 1, right) || 
                       isPalindromeRange(s, left, right - 1);
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    private static boolean isPalindromeRange(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    /**
     * Remove adjacent duplicate characters
     * Example: "abbaca" -> "ca" (remove bb, then aa)
     * 
     * Concept: Use StringBuilder as stack
     */
    public static String removeAdjacentDuplicates(String s) {
        if (s == null || s.length() <= 1) return s;
        
        StringBuilder stack = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            int len = stack.length();
            
            if (len > 0 && stack.charAt(len - 1) == c) {
                stack.deleteCharAt(len - 1);
            } else {
                stack.append(c);
            }
        }
        
        return stack.toString();
    }
    
    /**
     * Check if one string is rotation of another
     * Example: "waterbottle" is rotation of "erbottlewat"
     * 
     * Concept: s2 is rotation of s1 if s2 is substring of s1+s1
     */
    public static boolean isRotation(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.length() != s2.length()) return false;
        
        String combined = s1 + s1;
        return combined.contains(s2);
    }
    
    /**
     * Compress string: "aabcccccaaa" -> "a2b1c5a3"
     * Only compress if result is shorter
     */
    public static String compressString(String s) {
        if (s == null || s.length() <= 1) return s;
        
        StringBuilder compressed = new StringBuilder();
        int count = 1;
        
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                compressed.append(s.charAt(i - 1)).append(count);
                count = 1;
            }
        }
        
        compressed.append(s.charAt(s.length() - 1)).append(count);
        
        return compressed.length() < s.length() ? compressed.toString() : s;
    }
}
