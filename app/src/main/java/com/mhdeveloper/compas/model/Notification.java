package com.mhdeveloper.compas.model;


/**
 * Clase Notificacion para invitar al usuario a otra sala
 * @author Misael Harinero
 */
public class Notification {
    /**
     * Tag del usuario que se quiere invitar
     * */
    private String tagUser;
    /**
     * Tag de la Sala a la que se le quiere añadir
     * */
    private String roomUid;
    /**
     * Permisos que tendra el usuario  si acepta la Invitacion
     * */
    private String permissions;

    public Notification() {
    }

    public Notification(String tagUser, String roomUid, String permissions) {
        this.tagUser = tagUser;
        this.roomUid = roomUid;
        this.permissions = permissions;
    }

    public String getTagUser() {
        return tagUser;
    }

    public void setTagUser(String tagUser) {
        this.tagUser = tagUser;
    }

    public String getRoomUid() {
        return roomUid;
    }

    public void setRoomUid(String roomUid) {
        this.roomUid = roomUid;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
