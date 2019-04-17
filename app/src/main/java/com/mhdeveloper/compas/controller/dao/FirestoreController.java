package com.mhdeveloper.compas.controller.dao;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mhdeveloper.compas.controller.managements.MngRooms;
import com.mhdeveloper.compas.controller.notifications.INt;
import com.mhdeveloper.compas.controller.notifications.NtChargeTickets;
import com.mhdeveloper.compas.controller.notifications.NtRechargeAdapter;
import com.mhdeveloper.compas.model.Room;
import com.mhdeveloper.compas.model.Ticket;
import com.mhdeveloper.compas.model.User;

import java.util.ArrayList;

public class FirestoreController{
    public static FirebaseFirestore db  = null;

    public static void instanceFirestore() {
            db = FirebaseFirestore.getInstance();
    }
    public static void dataUser(String userUID, final INt event) {
        db.collection(DatabaseStrings.COLLECTION_USERS).document(userUID).get().addOnSuccessListener(
        new OnSuccessListener<DocumentSnapshot>(){
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot){
                   MngRooms.setUser(documentSnapshot.toObject(User.class));
                   event.action();
            }    

        });
    }
    public static void chargeRooms(String userTag){
        final NtRechargeAdapter notification  = new NtRechargeAdapter();
        final NtChargeTickets ntChargeTickets = new NtChargeTickets();
        //Consulta de aquellas rooms que tengan en su array members el tag introducido como parametro
        db.collection(DatabaseStrings.COLLECTION_ROOMS).whereArrayContains("members",userTag).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Room> rooms = MngRooms.getRoomCharged();
                    rooms.clear();
                    for (QueryDocumentSnapshot document :  task.getResult()) {
                        rooms.add(document.toObject(Room.class));
                    }
                    notification.action();
                    ntChargeTickets.action();
                }else{

                    //Error Mssg Class Implementation
                }
            }
        });



    }
    /* Añadiremos un evento que si es la producido por la creacion de una room que nos la carge mediante un Notification*/
    public static void saveRoom(final Room room){
        db.collection(DatabaseStrings.COLLECTION_ROOMS).document(room.getUid()).set(room);
    }
    public static void chargeTicketsByRoom(final String tagRoom){
        db.collection(DatabaseStrings.COLLECTION_TICKETS).document(tagRoom).collection(DatabaseStrings.COLLECTION_TICKETS).get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Ticket>tik = null;
                    if (MngRooms.getMapTickets().containsKey(tagRoom)/*Realizar la comprobacion de si existe la clave*/ ) {
                        tik = MngRooms.getMapTickets().get(tagRoom);
                    }else{
                        tik = new ArrayList<>();
                        MngRooms.getMapTickets().put(tagRoom,tik);

                    }
                    for (QueryDocumentSnapshot document :  task.getResult()) {
                        tik.add(document.toObject(Ticket.class));
                    }

                }
            }
        });

    }




}