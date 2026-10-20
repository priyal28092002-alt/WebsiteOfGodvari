package com.example.String;

/**
 * TECHNIQUE SELECTION GUIDE FOR STRING PROBLEMS
 * 
 * Use this as a decision tree to quickly identify which technique to apply
 * to solve string problems in coding interviews like TCS NQT Prime.
 * 
 * =============================================================================
 * PROBLEM PATTERN RECOGNITION GUIDE
 * =============================================================================
 * 
 * 1. TWO POINTER (StringTwoPointerTechnique.java)
 *    When to use:
 *    - Palindrome problems
 *    - Reversing string/words
 *    - Comparing from both ends
 *    - Removing characters from both sides
 *    - Words: "palindrome", "reverse", "mirror"
 *    
 *    Examples:
 *    - Check palindrome
 *    - Valid palindrome after removing one character
 *    - Reverse string/words
 *    - Remove adjacent duplicates
 *    - String rotation check
 * 
 * 
 * 2. FREQUENCY COUNTING (StringFrequencyTechnique.java)
 *    When to use:
 *    - Anagram problems
 *    - Character counting
 *    - First non-repeating character
 *    - Permutation in string
 *    - Words: "anagram", "frequency", "count", "unique"
 *    
 *    Examples:
 *    - Check if anagrams
 *    - Group anagrams
 *    - Find all anagrams in string
 *    - First unique character
 *    - Longest substring without repeating
 *    - Pangram check
 * 
 * 
 * 3. MANIPULATION (StringManipulationTechnique.java)
 *    When to use:
 *    - Case conversions
 *    - Character removal/replacement
 *    - Extract specific characters
 *    - String formatting
 *    - Words: "convert", "remove", "replace", "extract"
 *    
 *    Examples:
 *    - Toggle/swap case
 *    - Remove vowels/consonants/spaces
 *    - Capitalize words
 *    - Sum of digits in string
 *    - Remove duplicates
 *    - Keep only letters/digits
 * 
 * 
 * 4. PATTERN MATCHING (StringPatternMatchingTechnique.java)
 *    When to use:
 *    - Finding substring/pattern
 *    - Count occurrences
 *    - Wildcard matching
 *    - Common prefix/suffix
 *    - Words: "pattern", "substring", "find", "search", "match"
 *    
 *    Examples:
 *    - Find pattern in text
 *    - Count substring occurrences
 *    - Longest common prefix
 *    - Wildcard matching (* and ?)
 *    - Check subsequence
 *    - Repeated pattern detection
 * 
 * 
 * 5. SORTING (StringSortingTechnique.java)
 *    When to use:
 *    - Lexicographic ordering
 *    - Comparing strings
 *    - Finding smallest/largest
 *    - Custom sorting criteria
 *    - Words: "sort", "order", "lexicographic", "compare"
 *    
 *    Examples:
 *    - Sort strings alphabetically
 *    - Sort by length/frequency
 *    - Find kth smallest
 *    - Largest number from array
 *    - Version comparison
 *    - Custom comparisons
 * 
 * 
 * 6. SUBSTRING/SUBSEQUENCE (StringSubstringSubsequence.java)
 *    When to use:
 *    - Generate all substrings/subsequences
 *    - Longest common substring/subsequence
 *    - Palindromic substrings
 *    - Continuous vs non-continuous patterns
 *    - Words: "substring", "subsequence", "contiguous"
 *    
 *    Examples:
 *    - All substrings (n²)
 *    - All subsequences (2^n)
 *    - Longest common substring (DP)
 *    - Longest common subsequence (DP)
 *    - Palindromic substrings count
 *    - Longest palindromic substring
 * 
 * 
 * 7. VALIDATION (StringValidationTechnique.java)
 *    When to use:
 *    - Format validation
 *    - Property checking
 *    - Balanced structures
 *    - Pattern verification
 *    - Words: "valid", "check", "verify", "balanced"
 *    
 *    Examples:
 *    - Password strength
 *    - Balanced parentheses
 *    - Valid number/email/IP
 *    - Pangram check
 *    - Character type checking
 *    - Format conventions (camelCase, snake_case)
 * 
 * 
 * =============================================================================
 * QUICK DECISION TREE
 * =============================================================================
 * 
 * START HERE:
 * 
 * Q: Is it about palindrome or comparing ends?
 *    YES → Two Pointer
 *    NO  → Continue
 * 
 * Q: Need to count characters or check anagram?
 *    YES → Frequency Counting
 *    NO  → Continue
 * 
 * Q: Need to change case or remove/replace characters?
 *    YES → Manipulation
 *    NO  → Continue
 * 
 * Q: Finding pattern or substring in text?
 *    YES → Pattern Matching
 *    NO  → Continue
 * 
 * Q: Need to sort or compare strings?
 *    YES → Sorting
 *    NO  → Continue
 * 
 * Q: About substrings or subsequences?
 *    YES → Substring/Subsequence
 *    NO  → Continue
 * 
 * Q: Validating format or checking properties?
 *    YES → Validation
 *    NO  → May need combination
 * 
 * 
 * =============================================================================
 * KEY DIFFERENCES
 * =============================================================================
 * 
 * SUBSTRING vs SUBSEQUENCE:
 * - Substring: Contiguous characters ("abc" in "xabcy")
 * - Subsequence: Maintain order, can skip ("ac" in "abc")
 * 
 * ANAGRAM vs PERMUTATION:
 * - Anagram: Rearrangement using all characters
 * - Permutation: All possible arrangements
 * 
 * PALINDROME types:
 * - Simple: Same forward and backward
 * - Ignore case/spaces: "A man a plan a canal Panama"
 * - After removing one char: "abca" -> "aba"
 * 
 * 
 * =============================================================================
 * COMMON TCS NQT STRING PROBLEMS
 * =============================================================================
 * 
 * EASY:
 * - Check palindrome → Two Pointer O(n)
 * - Reverse string → Two Pointer O(n)
 * - Count vowels/consonants → Single pass O(n)
 * - Convert case → Manipulation O(n)
 * - Find character frequency → Frequency O(n)
 * 
 * MEDIUM:
 * - Check anagram → Frequency O(n)
 * - First non-repeating char → Frequency O(n)
 * - Remove adjacent duplicates → Two Pointer/Stack O(n)
 * - Longest substring without repeating → Sliding Window O(n)
 * - Check balanced brackets → Stack O(n)
 * - Reverse words → Two Pointer O(n)
 * 
 * HARD:
 * - Group anagrams → Frequency + Sorting O(n*k log k)
 * - Longest common substring → DP O(n*m)
 * - Longest palindromic substring → Expand O(n²)
 * - Find all anagrams → Sliding Window O(n)
 * - Wildcard matching → DP O(n*m)
 * 
 * 
 * =============================================================================
 * TIME COMPLEXITY CHEAT SHEET
 * =============================================================================
 * 
 * O(n)     - Single pass, Two Pointer, Frequency, Validation
 * O(n²)    - Substring generation, Naive pattern matching
 * O(2^n)   - Subsequence generation
 * O(n log n) - Sorting strings
 * O(n*m)   - DP (LCS, LCSubstring), Pattern matching
 * O(1) space - Two Pointer, bit manipulation
 * O(n) space - Frequency maps, DP tables
 * 
 * 
 * =============================================================================
 * IMPORTANT STRING PROPERTIES
 * =============================================================================
 * 
 * 1. Immutability: String objects cannot be changed
 *    → Use StringBuilder for multiple modifications
 * 
 * 2. Character operations:
 *    - 'A' to 'Z': 65 to 90
 *    - 'a' to 'z': 97 to 122
 *    - Difference: 32
 * 
 * 3. Common methods:
 *    - length(), charAt(), substring()
 *    - indexOf(), lastIndexOf()
 *    - startsWith(), endsWith(), contains()
 *    - split(), replace(), trim()
 *    - toUpperCase(), toLowerCase()
 * 
 * 4. String pool: Literal strings are pooled
 *    - "abc" == "abc" (same reference)
 *    - new String("abc") != "abc" (different reference)
 *    - Use equals() for content comparison
 * 
 * 
 * =============================================================================
 * PRACTICE STRATEGY
 * =============================================================================
 * 
 * 1. Master character operations first (ASCII values)
 * 2. Learn each technique with 5-10 problems
 * 3. Understand when to use char[] vs StringBuilder vs String
 * 4. Practice pattern recognition from problem statements
 * 5. Combine techniques for complex problems
 * 6. Time yourself on mixed problems
 * 
 * Mastering these 7 core techniques will enable you to solve 90%+ of
 * string problems asked in TCS NQT Prime and similar coding rounds.
 */
public class StringTechniqueSelectionGuide {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("STRING PROBLEM SOLVING - TECHNIQUE SELECTION GUIDE");
        System.out.println("=".repeat(70));
        System.out.println();
        
        printTechniqueSummary();
        System.out.println();
        printExampleProblems();
    }
    
    private static void printTechniqueSummary() {
        System.out.println("7 CORE TECHNIQUES:");
        System.out.println("-".repeat(70));
        System.out.println("1. Two Pointer          → Palindrome, reverse, compare ends");
        System.out.println("2. Frequency Counting   → Anagram, character count, unique");
        System.out.println("3. Manipulation         → Case, remove, replace, extract");
        System.out.println("4. Pattern Matching     → Find, search, wildcards, prefix");
        System.out.println("5. Sorting              → Order, compare, lexicographic");
        System.out.println("6. Substring/Subsequence → Generate, LCS, palindromic");
        System.out.println("7. Validation           → Format check, balanced, properties");
    }
    
    private static void printExampleProblems() {
        System.out.println("PROBLEM → TECHNIQUE MAPPING:");
        System.out.println("-".repeat(70));
        
        String[][] examples = {
            {"Check palindrome", "Two Pointer"},
            {"Reverse string", "Two Pointer"},
            {"Check anagram", "Frequency Counting"},
            {"First non-repeating character", "Frequency Counting"},
            {"Toggle case", "Manipulation"},
            {"Remove vowels", "Manipulation"},
            {"Find pattern in text", "Pattern Matching"},
            {"Longest common prefix", "Pattern Matching"},
            {"Sort strings alphabetically", "Sorting"},
            {"Largest number from array", "Sorting"},
            {"All substrings", "Substring/Subsequence"},
            {"Longest common substring", "Substring/Subsequence"},
            {"Valid parentheses", "Validation"},
            {"Password strength", "Validation"},
            {"Group anagrams", "Frequency + Sorting"},
            {"Longest substring without repeating", "Frequency + Sliding Window"}
        };
        
        for (String[] example : examples) {
            System.out.printf("%-40s → %s%n", example[0], example[1]);
        }
    }
}
