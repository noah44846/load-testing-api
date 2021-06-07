package ch.heia_fr.tic.load_testing_api.datasource.impl;

import ch.heia_fr.tic.load_testing_api.domain.ResultHandler;
import ch.heia_fr.tic.load_testing_api.utils.PropertiesUtility;

import javax.ws.rs.NotFoundException;
import java.io.File;

/**
 * Gets the result data in the storage.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
public class ResultMapper implements ResultHandler {
    
    /**
     * Path for the result CSV file of the load test app.
     */
    public static final String LT_RESULT_PATH = PropertiesUtility.getPropertyValue("results.lt");
    
    /**
     * Path for the result CSV file of the dataSource monitor app.
     */
    public static final String DSM_RESULT_PATH = PropertiesUtility.getPropertyValue("results.dsm");
    
    @Override
    public File getLoadTestResult() {
        File result = new File(LT_RESULT_PATH);
        if (!result.exists()) {
            throw new NotFoundException("The load test was never run.");
        }
        return result;
    }
    
    @Override
    public File getDataSourceMonitorResult() {
        File result = new File(DSM_RESULT_PATH);
        if (!result.exists()) {
            throw new NotFoundException("The dataSource monitor was never run.");
        }
        return result;
    }
}
