package ch.heia_fr.tic.load_testing_api.domain;

import ch.heia_fr.tic.load_testing_api.domain.dto.Configuration;

import java.util.Collection;

/**
 * Defines the methods to manage configuration data in the storage.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public interface ConfigurationHandler {
    
    /**
     * Returns a list of all configurations.
     * <p>
     * The list is empty if there is no {@code Configuration} in the storage.
     *
     * @return the list of all {@code Configuration}
     */
    Collection<Configuration> getConfigurationList();
    
    /**
     * Returns a specific configuration.
     *
     * @param name the unique name of the configuration to return
     * @return the corresponding {@code Configuration}, or {@code null} if the configuration does not exist
     */
    Configuration getConfiguration(String name);
    
    /**
     * Stores a new configuration.
     *
     * @param configuration the {@code Configuration} to store
     * @return the stored {@code Configuration}, or {@code null} if the {@code Configuration} was not stored
     */
    Configuration addConfiguration(Configuration configuration);
    
    /**
     * Replaces an configuration with the one in parameter, if both have the same unique ID.
     * <p>
     * The {@code Configuration} is not stored, if a corresponding {@code Configuration} does not exist.
     *
     * @param configuration the {@code Configuration} that will replace the stored one
     * @return the old {@code Configuration}, or {@code null} if the {@code Configuration} does not exist
     */
    void updateConfiguration(String name, Configuration configuration);
    
    /**
     * Deletes an configuration.
     *
     * @param name the unique name of the configuration to delete
     * @return the deleted {@code Configuration}, or {@code null} if the configuration does not exist
     */
    void deleteConfiguration(String name);
}
