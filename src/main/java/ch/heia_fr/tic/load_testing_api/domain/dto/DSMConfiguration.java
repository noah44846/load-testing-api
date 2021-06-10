package ch.heia_fr.tic.load_testing_api.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * Specifies the DTO (Data Transfer Object) for a configuration of the dataSource monitor app.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonPropertyOrder({DSMConfiguration.JMX_URI, DSMConfiguration.OBJECT_NAME, DSMConfiguration.WATCHED_ATTRIBUTES, DSMConfiguration.TIMEOUT, DSMConfiguration.TESTING_DURATION})
public class DSMConfiguration {
    
    /**
     * JSON property name for the jmx uri
     */
    static final String JMX_URI = "jmxURI";
    
    /**
     * JSON property name for the object name
     */
    static final String OBJECT_NAME = "objectName";
    
    /**
     * JSON property name for the watched attributes
     */
    static final String WATCHED_ATTRIBUTES = "watchedAttributes";
    
    /**
     * JSON property name for the timeout
     */
    static final String TIMEOUT = "timeout";
    
    /**
     * JSON property name for the testing duration
     */
    static final String TESTING_DURATION = "testingDuration";
    
    /**
     * The jmx uri
     */
    public @NotBlank String jmxURI;
    
    /**
     * The object name
     */
    public @NotBlank String objectName;
    
    /**
     * The watched attributes
     */
    public @NotEmpty String[] watchedAttributes;
    
    /**
     * The timeout
     */
    public @Min(0) long timeout;
    
    /**
     * The testing duration
     */
    public @NotBlank String testingDuration;
    
    /**
     * Returns a new {@code DSMConfiguration} with its properties initialized from parameters (used by Jackson for the request payload deserialization).
     *
     * @param jmxURI            the jmx uri
     * @param objectName        the object name
     * @param watchedAttributes the watched attributes
     * @param timeout           the timeout
     * @param testingDuration   the testing duration
     */
    public DSMConfiguration(@JsonProperty(JMX_URI) String jmxURI, @JsonProperty(OBJECT_NAME) String objectName, @JsonProperty(WATCHED_ATTRIBUTES) String[] watchedAttributes, @JsonProperty(TIMEOUT) long timeout, @JsonProperty(TESTING_DURATION) String testingDuration) {
        this.jmxURI = jmxURI;
        this.objectName = objectName;
        this.watchedAttributes = watchedAttributes;
        this.timeout = timeout;
        this.testingDuration = testingDuration;
    }
}
