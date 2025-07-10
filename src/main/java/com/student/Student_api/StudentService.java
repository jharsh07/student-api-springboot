package com.student.Student_api;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	
	@Autowired
	StudentRepository repo;
	
	public ResponseEntity<String> SaveStudent(Student data){
		if(repo.existsByEmail(data.getEmail())) {
			return new ResponseEntity<>("Email Already Exists",HttpStatus.BAD_REQUEST);
}
		repo.save(data);
		return new ResponseEntity<>("Student Created Successfully",HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> findById(int id) {

	    Optional<Student> Student=repo.findById(id);

	    if (Student.isPresent()) {
	        Student student=Student.get();
	        return new ResponseEntity<>(student, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
	    }
	}
	
	public ResponseEntity<?> findStudent(int id){
		Optional<Student> student=repo.findById(id);
		if(student.isPresent()) {
			Student stud=student.get();
			stud.setStatus("INACTIVE");
			repo.save(stud);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<>("Student not found",HttpStatus.BAD_REQUEST);
		}
	}
	
	public Page<Student> getAllStudent(Pageable pageable){
		return repo.findAll(pageable);
		}
	public ResponseEntity<?> updateStudent(int id,Student student){
		Optional<Student> existStudent=repo.findById(id);
		if(existStudent.isEmpty()) {
			return new ResponseEntity<>("Student not found",HttpStatus.BAD_REQUEST);
		}
		Student oldstudent=existStudent.get();
		if(!oldstudent.getEmail().equals(student.getEmail()) && repo.existsByEmail(student.getEmail())) {
			return new ResponseEntity<>("Email Already in Use",HttpStatus.BAD_REQUEST);
		}
		oldstudent.setFirstName(student.getFirstName());
		oldstudent.setLastName(student.getLastName());
		oldstudent.setEmail(student.getEmail());
		oldstudent.setEnrollmentDate(student.getEnrollmentDate());
		oldstudent.setGpa(student.getGpa());
		oldstudent.setStatus(student.getStatus());
		oldstudent.setDateOfBirth(student.getDateOfBirth());
		repo.save(oldstudent);
		return new ResponseEntity<>("Student Details Updated Successfully",HttpStatus.ACCEPTED);
	}
	
	public Page<Student> filterStudents(String status,Double minGpa,Double maxGpa,String name,Pageable pageable){
			return repo.filterStudents(status, minGpa, maxGpa, name,pageable);
	}
	
}
