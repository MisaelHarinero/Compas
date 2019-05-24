package com.mhdeveloper.compas.model;


import com.google.firebase.Timestamp;

/**
 * @author Misael Harinero 
 * Clase User -- Modelo que guarda los datos de nuestro user
 */
public class User {
    /**
     * Tag de nuestro Usuario
     * */
    private String tag;
    private String alias;
    /**
     * Nombre del usuario
     * */
    private String name;
    /**
     * Apellido del usuario
     * */
    private String surname;
    /**
     * Email del usuario
     * */
    private String email;
    /**
     * Fecha de nacimiento del usuario
     * */
    private Timestamp dateBorn;
    private boolean showAlias;
    /**
     * Ruta de la imagen del usuario en la base de datos
     * */
    private String imageRoute;

    public User(){

    }

    public User(String tag, String alias, String name, String surname, String email, Timestamp dateBorn, boolean showAlias, String imageRoute) {
        this.tag = tag;
        this.alias = alias;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dateBorn = dateBorn;
        this.showAlias = showAlias;
        this.imageRoute = imageRoute;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(Timestamp dateBorn) {
        this.dateBorn = dateBorn;
    }

    public boolean isShowAlias() {
        return showAlias;
    }

    public void setShowAlias(boolean showAlias) {
        this.showAlias = showAlias;
    }

    public String getImageRoute() {
        return imageRoute;
    }

    public void setImageRoute(String imageRoute) {
        this.imageRoute = imageRoute;
    }
}