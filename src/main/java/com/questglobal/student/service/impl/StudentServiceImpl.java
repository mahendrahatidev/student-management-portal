package com.questglobal.student.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.questglobal.student.dto.AddressDTO;
import com.questglobal.student.dto.StudentDTO;
import com.questglobal.student.exception.StudentNotFoundException;
import com.questglobal.student.model.Address;
import com.questglobal.student.model.Student;
import com.questglobal.student.repository.StudentRepository;
import com.questglobal.student.service.StudentService;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of StudentService interface providing methods to handle
 * operations on Student entities, such as creating, reading, updating, and
 * deleting students with associated addresses.
 *
 * @author Mahendra Hati
 * @email hatimahendra11@gmail.com
 * @version 1.0
 * @since 2024-11-08
 */

@Service
@Transactional
@Slf4j
public class StudentServiceImpl extends BaseService implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	/**
	 * Registers a new student along with their addresses.
	 * 
	 * @param studentDTO DTO containing student details and a list of addresses.
	 * @return DTO of the saved student.
	 */

	@Override
	public ResponseEntity<?> registerStudent(StudentDTO studentDTO) {
		
		
		try {
			Student student = new Student();
			student.setName(studentDTO.getName());
			student.setStudentClass(studentDTO.getStudentClass());
			student.setAge(studentDTO.getAge());

			// Convert AddressDTO list to Address entities and set the student reference
			List<Address> addresses = new ArrayList<>();
			for (AddressDTO addressDTO : studentDTO.getAddresses()) {
				Address address = new Address();
				address.setFlatNo(addressDTO.getFlatNo());
				address.setCity(addressDTO.getCity());
				address.setState(addressDTO.getState());
				address.setStudent(student); // Set the student reference in address
				addresses.add(address);
			}

			student.setAddresses(addresses);
			Student savedStudent = studentRepository.save(student);
			logger.info("Student registered with name: {}", studentDTO.getName());
			
			return createSuccessResponse(savedStudent);
		} catch (Exception e) {
			logger.error("Exception while Student registration: {}", studentDTO);
			 return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error registering student", "ERR_STUDENT_REGISTER");
		}
		
	}

	/**
	 * Retrieves a student by ID, throws StudentNotFoundException if not found.
	 * 
	 * @param id ID of the student to retrieve.
	 * @return DTO of the retrieved student.
	 */
	public ResponseEntity<?>  getStudentById(Long id) {
		logger.info("Fetching student with ID: {}", id);
		StudentDTO studentDto = null;

		try {
			Optional<Student> student = studentRepository.findById(id);
			studentDto = student.map(this::convertToStudentDto)
					.orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));
			
			return createSuccessResponse(studentDto);
		} catch (StudentNotFoundException e) {
			return createErrorResponse(HttpStatus.NOT_FOUND, "Student not found", "STD_NOT_FOUND");
		}catch (Exception e) {
			logger.error("Exception while fetching Student by Id:" + id + " :" + e.getMessage());
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error While Fetching Student", "INTERNAL_SERVER_ERROR");
		}

	}

	/**
	 * Converts a Student entity to StudentDTO.
	 * 
	 * @param student Student entity to convert.
	 * @return Converted StudentDTO.
	 */
	private StudentDTO convertToStudentDto(Student student) {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setId(student.getId());
		studentDTO.setName(student.getName());
		studentDTO.setStudentClass(student.getStudentClass());
		studentDTO.setAge(student.getAge());
		studentDTO.setAddresses(student.getAddresses().stream().map(this::convertToAddressDto).collect(Collectors.toList()));
		return studentDTO;
	}

	/**
	 * Converts an Address entity to AddressDTO.
	 * 
	 * @param address Address entity to convert.
	 * @return Converted AddressDTO.
	 */
	private AddressDTO convertToAddressDto(Address address) {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setFlatNo(address.getFlatNo());
		addressDTO.setCity(address.getCity());
		addressDTO.setState(address.getState());
		return addressDTO;
	}

	/**
	 * Retrieves a list of students filtered by class with pagination.
	 * 
	 * @param studentClass Class of students to retrieve.
	 * @param pageable     Pagination information.
	 * @return List of filtered StudentDTOs.
	 */
	@Override
	public ResponseEntity<?> getStudentsByClassWithPagination(String studentClass, Pageable pageable) {
		logger.info("Retrieving students of class: {}", studentClass);
		try {
			List<StudentDTO> studentList = studentRepository.findByStudentClass(studentClass, pageable).getContent().stream()
					.map(this::convertToStudentDto).collect(Collectors.toList());
			return createSuccessResponse(studentList);
		} catch (Exception e) {
			logger.error("Error retrieving students by class {}: {}", studentClass, e.getMessage());
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error while Retrieving students of class", "INTERNAL_SERVER_ERROR");
		}
	}

	/**
	 * Deletes a student by ID.
	 * 
	 * @param id ID of the student to delete.
	 */
	@Override
	public ResponseEntity<?> deleteStudent(Long id) {
		logger.info("Deleting student with ID: {}", id);
		try {
			if (!studentRepository.existsById(id)) {
				throw new StudentNotFoundException("Student not found with ID: " + id);
			}
			studentRepository.deleteById(id);
			logger.info("Student deleted with ID: {}", id);
			return createSuccessResponse("Student deleted with ID: {}"+id);
		} catch (StudentNotFoundException e) {
			return createErrorResponse(HttpStatus.NOT_FOUND, "Student not found", "STD_NOT_FOUND");
		}catch (Exception e) {
			logger.error("Error deleting student with ID {}: {}", id, e.getMessage());
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
	}

	/**
	 * Retrieves all students.
	 * 
	 * @return List of all StudentDTOs.
	 */
	@Override
	public ResponseEntity<?> getAllStudents() {
		logger.info("Retrieving all students.");
		try {
			List<StudentDTO> studentList = studentRepository.findAll().stream().map(this::convertToStudentDto).collect(Collectors.toList());
			
			return createSuccessResponse(studentList);
		} catch (Exception e) {
			logger.error("Error retrieving all students: {}", e.getMessage());
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
	}

	/**
	 * Updates an existing student.
	 * 
	 * @param id         ID of the student to update.
	 * @param studentDTO DTO containing updated student information.
	 * @return DTO of the updated student.
	 */
	@Override
	@Transactional
	public ResponseEntity<?> updateStudent(Long id, StudentDTO studentDTO) {
		
		
		try {
			logger.info("Updating student with ID: {}", id);
			StudentDTO updatedStudentDto = studentRepository.findById(id).map(existingStudent -> {
				existingStudent.setName(studentDTO.getName());
				existingStudent.setAge(studentDTO.getAge());
				existingStudent.setStudentClass(studentDTO.getStudentClass());
				existingStudent.setAddresses(studentDTO.getAddresses().stream().map(this::convertToAddressEntity) 
						.collect(Collectors.toList()));
				Student updatedStudent = studentRepository.save(existingStudent);
				logger.info("Student updated with ID: {}", id);
				return convertToStudentDto(updatedStudent);
			}).orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));
		return createSuccessResponse(updatedStudentDto);
		} catch (StudentNotFoundException e) {
			return createErrorResponse(HttpStatus.NOT_FOUND, "Student not found", "STD_NOT_FOUND");
		}catch (Exception e) {
			logger.error("Error deleting student with ID {}: {}", id, e.getMessage());
			return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error while updating student", "INTERNAL_SERVER_ERROR");
		}
		
	}

	/**
	 * Converts AddressDTO to Address entity.
	 * 
	 * @param addressDTO AddressDTO to convert.
	 * @return Converted Address entity.
	 */
	private Address convertToAddressEntity(AddressDTO addressDTO) {
		Address address = new Address();
		address.setFlatNo(addressDTO.getFlatNo());
		address.setCity(addressDTO.getCity());
		address.setState(addressDTO.getState());
		return address;
	}
}
