package com.example.String;

import java.util.*;

/**
 * CORE CONCEPT: Frequency Counting and Hashing for Strings
 * 
 * Use when: Anagrams, character counting, pattern matching
 * Time Complexity: O(n), Space: O(1) for fixed charset, O(k) for k unique chars
 * 
 * Pattern Recognition:
 * - Anagram problems
 * - Character frequency
 * - First non-repeating character
 * - Permutation in string
 * - Group similar strings
 * 
 * Key Techniques:
 * 1. int[26] for lowercase letters (O(1) space)
 * 2. HashMap for general characters
 * 3. Sliding window + frequency for patterns
 * 
 * Applications:
 * - Check if two strings are anagrams
 * - Find all anagrams in a string
 * - First unique character
 * - Longest substring without repeating
 */
public class StringFrequencyTechnique {
    
    public static void main(String[] args) {
        // Anagram check
        String s1 = "listen";
        String s2 = "silent";
        System.out.println("'" + s1 + "' and '" + s2 + "' are anagrams: " 
                          + isAnagram(s1, s2));
        
        // Character frequency
        String s3 = "programming";
        System.out.println("\n\nCharacter frequency in '" + s3 + "':");
        Map<Character, Integer> freq = getFrequency(s3);
        freq.forEach((k, v) -> System.out.println(k + ": " + v));
        
        // First non-repeating character
        String s4 = "leetcode";
        System.out.println("\n\nFirst non-repeating in '" + s4 + "': " 
                          + firstNonRepeating(s4));
        
        // Group anagrams
        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println("\n\nWords: " + Arrays.toString(words));
        System.out.println("Grouped anagrams:");
        List<List<String>> groups = groupAnagrams(words);
        groups.forEach(System.out::println);
        
        // Find all anagrams in string
        String text = "cbaebabacd";
        String pattern = "abc";
        System.out.println("\n\nFind '" + pattern + "' anagrams in '" + text + "':");
        System.out.println("Starting indices: " + findAnagrams(text, pattern));
        
        // Longest substring without repeating
        String s5 = "abcabcbb";
        System.out.println("\n\nLongest substring without repeating in '" + s5 + "': " 
                          + lengthOfLongestSubstring(s5));
    }
    
    /**
     * Check if two strings are anagrams
     * Concept: Same character frequencies
     * 
     * Method 1: Sort and compare
     * Method 2: Frequency count (shown here)
     */
    public static boolean isAnagram(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.length() != s2.length()) return false;
        
        int[] freq = new int[26];
        
        for (int i = 0; i < s1.length(); i++) {
            freq[s1.charAt(i) - 'a']++;
            freq[s2.charAt(i) - 'a']--;
        }
        
        for (int count : freq) {
            if (count != 0) return false;
        }
        
        return true;
    }
    
    /**
     * Get character frequency map
     */
    public static Map<Character, Integer> getFrequency(String s) {
        Map<Character, Integer> freq = new LinkedHashMap<>();
        
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        
        return freq;
    }
    
    /**
     * Find first non-repeating character
     * Concept: Count frequencies, find first with count = 1
     */
    public static char firstNonRepeating(String s) {
        if (s == null || s.isEmpty()) return '\0';
        
        Map<Character, Integer> freq = new LinkedHashMap<>();
        
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        
        for (char c : s.toCharArray()) {
            if (freq.get(c) == 1) {
                return c;
            }
        }
        
        return '\0';
    }
    
    /**
     * Group anagrams together
     * Concept: Use sorted string as key
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        
        return new ArrayList<>(map.values());
    }
    
    /**
     * Find all starting indices of anagrams of pattern in text
     * Concept: Sliding window with frequency matching
     */
    public static List<Integer> findAnagrams(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        
        if (text == null || pattern == null || text.length() < pattern.length()) {
            return result;
        }
        
        int[] patternFreq = new int[26];
        int[] windowFreq = new int[26];
        
        // Build pattern frequency
        for (char c : pattern.toCharArray()) {
            patternFreq[c - 'a']++;
        }
        
        int windowSize = pattern.length();
        
        // First window
        for (int i = 0; i < windowSize; i++) {
            windowFreq[text.charAt(i) - 'a']++;
        }
        
        if (Arrays.equals(patternFreq, windowFreq)) {
            result.add(0);
        }
        
        // Slide window
        for (int i = windowSize; i < text.length(); i++) {
            // Add new character
            windowFreq[text.charAt(i) - 'a']++;
            
            // Remove old character
            windowFreq[text.charAt(i - windowSize) - 'a']--;
            
            if (Arrays.equals(patternFreq, windowFreq)) {
                result.add(i - windowSize + 1);
            }
        }
        
        return result;
    }
    
    /**
     * Longest substring without repeating characters
     * Concept: Sliding window + HashSet
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) return 0;
        
        Set<Character> seen = new HashSet<>();
        int maxLength = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            
            // Remove characters until no duplicate
            while (seen.contains(c)) {
                seen.remove(s.charAt(left));
                left++;
            }
            
            seen.add(c);
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Check if string contains permutation of pattern
     * Concept: Sliding window with frequency match
     */
    public static boolean containsPermutation(String text, String pattern) {
        if (text == null || pattern == null || text.length() < pattern.length()) {
            return false;
        }
        
        int[] patternFreq = new int[26];
        int[] windowFreq = new int[26];
        
        for (char c : pattern.toCharArray()) {
            patternFreq[c - 'a']++;
        }
        
        for (int i = 0; i < pattern.length(); i++) {
            windowFreq[text.charAt(i) - 'a']++;
        }
        
        if (Arrays.equals(patternFreq, windowFreq)) {
            return true;
        }
        
        for (int i = pattern.length(); i < text.length(); i++) {
            windowFreq[text.charAt(i) - 'a']++;
            windowFreq[text.charAt(i - pattern.length()) - 'a']--;
            
            if (Arrays.equals(patternFreq, windowFreq)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Longest substring with at most K distinct characters
     * Concept: Sliding window + HashMap
     */
    public static int longestSubstringKDistinct(String s, int k) {
        if (s == null || s.isEmpty() || k <= 0) return 0;
        
        Map<Character, Integer> freq = new HashMap<>();
        int maxLength = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            freq.put(c, freq.getOrDefault(c, 0) + 1);
            
            // Shrink window if more than k distinct
            while (freq.size() > k) {
                char leftChar = s.charAt(left);
                freq.put(leftChar, freq.get(leftChar) - 1);
                
                if (freq.get(leftChar) == 0) {
                    freq.remove(leftChar);
                }
                
                left++;
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Check if string has all unique characters
     * Concept: Use frequency array or HashSet
     */
    public static boolean hasAllUniqueCharacters(String s) {
        if (s == null) return true;
        if (s.length() > 256) return false; // ASCII assumption
        
        boolean[] seen = new boolean[256];
        
        for (char c : s.toCharArray()) {
            if (seen[c]) return false;
            seen[c] = true;
        }
        
        return true;
    }
    
    /**
     * Most frequent character
     */
    public static char mostFrequentChar(String s) {
        if (s == null || s.isEmpty()) return '\0';
        
        int[] freq = new int[256];
        
        for (char c : s.toCharArray()) {
            freq[c]++;
        }
        
        char maxChar = s.charAt(0);
        int maxCount = freq[maxChar];
        
        for (int i = 0; i < 256; i++) {
            if (freq[i] > maxCount) {
                maxCount = freq[i];
                maxChar = (char) i;
            }
        }
        
        return maxChar;
    }
    
    /**
     * Check if string is pangram (contains all 26 letters)
     */
    public static boolean isPangram(String s) {
        if (s == null || s.length() < 26) return false;
        
        boolean[] seen = new boolean[26];
        int count = 0;
        
        for (char c : s.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z' && !seen[c - 'a']) {
                seen[c - 'a'] = true;
                count++;
                if (count == 26) return true;
            }
        }
        
        return false;
    }
}
