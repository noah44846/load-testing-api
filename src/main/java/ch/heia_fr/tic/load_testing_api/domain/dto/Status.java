package ch.heia_fr.tic.load_testing_api.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Specifies the DTO (Data Transfer Object) for the status of the tests.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonPropertyOrder({"loadTestStatus", "dataSourceMonitorStatus", "lastExecutedConfiguration"})
public class Status {
    
    /**
     * The status of the load test app.
     */
    public final TestStatus loadTestStatus;
    
    /**
     * The status of the DataSource monitor app.
     */
    public final TestStatus dataSourceMonitorStatus;
    
    /**
     * The name of the last configuration that was executed
     */
    public final String lastExecutedConfiguration;
    
    /**
     * Returns a new {@code Status} with its properties initialized from parameters.
     *
     * @param loadTestStatus the status of the load test app
     * @param dataSourceMonitorStatus the status of the DataSource monitor app
     * @param lastExecutedConfiguration the name of the last configuration that was executed
     */
    public Status(TestStatus loadTestStatus, TestStatus dataSourceMonitorStatus, String lastExecutedConfiguration) {
        this.loadTestStatus = loadTestStatus;
        this.dataSourceMonitorStatus = dataSourceMonitorStatus;
        this.lastExecutedConfiguration = lastExecutedConfiguration;
    }
    
    /**
     * Enumeration that represents the possible states of a test.
     *
     * @author Noah Godel (noah.godel@hefr.ch)
     */
    public enum TestStatus {
        RUNNING, IDLE
    }
}
