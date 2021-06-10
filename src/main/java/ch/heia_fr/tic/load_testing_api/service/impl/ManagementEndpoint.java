package ch.heia_fr.tic.load_testing_api.service.impl;

import ch.heia_fr.tic.load_testing_api.domain.ConfigurationHandler;
import ch.heia_fr.tic.load_testing_api.domain.LoadTestAppsManager;
import ch.heia_fr.tic.load_testing_api.domain.StorageFactory;
import ch.heia_fr.tic.load_testing_api.domain.dto.Configuration;
import ch.heia_fr.tic.load_testing_api.domain.dto.Status;
import ch.heia_fr.tic.load_testing_api.service.ManagementService;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public void run(String configName) {
        ConfigurationHandler configurationHandler = StorageFactory.getConfigurationStorage();
        Configuration configuration = configurationHandler.getConfiguration(configName);
        loadTestAppsManager.run(configuration);
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
