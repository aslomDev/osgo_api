package com.company.insomania.service;

import com.company.insomania.api.RequestResult;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


public interface ApiService {
    String NAME = "insomania_ApiService";

    RequestResult sendToProvider()
            throws NoSuchAlgorithmException, KeyManagementException;

    String createrequest();

}