package com.questglobal.student.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing an Address.
 * This class is used to transfer address-related information between layers of the application, 
 * such as from the client to the server or from the server to the database.
 * 
 * The address includes fields for flat number, city, and state, which are essential for 
 * representing a student's address in the system.
 * 
 * @author Mahendra Hati
 * @email hatimahendra11@gmail.com
 * @version 1.0
 * @since 2024-11-08
 */
@Data
public class AddressDTO {
    private String flatNo;
    private String city;
    private String state;
}
