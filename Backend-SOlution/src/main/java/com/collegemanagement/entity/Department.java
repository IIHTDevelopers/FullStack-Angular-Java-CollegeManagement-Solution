package com.collegemanagement.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "departments")
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@JsonManagedReference
	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
	private List<Student> students;

	@JsonManagedReference
	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
	private List<Teacher> teachers;

	public Department() {
		super();
	}

	public Department(Long id, String name, List<Student> students, List<Teacher> teachers) {
		super();
		this.id = id;
		this.name = name;
		this.students = students;
		this.teachers = teachers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}
}
