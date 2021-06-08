package ch.heia_fr.tic.load_testing_api.datasource.impl;

import ch.heia_fr.tic.load_testing_api.domain.ConfigurationHandler;
import ch.heia_fr.tic.load_testing_api.domain.dto.Configuration;
import ch.heia_fr.tic.load_testing_api.domain.dto.DSMConfiguration;
import ch.heia_fr.tic.load_testing_api.domain.dto.LTConfiguration;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

/**
 * Manages the configuration data in the storage.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public class ConfigurationMapper implements ConfigurationHandler {
    @Override
    public Collection<Configuration> getConfigurationList() {
        return null;
    }
    
    @Override
    public Configuration getConfiguration(String name) {
        if (!alreadyExists(name)){
            throw new NotFoundException("Configuration doesn't exist.");
        }
        return new Configuration(name, getLTConfigurationFromName(name), getDSMConfigurationFromFile(name));
    }
    
    @Override
    public Configuration addConfiguration(Configuration configuration) {
        return null;
    }
    
    @Override
    public Configuration updateConfiguration(Configuration configuration) {
        return null;
    }
    
    @Override
    public Configuration deleteConfiguration(String name) {
        return null;
    }
    
    private boolean alreadyExists(String name) {
        return (!(new File(String.format("/Users/noahgodel/projects/load-testing-api/configurations/lt/%s.properties", name))).exists() ||
                !(new File(String.format("/Users/noahgodel/projects/load-testing-api/configurations/dsm/%s.properties", name))).exists());
    }
    
    private LTConfiguration getLTConfigurationFromName(String name) {
        try (InputStream input = new FileInputStream(String.format("/Users/noahgodel/projects/load-testing-api/configurations/lt/%s.properties", name))) {
            Properties prop = new Properties();
            prop.load(input);
            return new LTConfiguration(
                    prop.getProperty("baseURL"),
                    prop.getProperty("subSite"),
                    prop.getProperty("httpMethod"),
                    prop.getProperty("requestBody"),
                    prop.getProperty("contentType"),
                    prop.getProperty("authType"),
                    prop.getProperty("apiKey"),
                    Integer.parseInt(prop.getProperty("numberOfRequests"))
            );
        } catch (IOException e) {
            throw new WebApplicationException(e);
        }
    }
    
    private DSMConfiguration getDSMConfigurationFromFile(String name) {
        try (InputStream input = new FileInputStream(String.format("/Users/noahgodel/projects/load-testing-api/configurations/dsm/%s.properties", name))) {
            Properties prop = new Properties();
            prop.load(input);
            return new DSMConfiguration(
                    prop.getProperty("jmx.url"),
                    prop.getProperty("mbean.objectName"),
                    prop.getProperty("mbean.watchedAttributes").split(" "),
                    Long.parseLong(prop.getProperty("monitor.timeout")),
                    prop.getProperty("monitor.testingDuration")
            );
        } catch (IOException e) {
            throw new WebApplicationException(e);
        }
    }
}
