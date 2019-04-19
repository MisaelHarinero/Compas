package com.mhdeveloper.compas.controller.notifications;

import com.mhdeveloper.compas.controller.dao.FirestoreController;
import com.mhdeveloper.compas.model.Room;

public class NtCreationRoom implements INt {

    private Room room;

    public NtCreationRoom() {
    }

    public NtCreationRoom( Room room) {
        this.room = room;
    }



    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public void action() {
        FirestoreController.saveRoom(this.room);
    }
}
