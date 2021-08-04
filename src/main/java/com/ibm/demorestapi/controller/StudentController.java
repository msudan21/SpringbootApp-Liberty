package com.ibm.demorestapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.demorestapi.model.Student;
import com.ibm.demorestapi.service.StudentService;

@RestController
@RequestMapping(value = "/api/")
public class StudentController {

	@Autowired
	@Qualifier("v1")
	StudentService studentserviceV1;

	/*@Autowired
	@Qualifier("v2")
	StudentService studentserviceV2;*/
	
	@RequestMapping(value = "/v1/student", method = RequestMethod.POST, produces = "application/JSON")
	public ResponseEntity<?> addStudent(@RequestBody Student student) {		
		System.out.println("STUDENT :: " + student);
		Student s = studentserviceV1.save(student);
		
		ResponseEntity<Student> responseEntity = new ResponseEntity<Student>(s, HttpStatus.OK);
		return responseEntity;
	}
	
	@RequestMapping(value = "/v1/student/students", produces = "application/JSON")
	   public ResponseEntity<?> getStudents() {
		List<Student> s = (List<Student>) studentserviceV1.findAll();
		return new ResponseEntity<>(s, HttpStatus.OK);
	   }


	@RequestMapping(value = "/v1/students", method = RequestMethod.GET, produces = "application/JSON")
	public ResponseEntity<?> studentV1() {
		Student s = new Student();
		s.setId("1000");
		s.setName("John");
		s.setRegistration(50001);
		
		return new ResponseEntity<>(s, HttpStatus.OK);
		
		// return studentserviceV1.findAll();
	}

	/*
	@RequestMapping(value = "/v2/student", method = RequestMethod.GET)
	public Iterable<Student> studentV2() {
		return studentserviceV2.findAll();
	}*/
	
}
