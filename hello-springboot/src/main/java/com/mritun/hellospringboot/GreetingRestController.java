package com.mritun.hellospringboot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@ConfigurationProperties(prefix = "greeting")
public class GreetingRestController {
    private String saying;
    private String backendServiceHost;
    private String backendServicePort;
    private RestTemplate template = new RestTemplate();


    @RequestMapping(method = RequestMethod.GET, value = "/greeting", produces = "text/plain")
    public String greeting() {

        String backendServiceUrl = String.format("http://%s:%s/api/backend?greeting={greeting}", backendServiceHost, backendServicePort);
        System.out.println("Sending to : " + backendServiceUrl);
        BackendDTO response = template.getForObject(backendServiceUrl, BackendDTO.class, saying);


        return response.getGreeting() + " at host:  " + response.getIp();
    }

    public void setBackendServiceHost(String backendServiceHost) {
        this.backendServiceHost = backendServiceHost;
    }

    public void setBackendServicePort(String backendServicePort) {
        this.backendServicePort = backendServicePort;
    }

    public void setSaying(String saying) {
        this.saying = saying;
    }
}
