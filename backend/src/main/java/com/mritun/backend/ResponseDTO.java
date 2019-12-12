package com.mritun.backend;

public class ResponseDTO {
    private String greeting;
    private long time;
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getGreeting() {
        return greeting;

    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
