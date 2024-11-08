package com.questglobal.student.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.questglobal.student.dto.StudentDTO;
import com.questglobal.student.exception.StudentNotFoundException;

/**
 * Service interface for managing Student-related operations.
 * This interface provides methods for registering, retrieving, updating,
 * and deleting students, as well as filtering students based on class.
 * <p>
 * The methods throw appropriate exceptions when necessary.
 * </p>
 * 
 * @author Mahendra Hati
 * @email hatimahendra11@gmail.com
 * @version 1.0
 * @since 2024-11-08
 */
public interface StudentService {

    /**
     * Registers a new student.
     * This method accepts a StudentDTO, maps it to a Student entity,
     * and saves it in the database.
     *
     * @param studentDTO the student information to be registered
     * @return the saved StudentDTO object
     * @throws RuntimeException if registration fails
     */
	ResponseEntity<?>registerStudent(StudentDTO studentDTO);

    /**
     * Retrieves a student by their ID.
     * This method looks up the student by ID and returns their details
     * as a StudentDTO.
     *
     * @param id the ID of the student
     * @return the StudentDTO of the student with the specified ID
     * @throws StudentNotFoundException if the student with the specified ID is not found
     */
	ResponseEntity<?>  getStudentById(Long id);

    /**
     * Retrieves all students.
     * This method fetches all students in the database and returns them as a list of StudentDTOs.
     *
     * @return a list of all students as StudentDTO objects
     */
	ResponseEntity<?> getAllStudents();

    /**
     * Retrieves students filtered by class and paginated.
     * This method returns students based on the specified class, with pagination support.
     *
     * @param studentClass the class by which students should be filtered
     * @param pageable pagination information, including page number and size
     * @return a paginated list of students filtered by class
     */
    ResponseEntity<?> getStudentsByClassWithPagination(String studentClass, Pageable pageable);

    /**
     * Deletes a student by their ID.
     * This method deletes the student with the specified ID from the database.
     *
     * @param id the ID of the student to be deleted
     * @throws StudentNotFoundException if the student with the specified ID is not found
     */
    ResponseEntity<?> deleteStudent(Long id);

    /**
     * Updates an existing student's information.
     * This method updates the student with the specified ID using the provided StudentDTO.
     *
     * @param id the ID of the student to be updated
     * @param studentDTO the new student information to update with
     * @return the updated StudentDTO object
     * @throws StudentNotFoundException if the student with the specified ID is not found
     */
    ResponseEntity<?> updateStudent(Long id, StudentDTO studentDTO);
}
