package com.company.insomania.config;


import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.defaults.Default;

public interface TokenConfig extends Config {

    @Property("osgo.token")
    @Default("")
    String getToken();

    void setToken(String token);


}
