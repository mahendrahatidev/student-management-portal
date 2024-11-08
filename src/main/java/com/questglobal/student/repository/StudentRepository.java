package com.questglobal.student.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.questglobal.student.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	
	
	Page<Student> findByStudentClass(String studentClass, Pageable pageable);
    List<Student> findByStudentClass(String studentClass);
}
