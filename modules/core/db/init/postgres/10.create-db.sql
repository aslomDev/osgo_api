-- begin INSOMANIA_TEST
create table INSOMANIA_TEST (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    primary key (ID)
)^
-- end INSOMANIA_TEST
-- begin INSOMANIA_SETTINGS
create table INSOMANIA_SETTINGS (
    ID integer,
    --
    NAME varchar(255) not null,
    VALUE_ text,
    --
    primary key (ID)
)^
-- end INSOMANIA_SETTINGS
