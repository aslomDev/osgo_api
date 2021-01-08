package com.company.insomania.web.screens.method;

import com.haulmont.cuba.gui.screen.*;
import com.company.insomania.entity.Method;

@UiController("insomania_Method.edit")
@UiDescriptor("method-edit.xml")
@EditedEntityContainer("methodDc")
@LoadDataBeforeShow
public class MethodEdit extends StandardEditor<Method> {
}