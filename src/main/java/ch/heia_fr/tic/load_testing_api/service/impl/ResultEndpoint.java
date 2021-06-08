package ch.heia_fr.tic.load_testing_api.service.impl;

import ch.heia_fr.tic.load_testing_api.domain.ResultHandler;
import ch.heia_fr.tic.load_testing_api.domain.StorageFactory;
import ch.heia_fr.tic.load_testing_api.service.ResultService;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.io.File;

/**
 * Implements the service endpoint for the result resource.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@Path("/results")
@Produces("text/csv")
public class ResultEndpoint implements ResultService {
    
    @Override
    public Response getLoadTestResult() {
        ResultHandler resultHandler = StorageFactory.getResultStorage();
        File resultFile = resultHandler.getLoadTestResult();
        ResponseBuilder responseBuilder = Response.ok(resultFile);
        return responseBuilder.build();
    }
    
    @Override
    public Response getDataSourceMonitorResult() {
        ResultHandler resultHandler = StorageFactory.getResultStorage();
        File resultFile = resultHandler.getDataSourceMonitorResult();
        ResponseBuilder responseBuilder = Response.ok(resultFile);
        return responseBuilder.build();
    }
}
