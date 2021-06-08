package ch.heia_fr.tic.load_testing_api.datasource.impl;

import ch.heia_fr.tic.load_testing_api.domain.ConfigurationHandler;
import ch.heia_fr.tic.load_testing_api.domain.dto.Configuration;
import ch.heia_fr.tic.load_testing_api.domain.dto.DSMConfiguration;
import ch.heia_fr.tic.load_testing_api.domain.dto.LTConfiguration;
import ch.heia_fr.tic.load_testing_api.utils.PropertiesUtility;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import java.io.*;
import java.util.Collection;
import java.util.Properties;

/**
 * Manages the configuration data in the storage.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public class ConfigurationMapper implements ConfigurationHandler {
    
    /**
     * Path of the directory containing the configuration files of the load test app.
     */
    public static final String LT_CONFIG_DIR = PropertiesUtility.getPropertyValue("configurations.lt");
    
    /**
     * Path of the directory containing the configuration files of the dataSource monitor app.
     */
    public static final String DSM_CONFIG_DIR = PropertiesUtility.getPropertyValue("configurations.dsm");
    
    /**
     * Pattern for the path of a load test configuration file.
     */
    public static final String LT_CONFIG_FILE_PATTERN = LT_CONFIG_DIR + "%s.properties";
    
    /**
     * Pattern for the path of a dataSource monitor configuration file.
     */
    public static final String DSM_CONFIG_FILE_PATTERN = DSM_CONFIG_DIR + "%s.properties";
    
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
        if (alreadyExists(configuration.name)) {
            throw new BadRequestException("Configuration already exists.");
        }
        if (configuration.ltConfiguration != null) {
            writeLTConfigurationToFile(configuration.ltConfiguration, configuration.name);
        }
        if (configuration.dsmConfiguration != null) {
            writeDSMConfigurationToFile(configuration.dsmConfiguration, configuration.name);
        }
        return getConfiguration(configuration.name);
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
        return ((new File(String.format(LT_CONFIG_FILE_PATTERN, name))).exists() ||
                (new File(String.format(DSM_CONFIG_FILE_PATTERN, name))).exists());
    }
    
    private LTConfiguration getLTConfigurationFromName(String name) {
        Properties props = getPropsFromFile(String.format(LT_CONFIG_FILE_PATTERN, name));
        return new LTConfiguration(
                props.getProperty("baseURL"),
                props.getProperty("subSite"),
                props.getProperty("httpMethod"),
                props.getProperty("requestBody"),
                props.getProperty("contentType"),
                props.getProperty("authType"),
                props.getProperty("apiKey"),
                Integer.parseInt(props.getProperty("numberOfRequests"))
        );
    }
    
    private void writeLTConfigurationToFile(LTConfiguration config, String name) {
        Properties props = new Properties();
        props.setProperty("outputPath", ResultMapper.LT_RESULT_PATH);
        props.setProperty("baseURL", config.baseURL);
        props.setProperty("subSite", config.subSite);
        props.setProperty("httpMethod", config.httpMethod);
        props.setProperty("requestBody", config.requestBody);
        props.setProperty("contentType", config.contentType);
        props.setProperty("apiKey", config.apiKey);
        props.setProperty("authType", config.authType);
        props.setProperty("numberOfRequests", config.numberOfRequests + "");
        writePropsToFile(props, String.format(LT_CONFIG_FILE_PATTERN, name));
    }
    
    private DSMConfiguration getDSMConfigurationFromFile(String name) {
        Properties props = getPropsFromFile(String.format(DSM_CONFIG_FILE_PATTERN, name));
        return new DSMConfiguration(
                props.getProperty("jmx.uri"),
                props.getProperty("mbean.objectName"),
                props.getProperty("mbean.watchedAttributes").split(" "),
                Long.parseLong(props.getProperty("monitor.timeout")),
                props.getProperty("monitor.testingDuration")
        );
    }
    
    private void writeDSMConfigurationToFile(DSMConfiguration config, String name) {
        Properties props = new Properties();
        props.setProperty("outputPath", ResultMapper.DSM_RESULT_PATH);
        props.setProperty("jmx.uri", config.jmxURI);
        props.setProperty("mbean.objectName", config.objectName);
        props.setProperty("mbean.watchedAttributes", String.join(" ", config.watchedAttributes));
        props.setProperty("monitor.timeout", config.timeout + "");
        props.setProperty("monitor.testingDuration", config.testingDuration);
        writePropsToFile(props, String.format(DSM_CONFIG_FILE_PATTERN, name));
    }
    
    private void writePropsToFile(Properties props, String filepath) {
        try (OutputStream output = new FileOutputStream(filepath)) {
            props.store(output, null);
        } catch (IOException e) {
            throw new WebApplicationException(e);
        }
    }
    
    private Properties getPropsFromFile(String filepath) {
        try (InputStream input = new FileInputStream(filepath)) {
            Properties props = new Properties();
            props.load(input);
            return props;
        } catch (IOException e) {
            throw new WebApplicationException(e);
        }
    }
}
