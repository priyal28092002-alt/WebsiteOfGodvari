package com.example.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Employee{
	int id;
	String name;
	int experience;
	double salary;
	
	public Employee(int id, String name, int experience, double sallary) {
		this.id = id;
		this.name = name;
		this.experience = experience;
		this.salary = sallary;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", experience=" + experience + ", sallary=" + salary + "]";
	}
}

class ABC implements Comparator<Employee>{
	@Override
	public int compare(Employee e1, Employee e2) {
		if(e1.salary>e2.salary) {
			return 1;
		}else if(e1.salary<e2.salary) {
			return -1;
		}else {
			return 0;
		}
	}
}

class DEF implements Comparator<Employee>{
	@Override
	public int compare(Employee e1, Employee e2) {
		if(e1.experience>e2.experience) {
			return 1;
		}else if(e1.experience<e2.experience) {
			return -1;
		}else {
			return 0;
		}
	}
}
public class ComparatorEx {
	public static void main(String[] args) {
		ArrayList<Employee> employee=new ArrayList<>();
		employee.add(new Employee(1,"Alice",5, 90000));
		employee.add(new Employee(2,"Bob",3, 60000));
		employee.add(new Employee(3,"Charlie",2, 70000));
		employee.add(new Employee(4,"Delta",1, 40000));
		
		ABC ref1=new ABC();
		Collections.sort(employee, ref1);
		for(Employee e:employee) {
			System.out.println(e);
		}
		
		System.out.println("============================================================");
		
		DEF ref2=new DEF();
		Collections.sort(employee, ref2);
		for(Employee e:employee) {
			System.out.println(e);
		}
	}
}
