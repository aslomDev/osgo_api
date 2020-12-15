package com.company.insomania.web.screens.settings;

import com.haulmont.cuba.gui.screen.*;
import com.company.insomania.entity.Settings;

@UiController("insomania_Settings.browse")
@UiDescriptor("settings-browse.xml")
@LookupComponent("settingsesTable")
@LoadDataBeforeShow
public class SettingsBrowse extends StandardLookup<Settings> {
}