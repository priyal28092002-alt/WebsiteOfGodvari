// Program demonstrating Complex String Methods in Java

package com.example.String;

public class ComplexStringMethods {
	public static void main(String[] args) {
		System.out.println("========== COMPLEX STRING METHODS EXAMPLES ==========\n");
		
		// 1. String.format() - Formatting strings with placeholders
		System.out.println("1. String.format() Examples:");
		String name = "John";
		int age = 25;
		double salary = 45000.50;
		String formatted = String.format("Name: %s, Age: %d, Salary: %.2f", name, age, salary);
		System.out.println(formatted);
		
		// Formatting with width and alignment
		String leftAlign = String.format("|%-15s|", "Left");
		String rightAlign = String.format("|%15s|", "Right");
		System.out.println(leftAlign);
		System.out.println(rightAlign);
		System.out.println();
		
		// 2. String.join() - Joining strings with delimiter
		System.out.println("2. String.join() Examples:");
		String joined = String.join(", ", "Apple", "Banana", "Cherry", "Date");
		System.out.println("Joined String: " + joined);
		
		String[] words = {"Java", "Python", "C++", "JavaScript"};
		String joinedArray = String.join(" | ", words);
		System.out.println("Joined Array: " + joinedArray);
		System.out.println();
		
		// 3. String.split() with regex patterns
		System.out.println("3. String.split() with Regex:");
		String text = "apple,banana;cherry:date|grape";
		String[] fruits = text.split("[,;:|]");
		System.out.println("Split by multiple delimiters:");
		for (String fruit : fruits) {
			System.out.println("  - " + fruit);
		}
		
		// Split with limit
		String sentence = "one-two-three-four-five";
		String[] limited = sentence.split("-", 3);
		System.out.println("Split with limit 3:");
		for (String part : limited) {
			System.out.println("  - " + part);
		}
		System.out.println();
		
		// 4. String.replaceAll() and replaceFirst() with regex
		System.out.println("4. replaceAll() and replaceFirst() with Regex:");
		String phone = "My phone is 123-456-7890 and 987-654-3210";
		String masked = phone.replaceAll("\\d", "*");
		System.out.println("Masked: " + masked);
		
		String firstMasked = phone.replaceFirst("\\d{3}-\\d{3}-\\d{4}", "XXX-XXX-XXXX");
		System.out.println("First Masked: " + firstMasked);
		
		// Remove all whitespaces
		String withSpaces = "Hello   World   Java";
		String noSpaces = withSpaces.replaceAll("\\s+", " ");
		System.out.println("Normalized spaces: " + noSpaces);
		System.out.println();
		
		// 5. String.matches() - Pattern matching
		System.out.println("5. String.matches() Examples:");
		String email1 = "user@example.com";
		String email2 = "invalid.email";
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		System.out.println(email1 + " is valid: " + email1.matches(emailRegex));
		System.out.println(email2 + " is valid: " + email2.matches(emailRegex));
		
		// Phone number validation
		String phone1 = "123-456-7890";
		String phone2 = "12345";
		String phoneRegex = "\\d{3}-\\d{3}-\\d{4}";
		System.out.println(phone1 + " is valid: " + phone1.matches(phoneRegex));
		System.out.println(phone2 + " is valid: " + phone2.matches(phoneRegex));
		System.out.println();
		
		// 6. String.regionMatches() - Compare portions of strings
		System.out.println("6. String.regionMatches() Examples:");
		String str1 = "Hello World";
		String str2 = "hello universe";
		// Case-sensitive comparison
		boolean match1 = str1.regionMatches(0, str2, 0, 5);
		System.out.println("Case-sensitive match: " + match1);
		
		// Case-insensitive comparison
		boolean match2 = str1.regionMatches(true, 0, str2, 0, 5);
		System.out.println("Case-insensitive match: " + match2);
		System.out.println();
		
		// 7. String.intern() - String pool management
		System.out.println("7. String.intern() Examples:");
		String s1 = new String("Java");
		String s2 = "Java";
		String s3 = s1.intern();
		System.out.println("s1 == s2: " + (s1 == s2));
		System.out.println("s3 == s2: " + (s3 == s2));
		System.out.println("s1.equals(s2): " + s1.equals(s2));
		System.out.println();
		
		// 8. String.codePointAt() and codePointBefore()
		System.out.println("8. CodePoint Methods:");
		String emoji = "Hello 🌍 World";
		int codePoint = emoji.codePointAt(6);
		System.out.println("CodePoint at index 6: " + codePoint);
		System.out.println("Character: " + Character.toChars(codePoint)[0]);
		System.out.println();
		
		// 9. String.getBytes() - Character encoding
		System.out.println("9. String.getBytes() Examples:");
		String original = "Hello";
		byte[] bytes = original.getBytes();
		System.out.print("Byte array: ");
		for (byte b : bytes) {
			System.out.print(b + " ");
		}
		System.out.println();
		String fromBytes = new String(bytes);
		System.out.println("String from bytes: " + fromBytes);
		System.out.println();
		
		// 10. String.contentEquals() - Compare with CharSequence
		System.out.println("10. String.contentEquals():");
		String str = "Hello";
		StringBuilder sb = new StringBuilder("Hello");
		StringBuffer sbuf = new StringBuffer("Hello");
		System.out.println("Equals StringBuilder: " + str.contentEquals(sb));
		System.out.println("Equals StringBuffer: " + str.contentEquals(sbuf));
		System.out.println();
		
		// 11. String.repeat() - Repeat string (Java 11+)
		System.out.println("11. String.repeat() Example:");
		String dash = "-";
		String repeated = dash.repeat(50);
		System.out.println(repeated);
		String pattern = "AB".repeat(5);
		System.out.println("Pattern: " + pattern);
		System.out.println();
		
		// 12. String.isBlank() vs isEmpty() (Java 11+)
		System.out.println("12. isBlank() vs isEmpty():");
		String empty = "";
		String blank = "   ";
		String notBlank = "  text  ";
		System.out.println("empty.isEmpty(): " + empty.isEmpty());
		System.out.println("empty.isBlank(): " + empty.isBlank());
		System.out.println("blank.isEmpty(): " + blank.isEmpty());
		System.out.println("blank.isBlank(): " + blank.isBlank());
		System.out.println("notBlank.isBlank(): " + notBlank.isBlank());
		System.out.println();
		
		// 13. String.strip(), stripLeading(), stripTrailing() (Java 11+)
		System.out.println("13. Strip Methods:");
		String padded = "   Hello World   ";
		System.out.println("Original: '" + padded + "'");
		System.out.println("strip(): '" + padded.strip() + "'");
		System.out.println("stripLeading(): '" + padded.stripLeading() + "'");
		System.out.println("stripTrailing(): '" + padded.stripTrailing() + "'");
		System.out.println("trim(): '" + padded.trim() + "'");
		System.out.println();
		
		// 14. String.lines() - Stream of lines (Java 11+)
		System.out.println("14. String.lines() Example:");
		String multiline = "Line 1\nLine 2\nLine 3\nLine 4";
		System.out.println("Lines in the string:");
		multiline.lines().forEach(line -> System.out.println("  > " + line));
		System.out.println();
		
		// 15. String.indent() and transform() (Java 12+)
		System.out.println("15. indent() and transform():");
		String code = "public class Test {\n  public static void main(String[] args) {\n    System.out.println(\"Hello\");\n  }\n}";
		System.out.println("Original:");
		System.out.println(code);
		System.out.println("\nIndented by 2:");
		System.out.println(code.indent(2));
		
		// transform() - Apply a function to string
		String transformed = "hello".transform(String::toUpperCase);
		System.out.println("Transformed: " + transformed);
		
		// Chain transformations
		String result = "  HELLO WORLD  "
				.transform(String::strip)
				.transform(String::toLowerCase)
				.transform(s -> s.replace(' ', '_'));
		System.out.println("Chained transformations: " + result);
		System.out.println();
		
		// 16. Complex string manipulation
		System.out.println("16. Complex String Manipulation:");
		demonstrateComplexManipulation();
		
		System.out.println("\n========== END OF EXAMPLES ==========");
	}
	
	// Helper method for complex string manipulation
	private static void demonstrateComplexManipulation() {
		// Extract domain from email
		String email = "user.name@example.com";
		String domain = email.substring(email.indexOf('@') + 1);
		System.out.println("Email: " + email);
		System.out.println("Domain: " + domain);
		
		// Parse CSV with quoted values
		String csv = "\"John Doe\",25,\"New York, NY\"";
		System.out.println("\nParsing CSV: " + csv);
		// Simple CSV parser (not handling all edge cases)
		String[] values = csv.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
		for (int i = 0; i < values.length; i++) {
			String value = values[i].replaceAll("^\"|\"$", "");
			System.out.println("  Value " + (i + 1) + ": " + value);
		}
		
		// Extract numbers from string
		String mixedText = "I have 10 apples, 5 oranges, and 3 bananas.";
		System.out.println("\nOriginal: " + mixedText);
		String numbersOnly = mixedText.replaceAll("[^0-9]", " ").trim().replaceAll("\\s+", ", ");
		System.out.println("Numbers extracted: " + numbersOnly);
		
		// Camel case to snake case
		String camelCase = "myVariableNameInCamelCase";
		String snakeCase = camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
		System.out.println("\nCamel case: " + camelCase);
		System.out.println("Snake case: " + snakeCase);
		
		// Title case conversion
		String sentence = "this is a test sentence";
		String[] wordsArray = sentence.split(" ");
		StringBuilder titleCase = new StringBuilder();
		for (String word : wordsArray) {
			if (word.length() > 0) {
				titleCase.append(Character.toUpperCase(word.charAt(0)))
						 .append(word.substring(1))
						 .append(" ");
			}
		}
		System.out.println("\nOriginal: " + sentence);
		System.out.println("Title case: " + titleCase.toString().trim());
	}
}
