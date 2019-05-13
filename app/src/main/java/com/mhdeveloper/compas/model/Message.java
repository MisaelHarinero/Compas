package com.mhdeveloper.compas.model;

import com.google.firebase.Timestamp;

public class Message {
    private String tagUser;
    private String tagTicket;
    private String typeMessage;
    private String text;
    private String uriPhoto;
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
