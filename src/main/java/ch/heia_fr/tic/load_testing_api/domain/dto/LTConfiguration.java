package ch.heia_fr.tic.load_testing_api.domain.dto;

import javax.validation.constraints.NotBlank;

public class LTConfiguration {
    
    public @NotBlank String baseURL;
    
    public String subSite;
    
    public @NotBlank String httpMethod;
    
    public String requestBody;
    
    public String contentType;
    
    public String apiKey;
    
    public String authType;
    
    public int numberOfRequest;
    
    public LTConfiguration(String baseURL, String subSite, String httpMethod, String requestBody, String contentType, String apiKey, String authType, int numberOfRequest) {
        this.baseURL = baseURL;
        this.subSite = subSite;
        this.httpMethod = httpMethod;
        this.requestBody = requestBody;
        this.contentType = contentType;
        this.apiKey = apiKey;
        this.authType = authType;
        this.numberOfRequest = numberOfRequest;
    }
}
