package ch.heia_fr.tic.load_testing_api.datasource.impl;

import ch.heia_fr.tic.datasource_monitor.JMXMonitorHandler;
import ch.heia_fr.tic.datasource_monitor.Result;
import ch.heia_fr.tic.load_testing_api.domain.LoadTestAppsManager;
import ch.heia_fr.tic.load_testing_api.domain.dto.Configuration;
import ch.heia_fr.tic.load_testing_api.domain.dto.Status;
import ch.heia_fr.tic.load_testing_api.domain.dto.Status.TestStatus;
import ch.heia_fr.tic.loadtest.LoadTestHandler;

import javax.management.MalformedObjectNameException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.List;
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
    private String lastExecutedConfiguration;
    
    public LoadTestAppsManagerImpl() {
        loadTestHandler = new LoadTestHandler();
        dataSourceMonitorHandler = new JMXMonitorHandler();
        lastExecutedConfiguration = "";
    }
    
    @Override
    public void run(Configuration configuration) {
        if (configuration.ltConfiguration != null) {
            runLoadTest(String.format(ConfigurationMapper.LT_CONFIG_FILE_PATTERN, configuration.name));
        }
        if (configuration.dsmConfiguration != null) {
            runDataSourceMonitor(String.format(ConfigurationMapper.DSM_CONFIG_FILE_PATTERN, configuration.name));
        }
        lastExecutedConfiguration = configuration.name;
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
                dataSourceMonitorHandler.isRunning() ? TestStatus.RUNNING : TestStatus.IDLE,
                lastExecutedConfiguration);
    }
    
    /**
     * Runs the load test app.
     *
     * @param configPath the path of the config to run
     * @throws WebApplicationException if there is an error parsing the configuration
     * @throws BadRequestException     if the app is already running
     */
    private void runLoadTest(String configPath) {
        try {
            // the config passed to the method is ignored for now
            ch.heia_fr.tic.loadtest.Config config = ch.heia_fr.tic.loadtest.ConfigFactory
                    .newInstance()
                    .createConfigFromPropFile(configPath);
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
     * @param configPath the path of the config to run
     * @throws WebApplicationException if there is an error parsing the configuration or running the test
     * @throws BadRequestException     if the app is already running
     */
    private void runDataSourceMonitor(String configPath) {
        try {
            ch.heia_fr.tic.datasource_monitor.Config conf = ch.heia_fr.tic.datasource_monitor.ConfigFactory
                    .newInstance()
                    .createConfigFromPropFile(configPath);
            CompletableFuture<List<Result>> results = dataSourceMonitorHandler.run(conf);
            CompletableFuture.runAsync(() -> {
                try {
                    ch.heia_fr.tic.datasource_monitor.ResultWriter.writeToCSVFile(results.get(), conf.outputPath);
                    
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
