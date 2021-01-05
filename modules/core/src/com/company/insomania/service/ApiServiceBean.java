package com.company.insomania.service;

import com.company.insomania.api.RequestResult;
import com.company.insomania.service.Serv;
import com.company.insomania.entity.Settings;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.TimeSource;
import jdk.internal.jline.internal.Urls;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service(ApiService.NAME)
public class ApiServiceBean implements ApiService {
    private static final String USER_AGENT = "Mozilla/5.0";
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;
    @Inject
    private TimeSource timeSource;


    @Override
    public RequestResult sendToProvider() throws NoSuchAlgorithmException, KeyManagementException {
        RequestResult requestResult = new RequestResult();
        try {
            URL targetUrl = null;
            String USERNAME = "";
            String PASSWORD = "";
            String grant_type = "password";
            HttpHeaders httpHeaders = new HttpHeaders();

            List<Settings> settings = getSettings();
            for (Settings setting : settings) {
                if (setting.getName().equals("url")) targetUrl = new URL(setting.getValue());
                if (setting.getName().equals("USERNAME")) USERNAME = setting.getValue();
                if (setting.getName().equals("PASSWORD")) PASSWORD = setting.getValue();
            }
            String basicAuth = "Basic " + Base64.getEncoder().encodeToString(
                    (USERNAME+":"+PASSWORD).getBytes(StandardCharsets.UTF_8));




            HttpURLConnection httpConnection =
                    (HttpURLConnection) Objects.requireNonNull(targetUrl).openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Authorization: " + "Basic ", basicAuth);


            System.out.println(httpConnection);

            //// https://stackoverflow.com/questions/3283234/http-basic-authentication-in-java-using-httpclient

            OutputStream outputStream = httpConnection.getOutputStream();
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
                String res = String.valueOf((new JSONObject(responseBuffer.readLine()).toString(4)));
                requestResult.setResponseCode(httpConnection.getResponseCode());
                requestResult.setResponse(res);
            }
            httpConnection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }



        return  requestResult;

    }


    @Override
    public String createrequest(){

        String carJson =
                "{ \"brand\" : \"Mercedes\", \"doors\" : 4 }";
        return carJson;


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

    public String auths(String userName, String password, String grant_type){

        String url = "/oauth/v2/token?"+userName+"&"+password+"&"+grant_type;

        return url;

    }

}