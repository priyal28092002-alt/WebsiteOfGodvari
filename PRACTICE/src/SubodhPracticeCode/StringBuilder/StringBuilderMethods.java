package com.example.StringBuilder;

public class StringBuilderMethods {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder("Hello");
		System.out.println("Initial String: " + sb);

		// append()
		sb.append(" World");
		System.out.println("After append: " + sb);

		// insert()
		sb.insert(5, " Java");
		System.out.println("After insert: " + sb);

		// replace()
		sb.replace(6, 10, "Python");
		System.out.println("After replace: " + sb);

		// delete()
		sb.delete(6, 13);
		System.out.println("After delete: " + sb);

		// reverse()
		sb.reverse();
		System.out.println("After reverse: " + sb);
		
		// length()
		System.out.println("Length of the string: " + sb.length());
		
		// capacity()
		// capacity() method returns the current capacity of the StringBuilder
		System.out.println("Capacity of the string: " + sb.capacity());
		
		// charAt()
		System.out.println("Character at index 5: " + sb.charAt(5));
		
		// indexOf()
		System.out.println("Index of 'o': " + sb.indexOf("o"));
		
		// lastIndexOf()
		System.out.println("Last index of 'o': " + sb.lastIndexOf("o"));
		
		// substring()
		System.out.println("Substring from index 5: " + sb.substring(5));
		
		// substring()
		System.out.println("Substring from index 5 to 10: " + sb.substring(5, 10));
		
		// trimToSize()
		// trimToSize() method reduces the storage of the StringBuilder object to the minimum required.
		sb.trimToSize();
		System.out.println("After trimToSize: " + sb);
		
		// setLength()
		sb.setLength(5);
		System.out.println("After setLength: " + sb);
		
		// ensureCapacity()
		// ensureCapacity() method ensures that the capacity of the StringBuilder object is at least equal to the specified minimum
		sb.ensureCapacity(50);
		System.out.println("After ensureCapacity: " + sb);
		
		// codePointAt()
		// codePointAt() method returns the Unicode code point value at the specified index.
		System.out.println("Code point at index 2: " + sb.codePointAt(2));
		
		// codePointBefore()
		// codePointBefore() method returns the Unicode code point value before the specified index.
		System.out.println("Code point before index 2: " + sb.codePointBefore(2));
		
		// codePointCount()
		// codePointCount() method returns the number of Unicode code points between the specified
		System.out.println("Code point count: " + sb.codePointCount(0, 5));
		
		// offsetByCodePoints()
		// offsetByCodePoints() method returns the index within the StringBuilder object that is offset from the specified index by code points.
		System.out.println("Offset by code points: " + sb.offsetByCodePoints(0, 2));
		
		// getChars()
		// getChars() method copies the characters of the StringBuilder object into the specified character array.
		char[] ch = new char[5];
		sb.getChars(0, 5, ch, 0);
		System.out.print("Get chars: ");
		for (char c : ch) {
			System.out.print(c + " ");
		}
		System.out.println();
		
		// appendCodePoint()
		sb.appendCodePoint(65);
		System.out.println("After appendCodePoint: " + sb);
		
		// codePointCount()
		System.out.println("Code point count: " + sb.codePointCount(0, 5));
		
		// codePointBefore()
		System.out.println("Code point before index 2: " + sb.codePointBefore(2));
		
		// offsetByCodePoints()
		System.out.println("Offset by code points: " + sb.offsetByCodePoints(0, 2));
		
		// codePointAt()
		System.out.println("Code point at index 2: " + sb.codePointAt(2));
		
	}
}
