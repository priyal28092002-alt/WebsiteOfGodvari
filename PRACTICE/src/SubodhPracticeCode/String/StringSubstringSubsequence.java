package com.example.String;

import java.util.*;

/**
 * CORE CONCEPT: Substring and Subsequence Problems
 * 
 * Use when: Finding substrings, subsequences, continuous vs non-continuous
 * Time Complexity: O(n²) for substring generation, O(2^n) for subsequences
 * 
 * Pattern Recognition:
 * - Substring: Contiguous characters (abc has: a,b,c,ab,bc,abc)
 * - Subsequence: Non-contiguous, maintain order (abc has: a,b,c,ab,ac,bc,abc)
 * - Generate all substrings/subsequences
 * - Longest/shortest with specific property
 * 
 * Key Difference:
 * - Substring: Must be continuous
 * - Subsequence: Can skip characters but maintain order
 * 
 * Applications:
 * - All substrings
 * - All subsequences
 * - Longest common substring
 * - Longest common subsequence
 */
public class StringSubstringSubsequence {
    
    public static void main(String[] args) {
        String s = "abc";
        
        System.out.println("String: " + s);
        System.out.println("\n=== SUBSTRINGS (contiguous) ===");
        List<String> substrings = getAllSubstrings(s);
        System.out.println("All substrings: " + substrings);
        System.out.println("Count: " + substrings.size());
        
        System.out.println("\n=== SUBSEQUENCES (non-contiguous) ===");
        List<String> subsequences = getAllSubsequences(s);
        System.out.println("All subsequences: " + subsequences);
        System.out.println("Count: " + subsequences.size());
        
        // Palindromic substrings
        String s2 = "aab";
        System.out.println("\n\nString: " + s2);
        System.out.println("Palindromic substrings: " 
                          + countPalindromicSubstrings(s2));
        
        // Longest common substring
        String s3 = "abcdef";
        String s4 = "zbcdf";
        System.out.println("\n\nLongest common substring:");
        System.out.println("'" + s3 + "' and '" + s4 + "'");
        System.out.println("LCS: '" + longestCommonSubstring(s3, s4) + "'");
        
        // Longest common subsequence
        String s5 = "abcde";
        String s6 = "ace";
        System.out.println("\n\nLongest common subsequence:");
        System.out.println("'" + s5 + "' and '" + s6 + "'");
        System.out.println("LCS length: " + longestCommonSubsequence(s5, s6));
        
        // Distinct substrings
        String s7 = "aaa";
        System.out.println("\n\nDistinct substrings in '" + s7 + "': " 
                          + countDistinctSubstrings(s7));
    }
    
    /**
     * Generate all substrings
     * Concept: For each starting position, generate all ending positions
     * Count = n*(n+1)/2 where n = length
     */
    public static List<String> getAllSubstrings(String s) {
        List<String> result = new ArrayList<>();
        
        if (s == null || s.isEmpty()) return result;
        
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                result.add(s.substring(i, j));
            }
        }
        
        return result;
    }
    
    /**
     * Generate all subsequences
     * Concept: For each character, include or exclude (binary decision)
     * Count = 2^n where n = length
     */
    public static List<String> getAllSubsequences(String s) {
        List<String> result = new ArrayList<>();
        
        if (s == null) return result;
        
        generateSubsequences(s, 0, "", result);
        
        return result;
    }
    
    private static void generateSubsequences(String s, int index, 
                                             String current, List<String> result) {
        if (index == s.length()) {
            result.add(current);
            return;
        }
        
        // Exclude current character
        generateSubsequences(s, index + 1, current, result);
        
        // Include current character
        generateSubsequences(s, index + 1, current + s.charAt(index), result);
    }
    
    /**
     * Generate all subsequences using bit manipulation
     * Concept: Each bit pattern represents include/exclude decision
     */
    public static List<String> getAllSubsequencesBitwise(String s) {
        List<String> result = new ArrayList<>();
        
        if (s == null) return result;
        
        int n = s.length();
        int total = 1 << n; // 2^n
        
        for (int i = 0; i < total; i++) {
            StringBuilder sb = new StringBuilder();
            
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    sb.append(s.charAt(j));
                }
            }
            
            result.add(sb.toString());
        }
        
        return result;
    }
    
    /**
     * Count distinct substrings
     * Concept: Use HashSet to store unique substrings
     */
    public static int countDistinctSubstrings(String s) {
        if (s == null || s.isEmpty()) return 0;
        
        Set<String> distinct = new HashSet<>();
        
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                distinct.add(s.substring(i, j));
            }
        }
        
        return distinct.size();
    }
    
    /**
     * Count palindromic substrings
     * Concept: Expand around each center
     */
    public static int countPalindromicSubstrings(String s) {
        if (s == null || s.isEmpty()) return 0;
        
        int count = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // Odd length palindromes
            count += expandAroundCenter(s, i, i);
            
            // Even length palindromes
            count += expandAroundCenter(s, i, i + 1);
        }
        
        return count;
    }
    
    private static int expandAroundCenter(String s, int left, int right) {
        int count = 0;
        
        while (left >= 0 && right < s.length() && 
               s.charAt(left) == s.charAt(right)) {
            count++;
            left--;
            right++;
        }
        
        return count;
    }
    
    /**
     * Longest Common Substring (contiguous)
     * Concept: Dynamic Programming - dp[i][j] stores length ending at i,j
     */
    public static String longestCommonSubstring(String s1, String s2) {
        if (s1 == null || s2 == null) return "";
        
        int m = s1.length();
        int n = s2.length();
        int maxLength = 0;
        int endIndex = 0;
        
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    
                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        endIndex = i;
                    }
                }
            }
        }
        
        return s1.substring(endIndex - maxLength, endIndex);
    }
    
    /**
     * Longest Common Subsequence (non-contiguous)
     * Concept: DP - if chars match, add 1, else take max of two options
     */
    public static int longestCommonSubsequence(String s1, String s2) {
        if (s1 == null || s2 == null) return 0;
        
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }
    
    /**
     * Print Longest Common Subsequence
     */
    public static String printLCS(String s1, String s2) {
        if (s1 == null || s2 == null) return "";
        
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Backtrack to find the LCS
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        
        while (i > 0 && j > 0) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                lcs.insert(0, s1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        
        return lcs.toString();
    }
    
    /**
     * Longest Palindromic Substring
     * Concept: Expand around center for each position
     */
    public static String longestPalindromicSubstring(String s) {
        if (s == null || s.length() < 2) return s;
        
        String longest = "";
        
        for (int i = 0; i < s.length(); i++) {
            String odd = expandPalindrome(s, i, i);
            String even = expandPalindrome(s, i, i + 1);
            
            String current = odd.length() > even.length() ? odd : even;
            if (current.length() > longest.length()) {
                longest = current;
            }
        }
        
        return longest;
    }
    
    private static String expandPalindrome(String s, int left, int right) {
        while (left >= 0 && right < s.length() && 
               s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return s.substring(left + 1, right);
    }
    
    /**
     * Longest Palindromic Subsequence
     * Concept: LCS of string with its reverse
     */
    public static int longestPalindromicSubsequence(String s) {
        if (s == null || s.isEmpty()) return 0;
        
        String reversed = new StringBuilder(s).reverse().toString();
        return longestCommonSubsequence(s, reversed);
    }
    
    /**
     * Check if s2 is substring of s1
     */
    public static boolean isSubstring(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        return s1.contains(s2);
    }
    
    /**
     * Check if s2 is subsequence of s1
     * Concept: Two pointer, match characters in order
     */
    public static boolean isSubsequence(String s1, String s2) {
        if (s2 == null || s2.isEmpty()) return true;
        if (s1 == null) return false;
        
        int j = 0;
        
        for (int i = 0; i < s1.length() && j < s2.length(); i++) {
            if (s1.charAt(i) == s2.charAt(j)) {
                j++;
            }
        }
        
        return j == s2.length();
    }
    
    /**
     * Minimum deletions to make palindrome
     * Concept: length - LPS (longest palindromic subsequence)
     */
    public static int minDeletionsForPalindrome(String s) {
        if (s == null || s.isEmpty()) return 0;
        
        return s.length() - longestPalindromicSubsequence(s);
    }
}
