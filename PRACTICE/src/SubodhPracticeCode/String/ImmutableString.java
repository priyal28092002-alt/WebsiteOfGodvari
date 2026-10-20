/*How to make your own class as Immutable?

1. Make the class as final
2. Make all data members as private and final
3. Create only getter method */

package com.example.String;

final class Student{
	private final int rollno;
	final private String name;
	
	public Student(int rollno , String name)
	{
		this.rollno = rollno;
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return rollno +" "+ name;
	}
	
	public int getRollno()
	{
		return rollno;
	}
	
	public String getName()
	{
		return name;
	}
}

public class ImmutableString {
	public static void main(String[] args) {
		Student s1 = new Student(1,"ABC");
		System.out.println(s1);
	}
}