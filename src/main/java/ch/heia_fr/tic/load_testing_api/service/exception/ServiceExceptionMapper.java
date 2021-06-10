package ch.heia_fr.tic.load_testing_api.service.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps a {@code ServiceException} to the response of an API service call.
 * <p>
 * This class is used to log error and customize response when a service exception occurred.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {
    
    /**
     * The {@code Logger} to write messages to log file
     */
    private static final Logger logger = LogManager.getLogger();
    
    @Override
    public Response toResponse(ServiceException exception) {
        // Gets the error DTO from exception, writes the error to log file and returns a custom response.
        ServiceError serviceError = exception.serviceError;
        String error = serviceError.toString();
        ServiceExceptionMapper.logger.warn(error);
        return MapperUtility.getResponse(serviceError);
    }
}
