STRING TECHNIQUES - TCS NQT PRIME CODING ROUND
==============================================

Created comprehensive string problem-solving programs in:
com.example.String package (Java_Programming project)

FILES CREATED:
--------------

1. StringTwoPointerTechnique.java
   - Check palindrome (simple and with ignore special chars)
   - Reverse string/words
   - Valid palindrome after removing one character
   - Remove adjacent duplicates
   - String rotation check
   - String compression
   - Core concept for palindrome and reversal problems
   - Time: O(n), Space: O(1) or O(n)

2. StringFrequencyTechnique.java
   - Check if anagrams
   - Character frequency counting
   - First non-repeating character
   - Group anagrams
   - Find all anagrams in text
   - Longest substring without repeating characters
   - Longest substring with K distinct characters
   - Pangram check
   - Core concept for counting and anagram problems
   - Time: O(n), Space: O(1) for fixed charset

3. StringManipulationTechnique.java
   - Case conversions (upper/lower/toggle)
   - Capitalize words
   - Remove vowels/consonants/spaces/digits
   - Keep only letters/digits
   - Sum of digits in string
   - Remove duplicates
   - Reverse vowels only
   - Trim operations
   - Core concept for transformation problems
   - Time: O(n), Space: O(n)

4. StringPatternMatchingTechnique.java
   - Find pattern in text (naive approach)
   - Count occurrences (overlapping/non-overlapping)
   - Longest common prefix
   - Wildcard matching (* and ?)
   - Check subsequence
   - Repeated pattern detection
   - Longest common substring
   - Longest palindromic substring
   - Core concept for search and pattern problems
   - Time: O(n*m) or O(n²)

5. StringSortingTechnique.java
   - Sort lexicographically
   - Sort by length
   - Sort by frequency
   - Find smallest/largest
   - Sort characters in string
   - Largest number from array
   - Version comparison
   - Custom sorting (vowels first, etc.)
   - Core concept for ordering problems
   - Time: O(n log n)

6. StringSubstringSubsequence.java
   - Generate all substrings (n² total)
   - Generate all subsequences (2^n total)
   - Count distinct substrings
   - Count palindromic substrings
   - Longest common substring (DP)
   - Longest common subsequence (DP)
   - Longest palindromic substring
   - Longest palindromic subsequence
   - Core concept for substring/subsequence problems
   - Time: O(n²) for substrings, O(2^n) for subsequences

7. StringValidationTechnique.java
   - Password strength validation
   - Balanced brackets/parentheses
   - Pangram check
   - Valid number validation
   - Character type checking (upper, lower, digit, special)
   - Email/IP validation
   - Naming convention validation (camelCase, snake_case)
   - Character type counting
   - Core concept for validation and property checking
   - Time: O(n), Space: O(1) or O(n)

8. StringTechniqueSelectionGuide.java
   - Decision tree for selecting right technique
   - Pattern recognition guide
   - Problem-to-technique mapping
   - Quick reference for interviews
   - Key differences explained
   - Common problem categorization

KEY FEATURES:
-------------
✓ Each program focuses on ONE core concept
✓ Explanations embedded in code (no separate docs)
✓ Multiple variations of each technique
✓ Real problem examples with solutions
✓ Time and space complexity noted
✓ Runnable main methods with test cases
✓ Industry-standard code patterns
✓ Ready for TCS NQT Prime level problems

IMPORTANT STRING CONCEPTS:
--------------------------
1. Immutability: String objects cannot be modified
   → Use StringBuilder for multiple operations
   
2. Character operations:
   - 'A' to 'Z': ASCII 65-90
   - 'a' to 'z': ASCII 97-122
   - Difference between upper and lower: 32
   
3. Substring vs Subsequence:
   - Substring: Contiguous ("ab" in "abc")
   - Subsequence: Can skip, maintain order ("ac" in "abc")
   
4. Common patterns:
   - Two Pointer: Palindrome, reverse
   - Frequency: Anagram, counting
   - Sliding Window: Pattern matching, longest substring
   - DP: LCS, palindromic substring

TECHNIQUE SELECTION QUICK GUIDE:
---------------------------------
Palindrome problem           → Two Pointer
Anagram problem              → Frequency Counting
Case conversion              → Manipulation
Find pattern                 → Pattern Matching
Sort strings                 → Sorting
All substrings               → Substring/Subsequence
Balanced brackets            → Validation
First non-repeating          → Frequency Counting
Reverse string               → Two Pointer
Remove characters            → Manipulation
Common prefix                → Pattern Matching
Longest common substring     → DP (Substring/Subsequence)

LEARNING APPROACH:
------------------
1. Start with StringTechniqueSelectionGuide.java
2. Study one technique file at a time
3. Run the program to see outputs
4. Understand the core concept
5. Practice variations of that technique
6. Move to next technique
7. Practice mixed problems for pattern recognition

TIME COMPLEXITY REFERENCE:
--------------------------
O(n)     - Single pass, Two Pointer, Frequency, Validation
O(n²)    - Substring generation, Naive pattern matching
O(2^n)   - Subsequence generation
O(n log n) - Sorting strings
O(n*m)   - Pattern matching, DP problems
O(1) space - Two Pointer, in-place operations
O(n) space - Frequency maps, StringBuilder

COMMON TCS NQT PROBLEMS:
------------------------
✓ Check palindrome
✓ Reverse string
✓ Check anagram
✓ First non-repeating character
✓ Remove duplicates
✓ Count vowels/consonants
✓ Toggle case
✓ Longest substring without repeating
✓ Valid parentheses
✓ Password validation
✓ Group anagrams
✓ Find pattern occurrences
✓ Longest common prefix
✓ Sort strings

Each program teaches you a TRANSFERABLE SKILL that applies to
multiple problem variations, not just one specific problem.

Master these 7 techniques → Solve 90%+ string problems in interviews.

===============================================================================
ALSO AVAILABLE: ARRAY TECHNIQUES
===============================================================================

Check com.example.arrays package for 9 comprehensive array techniques:
1. SlidingWindowTechnique.java - Consecutive elements, subarrays
2. TwoPointerTechnique.java - Pairs, partition, sorted arrays
3. PrefixSumTechnique.java - Range queries, Kadane's
4. ArrayRotationTechnique.java - Rotation, circular patterns
5. FrequencyCountingTechnique.java - Duplicates, counting
6. SortingBasedTechnique.java - Intervals, meetings
7. InPlaceManipulation.java - O(1) space solutions
8. StackBasedTechnique.java - NGE, histogram problems
9. MathematicalTechnique.java - XOR, formulas, bits

See: com/example/MASTER_GUIDE.txt for complete preparation roadmap
