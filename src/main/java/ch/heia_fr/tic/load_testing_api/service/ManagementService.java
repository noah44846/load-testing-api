package ch.heia_fr.tic.load_testing_api.service;

import ch.heia_fr.tic.load_testing_api.domain.dto.Status;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Defines the service endpoint for the management resource.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public interface ManagementService {
    
    /**
     * Starts running the test with the configuration identified by the {@code id} value.
     * <p>
     * Throws a 400 Bad Request if another test is already running
     *
     * @param configName the name of the configuration resource
     */
    @POST
    @Path("/run/{configName}")
    void run(@PathParam("configName") String configName);
    
    /**
     * Stops the currently running test.
     * <p>
     * Returns an empty body with the HTTP status code 204 No Content.
     * <p>
     * Throws a 400 Bad Request error if no test is running.
     */
    @POST
    @Path("/stop")
    void stop();
    
    /**
     * Gets the {@link Status} of the test.
     *
     * @return the status resource, in the response body as JSON, based on the {@link Status} DTO with the HTTP status code 200 OK
     */
    @GET
    @Path("/status")
    Response getStatus();
}
