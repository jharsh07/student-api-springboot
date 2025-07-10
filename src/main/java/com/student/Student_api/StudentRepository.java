package com.student.Student_api;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
	boolean existsByEmail(String email);
	Optional<Student> findById(int id);
	
	@Query("SELECT s FROM Student s WHERE "+
		       "(:status IS NULL OR s.status = :status) AND "+
		       "(:minGpa IS NULL OR s.gpa >= :minGpa) AND "+
		       "(:maxGpa IS NULL OR s.gpa <= :maxGpa) AND "+
		       "(:name IS NULL OR (" +
		       "LOWER(s.firstName) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')) OR "+
		       "LOWER(s.lastName) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%'))))")
		Page<Student> filterStudents(
		        @Param("status") String status,
		        @Param("minGpa") Double minGpa,
		        @Param("maxGpa") Double maxGpa,
		        @Param("name") String name,
		        Pageable page
		);

}
