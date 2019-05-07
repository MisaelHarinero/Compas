package com.mhdeveloper.compas.controller.dao;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;
import com.mhdeveloper.compas.controller.managements.MngRooms;
import com.mhdeveloper.compas.controller.notifications.*;
import com.mhdeveloper.compas.model.Notification;
import com.mhdeveloper.compas.model.Room;
import com.mhdeveloper.compas.model.Ticket;
import com.mhdeveloper.compas.model.User;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class FirestoreController {
    public static FirebaseFirestore db = null;

    public static void instanceFirestore() {
        db = FirebaseFirestore.getInstance();
    }

    public static void dataUser(String userUID, final INt event) {
        db.collection(DatabaseStrings.COLLECTION_USERS).document(userUID).get().addOnSuccessListener(
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
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

    public static void chargeRooms(String userTag) {
        final NtRechargeAdapter notification = new NtRechargeAdapter();
        final NtChargeTickets ntChargeTickets = new NtChargeTickets();
        //Consulta de aquellas rooms que tengan en su array members el tag introducido como parametro
        db.collection(DatabaseStrings.COLLECTION_ROOMS).whereArrayContains("members", userTag).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                    new NtOneSelected();
                } else {

                    //Error Mssg Class Implementation
                }
            }
        });


    }

    /* Añadiremos un evento que si es la producido por la creacion de una room que nos la carge mediante un Notification*/
    public static void saveRoom(final Room room) {
        db.collection(DatabaseStrings.COLLECTION_ROOMS).document(room.getUid()).set(room);
    }

    public static void chargeTicketsByRoom(final String roomTag) {
        db.collection(DatabaseStrings.COLLECTION_TICKETS).whereEqualTo("roomTag", roomTag)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            ArrayList<Ticket> tik = null;
                            if (MngRooms.getMapTickets().containsKey(roomTag)/*Realizar la comprobacion de si existe la clave*/) {
                                tik = MngRooms.getMapTickets().get(roomTag);
                            } else {
                                tik = new ArrayList<>();
                                MngRooms.getMapTickets().put(roomTag, tik);

                            }
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                tik.add(document.toObject(Ticket.class));
                            }

                        }
                    }
                });
    }

    public static void saveTicket(Ticket ticket) {
        db.collection(DatabaseStrings.COLLECTION_TICKETS).document(ticket.getTag()).set(ticket);
    }

    public static void saveUser(User user, String uid) {
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

    public static void createRoom(final Room room) {
        final NtCreationRoom notif = new NtCreationRoom(room);
        db.collection(DatabaseStrings.COLLECTION_ROOMS).whereEqualTo("uid", room.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult().isEmpty()) {
                    notif.action();
                } else {
                    notif.getRoom().setUid(room.getUid() + UtilitiesClass.generateTag());
                    createRoom(notif.getRoom());
                }
            }
        });
    }

    public static void createNotification(final String tagUser, final String permission, final String roomUid) {
        db.collection(DatabaseStrings.COLLECTION_USERS).whereEqualTo("tag", tagUser).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    db.collection(DatabaseStrings.COLLECTION_NOTIFICATIONS).document().set(new Notification(tagUser, roomUid, permission));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public static void chargeNotifications(String tag) {
        db.collection(DatabaseStrings.COLLECTION_NOTIFICATIONS).whereEqualTo("tagUser", tag).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    MngRooms.getNotifications().clear();
                    for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots.getDocuments()) {
                        MngRooms.getNotifications().add(documentSnapshot.toObject(Notification.class));
                        new NtRechargeNotificationAdapter();
                    }
                }
            }
        });
    }
    public static void eventNotification(final Notification notification, boolean accion){
        if (accion){
            db.collection(DatabaseStrings.COLLECTION_ROOMS).document(notification.getRoomUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                       Room room = task.getResult().toObject(Room.class);
                       if (room != null){
                           room.getMembers().add(notification.getTagUser());
                           room.getPermissesUser().put(notification.getTagUser(),notification.getPermissions());
                           saveRoom(room);
                       }

                    }
                }
            });
        }
        db.collection(DatabaseStrings.COLLECTION_NOTIFICATIONS).whereEqualTo("tagUser",notification.getTagUser()).whereEqualTo("roomUid",notification.getRoomUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshots:task.getResult()) {
                        db.collection(DatabaseStrings.COLLECTION_NOTIFICATIONS).document(snapshots.getId()).delete();
                    }
                }
            }
        });
    }
    /**
     * Method thath is listener for one new Message in the Database where the emmiter is the user & the emitter is the receptor
     * */
    public static void getSnapshotForMessages(){
        db.collection(DatabaseStrings.COLLECTION_TICKETS).document().collection(DatabaseStrings.COLLECTION_MESSAGES).whereEqualTo("tagEmmiter",MngRooms.getUser().getTag());
        db.collection(DatabaseStrings.COLLECTION_TICKETS).document().collection(DatabaseStrings.COLLECTION_MESSAGES).whereEqualTo("tagReceptor",MngRooms.getUser().getTag());

    }
    /**
     * Method that clean any trazes for the user in the room
     * */
    public static void cleanForUser(String tag,String roomUid){
    final String options [] = {"tagUserAttended","tagUserEmmiter"};
    Query reference = null;
        for (final String option: options) {
            reference =  db.collection(DatabaseStrings.COLLECTION_TICKETS).whereEqualTo(option,tag).whereEqualTo("roomTag",roomUid);
            reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot: task.getResult()) {
                        if (documentSnapshot != null){
                            final String id = documentSnapshot.getId();
                            db.collection(DatabaseStrings.COLLECTION_TICKETS).document(id).collection(DatabaseStrings.COLLECTION_MESSAGES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (DocumentSnapshot document:task.getResult()) {
                                        if (document != null){
                                            db.collection(DatabaseStrings.COLLECTION_TICKETS).document(id).collection(DatabaseStrings.COLLECTION_MESSAGES).document(document.getId()).delete();
                                        }
                                    }
                                }
                            });
                            Ticket tk = documentSnapshot.toObject(Ticket.class);
                            if (option.equals(options[0])){
                                tk.setTagUserAttended(null);
                            }else{
                                db.collection(DatabaseStrings.COLLECTION_TICKETS).document(tk.getTag()).delete();
                            }
                            saveTicket(tk);
                        }
                    }
                }
            });
        }


    }


}