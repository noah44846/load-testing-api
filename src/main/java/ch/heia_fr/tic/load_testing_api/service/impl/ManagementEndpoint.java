package ch.heia_fr.tic.load_testing_api.service.impl;

import ch.heia_fr.tic.load_testing_api.domain.LoadTestAppsManager;
import ch.heia_fr.tic.load_testing_api.domain.dto.Configuration;
import ch.heia_fr.tic.load_testing_api.domain.dto.Status;
import ch.heia_fr.tic.load_testing_api.service.ManagementService;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Implements the service endpoint for the management resource.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ManagementEndpoint implements ManagementService {
    
    @Inject
    LoadTestAppsManager loadTestAppsManager;
    
    @Override
    public void run(int id) {
        // the identified config is ignored for now
        // so an empty object is passed for now
        loadTestAppsManager.run(new Configuration("", null, null));
    }
    
    @Override
    public void stop() {
        loadTestAppsManager.stop();
    }
    
    @Override
    public Response getStatus() {
        Status status = loadTestAppsManager.getStatus();
        ResponseBuilder responseBuilder = Response.ok(status);
        return responseBuilder.build();
    }
}
