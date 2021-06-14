package ch.heia_fr.tic.load_testing_api.datasource.impl;

import ch.heia_fr.tic.datasource_monitor.JMXMonitorHandler;
import ch.heia_fr.tic.datasource_monitor.Result;
import ch.heia_fr.tic.load_testing_api.domain.LoadTestAppsManager;
import ch.heia_fr.tic.load_testing_api.domain.dto.Configuration;
import ch.heia_fr.tic.load_testing_api.domain.dto.Status;
import ch.heia_fr.tic.load_testing_api.domain.dto.Status.TestStatus;
import ch.heia_fr.tic.load_testing_api.utils.PropertiesUtility;
import ch.heia_fr.tic.loadtest.LoadTestHandler;

import javax.management.MalformedObjectNameException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
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
        dataSourceMonitorHandler = new JMXMonitorHandler(PropertiesUtility.getPropertyValue("jmx.uri"));
        lastExecutedConfiguration = "";
    }
    
    @Override
    public void run(Configuration configuration) {
        if (loadTestHandler.isRunning() || dataSourceMonitorHandler.isRunning()) {
            throw new ClientErrorException("A test is already running. Wait for this test to finnish", Response.Status.CONFLICT);
        }
        if (configuration.loadTestConfiguration != null) {
            runLoadTest(String.format(ConfigurationMapper.LT_CONFIG_FILE_PATTERN, configuration.name));
        }
        if (configuration.dataSourceMonitorConfiguration != null) {
            runDataSourceMonitor(String.format(ConfigurationMapper.DSM_CONFIG_FILE_PATTERN, configuration.name));
        }
        lastExecutedConfiguration = configuration.name;
    }
    
    @Override
    public void stop() {
        if (!loadTestHandler.isRunning() && !dataSourceMonitorHandler.isRunning()) {
            throw new NotFoundException("No testing app is running.");
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
        } catch (IOException | IllegalStateException e) {
            throw new WebApplicationException(e);
        }
    }
    
    /**
     * Runs the dataSource monitoring app.
     *
     * @param configPath the path of the config to run
     * @throws WebApplicationException if there is an error parsing the configuration or running the test
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
        } catch (IOException | MalformedObjectNameException | IllegalStateException e) {
            throw new WebApplicationException(e);
        }
    }
}
