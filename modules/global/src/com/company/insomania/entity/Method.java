package com.company.insomania.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.HasUuid;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NamePattern("%s|name")
@Table(name = "INSOMANIA_METHOD")
@Entity(name = "insomania_Method")
public class Method extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -3261533585848777703L;

    @Lookup(type = LookupType.DROPDOWN, actions = "lookup")
    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MODULE_ID")
    protected Module module;

    @NotNull
    @Column(name = "NAME", nullable = false, unique = true, length = 100)
    protected String name;

    @Column(name = "DESCRIPTION", length = 100)
    protected String description;

    @NotNull
    @Column(name = "REQUEST_TYPE", nullable = false)
    protected String requestType;

    @NotNull
    @Column(name = "REQUEST_URL", nullable = false)
    protected String requestUrl;

    @NotNull
    @Lob
    @Column(name = "REQUEST_CONTENT", nullable = false)
    protected String request_content;


    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestType getRequestType() {
        return requestType == null ? null : RequestType.fromId(requestType);
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType == null ? null : requestType.getId();
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequest_content() {
        return request_content;
    }

    public void setRequest_content(String request_content) {
        this.request_content = request_content;
    }

}