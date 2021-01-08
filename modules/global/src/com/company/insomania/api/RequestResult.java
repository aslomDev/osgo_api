package com.company.insomania.api;

import java.io.Serializable;

public class RequestResult implements Serializable {
    private Integer responseCode;
    private String response;
    private long responseLoadTime;

    public long getResponseLoadTime() {
        return responseLoadTime;
    }

    public void setResponseLoadTime(long responseLoadTime) {
        this.responseLoadTime = responseLoadTime;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
