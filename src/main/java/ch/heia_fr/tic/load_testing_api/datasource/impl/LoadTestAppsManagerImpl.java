package ch.heia_fr.tic.load_testing_api.datasource.impl;

import ch.heia_fr.tic.load_testing_api.domain.LoadTestAppsManager;
import ch.heia_fr.tic.load_testing_api.domain.dto.Configuration;
import ch.heia_fr.tic.load_testing_api.domain.dto.Status;
import ch.heia_fr.tic.load_testing_api.domain.dto.Status.TestStatus;
import ch.heia_fr.tic.loadtest.Config;
import ch.heia_fr.tic.loadtest.ConfigFactory;
import ch.heia_fr.tic.loadtest.LoadTestHandler;
import ch.heia_fr.tic.loadtest.ResultWriter;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Manages the manage the execution of the tests.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public class LoadTestAppsManagerImpl implements LoadTestAppsManager {
    private final LoadTestHandler handler;
    
    public LoadTestAppsManagerImpl() {
        handler = new LoadTestHandler();
    }
    
    @Override
    public void run(Configuration configuration) {
        // TODO add support for the DataSource monitor app
        ConfigFactory factory = ConfigFactory.newInstance();
        try {
            // the config passed to the method is ignored for now
            Config config = factory.createConfigFromPropFile("config.properties");
            CompletableFuture.runAsync(() -> {
                try {
                    ResultWriter.writeToCSVFile(handler.run(config), config.outputPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.stop();
            });
        } catch (IOException e) {
            // throws a 500 because the configuration file can't be parsed
            throw new WebApplicationException(e);
        } catch (IllegalStateException e) {
            // throws a 400 because a test is already running
            throw new BadRequestException(e);
        }
    }
    
    @Override
    public void stop() {
        if (!handler.isRunning()) {
            // throw a 400 because no test is running
            throw new BadRequestException();
        }
        handler.stop();
    }
    
    @Override
    public Status getStatus() {
        return new Status(
                handler.isRunning() ? TestStatus.RUNNING : TestStatus.IDLE,
                TestStatus.IDLE);
    }
}
