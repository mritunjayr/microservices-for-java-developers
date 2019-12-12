package com.mritun.hellospringboot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/api")
@ConfigurationProperties(prefix = "helloapp")
public class HelloRestController {
    private String saying;


    public void setSaying(String saying) {
        this.saying = saying;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello", produces = "text/plain")
    public String hello() {
        String hostname = null;
        try {
            hostname = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            hostname = "unknown";
        }
        return saying + " " + hostname;
    }
}
