/*Program to display details of students in ascending order based on the marks of students
where if the students has same marks then it should be displayed in descending order based on their age.
(Students should have properties such as rollNo, name, marks, age)*/

package com.example.comparable;

import java.util.ArrayList;
import java.util.Collections;

class StudentMarks implements Comparable<StudentMarks>{
	int rollNo;
	String name;
	int age;
	int marks;
	
	public StudentMarks(int rollNo, String name, int age, int marks) {
		this.rollNo = rollNo;
		this.name = name;
		this.age = age;
		this.marks = marks;
	}
	
	@Override
	public String toString() {
		return "StudentMarks [rollNo=" + rollNo + ", name=" + name + ", age=" + age + ", marks=" + marks + "]";
	}

	@Override
	public int compareTo(StudentMarks std) {
		if (this.marks > std.marks) {
			return 1;
		}else if(this.marks < std.marks) {
			return -1;
		}else {
			if(this.age < std.age) {
				return 1;
			}else if(this.age > std.age) {
				return -1;
			}else {
				return 0;
			}
		}
	}
}

public class StudentDetails {
	public static void main(String[] args) {
		ArrayList<StudentMarks> student = new ArrayList<>();
		student.add(new StudentMarks(1, "Alice", 22, 20));
		student.add(new StudentMarks(2, "Bob", 23, 19));
		student.add(new StudentMarks(3, "Charlie", 21, 18));
		student.add(new StudentMarks(4, "Delta", 20, 19));
		student.add(new StudentMarks(5, "John", 22, 16));
		
		Collections.sort(student);
		for(StudentMarks std: student) {
			System.out.println(std);
		}
	}
}