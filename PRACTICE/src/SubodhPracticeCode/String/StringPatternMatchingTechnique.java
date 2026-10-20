package com.example.String;

/**
 * CORE CONCEPT: Pattern Matching and Searching
 * 
 * Use when: Finding substring, pattern occurrence, string matching
 * Time Complexity: O(n*m) naive, O(n+m) with KMP/Boyer-Moore
 * 
 * Pattern Recognition:
 * - Find substring/pattern in text
 * - Count occurrences
 * - Find all occurrences
 * - Wildcard matching
 * - Regular patterns
 * 
 * Key Algorithms:
 * 1. Naive pattern matching - O(n*m)
 * 2. Two pointer for simple patterns
 * 3. Sliding window for fixed patterns
 * 
 * Applications:
 * - Find pattern in text
 * - Count substring occurrences
 * - Find longest common prefix
 * - String matching with wildcards
 */
public class StringPatternMatchingTechnique {
    
    public static void main(String[] args) {
        String text = "AABAACAADAABAABA";
        String pattern = "AABA";
        
        System.out.println("Text: " + text);
        System.out.println("Pattern: " + pattern);
        System.out.println("\nPattern found at indices: " + findPattern(text, pattern));
        System.out.println("Pattern count: " + countOccurrences(text, pattern));
        
        System.out.println("\n\nSubstring operations:");
        System.out.println("First occurrence of 'AAB': " 
                          + text.indexOf("AAB"));
        System.out.println("Last occurrence of 'AAB': " 
                          + text.lastIndexOf("AAB"));
        
        System.out.println("\n\nCommon prefix:");
        String[] strs = {"flower", "flow", "flight"};
        System.out.println("Strings: " + java.util.Arrays.toString(strs));
        System.out.println("Longest common prefix: " + longestCommonPrefix(strs));
        
        System.out.println("\n\nPattern with wildcards:");
        System.out.println("'aa' matches 'a*': " + wildcardMatch("aa", "a*"));
        System.out.println("'ab' matches 'a?': " + wildcardMatch("ab", "a?"));
        
        System.out.println("\n\nSubsequence check:");
        System.out.println("'abc' is subsequence of 'ahbgdc': " 
                          + isSubsequence("abc", "ahbgdc"));
        
        System.out.println("\n\nRepeated substring:");
        System.out.println("'abab' has repeated pattern: " 
                          + hasRepeatedPattern("abab"));
        System.out.println("Pattern: " + findRepeatedPattern("abababab"));
    }
    
    /**
     * Find all occurrences of pattern in text - Naive approach
     * Concept: Check pattern at each position
     */
    public static java.util.List<Integer> findPattern(String text, String pattern) {
        java.util.List<Integer> indices = new java.util.ArrayList<>();
        
        if (text == null || pattern == null || pattern.length() > text.length()) {
            return indices;
        }
        
        int n = text.length();
        int m = pattern.length();
        
        for (int i = 0; i <= n - m; i++) {
            int j;
            
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            
            if (j == m) {
                indices.add(i);
            }
        }
        
        return indices;
    }
    
    /**
     * Count occurrences of pattern in text
     */
    public static int countOccurrences(String text, String pattern) {
        if (text == null || pattern == null || pattern.isEmpty()) {
            return 0;
        }
        
        int count = 0;
        int index = 0;
        
        while ((index = text.indexOf(pattern, index)) != -1) {
            count++;
            index += pattern.length();
        }
        
        return count;
    }
    
    /**
     * Count overlapping occurrences
     * Example: "AAAA" contains "AA" three times (overlapping)
     */
    public static int countOverlappingOccurrences(String text, String pattern) {
        if (text == null || pattern == null || pattern.isEmpty()) {
            return 0;
        }
        
        int count = 0;
        int index = 0;
        
        while ((index = text.indexOf(pattern, index)) != -1) {
            count++;
            index++; // Move by 1 for overlapping
        }
        
        return count;
    }
    
    /**
     * Longest common prefix of array of strings
     * Concept: Compare characters at each position across all strings
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        
        StringBuilder prefix = new StringBuilder();
        
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[j].charAt(i) != c) {
                    return prefix.toString();
                }
            }
            
            prefix.append(c);
        }
        
        return prefix.toString();
    }
    
    /**
     * Wildcard pattern matching
     * * matches any sequence
     * ? matches single character
     * 
     * Simple implementation for basic cases
     */
    public static boolean wildcardMatch(String text, String pattern) {
        if (pattern.equals("*")) return true;
        if (text == null || pattern == null) return false;
        
        int i = 0, j = 0;
        int starIdx = -1, matchIdx = 0;
        
        while (i < text.length()) {
            if (j < pattern.length() && 
                (pattern.charAt(j) == '?' || pattern.charAt(j) == text.charAt(i))) {
                i++;
                j++;
            } else if (j < pattern.length() && pattern.charAt(j) == '*') {
                starIdx = j;
                matchIdx = i;
                j++;
            } else if (starIdx != -1) {
                j = starIdx + 1;
                matchIdx++;
                i = matchIdx;
            } else {
                return false;
            }
        }
        
        while (j < pattern.length() && pattern.charAt(j) == '*') {
            j++;
        }
        
        return j == pattern.length();
    }
    
    /**
     * Check if s is subsequence of t
     * Concept: Two pointer, match characters in order
     */
    public static boolean isSubsequence(String s, String t) {
        if (s == null || t == null) return false;
        if (s.isEmpty()) return true;
        
        int i = 0;
        
        for (int j = 0; j < t.length() && i < s.length(); j++) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
        }
        
        return i == s.length();
    }
    
    /**
     * Check if string has repeated pattern
     * Example: "abab" = "ab" + "ab"
     * 
     * Concept: If s = pattern repeated, then (s+s) contains s at position != 0
     */
    public static boolean hasRepeatedPattern(String s) {
        if (s == null || s.length() <= 1) return false;
        
        String doubled = s + s;
        return doubled.substring(1, doubled.length() - 1).contains(s);
    }
    
    /**
     * Find smallest repeated pattern
     * Example: "abcabcabc" -> "abc"
     */
    public static String findRepeatedPattern(String s) {
        if (s == null || s.isEmpty()) return s;
        
        int n = s.length();
        
        for (int len = 1; len <= n / 2; len++) {
            if (n % len == 0) {
                String pattern = s.substring(0, len);
                boolean valid = true;
                
                for (int i = len; i < n; i += len) {
                    if (!s.substring(i, i + len).equals(pattern)) {
                        valid = false;
                        break;
                    }
                }
                
                if (valid) return pattern;
            }
        }
        
        return s;
    }
    
    /**
     * Longest common substring (contiguous)
     * Concept: Check all substrings of shorter string in longer
     */
    public static String longestCommonSubstring(String s1, String s2) {
        if (s1 == null || s2 == null) return "";
        
        String shorter = s1.length() < s2.length() ? s1 : s2;
        String longer = s1.length() >= s2.length() ? s1 : s2;
        
        String longest = "";
        
        for (int i = 0; i < shorter.length(); i++) {
            for (int j = i + 1; j <= shorter.length(); j++) {
                String sub = shorter.substring(i, j);
                if (longer.contains(sub) && sub.length() > longest.length()) {
                    longest = sub;
                }
            }
        }
        
        return longest;
    }
    
    /**
     * Find first occurrence of needle in haystack
     * Returns index or -1
     */
    public static int strStr(String haystack, String needle) {
        if (needle == null || needle.isEmpty()) return 0;
        if (haystack == null) return -1;
        
        return haystack.indexOf(needle);
    }
    
    /**
     * Check if strings are rotations of each other
     * Example: "waterbottle" and "erbottlewat"
     */
    public static boolean areRotations(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.length() != s2.length()) return false;
        
        return (s1 + s1).contains(s2);
    }
    
    /**
     * Count distinct substrings
     */
    public static int countDistinctSubstrings(String s) {
        if (s == null) return 0;
        
        java.util.Set<String> substrings = new java.util.HashSet<>();
        
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                substrings.add(s.substring(i, j));
            }
        }
        
        return substrings.size();
    }
    
    /**
     * Find all palindromic substrings
     */
    public static java.util.List<String> findPalindromicSubstrings(String s) {
        java.util.List<String> palindromes = new java.util.ArrayList<>();
        
        if (s == null || s.isEmpty()) return palindromes;
        
        for (int i = 0; i < s.length(); i++) {
            // Odd length palindromes
            expandAroundCenter(s, i, i, palindromes);
            
            // Even length palindromes
            expandAroundCenter(s, i, i + 1, palindromes);
        }
        
        return palindromes;
    }
    
    private static void expandAroundCenter(String s, int left, int right, 
                                           java.util.List<String> palindromes) {
        while (left >= 0 && right < s.length() && 
               s.charAt(left) == s.charAt(right)) {
            palindromes.add(s.substring(left, right + 1));
            left--;
            right++;
        }
    }
    
    /**
     * Longest palindromic substring
     */
    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 2) return s;
        
        String longest = "";
        
        for (int i = 0; i < s.length(); i++) {
            String odd = expandFromCenter(s, i, i);
            String even = expandFromCenter(s, i, i + 1);
            
            String current = odd.length() > even.length() ? odd : even;
            if (current.length() > longest.length()) {
                longest = current;
            }
        }
        
        return longest;
    }
    
    private static String expandFromCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && 
               s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return s.substring(left + 1, right);
    }
}
