package com.mhdeveloper.compas.controller.managements;

import androidx.collection.ArrayMap;
import com.google.firebase.firestore.ListenerRegistration;
import com.mhdeveloper.compas.controller.dao.FirestoreController;
import com.mhdeveloper.compas.controller.notifications.NtRechargeAdapterUser;
import com.mhdeveloper.compas.model.*;

import java.util.ArrayList;

/**
 * @author Misael Harinero
 * Clase que nos permite gestionar los datos del Usuario
 */
public class MngRooms {
    /**
     * Lista de Rooms a las que pertenece el Usuario
     * */
    private static ArrayList<Room> roomCharged = new ArrayList<>();
    /**
     * Datos de la Room Seleccionada
     * */
    private static Room roomSelected = null;
    /**
     * Datos del Usuario que ha iniciado Sesion
     * */
    private static User user;
    /**
     * Lista con las notificaciones del Usuario
     * */
    private static ArrayList<Notification> notifications = new ArrayList<>();
    /**
     * Permisos en la sala seleccionada
     * */
    private static Permission permissions = null;
    private static boolean haveRooms;
    private static ArrayMap<String,ArrayList<Ticket>> mapTickets = new ArrayMap<>();
    private static void setRoom(int position) {
        roomSelected = roomCharged.get(position);
    }
    /**
     * Lista de eventos abiertos en firebase
     * */
    private static ArrayList<ListenerRegistration>registrations = new ArrayList<>();
    public static void setUser(User usr){
        user = usr;
    }
    public static ArrayList<Notification> getNotifications(){
        return notifications;
    }
    public static ArrayMap<String,ArrayList<Ticket>> getMapTikets(){
        return mapTickets;
    }

    public static void setRoomCharged(ArrayList<Room> roomCharged) {
        MngRooms.roomCharged = roomCharged;
    }

    public static ArrayList<Room> getRoomCharged() {
        return roomCharged;
    }

    public static Room getRoomSelected() {
        return roomSelected;
    }

    public static void setRoomSelected(Room roomSelected) {
        MngRooms.roomSelected = roomSelected;
    }

    public static User getUser() {
        return user;
    }

    public static void setNotifications(ArrayList<Notification> notifications) {
        MngRooms.notifications = notifications;
    }

    public static Permission getPermissions() {
        return permissions;
    }

    public static void setPermissions(Permission permissions) {
        MngRooms.permissions = permissions;
    }

    public static boolean isHaveRooms() {
        return haveRooms;
    }

    public static void setHaveRooms(boolean haveRooms) {
        MngRooms.haveRooms = haveRooms;
    }

    public static void chargePermissions(){
        for (Permission per: MngRooms.getRoomSelected().getPermissions()) {
            if (per.getName().equals(MngRooms.getRoomSelected().getPermissesUser().get(MngRooms.getUser().getTag()))){
                MngRooms.setPermissions(per);
            }
        }
    }

    public static ArrayMap<String, ArrayList<Ticket>> getMapTickets() {
        return mapTickets;
    }

    public static void setMapTickets(ArrayMap<String, ArrayList<Ticket>> mapTickets) {
        MngRooms.mapTickets = mapTickets;
    }
    public static void chargeRooms(){
        FirestoreController.instanceFirestore();
        FirestoreController.chargeRooms(user.getTag());
        FirestoreController.chargeNotifications(user.getTag());
    }
    public static void clear(){
        roomCharged.clear();
        roomSelected = null;
        user = null;
        permissions = null;
        notifications.clear();
        closeAllEvents();
        registrations.clear();
        FirestoreController.clearListener();
    }
    public static void rechargeRoomSelected(){
        boolean founded = false;
        if (getRoomSelected() != null && roomCharged.size()>0){
            for (Room room:getRoomCharged()) {
                if (room.getUid().equals(getRoomSelected().getUid())){
                    setRoomSelected(room);
                    chargePermissions();
                    new NtRechargeAdapterUser();
                    founded = true;
                }
            }
        }
        if (!founded){
            setRoomSelected(null);
            permissions = null;
        }
    }
    public static void selectFirstInit(){
        if (getRoomSelected() == null){
            if (MngRooms.getRoomCharged().size()>0 && MngRooms.getRoomCharged().get(0) != null){
                setRoomSelected(MngRooms.getRoomCharged().get(0));
                chargePermissions();
            }
        }
    }
    public static void closeAllEvents(){
        for (ListenerRegistration listener:
             registrations) {
            listener.remove();
        }
        registrations.clear();
    }

    public static ArrayList<ListenerRegistration> getRegistrations() {
        return registrations;
    }

    public static void setRegistrations(ArrayList<ListenerRegistration> registrations) {
        MngRooms.registrations = registrations;
    }
    public static Permission getPermisesForUserInRoom(Room room){
        for (Permission permission: room.getPermissions()
             ) {
                if (permission.getName().equals(room.getPermissesUser().get(MngRooms.getUser().getTag()))){
                    return permission;
                }
        }
        return null;
    }
}