package ch.heia_fr.tic.load_testing_api.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Specifies the DTO (Data Transfer Object) for a configuration.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonPropertyOrder({"ltConfiguration", "dsmConfiguration"})
public class Configuration {
    
    public final LTConfiguration ltConfiguration;
    
    public final DSMConfiguration dsmConfiguration;
    
    public Configuration(LTConfiguration ltConfiguration, DSMConfiguration dsmConfiguration) {
        this.ltConfiguration = ltConfiguration;
        this.dsmConfiguration = dsmConfiguration;
    }
}
