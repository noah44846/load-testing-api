package ch.heia_fr.tic.load_testing_api.service.exception;

import com.fasterxml.jackson.core.JsonParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps a {@code JsonParseException} to the response of an API service call.
 * <p>
 * This class is used to log error and customize response when a JSON parsing exception occurred.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 * @see <a href="https://stackoverflow.com/a/45478604/2173651">Stack Overflow</a>
 */
@Provider
@Priority(Priorities.USER)
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {
    
    /**
     * The {@code Logger} to write messages to log file
     */
    private static final Logger logger = LogManager.getLogger();
    
    @Override
    public Response toResponse(JsonParseException exception) {
        // Creates the error DTO, writes the error to log file and returns a custom response.
        ServiceError serviceError = new ServiceError(Status.BAD_REQUEST, ServiceError.DESCRIPTION_4XX);
        String error = serviceError.toString();
        JsonParseExceptionMapper.logger.warn(error);
        JsonParseExceptionMapper.logger.warn(exception);
        return MapperUtility.getResponse(serviceError);
    }
}
