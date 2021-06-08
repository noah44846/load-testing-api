package ch.heia_fr.tic.load_testing_api.service;

import ch.heia_fr.tic.load_testing_api.domain.dto.Configuration;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Defines the service endpoint for the configuration resource.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public interface ConfigurationService {
    
    /**
     * Gets all configuration resources.
     *
     * @return a list of configuration resources, in the response body as JSON, based on the {@link Configuration} DTO with the HTTP status code 200 OK
     */
    @GET
    Response getConfigurationList();
    
    /**
     * Gets the configuration resource identified by the {@code id} value.
     * <p>
     * Throws a 404 Not Found error if the resource does not exist.
     *
     * @param name the name of the resource
     * @return the configuration resource, in the response body as JSON, based on the {@link Configuration} DTO with the HTTP status code 200 OK
     */
    @GET
    @Path("/{name}")
    Response getConfiguration(@PathParam("name") String name);
    
    /**
     * Adds a new configuration resource from the {@code Configuration} data in the request payload.
     * <p>
     * Throws a 404 Not Found error if the new resource was not created.
     *
     * @param configuration the data of the configuration resource to add
     * @return the added configuration resource, in the response body as JSON, based on the {@code Configuration} DTO with the HTTP status code 201 Created and the URI of the new resource
     */
    @POST
    Response addConfiguration(@Valid Configuration configuration);
    
    /**
     * Updates an configuration resource with the {@code Configuration} data in the request payload.
     * <p>
     * Returns an empty body with the HTTP status code 204 No Content.
     * <p>
     * Throws a 404 Not Found error if the resource does not exist.
     *
     * @param configuration the data of the configuration resource to update
     * @param name          the name of the resource
     */
    @PUT
    @Path("/{name}")
    void updateConfiguration(@Valid Configuration configuration, @PathParam("name") String name);
    
    /**
     * Deletes the configuration resource identified by the {@code id} value.
     * <p>
     * Returns an empty body with the HTTP status code 204 No Content.
     * <p>
     * Throws a 404 Not Found error if the resource does not exist.
     *
     * @param name the name of the resource
     */
    @DELETE
    @Path("/{name}")
    void deleteConfiguration(@PathParam("name") String name);
}
