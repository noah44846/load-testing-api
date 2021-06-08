package ch.heia_fr.tic.load_testing_api.utils;

import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads the webapp properties file.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public enum PropertiesUtility {
    ;
    
    /**
     * The {@code Properties} that contains the set of properties
     */
    private static final Properties PROPERTIES = new Properties();
    
    // Static class initializer for the properties class variable
    static {
        Thread thread = Thread.currentThread();
        ClassLoader classLoader = thread.getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("webapp.properties")) {
            PropertiesUtility.PROPERTIES.load(inputStream);
        }
        catch (IOException e) {
            throw new WebApplicationException(e);
        }
    }
    
    /**
     * Returns the property value for the specified key.
     * The method returns {@code null} if the property is not found.
     *
     * @param key the property key
     * @return the property value, or {@code null} if the property does not exist
     */
    public static String getPropertyValue(String key) {
        return PropertiesUtility.PROPERTIES.getProperty(key);
    }
}
