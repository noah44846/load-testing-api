package ch.heia_fr.tic.load_testing_api.datasource.impl;

import ch.heia_fr.tic.datasource_monitor.JMXMonitorHandler;
import ch.heia_fr.tic.datasource_monitor.TestResults;
import ch.heia_fr.tic.load_testing_api.domain.LoadTestAppsManager;
import ch.heia_fr.tic.load_testing_api.domain.dto.Configuration;
import ch.heia_fr.tic.load_testing_api.domain.dto.Status;
import ch.heia_fr.tic.load_testing_api.domain.dto.Status.TestStatus;
import ch.heia_fr.tic.loadtest.LoadTestHandler;

import javax.management.MalformedObjectNameException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Manages the manage the execution of the tests.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public class LoadTestAppsManagerImpl implements LoadTestAppsManager {
    private final LoadTestHandler loadTestHandler;
    private final JMXMonitorHandler dataSourceMonitorHandler;
    
    public LoadTestAppsManagerImpl() {
        loadTestHandler = new LoadTestHandler();
        dataSourceMonitorHandler = new JMXMonitorHandler();
    }
    
    @Override
    public void run(Configuration configuration) {
        // TODO add support for the DataSource monitor app
        runLoadTest();
        runDataSourceMonitor();
    }
    
    @Override
    public void stop() {
        if (!loadTestHandler.isRunning() && !dataSourceMonitorHandler.isRunning()) {
            // throw a 400 because no app is running
            throw new BadRequestException("No testing app is running.");
        }
        loadTestHandler.stop();
        dataSourceMonitorHandler.stop();
    }
    
    @Override
    public Status getStatus() {
        return new Status(
                loadTestHandler.isRunning() ? TestStatus.RUNNING : TestStatus.IDLE,
                dataSourceMonitorHandler.isRunning() ? TestStatus.RUNNING : TestStatus.IDLE);
    }
    
    /**
     * Runs the load test app.
     *
     * @throws WebApplicationException if there is an error parsing the configuration
     * @throws BadRequestException if the app is already running
     */
    private void runLoadTest() {
        try {
            // the config passed to the method is ignored for now
            ch.heia_fr.tic.loadtest.Config config = ch.heia_fr.tic.loadtest.ConfigFactory
                    .newInstance()
                    .createConfigFromPropFile("config.properties");
            CompletableFuture.runAsync(() -> {
                try {
                    ch.heia_fr.tic.loadtest.ResultWriter.writeToCSVFile(loadTestHandler.run(config), config.outputPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                loadTestHandler.stop();
            });
        } catch (IOException e) {
            throw new WebApplicationException(e);
        } catch (IllegalStateException e) {
            throw new BadRequestException(e);
        }
    }
    
    /**
     * Runs the dataSource monitoring app.
     *
     * @throws WebApplicationException if there is an error parsing the configuration or running the test
     * @throws BadRequestException if the app is already running
     */
    private void runDataSourceMonitor() {
        try {
            ch.heia_fr.tic.datasource_monitor.Config conf = ch.heia_fr.tic.datasource_monitor.ConfigFactory
                    .newInstance()
                    .createConfigFromPropFile("config1.properties");
            CompletableFuture<TestResults> results = dataSourceMonitorHandler.run(conf);
            CompletableFuture.runAsync(() -> {
                try {
                    ch.heia_fr.tic.datasource_monitor.ResultWriter.writeToCSVFile(results.get(), conf.outputPath, conf.attributes);
                    
                } catch (IOException | InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                loadTestHandler.stop();
            });
        } catch (IOException | MalformedObjectNameException e) {
            throw new WebApplicationException(e);
        } catch (IllegalStateException e) {
            throw new BadRequestException(e);
        }
    }
}
