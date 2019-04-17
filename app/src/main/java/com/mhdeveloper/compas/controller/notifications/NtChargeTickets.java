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
        boolean founded ;
        int position ;
        for (String key : MngRooms.getMapTickets().keySet()) {
            founded = false;
            position = -1;
            for (int i = 0; i <MngRooms.getRoomCharged().size() ; i++) {
                if (key.equals(MngRooms.getRoomCharged().get(i).getUid())) {
                    founded = true;
                    position = i;
                }
            }
            if (!founded) {

                MngRooms.getRoomCharged().remove(position);
            }
        }

    }

}
