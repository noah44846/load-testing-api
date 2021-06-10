package ch.heia_fr.tic.load_testing_api.service.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps a {@code WebApplicationException} to the response of an API service call.
 * <p>
 * This class is used to log error and customize response when a web application exception occurred.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    
    /**
     * The {@code Logger} to write messages to log file
     */
    private static final Logger logger = LogManager.getLogger();
    
    @Override
    public Response toResponse(WebApplicationException exception) {
        try (Response response = exception.getResponse()) {
            // Gets the error description according to the status code family.
            StatusType statusInfo = response.getStatusInfo();
            System.out.println(exception.getMessage());
            String description;
            switch (statusInfo.getFamily()) {
                case REDIRECTION:
                    description = ServiceError.DESCRIPTION_3XX;
                    break;
                case CLIENT_ERROR:
                    description = exception.getMessage();
                    break;
                case SERVER_ERROR:
                    description = ServiceError.DESCRIPTION_5XX;
                    break;
                case INFORMATIONAL:
                case SUCCESSFUL:
                case OTHER:
                default:
                    description = "N/A";
            }
            
            // Creates the error DTO, writes the error to log file and returns a custom response.
            ServiceError serviceError = new ServiceError(statusInfo, description);
            String error = serviceError.toString();
            if (statusInfo == Status.INTERNAL_SERVER_ERROR) {
                WebApplicationExceptionMapper.logger.error(error, exception);
            }
            else {
                WebApplicationExceptionMapper.logger.warn(error);
            }
            return MapperUtility.getResponse(serviceError);
        }
    }
}
