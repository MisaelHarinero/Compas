package com.mhdeveloper.compas.controller.notifications;

import com.mhdeveloper.compas.controller.dao.FirestoreController;
import com.mhdeveloper.compas.controller.managements.MngRooms;
import com.mhdeveloper.compas.model.Permission;
import com.mhdeveloper.compas.model.Room;

/**
 * Clase que se encarga de recargar los eventos a las notificaciones
 * @author Misael Harinero
 */
public class NtRechargeListener implements INt {

    public NtRechargeListener() {
        action();
    }

    @Override
    public void action() {
        FirestoreController.clearListener();
        for (Room room:MngRooms.getRoomCharged()) {
            Permission permission = MngRooms.getPermisesForUserInRoom(room);
                if (permission!= null && permission.isReadTk()){
                    FirestoreController.getSnapshotForTickets(room.getUid());
                }
        }
    }
}
