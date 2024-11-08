package com.questglobal.student.dto;

import java.util.List;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing a Student.
 * This class is used to transfer student-related information between layers of the application, 
 * such as from the client to the server or from the server to the database.
 * 
 * The student includes fields for the student's ID, name, class, age, and a list of addresses.
 * 
 * @author Mahendra Hati
 * @email hatimahendra11@gmail.com
 * @version 1.0
 * @since 2024-11-08
 */
@Data
public class StudentDTO {
	private Long id;
    private String name;
    private String studentClass;
    private int age;
    private List<AddressDTO> addresses;
}
