package com.mhdeveloper.compas.controller.dao;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;
import com.mhdeveloper.compas.controller.managements.MngRooms;
import com.mhdeveloper.compas.controller.notifications.*;
import com.mhdeveloper.compas.model.Room;
import com.mhdeveloper.compas.model.Ticket;
import com.mhdeveloper.compas.model.User;

import javax.annotation.Nullable;
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
                   chargeRooms(MngRooms.getUser().getTag());
                   event.action();
            }    

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               new NtErrorLoggin("ERROR GETTING USER");
            }
        });
    }
    public static void chargeRooms(String userTag){
        final NtRechargeAdapter notification  = new NtRechargeAdapter();
        final NtChargeTickets ntChargeTickets = new NtChargeTickets();
        //Consulta de aquellas rooms que tengan en su array members el tag introducido como parametro
        db.collection(DatabaseStrings.COLLECTION_ROOMS).whereArrayContains("members",userTag).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    ArrayList<Room> rooms = MngRooms.getRoomCharged();
                    rooms.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        rooms.add(document.toObject(Room.class));
                    }
                    notification.action();
                    ntChargeTickets.action();
                    MngRooms.rechargeRoomSelected();
                    MngRooms.selectFirstInit();
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
    public static void chargeTicketsByRoom(final String roomTag){
        db.collection(DatabaseStrings.COLLECTION_TICKETS).whereEqualTo("roomTag",roomTag)
        .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots!=null) {
                    ArrayList<Ticket>tik = null;
                    if (MngRooms.getMapTickets().containsKey(roomTag)/*Realizar la comprobacion de si existe la clave*/ ) {
                        tik = MngRooms.getMapTickets().get(roomTag);
                    }else{
                        tik = new ArrayList<>();
                        MngRooms.getMapTickets().put(roomTag,tik);

                    }
                    for (DocumentSnapshot document :  queryDocumentSnapshots.getDocuments()) {
                        tik.add(document.toObject(Ticket.class));
                    }

                }
            }
        });
    }
    public static void saveTicket(Ticket ticket){
        db.collection(DatabaseStrings.COLLECTION_TICKETS).document(ticket.getTag()).set(ticket);
    }
    public static void saveUser(User user,String uid){
        db.collection(DatabaseStrings.COLLECTION_USERS).document(uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                new NtRegister().action();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new NtErrorLoggin("ERROR_DATABASE_SAVE");
            }
        });
    }
    public static void createRoom(final Room room){
        final NtCreationRoom notif = new NtCreationRoom(room);
        db.collection(DatabaseStrings.COLLECTION_ROOMS).whereEqualTo("uid",room.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()){
                    notif.action();
                }else{
                    notif.getRoom().setUid(room.getUid()+UtilitiesClass.generateTag());
                    createRoom(notif.getRoom());
                }
            }
        });
    }




}