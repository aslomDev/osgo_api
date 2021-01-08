package com.company.insomania.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;



public enum RequestType implements EnumClass<String> {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String id;

    RequestType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static RequestType fromId(String id) {
        for (RequestType at : RequestType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}