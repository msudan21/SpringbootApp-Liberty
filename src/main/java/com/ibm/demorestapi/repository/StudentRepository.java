package com.ibm.demorestapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibm.demorestapi.model.Student;

public interface StudentRepository extends MongoRepository<Student, String> {


}
