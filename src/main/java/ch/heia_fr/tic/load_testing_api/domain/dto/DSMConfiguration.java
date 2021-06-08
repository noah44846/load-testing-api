package ch.heia_fr.tic.load_testing_api.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonPropertyOrder({"jmxURI", "objectName", "watchedAttributes", "timeout", "testingDuration"})
public class DSMConfiguration {
    
    public @NotBlank String jmxURI;
    
    public @NotBlank String objectName;
    
    public @NotEmpty String[] watchedAttributes;
    
    public @Min(0) long timeout;
    
    public @NotBlank String testingDuration;
    
    public DSMConfiguration(String jmxURI, String objectName, String[] watchedAttributes, long timeout, String testingDuration) {
        this.jmxURI = jmxURI;
        this.objectName = objectName;
        this.watchedAttributes = watchedAttributes;
        this.timeout = timeout;
        this.testingDuration = testingDuration;
    }
}
