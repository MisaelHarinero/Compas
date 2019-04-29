package com.mhdeveloper.compas.model;
/**
 * @author Misael Harinero
 * Clase que es modelo para guardar los datos de un permiso
 */
 public class Permission{
    private String name;
    private boolean adminUser;
    private boolean writeTk;
    private boolean readTk;
    private boolean modifyRoom;
    public Permission(String name, boolean adminUser, boolean writeTk, boolean readTk, boolean modifyRoom) {
        this.name = name;
        this.adminUser = adminUser;
        this.writeTk = writeTk;
        this.readTk = readTk;
        this.modifyRoom = modifyRoom;
    }

    public Permission() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdminUser() {
        return adminUser;
    }

    public void setAdminUser(boolean adminUser) {
        this.adminUser = adminUser;
    }

    public boolean isWriteTk() {
        return writeTk;
    }

    public void setWriteTk(boolean writeTk) {
        this.writeTk = writeTk;
    }

    public boolean isReadTk() {
        return readTk;
    }

    public void setReadTk(boolean readTk) {
        this.readTk = readTk;
    }

    public boolean isModifyRoom() {
        return modifyRoom;
    }

    public void setModifyRoom(boolean modifyRoom) {
        this.modifyRoom = modifyRoom;
    }
}