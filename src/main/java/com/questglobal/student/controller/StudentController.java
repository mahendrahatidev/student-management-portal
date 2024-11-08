package com.questglobal.student.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.questglobal.student.dto.StudentDTO;
import com.questglobal.student.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

	/**
	 * Register a new student.
	 * <p>
	 * POST /student/register
	 * </p>
	 *
	 * @param studentDTO the student information to be registered
	 * @return ResponseEntity containing the saved StudentDTO object
	 */
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerStudent(@RequestBody @Valid StudentDTO studentDTO) {
		logger.info("Registering student with name: {}", studentDTO.getName());
		return studentService.registerStudent(studentDTO);
	}

	/**
	 * Get student by ID.
	 * <p>
	 * GET /student/{id}
	 * </p>
	 *
	 * @param id the ID of the student
	 * @return ResponseEntity containing the StudentDTO of the student with the
	 *         specified ID
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStudentById(@PathVariable Long id) {
		logger.info("Fetching student with ID: {}", id);
		return studentService.getStudentById(id);
	}

	/**
	 * Get students filtered by class with pagination.
	 * <p>
	 * GET /student/filter/{studentClass}
	 * </p>
	 *
	 * @param studentClass the class by which students should be filtered
	 * @param page         the page number for pagination (1-based index)
	 * @param size         the number of students per page
	 * @return ResponseEntity containing a list of students in the specified class
	 */
	@GetMapping(value = "/filter/{studentClass}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStudentsByClass(@PathVariable String studentClass, @RequestParam int page,
			@RequestParam int size) {
		logger.info("Fetching students for class: {} with pagination - page: {}, size: {}", studentClass, page, size);
		return studentService.getStudentsByClassWithPagination(studentClass, PageRequest.of(page - 1, size));
	}

	/**
	 * Delete student by ID.
	 * <p>
	 * DELETE /student/{id}
	 * </p>
	 *
	 * @param id the ID of the student to be deleted
	 * @return ResponseEntity indicating the success of the operation
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
		logger.info("Deleting student with ID: {}", id);
		return studentService.deleteStudent(id);
	}

	/**
	 * Get all students.
	 * <p>
	 * GET /api/students
	 * </p>
	 *
	 * @return ResponseEntity containing a list of all students
	 */
	@GetMapping(value = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllStudents() {
		logger.info("Fetching all students");
		return studentService.getAllStudents();
	}

	/**
	 * Update an existing student's information.
	 * <p>
	 * PUT /student/{id}
	 * </p>
	 *
	 * @param id         the ID of the student to be updated
	 * @param studentDTO the new student information to update with
	 * @return ResponseEntity containing the updated StudentDTO object
	 */
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
		logger.info("Updating student with ID: {}", id);
		return studentService.updateStudent(id, studentDTO);
	}
}
