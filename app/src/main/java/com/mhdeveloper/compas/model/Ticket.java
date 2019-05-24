package com.mhdeveloper.compas.model;


import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Clase Modelo para guardar los dastos de los tickets
 * @author Misael Harinero
 */
public class Ticket  implements Comparator<Ticket> {
    /**
     * Tag del Ticket
     * */
    private String tag;
    /**
     * Tag de la room a la que pertenece el ticket
     * */
    private String roomTag;
    /**
     * Importancia del ticked
     * */
    private int importance;
    /**
     * Si ha sido finalizaado el ticket
     * */
    private boolean finished;
    /**
     * Url de la foto en el caso de que la tenga
     * */
    private String uriPhoto;
    /**
     * Titulo del Ticket
     * */
    private String title;
    /**
     * Descripcion del Ticket
     * */
    private String description;
    /**
     * Tag del Emisor
     * */
    private String tagUserEmmiter;
    /**
     * Tag del Receptor
     * */
    private String tagUserAttended;
    /**
     * Fecha de Creación
     * */
    private Timestamp date;

    public Ticket() {
    }

    public Ticket(String tag,String roomTag, int importance, String title, String description, String tagUserEmmiter, Timestamp date) {
        this.tag = tag;
        this.roomTag = roomTag;
        this.importance = importance;
        this.finished = false;
        this.uriPhoto = null;
        this.title = title;
        this.description = description;
        this.tagUserEmmiter = tagUserEmmiter;
        this.date = date;
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

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public int compare(Ticket o1, Ticket o2) {
        return o1.importance-o2.importance;
    }
}
