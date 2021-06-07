package ch.heia_fr.tic.load_testing_api.service.impl;

import ch.heia_fr.tic.load_testing_api.domain.ConfigurationHandler;
import ch.heia_fr.tic.load_testing_api.domain.StorageFactory;
import ch.heia_fr.tic.load_testing_api.domain.dto.Configuration;
import ch.heia_fr.tic.load_testing_api.service.ConfigurationService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.ws.WebServiceException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

/**
 * Implements the service endpoint for the configuration resource.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@Path("/configurations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConfigurationEndpoint implements ConfigurationService {
    
    @Override
    public Response getConfigurationList() {
        return null;
    }
    
    @Override
    public Response getConfiguration(int id) {
        return null;
    }
    
    @Override
    public Response addConfiguration(Configuration configuration) {
        return null;
    }
    
    @Override
    public void updateConfiguration(Configuration configuration, int id) {
    }
    
    @Override
    public void deleteConfiguration(int id) {
    }
}
