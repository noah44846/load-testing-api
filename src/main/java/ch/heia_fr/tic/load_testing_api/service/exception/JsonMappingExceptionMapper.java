package ch.heia_fr.tic.load_testing_api.service.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps a {@code JsonMappingException} to the response of an API service call.
 * <p>
 * This class is used to log error and customize response when a JSON mapping exception occurred.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 * @see <a href="https://stackoverflow.com/questions/45478512/jackson-jsonparseexceptionmapper-and-jsonmappingexceptionmapper-shadows-custom-m">Stack Overflow</a>
 */
@Provider
@Priority(Priorities.USER)
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {
    
    /**
     * The {@code Logger} to write messages to log file
     */
    private static final Logger logger = LogManager.getLogger();
    
    @Override
    public Response toResponse(JsonMappingException exception) {
        // Creates the error DTO, writes the error to log file and returns a custom response.
        ServiceError serviceError = new ServiceError(Status.BAD_REQUEST, ServiceError.DESCRIPTION_4XX);
        String error = serviceError.toString();
        JsonMappingExceptionMapper.logger.warn(error);
        JsonMappingExceptionMapper.logger.warn(exception);
        return MapperUtility.getResponse(serviceError);
    }
}
