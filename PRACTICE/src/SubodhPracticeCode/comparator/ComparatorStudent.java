package com.example.comparator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Student{
	Integer id;
	String name;
	Integer age;
	String gender;
	String department;
	Double gpa;
	String eMail;
	String phone;
	List<String> subjects;
	LocalDate birthDate;
	String bloodGroup;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Double getGpa() {
		return gpa;
	}
	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<String> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	
	public Student(Integer id, String name, Integer age, String gender, String department, Double gpa, String eMail,
			String phone, List<String> subjects, LocalDate birthDate, String bloodGroup) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.department = department;
		this.gpa = gpa;
		this.eMail = eMail;
		this.phone = phone;
		this.subjects = subjects;
		this.birthDate = birthDate;
		this.bloodGroup = bloodGroup;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", department="
				+ department + ", gpa=" + gpa + ", eMail=" + eMail + ", phone=" + phone + ", subjects=" + subjects
				+ ", birthDate=" + birthDate + ", bloodGroup=" + bloodGroup + "]";
	}	
}

public class ComparatorStudent {
	public static void main(String[] args) {
		List<Student> student = new ArrayList<>();

		student.add(new Student(1, "Alice Johnson", 20, "F", "Computer Science", 7.8,
                "alice@example.com", "9876543210", Arrays.asList("Math", "Java", "DBMS"),
                LocalDate.of(2004, 3, 15), "O+"));

		student.add(new Student(2, "Bob Smith", 21, "M", "Mechanical Engineering", 3.5,
                "bob@example.com", "9765432109", Arrays.asList("Physics", "Thermodynamics", "Mechanics"),
                LocalDate.of(2003, 7, 20), "A-"));

		student.add(new Student(3, "Charlie Brown", 22, "M", "Electrical Engineering", 6.7,
                "charlie@example.com", "9654321098", Arrays.asList("Circuits", "Electronics", "Control Systems"),
                LocalDate.of(2002, 5, 10), "B+"));

		student.add(new Student(4, "David Lee", 19, "M", "Information Technology", 8.9,
                "david@example.com", "9543210987", Arrays.asList("Programming", "AI", "Networking"),
                LocalDate.of(2005, 10, 25), "AB+"));

		student.add(new Student(5, "Emma Davis", 20, "F", "Civil Engineering", 9.6,
                "emma@example.com", "9432109876", Arrays.asList("Structures", "Surveying", "Geotech"),
                LocalDate.of(2004, 12, 5), "O-"));
		
		// Sorting in ascending order using Comparator
		/*Collections.sort(student, new Comparator<Student>() { 
			@Override
			public int compare(Student s1, Student s2) {
                return s1.gpa.compareTo(s2.gpa); //Ascending order
            }
		});*/
		
		// Sorting in descending order using Comparator
        Collections.sort(student, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s2.gpa.compareTo(s1.gpa); // Descending order
            }
        });
		
		//Using Lambda Expression
		//Collections.sort(student, (s1, s2) -> s1.gpa.compareTo(s2.gpa));
        
		//Using Comparator
		//student.sort(Comparator.comparing(s -> s.gpa));
		
		
		/*Object[] students = student.toArray();
		 
		for (int i = 0; i < students.length; i++) {
			System.out.println(students[i]);
			System.out.println();
		}*/
        
		for (Student list : student) {
			System.out.println(list);
		}	 
	}
}