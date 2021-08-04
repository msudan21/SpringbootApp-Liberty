package com.ibm.demorestapi.service;

import java.util.List;
import java.util.Optional;

import com.ibm.demorestapi.model.Student;

public interface StudentService {

	public List<Student> findAll();

	public Student save(Student student);

	public Optional<Student> findByID(String id);

	public void removeStudent(String id);

}
