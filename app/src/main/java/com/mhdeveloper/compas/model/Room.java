package com.mhdeveloper.compas.model;

import androidx.collection.ArrayMap;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Misael Harinero
 * Clase que es el modelo para guardar los datos de una sala. 
 * 
 */
public class Room {
    private String uid;
    private String name;
    private String urlImage;
    private ArrayList<Permission> permissions;
    /**
     * Map de permisos : 
     * key : user.tag
     * value: permission.name
     */
    private HashMap<String,String> permissesUser;
    /**
     * Arraylist de miembros : 
     * value : user.tag
     */
    private ArrayList<String> members;
    private Timestamp createDate;

    public Room(String uid, String name, String urlImage, ArrayList<Permission> permissions, HashMap<String, String> permissesUser) {
        this.uid = uid;
        this.name = name;
        this.urlImage = urlImage;
        this.permissions = permissions;
        this.permissesUser = permissesUser;
    }
    public Room(){

    }
    /**
     *Constructor que sera el que usaremos para crear la Room por primera vez 
     */

    public Room(String uid,String name,String userAdminTag){
        this.uid = uid;
        this.name = name;
        this.permissions = new ArrayList<>();
        this.permissions.add(DefaultPermissions.admin);
        this.permissions.add(DefaultPermissions.normalUser);
        this.permissions.add(DefaultPermissions.responsable);
        this.permissesUser = new HashMap<>();
        this.permissesUser.put(userAdminTag,DefaultPermissions.admin.getName());
        this.members = new ArrayList<>();
        this.members.add(userAdminTag);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public ArrayList<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(ArrayList<Permission> permissions) {
        this.permissions = permissions;
    }

    public HashMap<String, String> getPermissesUser() {
        return permissesUser;
    }

    public void setPermissesUser(HashMap<String, String> permissesUser) {
        this.permissesUser = permissesUser;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}