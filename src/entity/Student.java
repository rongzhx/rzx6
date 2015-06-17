package entity;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private String student_nickname="";
	private String student_name="";
	private String student_id="";
	private String student_password="";
	private String student_phone="";
	private String student_university="";
	private String student_college="";
	private List<String> join_activities= new ArrayList<String>();
	private String student_sex="";
	
	public Student()
	{
		
	}
	public Student(Student s)
	{
		this.setJoin_activities(s.getJoin_activities());
		this.setStudent_college(s.getStudent_college());
		this.setStudent_id(s.getStudent_id());
		this.setStudent_name(s.getStudent_name());
		this.setStudent_nickname(s.getStudent_nickname());
		this.setStudent_password(s.getStudent_password());
		this.setStudent_phone(s.getStudent_phone());
		this.setStudent_sex(s.getStudent_sex());
		this.setStudent_university(s.getStudent_university());
	}
	public String getStudent_nickname() {
		return student_nickname;
	}

	public void setStudent_nickname(String student_nickname) {
		this.student_nickname = student_nickname;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public String getStudent_password() {
		return student_password;
	}

	public void setStudent_password(String student_password) {
		this.student_password = student_password;
	}

	public String getStudent_phone() {
		return student_phone;
	}

	public void setStudent_phone(String student_phone) {
		this.student_phone = student_phone;
	}

	public String getStudent_university() {
		return student_university;
	}

	public void setStudent_university(String student_university) {
		this.student_university = student_university;
	}

	public String getStudent_college() {
		return student_college;
	}

	public void setStudent_college(String student_college) {
		this.student_college = student_college;
	}


	public List<String> getJoin_activities() {
		return join_activities;
	}

	public void setJoin_activities(List<String> join_activities) {
		this.join_activities = join_activities;
	}

	public String getStudent_sex() {
		return student_sex;
	}

	public void setStudent_sex(String student_sex) {
		this.student_sex = student_sex;
	}
	
}
