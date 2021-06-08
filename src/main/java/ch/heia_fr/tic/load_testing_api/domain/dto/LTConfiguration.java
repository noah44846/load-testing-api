package ch.heia_fr.tic.load_testing_api.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotBlank;

/**
 * Specifies the DTO (Data Transfer Object) for a configuration of the load test app.
 *
 * @author Noah Godel (noah.godel@hefr.ch)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonPropertyOrder({LTConfiguration.BASE_URL, LTConfiguration.SUB_SITE, LTConfiguration.HTTP_METHOD, LTConfiguration.REQUEST_BODY, LTConfiguration.CONTENT_TYPE, LTConfiguration.API_KEY, LTConfiguration.AUTH_TYPE, LTConfiguration.NUM_OF_REQUESTS})
public class LTConfiguration {
    
    /**
     * JSON property name for the base url
     */
    static final String BASE_URL = "baseURL";
    
    /**
     * JSON property name for the sub site
     */
    static final String SUB_SITE = "subSite";
    
    /**
     * JSON property name for the http method
     */
    static final String HTTP_METHOD = "httpMethod";
    
    /**
     * JSON property name for the request body
     */
    static final String REQUEST_BODY = "requestBody";
    
    /**
     * JSON property name for the content type
     */
    static final String CONTENT_TYPE = "contentType";
    
    /**
     * JSON property name for the api key
     */
    static final String API_KEY = "apiKey";
    
    /**
     * JSON property name for the authentication type
     */
    static final String AUTH_TYPE = "authType";
    
    /**
     * JSON property name for the number of requests
     */
    static final String NUM_OF_REQUESTS = "numberOfRequests";
    
    /**
     * The base url of the API to test
     */
    public @NotBlank String baseURL;
    
    /**
     * The sub site to test
     */
    public String subSite;
    
    /**
     * The http method of the request
     */
    public @NotBlank String httpMethod;
    
    /**
     * The request body
     */
    public String requestBody;
    
    /**
     * The content-type of the request body
     */
    public String contentType;
    
    /**
     * The API key for authentication
     */
    public String apiKey;
    
    /**
     * The authentication type
     */
    public String authType;
    
    /**
     * The number of requests the test will send
     */
    public int numberOfRequests;
    
    /**
     * Returns a new {@code LTConfiguration} with its properties initialized from parameters (used by Jackson for the request payload deserialization).
     *
     * @param baseURL the base url of the API to test
     * @param subSite the sub site to test
     * @param httpMethod the http method of the request
     * @param requestBody the request body
     * @param contentType the content-type of the request body
     * @param apiKey the API key for authentication
     * @param authType the authentication type
     * @param numberOfRequests the number of requests the test will send
     */
    public LTConfiguration(@JsonProperty(BASE_URL) String baseURL, @JsonProperty(SUB_SITE) String subSite, @JsonProperty(HTTP_METHOD) String httpMethod, @JsonProperty(REQUEST_BODY) String requestBody, @JsonProperty(CONTENT_TYPE) String contentType, @JsonProperty(API_KEY) String apiKey, @JsonProperty(AUTH_TYPE) String authType, @JsonProperty(NUM_OF_REQUESTS) int numberOfRequests) {
        this.baseURL = baseURL;
        this.subSite = subSite;
        this.httpMethod = httpMethod;
        this.requestBody = requestBody;
        this.contentType = contentType;
        this.apiKey = apiKey;
        this.authType = authType;
        this.numberOfRequests = numberOfRequests;
    }
}
