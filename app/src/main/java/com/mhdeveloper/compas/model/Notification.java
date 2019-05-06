package com.mhdeveloper.compas.model;

public class Notification {
    private String tagUser;
    private String roomUid;
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
