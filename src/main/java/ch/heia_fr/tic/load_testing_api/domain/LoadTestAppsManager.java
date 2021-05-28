package ch.heia_fr.tic.load_testing_api.domain;

import ch.heia_fr.tic.load_testing_api.domain.dto.Configuration;
import ch.heia_fr.tic.load_testing_api.domain.dto.Status;

/**
 * Defines the methods to manage the execution of the tests.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public interface LoadTestAppsManager {
    /**
     * Starts test if no other test is running.
     *
     * @param configuration the configuration to run
     */
    void run(Configuration configuration);
    /**
     * Stops currently running test
     */
    void stop();
    
    /**
     * Gets the status of the test if there is any
     */
    Status getStatus();
}
