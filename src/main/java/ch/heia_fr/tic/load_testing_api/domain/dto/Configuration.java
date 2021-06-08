package ch.heia_fr.tic.load_testing_api.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotBlank;

/**
 * Specifies the DTO (Data Transfer Object) for a configuration.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonPropertyOrder({"ltConfiguration", "dsmConfiguration"})
public class Configuration {
    
    public final @NotBlank String name;
    
    public final LTConfiguration ltConfiguration;
    
    public final DSMConfiguration dsmConfiguration;
    
    public Configuration(String name, LTConfiguration ltConfiguration, DSMConfiguration dsmConfiguration) {
        this.name = name;
        this.ltConfiguration = ltConfiguration;
        this.dsmConfiguration = dsmConfiguration;
    }
}
