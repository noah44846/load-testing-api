package ch.heia_fr.tic.load_testing_api.domain;

import ch.heia_fr.tic.load_testing_api.datasource.impl.ConfigurationMapper;
import ch.heia_fr.tic.load_testing_api.datasource.impl.ResultMapper;

/**
 * Factory that provides the application data storage.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public enum StorageFactory {
    ;
    
    /**
     * Returns a loosely coupled storage to manage user data.
     *
     * @return a storage handler for the user data
     */
    public static ConfigurationHandler getConfigurationStorage() {
        return new ConfigurationMapper();
    }
    
    /**
     * Returns a loosely coupled storage to get the result data.
     *
     * @return a storage handler for the result data
     */
    public static ResultHandler getResultStorage() {
        return new ResultMapper();
    }
}
