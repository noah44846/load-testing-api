package ch.heia_fr.tic.load_testing_api.service.exception;

//import ch.heia_fr.tic.load_testing_api.utils.PropertiesUtility;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Contains method to get a customized {@code Response} when an error occurred in the application.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
enum MapperUtility {
    ;
    
    /**
     * Returns a {@code Response} for an error to the custom mappers.
     *
     * @param serviceError the {@code ServiceError} that contains the HTTP status code, the message and the description
     * @return the {@code Response} with the error information in the body as JSON and the corresponding HTTP status code
     */
    public static Response getResponse(ServiceError serviceError) {
        ResponseBuilder responseBuilder = Response.status(serviceError.status);
        responseBuilder.entity(serviceError);
        responseBuilder.type(MediaType.APPLICATION_JSON);
        
        return responseBuilder.build();
    }
}
