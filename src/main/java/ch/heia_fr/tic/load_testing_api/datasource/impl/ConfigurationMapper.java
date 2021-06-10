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
import java.util.*;
import java.util.stream.Stream;

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
        Stream<File> ltConfigs = Arrays.stream(new File(LT_CONFIG_DIR).listFiles()).filter(File::isFile);
        Stream<File> dsmConfigs = Arrays.stream(new File(DSM_CONFIG_DIR).listFiles()).filter(File::isFile);
        HashMap<String, Configuration> configMap = new HashMap<>();
        
        ltConfigs.forEach(f -> {
            String name = f.getName().split("\\.")[0];
            LTConfiguration ltConf = getLTConfigurationFromName(name);
            configMap.put(name, new Configuration(name, ltConf, null));
        });
        
        dsmConfigs.forEach(f -> {
            String name = f.getName().split("\\.")[0];
            DSMConfiguration dsmConf = getDSMConfigurationFromFile(name);
            if (configMap.containsKey(name)) {
                Configuration conf = configMap.get(name);
                configMap.put(name, new Configuration(name, conf.ltConfiguration, dsmConf));
            } else {
                configMap.put(name, new Configuration(name,null, dsmConf));
            }
        });
        
        return configMap.values();
    }
    
    @Override
    public Configuration getConfiguration(String name) {
        if (!alreadyExists(name)) {
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
    public void updateConfiguration(String name, Configuration configuration) {
        if (!alreadyExists(name)) {
            throw new NotFoundException("Configuration doesn't exists.");
        }
        deleteConfiguration(name);
        addConfiguration(configuration);
    }
    
    @Override
    public void deleteConfiguration(String name) {
        (new File(String.format(LT_CONFIG_FILE_PATTERN, name))).delete();
        (new File(String.format(DSM_CONFIG_FILE_PATTERN, name))).delete();
    }
    
    /**
     * Checks if the specified configuration already exists.
     *
     * @param name the name of the configuration
     * @return a boolean that indicates if the configuration already exists
     */
    private boolean alreadyExists(String name) {
        return ((new File(String.format(LT_CONFIG_FILE_PATTERN, name))).exists() ||
                (new File(String.format(DSM_CONFIG_FILE_PATTERN, name))).exists());
    }
    
    /**
     * Maps the data in the specified file to a {@code LTConfiguration}.
     *
     * @param name the name of the configuration
     * @return a {@code LTConfiguration} with the data of the file
     */
    private LTConfiguration getLTConfigurationFromName(String name) {
        if (!(new File(String.format(LT_CONFIG_FILE_PATTERN, name))).exists()) {
            return null;
        }
        
        Properties props = getPropsFromFile(String.format(LT_CONFIG_FILE_PATTERN, name));
        return new LTConfiguration(
                props.getProperty("baseURL"),
                props.getProperty("subSite"),
                props.getProperty("httpMethod"),
                props.getProperty("requestBody"),
                props.getProperty("contentType"),
                props.getProperty("apiKey"),
                props.getProperty("authType"),
                Integer.parseInt(props.getProperty("numberOfRequests"))
        );
    }
    
    /**
     * Writes a {@code LTConfiguration}.in a properties file.
     *
     * @param config the {@code LTConfiguration} to write
     * @param name   the name of the configuration
     */
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
    
    /**
     * Maps the data in the specified file to a {@code DSMConfiguration}.
     *
     * @param name the name of the configuration
     * @return a {@code DSMConfiguration} with the data of the file
     */
    private DSMConfiguration getDSMConfigurationFromFile(String name) {
        if (!(new File(String.format(DSM_CONFIG_FILE_PATTERN, name))).exists()) {
            return null;
        }
        
        Properties props = getPropsFromFile(String.format(DSM_CONFIG_FILE_PATTERN, name));
        return new DSMConfiguration(
                props.getProperty("jmx.uri"),
                props.getProperty("mbean.objectName"),
                props.getProperty("mbean.watchedAttributes").split(" "),
                Long.parseLong(props.getProperty("monitor.timeout")),
                props.getProperty("monitor.testingDuration")
        );
    }
    
    /**
     * Writes a {@code DSMConfiguration}.in a properties file.
     *
     * @param config the {@code DSMConfiguration} to write
     * @param name   the name of the configuration
     */
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
    
    /**
     * Writes a {@code LTConfiguration}.in a properties file.
     *
     * @param props    the properties object to put in the file
     * @param filepath the path of the file
     * @throws WebApplicationException if there is an error while writing to the file
     */
    private void writePropsToFile(Properties props, String filepath) {
        try (OutputStream output = new FileOutputStream(filepath)) {
            props.store(output, null);
        } catch (IOException e) {
            throw new WebApplicationException(e);
        }
    }
    
    /**
     * Maps the data in the specified file to a {@code DSMConfiguration}.
     *
     * @param filepath the name of the configuration
     * @return a {@code Properties} object with the data of the file
     * @throws WebApplicationException if there is an error while reading the file
     */
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
