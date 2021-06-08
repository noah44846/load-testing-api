package ch.heia_fr.tic.load_testing_api.domain;


import java.io.File;
import javax.ws.rs.BadRequestException;

/**
 * Defines the methods to manage result data in the storage.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public interface ResultHandler {
    
    /**
     * Returns the last result of the load test app.
     *
     * @throws BadRequestException if there is no result
     *
     * @return the corresponding file
     */
    File getLoadTestResult();
    
    /**
     * Returns the last result of the dataSource monitor app.
     *
     * @throws BadRequestException if there is no result
     *
     * @return the corresponding file
     */
    File getDataSourceMonitorResult();
}
