package com.mhdeveloper.compas.model;


import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Comparator;

public class Ticket  implements Comparator<Ticket> {
    private String tag;
    private String roomTag;
    private int importance;
    private String uriPhoto;
    private String title;
    private String description;
    private String tagUserEmmiter;
    private String tagUserAttended;
    private Timestamp date;
    private ArrayList<Object>mssg;

    public Ticket() {
    }

    public Ticket(String tag,String roomTag, int importance, String title, String description, String tagUserEmmiter, Timestamp date) {
        this.tag = tag;
        this.roomTag = roomTag;
        this.importance = importance;
        this.uriPhoto = null;
        this.title = title;
        this.description = description;
        this.tagUserEmmiter = tagUserEmmiter;
        this.date = date;
        this.mssg = new ArrayList<>();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRoomTag() {
        return roomTag;
    }

    public void setRoomTag(String roomTag) {
        this.roomTag = roomTag;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getUriPhoto() {
        return uriPhoto;
    }

    public void setUriPhoto(String uriPhoto) {
        this.uriPhoto = uriPhoto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagUserEmmiter() {
        return tagUserEmmiter;
    }

    public void setTagUserEmmiter(String tagUserEmmiter) {
        this.tagUserEmmiter = tagUserEmmiter;
    }

    public String getTagUserAttended() {
        return tagUserAttended;
    }

    public void setTagUserAttended(String tagUserAttended) {
        this.tagUserAttended = tagUserAttended;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public ArrayList<Object> getMssg() {
        return mssg;
    }

    public void setMssg(ArrayList<Object> mssg) {
        this.mssg = mssg;
    }

    @Override
    public int compare(Ticket o1, Ticket o2) {
        return o1.importance-o2.importance;
    }
}
