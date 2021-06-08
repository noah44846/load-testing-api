package ch.heia_fr.tic.load_testing_api.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotBlank;

/**
 * Specifies the DTO (Data Transfer Object) for a configuration.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonPropertyOrder({Configuration.NAME, Configuration.LT_CONFIGURATION, Configuration.DSM_CONFIGURATION})
public final class Configuration {
    
    /**
     * JSON property name for the name
     */
    static final String NAME = "name";
    
    /**
     * JSON property name for the load test configuration
     */
    static final String LT_CONFIGURATION = "ltConfiguration";
    
    /**
     * JSON property name for the dataSource monitor configuration
     */
    static final String DSM_CONFIGURATION = "dsmConfiguration";
    
    /**
     * The name of the configuration
     */
    public final @NotBlank String name;
    
    /**
     * The configuration of the load test app
     */
    public final LTConfiguration ltConfiguration;
    
    /**
     * The configuration of the dataSource monitor app
     */
    public final DSMConfiguration dsmConfiguration;
    
    /**
     * Returns a new {@code Configuration} with its properties initialized from parameters (used by Jackson for the request payload deserialization).
     *
     * @param name the name of the configuration
     * @param ltConfiguration the configuration of the load test app
     * @param dsmConfiguration the configuration of the dataSource monitor app
     */
    public Configuration(@JsonProperty(NAME) String name, @JsonProperty(LT_CONFIGURATION) LTConfiguration ltConfiguration, @JsonProperty(DSM_CONFIGURATION) DSMConfiguration dsmConfiguration) {
        this.name = name;
        this.ltConfiguration = ltConfiguration;
        this.dsmConfiguration = dsmConfiguration;
    }
}
