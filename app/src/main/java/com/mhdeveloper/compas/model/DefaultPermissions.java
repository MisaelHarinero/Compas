package com.mhdeveloper.compas.model;

public class DefaultPermissions {
    public final static Permission admin = new Permission("admin",true,true,true,true);
    public final static Permission normalUser = new Permission("writter",false,true,false,false);
    public final static Permission responsable = new Permission("reader",false,false,true,false);
}
