package com.company.insomania.service;

import com.company.insomania.api.RequestResult;
import com.company.insomania.config.TokenConfig;
import com.company.insomania.service.Serv;
import com.company.insomania.entity.Settings;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.TimeSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service(ApiService.NAME)
public class ApiServiceBean implements ApiService {

    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;
    @Inject
    private TimeSource timeSource;
    @Inject private TokenConfig tokenConfig;


    @Override
    public RequestResult sendToProvider() throws NoSuchAlgorithmException, KeyManagementException {
        RequestResult requestResult = new RequestResult();
        try {
            URL targetUrl = null;
            String user = "";
            String password = "";
            List<Settings> settings = getSettings();
            for (Settings setting : settings) {
                if (setting.getName().equals("AUTH")) targetUrl = new URL(setting.getValue());
                if (setting.getName().equals("USERNAME")) user = setting.getValue();
                if (setting.getName().equals("PASSWORD")) password = setting.getValue();
            }
            String basicAuth = "Basic " + Base64.getEncoder().encodeToString(
                    (user + ":" + password).getBytes(StandardCharsets.UTF_8));
            HttpURLConnection httpConnection =
                    (HttpURLConnection) Objects.requireNonNull(targetUrl).openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Authorization", basicAuth);

            if (httpConnection.getResponseCode() != 200) {
                InputStreamReader inputStreamReader = new InputStreamReader((httpConnection.getErrorStream()));
                BufferedReader responseBuffer = new BufferedReader(inputStreamReader);
                StringBuilder res = new StringBuilder();
                String output;
                while ((output = responseBuffer.readLine()) != null) {
                    res.append(output).append("\n");
                    System.out.println(output);
                }
                requestResult.setResponseCode(httpConnection.getResponseCode());
                requestResult.setResponse(res.toString());
            } else {
                InputStreamReader inputStreamReader = new InputStreamReader((httpConnection.getInputStream()));
                BufferedReader responseBuffer = new BufferedReader(inputStreamReader);
                String res = String.valueOf((new JSONObject(responseBuffer.readLine()).toString(4)));
                requestResult.setResponseCode(httpConnection.getResponseCode());
                requestResult.setResponse(res);

                // получить токен
                String token = res.substring(68,154);

                // set на конфиг токен
                tokenConfig.setToken(token);
            }
            httpConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestResult;
    }


    @Override
    public RequestResult createrequest(String subUrl, String requestType, String param) {

        RequestResult requestResult = new RequestResult();
        try {
            URL targetUrl = null;
            List<Settings> settings = getSettings();
            for (Settings setting : settings) {
                if (setting.getName().equals("URL")) targetUrl = new URL(setting.getValue());
            }
            URI subUri = targetUrl.toURI();
            subUri = subUri.resolve(subUrl);
            targetUrl = subUri.toURL();
            HttpURLConnection httpConnection =
                    (HttpURLConnection) Objects.requireNonNull(targetUrl).openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod(requestType);
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Authorization", "Bearer " + tokenConfig.getToken());
            System.out.println("URL " + httpConnection.getURL());


            JSONObject jo = new JSONObject(param);
            param = jo.toString(4);
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(param.getBytes());
            outputStream.flush();


            if (httpConnection.getResponseCode() != 200) {
                InputStreamReader inputStreamReader = new InputStreamReader((httpConnection.getErrorStream()));
                BufferedReader responseBuffer = new BufferedReader(inputStreamReader);
                StringBuilder res = new StringBuilder();
                String output;
                while ((output = responseBuffer.readLine()) != null) {
                    res.append(output).append("\n");
                    System.out.println(output);
                }
                requestResult.setResponseCode(httpConnection.getResponseCode());
                requestResult.setResponse(res.toString());
            } else {
                InputStreamReader inputStreamReader = new InputStreamReader((httpConnection.getInputStream()));
                BufferedReader responseBuffer = new BufferedReader(inputStreamReader);
                String res = String.valueOf((new JSONObject(responseBuffer.readLine()).toString(10)));
                requestResult.setResponseCode(httpConnection.getResponseCode());
                requestResult.setResponse(res);
            }
            httpConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        return requestResult;

    }






    /**
     * Получение Настроек из базы
     * @return Настройки
     */
    private List<Settings> getSettings() {
        List<Settings> settings;
        Transaction tx = persistence.createTransaction();
        try {
            TypedQuery<Settings> queryA = persistence.getEntityManager().createQuery(
                    "select s from insomania_Settings s ", Settings.class);
            settings = queryA.getResultList();
            tx.commit();
        } finally {
            tx.end();
        }
        return settings;
    }


}