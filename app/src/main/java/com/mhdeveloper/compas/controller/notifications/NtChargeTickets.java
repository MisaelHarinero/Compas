package com.mhdeveloper.compas.controller.notifications;

import com.mhdeveloper.compas.controller.dao.FirestoreController;
import com.mhdeveloper.compas.controller.managements.MngRooms;
import com.mhdeveloper.compas.model.Room;

public class NtChargeTickets implements INt{

    @Override
    public void action(){
        if (MngRooms.getRoomCharged().size()>0) {
            for (Room room: MngRooms.getRoomCharged()) {
                if(!MngRooms.getMapTickets().containsKey(room.getUid())){
                    FirestoreController.chargeTicketsByRoom(room.getUid());
                }else{

                }
            }
        }

    }

}
