package com.mhdeveloper.compas.controller.managements;

import androidx.collection.ArrayMap;
import com.mhdeveloper.compas.controller.dao.FirestoreController;
import com.mhdeveloper.compas.controller.notifications.NtRechargeAdapterUser;
import com.mhdeveloper.compas.model.*;

import java.util.ArrayList;

public class MngRooms {
    private static ArrayList<Room> roomCharged = new ArrayList<>();
    private static Room roomSelected = null;
    private static User user;
    private static ArrayList<Notification> notifications = new ArrayList<>();
    private static Permission permissions = null;
    private static boolean haveRooms;
    private static ArrayMap<String,ArrayList<Ticket>> mapTickets = new ArrayMap<>();
    private static void setRoom(int position) {
        roomSelected = roomCharged.get(position);
    }
    private static void setRoomFirstTime(){
        if (roomCharged.size()>0) {
            roomSelected = roomCharged.get(0);
        }else{
            haveRooms = false;
        }        
    }
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
    public static void rechargeRoomSelected(){
        if (getRoomSelected() != null){
            for (Room room:getRoomCharged()) {
                if (room.getUid().equals(getRoomSelected().getUid())){
                    setRoomSelected(room);
                    chargePermissions();
                    new NtRechargeAdapterUser();
                }
            }
        }
    }
    public static void selectFirstInit(){
        if (getRoomSelected() == null){
            if (MngRooms.getRoomCharged().size()>0){
                setRoomSelected(MngRooms.getRoomCharged().get(0));
                chargePermissions();
            }
        }
    }
}