package ch.heia_fr.tic.load_testing_api.service.exception;

import javax.ws.rs.WebApplicationException;
import java.io.Serializable;

/**
 * Specifies a custom exception for an API service.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public class ServiceException extends WebApplicationException {
    
    /**
     * The version number for this class used during deserialization
     *
     * @see Serializable
     */
    private static final long serialVersionUID = -2184819204267558757L;
    
    /**
     * The DTO for the error information
     */
    final ServiceError serviceError;
    
    /**
     * Returns a new {@code ServiceException} with its property initialized from parameter.
     *
     * @param serviceError the error information
     */
    public ServiceException(ServiceError serviceError) {
        this.serviceError = serviceError;
    }
}
