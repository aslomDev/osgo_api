package com.company.insomania.service;

import com.company.insomania.api.RequestResult;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


public interface ApiService {
    String NAME = "insomania_ApiService";

    RequestResult authorization()
            throws NoSuchAlgorithmException, KeyManagementException;

    RequestResult sendToProvider(String subUrl, String requestType, String param);


}