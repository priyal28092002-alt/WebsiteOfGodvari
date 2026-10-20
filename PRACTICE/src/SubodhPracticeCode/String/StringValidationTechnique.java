package com.example.String;

/**
 * CORE CONCEPT: String Validation and Pattern Recognition
 * 
 * Use when: Validating format, checking properties, pattern verification
 * Time Complexity: O(n) for single pass validation
 * 
 * Pattern Recognition:
 * - Validate email, phone, password
 * - Check string properties (pangram, balanced brackets)
 * - Number validation
 * - Format checking
 * 
 * Key Techniques:
 * 1. Character classification (digit, letter, special)
 * 2. Counting specific characters
 * 3. State-based validation
 * 4. Stack for balanced structures
 * 
 * Applications:
 * - Password strength checker
 * - Balanced parentheses
 * - Valid number
 * - Pangram check
 */
public class StringValidationTechnique {
    
    public static void main(String[] args) {
        System.out.println("=== PASSWORD VALIDATION ===");
        String[] passwords = {"Weak1", "Strong@123", "NoDigit!", "noUpper123!"};
        for (String pwd : passwords) {
            System.out.println("'" + pwd + "' is strong: " + isStrongPassword(pwd));
        }
        
        System.out.println("\n\n=== BALANCED BRACKETS ===");
        String[] brackets = {"{[()]}", "{[(])}", "((()))", "(()"};
        for (String b : brackets) {
            System.out.println("'" + b + "' is balanced: " + isBalanced(b));
        }
        
        System.out.println("\n\n=== PANGRAM CHECK ===");
        String pangram = "The quick brown fox jumps over the lazy dog";
        System.out.println("'" + pangram + "'");
        System.out.println("Is pangram: " + isPangram(pangram));
        
        System.out.println("\n\n=== NUMBER VALIDATION ===");
        String[] numbers = {"123", "-123", "12.34", "1e10", "abc"};
        for (String num : numbers) {
            System.out.println("'" + num + "' is valid number: " + isValidNumber(num));
        }
        
        System.out.println("\n\n=== STRING PROPERTIES ===");
        String test = "A1b2C3";
        System.out.println("String: " + test);
        System.out.println("Has uppercase: " + hasUpperCase(test));
        System.out.println("Has lowercase: " + hasLowerCase(test));
        System.out.println("Has digit: " + hasDigit(test));
        System.out.println("Has special char: " + hasSpecialChar(test));
        System.out.println("Is alphanumeric: " + isAlphanumeric(test));
    }
    
    /**
     * Check if password is strong
     * Rules:
     * - At least 8 characters
     * - Contains uppercase letter
     * - Contains lowercase letter
     * - Contains digit
     * - Contains special character
     */
    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) return false;
        
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }
        
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
    
    /**
     * Check if brackets are balanced
     * Types: () [] {}
     * Concept: Use stack, push opening, pop and match closing
     */
    public static boolean isBalanced(String s) {
        if (s == null || s.isEmpty()) return true;
        
        java.util.Stack<Character> stack = new java.util.Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) return false;
                
                char top = stack.pop();
                if (!isMatchingPair(top, c)) return false;
            }
        }
        
        return stack.isEmpty();
    }
    
    private static boolean isMatchingPair(char opening, char closing) {
        return (opening == '(' && closing == ')') ||
               (opening == '[' && closing == ']') ||
               (opening == '{' && closing == '}');
    }
    
    /**
     * Check if string is pangram (contains all 26 letters)
     * Concept: Track seen letters using boolean array
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
    
    /**
     * Validate if string represents a valid number
     * Valid: 123, -123, 12.34, 1e10, -2.5e-3
     * Invalid: abc, 1.2.3, 1e2.5
     */
    public static boolean isValidNumber(String s) {
        if (s == null || s.isEmpty()) return false;
        
        s = s.trim();
        boolean seenDigit = false;
        boolean seenDot = false;
        boolean seenE = false;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (Character.isDigit(c)) {
                seenDigit = true;
            } else if (c == '.') {
                if (seenDot || seenE) return false;
                seenDot = true;
            } else if (c == 'e' || c == 'E') {
                if (seenE || !seenDigit) return false;
                seenE = true;
                seenDigit = false; // Need digit after e
            } else if (c == '+' || c == '-') {
                if (i != 0 && s.charAt(i - 1) != 'e' && s.charAt(i - 1) != 'E') {
                    return false;
                }
            } else {
                return false;
            }
        }
        
        return seenDigit;
    }
    
    /**
     * Check if string contains uppercase letter
     */
    public static boolean hasUpperCase(String s) {
        if (s == null) return false;
        
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) return true;
        }
        
        return false;
    }
    
    /**
     * Check if string contains lowercase letter
     */
    public static boolean hasLowerCase(String s) {
        if (s == null) return false;
        
        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) return true;
        }
        
        return false;
    }
    
    /**
     * Check if string contains digit
     */
    public static boolean hasDigit(String s) {
        if (s == null) return false;
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) return true;
        }
        
        return false;
    }
    
    /**
     * Check if string contains special character
     */
    public static boolean hasSpecialChar(String s) {
        if (s == null) return false;
        
        for (char c : s.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Check if string is alphanumeric (only letters and digits)
     */
    public static boolean isAlphanumeric(String s) {
        if (s == null || s.isEmpty()) return false;
        
        for (char c : s.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) return false;
        }
        
        return true;
    }
    
    /**
     * Check if string contains only letters
     */
    public static boolean isAlpha(String s) {
        if (s == null || s.isEmpty()) return false;
        
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) return false;
        }
        
        return true;
    }
    
    /**
     * Check if string contains only digits
     */
    public static boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) return false;
        
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        
        return true;
    }
    
    /**
     * Validate email format (basic)
     * Format: local@domain.extension
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        
        int atPos = email.indexOf('@');
        int dotPos = email.lastIndexOf('.');
        
        return atPos > 0 && 
               dotPos > atPos + 1 && 
               dotPos < email.length() - 1;
    }
    
    /**
     * Count character types in string
     * Returns: [uppercase, lowercase, digits, special]
     */
    public static int[] countCharacterTypes(String s) {
        int[] counts = new int[4]; // upper, lower, digit, special
        
        if (s == null) return counts;
        
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                counts[0]++;
            } else if (Character.isLowerCase(c)) {
                counts[1]++;
            } else if (Character.isDigit(c)) {
                counts[2]++;
            } else {
                counts[3]++;
            }
        }
        
        return counts;
    }
    
    /**
     * Check if string has balanced vowels and consonants
     */
    public static boolean hasBalancedVowelsConsonants(String s) {
        if (s == null || s.isEmpty()) return false;
        
        int vowels = 0, consonants = 0;
        String vowelSet = "aeiouAEIOU";
        
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                if (vowelSet.indexOf(c) != -1) {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }
        
        return vowels == consonants;
    }
    
    /**
     * Check if string is valid IPv4 address
     * Format: x.x.x.x where x is 0-255
     */
    public static boolean isValidIPv4(String ip) {
        if (ip == null || ip.isEmpty()) return false;
        
        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;
        
        for (String part : parts) {
            if (part.isEmpty() || part.length() > 3) return false;
            
            try {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) return false;
                
                // Check leading zeros
                if (part.length() > 1 && part.charAt(0) == '0') return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Check if string follows snake_case convention
     */
    public static boolean isSnakeCase(String s) {
        if (s == null || s.isEmpty()) return false;
        
        return s.matches("[a-z]+(_[a-z]+)*");
    }
    
    /**
     * Check if string follows camelCase convention
     */
    public static boolean isCamelCase(String s) {
        if (s == null || s.isEmpty()) return false;
        
        return s.matches("[a-z]+([A-Z][a-z]+)*");
    }
}
