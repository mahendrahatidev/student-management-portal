package com.questglobal.student.exception;

/**
 * Custom exception class to handle cases where a student is not found by ID.
 * <p>
 * This exception is thrown when the requested student data cannot be found in the database.
 * It extends {@link RuntimeException}, making it an unchecked exception.
 * </p>
 * 
 * <p>
 * <b>Note:</b> It is important to provide detailed messages when throwing this exception for debugging purposes.
 * </p>
 * 
 * <p>
 * @author Mahendra Hati
 * @email hatimahendra11@gmail.com
 * @version 1.0
 * @since 2024-11-08
 * </p>
 */
public class StudentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor to create a new instance of StudentNotFoundException with a detailed message.
     * 
     * @param message The detailed error message to be included with the exception.
     */
    public StudentNotFoundException(String message) {
        // Pass the message to the superclass constructor (RuntimeException)
        super(message);

        // Log the exception creation for debugging purposes (optional)
        //logger.error("StudentNotFoundException: " + message); // Uncomment for logging, if needed
    }
}
