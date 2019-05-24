package com.mhdeveloper.compas.model;

import com.google.firebase.Timestamp;

/**
 * Clase Modelo para los mensajes
 * @author  Misael Harinero
 */
public class Message {
    /**
     * tag del Usuario que manda el mensaje
     * */
    private String tagUser;
    /**
     * tag del ticket al que pertenece el  mensaje
     * */
    private String tagTicket;
    /**
     * Tipo del mensaje
     * */
    private String typeMessage;
    /**
     * Contenido de Texto del Mensaje
     * */
    private String text;
    /**
     * url de la foto en el Cloud
     * */
    private String uriPhoto;
    /**
     * Fecha del Mensaje
     * */
    private Timestamp date;
    public final static String TYPE_PHOTO = "pht";
    public final static String TYPE_TEXT = "txt";

    public Message() {
    }

    public Message(String tagUser, String tagTicket, String typeMessage, String text, String uriPhoto, Timestamp date) {
        this.tagUser = tagUser;
        this.tagTicket = tagTicket;
        this.typeMessage = typeMessage;
        this.text = text;
        this.uriPhoto = uriPhoto;
        this.date = date;
    }

    public String getTagUser() {
        return tagUser;
    }

    public void setTagUser(String tagUser) {
        this.tagUser = tagUser;
    }

    public String getTagTicket() {
        return tagTicket;
    }

    public void setTagTicket(String tagTicket) {
        this.tagTicket = tagTicket;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUriPhoto() {
        return uriPhoto;
    }

    public void setUriPhoto(String uriPhoto) {
        this.uriPhoto = uriPhoto;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
