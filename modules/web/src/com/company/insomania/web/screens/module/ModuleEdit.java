package com.company.insomania.web.screens.module;

import com.haulmont.cuba.gui.screen.*;
import com.company.insomania.entity.Module;

@UiController("insomania_Module.edit")
@UiDescriptor("module-edit.xml")
@EditedEntityContainer("moduleDc")
@LoadDataBeforeShow
public class ModuleEdit extends StandardEditor<Module> {
}