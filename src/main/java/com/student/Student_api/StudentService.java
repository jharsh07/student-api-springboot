package com.student.Student_api;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
	
	@Cacheable(value="students",key ="#p0")
	public Student getStudent(int id) {
	    return repo.findById(id)
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
	}
	
	@CacheEvict(value="students",key="#p0")
	public void findStudent(int id) {
	    Optional<Student> student = repo.findById(id);
	    if (student.isPresent()) {
	        Student stud=student.get();
	        stud.setStatus("INACTIVE");
	        repo.save(stud);
	    } else{
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student not found");
	    }
	}
	
	
	@Cacheable("students")
	public Page<Student> getAllStudent(Pageable pageable){
		return repo.findAll(pageable);
		}
	
	@CachePut(value="students",key="#p0")
	public Student updateStudent(int id,Student student){
		Student existStudent=repo.findById(id).
				orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student not found"));
		
		
		if (!existStudent.getEmail().equals(student.getEmail()) &&repo.existsByEmail(student.getEmail())) {
		        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Already in Use");
		    }
		existStudent.setFirstName(student.getFirstName());
		existStudent.setLastName(student.getLastName());
		existStudent.setEmail(student.getEmail());
		existStudent.setEnrollmentDate(student.getEnrollmentDate());
		existStudent.setGpa(student.getGpa());
		existStudent.setStatus(student.getStatus());
		existStudent.setDateOfBirth(student.getDateOfBirth());
		return repo.save(existStudent);
		
	}
	
	@Cacheable("students")
	public Page<Student> filterStudents(String status,Double minGpa,Double maxGpa,String name,Pageable pageable){
			return repo.filterStudents(status, minGpa, maxGpa, name,pageable);
	}
	
}