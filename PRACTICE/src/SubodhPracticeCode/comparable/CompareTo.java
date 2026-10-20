package com.example.comparable;

import java.util.ArrayList;
import java.util.Collections;

class Student implements Comparable<Student>{
	int rollNo;
	String name;
	double marks;
	
	public Student(int rollNo, String name, double marks) {
		super();
		this.rollNo = rollNo;
		this.name = name;
		this.marks = marks;
	}

	@Override
	public String toString() {
		return "Student [rollNo=" + rollNo + ", name=" + name + ", marks=" + marks + "]";
	}
	
	@Override
	public int compareTo(Student std) {
		/*if(this.marks>std.marks) {
			return 1;
		}else if (this.marks<std.marks) {
			return -1;
		}else {
			return 0;
		}*/
		
		//return this.name.compareTo(std.name); //To compare String
		
		return (int) (this.marks-std.marks); //Reduced condition
	}
}

public class CompareTo{
	public static void main(String[] args) {
		ArrayList<Student> students=new ArrayList<>();
		students.add(new Student(1, "Alice", 89.90));
		students.add(new Student(2, "Bob", 93.00));
		students.add(new Student(3, "Charlie", 87.40));
		students.add(new Student(4, "Delta", 85.60));
		students.add(new Student(5, "Gama", 95.60));
		
		Collections.sort(students);
		for(Student list: students) {
			System.out.println(list);
		}
	}
}
