package ch.heia_fr.tic.load_testing_api.service.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps a {@code RuntimeException} to the response of an API service call.
 * <p>
 * This class is used to log error and customize response when an unchecked exception occurred.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
    
    /**
     * The {@code Logger} to write messages to log file
     */
    private static final Logger logger = LogManager.getLogger();
    
    /**
     * The description for this error
     */
    private static final String RUNTIME_ERROR_DESC = "A runtime exception occurred on the server.";
    
    @Override
    public Response toResponse(RuntimeException exception) {
        // Creates the error DTO, writes the error to log file and returns a custom response.
        ServiceError serviceError = new ServiceError(Status.INTERNAL_SERVER_ERROR, RuntimeExceptionMapper.RUNTIME_ERROR_DESC);
        String error = serviceError.toString();
        RuntimeExceptionMapper.logger.error(error, exception);
        return MapperUtility.getResponse(serviceError);
    }
}
