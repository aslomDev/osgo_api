package com.company.insomania.web.screens.method;

import com.haulmont.cuba.gui.screen.*;
import com.company.insomania.entity.Method;

@UiController("insomania_Method.browse")
@UiDescriptor("method-browse.xml")
@LookupComponent("methodsTable")
@LoadDataBeforeShow
public class MethodBrowse extends StandardLookup<Method> {
}