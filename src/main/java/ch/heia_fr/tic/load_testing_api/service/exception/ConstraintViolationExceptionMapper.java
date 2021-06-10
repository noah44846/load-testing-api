package ch.heia_fr.tic.load_testing_api.service.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps a {@code ConstraintViolationException} to the response of an API service call.
 * <p>
 * This class is used to log error and customize response when a JSON constraint violation exception occurred.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    
    /**
     * The {@code Logger} to write messages to log file
     */
    private static final Logger logger = LogManager.getLogger();
    
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // Creates the error DTO, writes the error to log file and returns a custom response.
        ServiceError serviceError = new ServiceError(Status.BAD_REQUEST, ServiceError.DESCRIPTION_4XX);
        String error = serviceError.toString();
        ConstraintViolationExceptionMapper.logger.warn(error);
        ConstraintViolationExceptionMapper.logger.warn(exception);
        return MapperUtility.getResponse(serviceError);
    }
}
