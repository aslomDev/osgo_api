package com.company.insomania.web.screens;

import com.company.insomania.api.RequestResult;
import com.company.insomania.config.TokenConfig;
import com.company.insomania.entity.Method;
import com.company.insomania.entity.Module;
import com.company.insomania.service.ApiService;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@UiController("insomania_Api")
@UiDescriptor("Api.xml")
@EditedEntityContainer("ApiDc1")
@LoadDataBeforeShow()
public class Api extends Screen {

    @Inject
    private TextField<String> responseCodeField;
    @Inject
    private SourceCodeEditor responseField;
    @Inject
    private ApiService apiService;
    @Inject
    private SourceCodeEditor requestField;
    @Inject
    private CollectionLoader<Method> methodsLc;
    @Inject
    private LookupPickerField<Method> methodField;
    @Inject
    private CollectionLoader<Module> modulesDl;
    @Inject
    private CollectionContainer<Method> methodsDc;
    @Inject
    private NotificationFacet notification;
    @Inject
    private TextField<String> responseLoadTime;


    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        methodsLc.setParameter("module", null);
        getScreenData().loadAll();
    }


    @Subscribe("moduleField")
    public void onModuleFieldValueChange(HasValue.ValueChangeEvent<Module> event) {
        if (event.getValue() != null) {
            methodsLc.setParameter("module", event.getValue());
        } else {
            methodsLc.setParameter("module", null);
        }
        methodsLc.load();
        methodField.setValue(null);
    }



    @Subscribe("sendRequest")
    public void onSendRequest(Action.ActionPerformedEvent event)
            throws KeyManagementException, NoSuchAlgorithmException {
        responseCodeField.setValue(null);
        responseField.setValue(null);
        RequestResult rr = apiService.createrequest(modulesDl.getContainer().getItem().getSubUrl()+methodsLc.getContainer().getItem().getRequestUrl(), String.valueOf(methodsLc.getContainer().getItem().getRequestType()), requestField.getValue());
        if (rr.getResponseCode()!=null && rr.getResponse()!=null) {
            responseCodeField.setValue(rr.getResponseCode().toString());
            responseField.setValue(rr.getResponse());
            responseLoadTime.setValue(String.valueOf(rr.getResponseLoadTime() + " milliseconds"));

        }
    }

    @Subscribe("generateRequest")
    public void onGenerateRequest(Action.ActionPerformedEvent event) {

        if (methodsDc.getItemOrNull()!=null) {

            requestField.setValue(methodsLc.getContainer().getItem().getRequest_content());
        } else
            notification.show();



    }


    @Subscribe("close")
    public void onClose(Action.ActionPerformedEvent event) {
        close(Screen.WINDOW_CLOSE_ACTION);
    }



}