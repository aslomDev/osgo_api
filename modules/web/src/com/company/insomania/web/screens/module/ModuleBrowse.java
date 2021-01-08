package com.company.insomania.web.screens.module;

import com.haulmont.cuba.gui.screen.*;
import com.company.insomania.entity.Module;

@UiController("insomania_Module.browse")
@UiDescriptor("module-browse.xml")
@LookupComponent("modulesTable")
@LoadDataBeforeShow
public class ModuleBrowse extends StandardLookup<Module> {
}