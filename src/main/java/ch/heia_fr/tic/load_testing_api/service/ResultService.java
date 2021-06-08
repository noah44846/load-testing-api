package ch.heia_fr.tic.load_testing_api.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Defines the service endpoint for the results.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public interface ResultService {
    
    /**
     * Gets the result of the last time the load test app was executed.
     * <p>
     * Throws a 404 Not Found if no result is present
     *
     * @return the result, in the response body as CSV, with the HTTP status code 200 OK
     */
    @GET
    @Path("/loadTest")
    Response getLoadTestResult();
    
    /**
     * Gets the result of the last time the dataSource monitor app was executed.
     * <p>
     * Throws a 404 Not Found if no result is present
     *
     * @return the result, in the response body as CSV, with the HTTP status code 200 OK
     */
    @GET
    @Path("/dataSourceMonitor")
    Response getDataSourceMonitorResult();
}
