package com.company.insomania.web.screens.settings;

import com.haulmont.cuba.gui.screen.*;
import com.company.insomania.entity.Settings;

@UiController("insomania_Settings.edit")
@UiDescriptor("settings-edit.xml")
@EditedEntityContainer("settingsDc")
@LoadDataBeforeShow
public class SettingsEdit extends StandardEditor<Settings> {
}