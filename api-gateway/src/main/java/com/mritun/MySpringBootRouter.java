package com.mritun;



import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
@ConfigurationProperties(prefix = "gateway")
public class MySpringBootRouter extends RouteBuilder {

    private String springbootsvcurl,microprofilesvcurl;
    private static final String REST_ENDPOINT="http:%s/api/greeting?httpClient.connectTimeout=1000"
            +"&bridgeEndpoint=true"
            +"&copyHeaders=true"
            +"&connectionClose=true";



    @Override
    public void configure() {
        from("direct:microprofile").streamCaching()
                .toF(REST_ENDPOINT,microprofilesvcurl)
        .log("Response from MicroProfile microserivce:${body}")
        .convertBodyTo(String.class)
        .end();

        from("direct:springboot").streamCaching()
                .toF(REST_ENDPOINT,springbootsvcurl)
                .log("Response from Spring boot microservice: ${body}")
                .convertBodyTo(String.class)
                .end();

        rest().get("/gateway").enableCORS(true)
                .route()
                .multicast(AggregationStrategies.flexible().accumulateInCollection(ArrayList.class))
                .parallelProcessing()
                .to("direct:microprofile")
                .to("direct:springboot")
                .end()
                .marshal().json(JsonLibrary.Jackson).
                convertBodyTo(String.class)
                .endRest();
    }


    public void setSpringbootsvcurl(String springbootsvcurl) {
        this.springbootsvcurl = springbootsvcurl;
    }

    public void setMicroprofilesvcurl(String microprofilesvcurl) {
        this.microprofilesvcurl = microprofilesvcurl;
    }
}
