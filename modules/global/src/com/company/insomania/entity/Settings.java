package com.company.insomania.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "INSOMANIA_SETTINGS")
@Entity(name = "insomania_Settings")
public class Settings extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 3539551831209089462L;

    @NotNull
    @Column(name = "NAME", nullable = false, unique = true)
    protected String name;

    @Lob
    @Column(name = "VALUE_")
    protected String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}