package com.student.Student_api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api")
public class StudentController {
	
	@Autowired
	StudentService service;
	
	@PostMapping("/students")
	public ResponseEntity<String> PostStudent(@RequestBody @Valid Student data,BindingResult result){
		if(result.hasErrors()){
			 return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
		return service.SaveStudent(data);
	}
	
	
	@GetMapping("/students/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable("id") int id){
		Student student= service.getStudent(id);
		return ResponseEntity.ok(student);
	}
	
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> setStatusInactive(@PathVariable("id") int id){
		service.findStudent(id);
	    return ResponseEntity.accepted().build();
	}
	
	
	@PutMapping("/students/{id}")
	public ResponseEntity<?> updateStudent(@PathVariable("id") int id,@RequestBody Student student){
		Student stud= service.updateStudent(id, student);
		return ResponseEntity.ok(stud);
	}
	
	
	@GetMapping("/students")
	public ResponseEntity<Map<String, Object>> filterStudents(
			@RequestParam(name="status",required=false) String status,
	        @RequestParam(name="minGpa",required=false) Double minGpa,
	        @RequestParam(name="maxGpa",required=false) Double maxGpa,
	        @RequestParam(name="name",required=false) String name,
	        @PageableDefault(size = 10) Pageable pageable) {
		
		Page<Student> pageStudents;
		 if (status!=null||minGpa!=null||maxGpa!= null||name!=null) {
		        pageStudents= service.filterStudents(status, minGpa, maxGpa, name, pageable);
		    } else {
		        pageStudents=service.getAllStudent(pageable);
		    }
		
		 Map<String, Object> response = new HashMap<>();
		    response.put("students",pageStudents.getContent());
		    response.put("currentPage",pageStudents.getNumber());
		    response.put("totalItems",pageStudents.getTotalElements());
		    response.put("totalPages",pageStudents.getTotalPages());
		    response.put("pageSize",pageStudents.getSize());
		    response.put("sort",pageStudents.getSort());

		    return ResponseEntity.ok(response);
	}
	
	
	
		
	
	
}