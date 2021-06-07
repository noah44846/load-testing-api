package ch.heia_fr.tic.load_testing_api.datasource.impl;

import ch.heia_fr.tic.load_testing_api.domain.ConfigurationHandler;
import ch.heia_fr.tic.load_testing_api.domain.dto.Configuration;

import java.util.Collection;

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
    public Configuration getConfiguration(int id) {
        return null;
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
    public Configuration deleteConfiguration(int id) {
        return null;
    }
}
