package com.mhdeveloper.compas.controller.dao;

public class UtilitiesClass {
    public static String generateTag(){
        String cadena =  "#";
        for (int i = 0; i < 4; i++) {
            cadena+=Integer.toString((int)Math.floor(Math.random()*10));
        }
        return  cadena;
    }
}
