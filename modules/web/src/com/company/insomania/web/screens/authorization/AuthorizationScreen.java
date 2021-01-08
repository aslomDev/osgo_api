package com.company.insomania.web.screens.authorization;

import com.company.insomania.api.RequestResult;
import com.company.insomania.service.ApiService;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.SourceCodeEditor;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@UiController("insomania_AuthorizationScreen")
@UiDescriptor("authorization-screen.xml")
public class AuthorizationScreen extends Screen {
    @Inject
    private ApiService apiService;
    @Inject
    private TextField<String> responseCodeField;
    @Inject
    private SourceCodeEditor responseField;

    @Subscribe("auth")
    public void onAuth(Action.ActionPerformedEvent event) throws KeyManagementException, NoSuchAlgorithmException {
        responseCodeField.setValue(null);
        responseField.setValue(null);
        RequestResult rr = apiService.sendToProvider();
        if (rr.getResponseCode() != null && rr.getResponse() != null){
            responseCodeField.setValue(rr.getResponseCode().toString());
            responseField.setValue(rr.getResponse());
        }

    }


    @Subscribe("close")
    public void onClose(Action.ActionPerformedEvent event) {
        close(Screen.WINDOW_CLOSE_ACTION);
    }

}