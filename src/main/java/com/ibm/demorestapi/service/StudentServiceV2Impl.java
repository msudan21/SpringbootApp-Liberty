package com.ibm.demorestapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.demorestapi.model.Student;
import com.ibm.demorestapi.repository.StudentRepository;

@Service("v2")
public class StudentServiceV2Impl implements StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	@Override
	public Student save(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public Optional<Student> findByID(String id) {
		return studentRepository.findById(id);
	}

	@Override
	public void removeStudent(String id) {
    	Student st = new Student();
    	st.setId(id);
    	studentRepository.delete(st);		
	}
}
