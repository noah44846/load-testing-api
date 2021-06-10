package ch.heia_fr.tic.load_testing_api.service.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.ws.rs.core.Response.StatusType;
import java.io.Serializable;

/**
 * Specifies the DTO (Data Transfer Object) for an error in an API service.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonPropertyOrder({ServiceError.STATUS, ServiceError.MESSAGE, ServiceError.DESCRIPTION})
public class ServiceError implements Serializable {
    
    /**
     * The version number for this class used during deserialization
     *
     * @see Serializable
     */
    private static final long serialVersionUID = -1177217534993354793L;
    
    /**
     * The format string for the toString method
     */
    private static final String TO_STRING_FORMAT = "%s (%s) [%d]";
    
    /**
     * JSON property name for the status
     */
    static final String STATUS = "status";
    
    /**
     * JSON property name for the message
     */
    static final String MESSAGE = "message";
    
    /**
     * JSON property name for the description
     */
    static final String DESCRIPTION = "description";
    
    /**
     * The description for the 3XX HTTP codes family
     */
    static final String DESCRIPTION_3XX = "Further action must be taken in order to complete the request.";
    
    /**
     * The description for the 5XX HTTP codes family
     */
    static final String DESCRIPTION_5XX = "The server failed to fulfill an apparently valid request.";
    
    /**
     * The HTTP status code of the error
     */
    final int status;
    
    /**
     * The message of the error
     */
    private final String message;
    
    /**
     * The description of the error
     */
    private final String description;
    
    /**
     * Returns a new {@code ServiceError} with its properties initialized from parameters.
     *
     * @param status the HTTP status code of the error
     * @param message the message of the error
     * @param description the description of the error
     */
    public ServiceError(int status, String message, String description) {
        this.status = status;
        this.message = message;
        this.description = description;
    }
    
    /**
     * Returns a new {@code ServiceError} with its properties initialized from parameters.
     *
     * @param status the {@code StatusType} for the error
     * @param description the description of the error
     */
    public ServiceError(StatusType status, String description) {
        this.status = status.getStatusCode();
        this.message = status.getReasonPhrase();
        this.description = description;
    }
    
    /**
     * Returns a textual representation of the {@code ServiceError}: {@link #message} ({@link #description}) [{@link #status}].
     *
     * @return a string representation of the {@code ServiceError}
     */
    @Override
    public String toString() {
        return String.format(ServiceError.TO_STRING_FORMAT, this.message, this.description, this.status);
    }
}
