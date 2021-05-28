package ch.heia_fr.tic.load_testing_api.service;

import ch.heia_fr.tic.load_testing_api.datasource.impl.LoadTestAppsManagerImpl;
import ch.heia_fr.tic.load_testing_api.domain.LoadTestAppsManager;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.JaxRSFeature;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Customizes the resource configuration for the web application.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public final class MyApplication extends ResourceConfig {
    
    /**
     * Constructor that specifies packages that must be scanned to register resources and providers automatically.
     * This is necessary because the web.xml declares {@code metadata-complete="true"} to speed up Tomcat startup by disabling files scanning for Servlet 3.0 specific annotations.
     * <p>
     * The dependency injection for {@link LoadTestAppsManager} is set up here so it can be used anywhere in the project
     * <p>
     * It also instantiates and registers a {@link JacksonJaxbJsonProvider}.
     * This {@code JacksonJaxbJsonProvider} is used to disable the {@code JaxRSFeature.ALLOW_EMPTY_INPUT} feature.
     * When disabled, this feature will rise an HTTP 400 Bad Request response, if the request payload is empty during the Jackson deserialization process.
     *
     * @see <a href="https://github.com/FasterXML/jackson-jaxrs-providers/issues/49">Issue #49</a>
     */
    public MyApplication() {
        this.packages("ch.heia_fr.tic.load_testing_api.service");
        this.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(new LoadTestAppsManagerImpl()).to(LoadTestAppsManager.class);
            }
        });
        
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.disable(JaxRSFeature.ALLOW_EMPTY_INPUT);
        this.register(provider);
    }
}
