package ch.heia_fr.tic.load_testing_api.domain;


import javax.ws.rs.BadRequestException;
import java.io.File;

/**
 * Defines the methods to manage result data in the storage.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public interface ResultHandler {
    
    /**
     * Returns the last result of the load test app.
     *
     * @return the corresponding file
     * @throws BadRequestException if there is no result
     */
    File getLoadTestResult();
    
    /**
     * Returns the last result of the dataSource monitor app.
     *
     * @return the corresponding file
     * @throws BadRequestException if there is no result
     */
    File getDataSourceMonitorResult();
}
