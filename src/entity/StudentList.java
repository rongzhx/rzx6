package entity;

import java.util.ArrayList;
import java.util.List;

public class StudentList {
	private List<Student> students = new ArrayList<Student>();
	public StudentList()
	{
		
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public void addStudents(Student s)
	{
		Student ns = new Student(s);
		students.add(ns);
	}
}
