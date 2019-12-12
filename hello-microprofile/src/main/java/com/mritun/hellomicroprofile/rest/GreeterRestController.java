package com.mritun.hellomicroprofile.rest;

import com.mritun.hellomicroprofile.BackendDTO;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class GreeterRestController {

    @Inject
    @ConfigProperty(name="greeting.saying", defaultValue = "Hello")
    private String saying;
    @Inject
    @ConfigProperty(name = "greeting.backendServiceHost",
            defaultValue = "localhost")
    private String backendServiceHost;
    @Inject
    @ConfigProperty(name = "greeting.backendServicePort",
            defaultValue = "8080")
    private int backedServicePort;

    @GET
    @Produces("text/plain")
    @Path("/greeting")
    public String greeting(){
        String backedServiceUrl=String.format("http://%s:%d",backendServiceHost,backedServicePort);
        System.out.println("Sending to: "+backedServiceUrl);
        Client client= ClientBuilder.newClient();
        BackendDTO response=client.target(backedServiceUrl)
                .path("api")
                .path("backend")
                .queryParam("greeting",saying)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(BackendDTO.class);

        return response.getGreeting()+" at host:"+response.getIp();
    }

}
