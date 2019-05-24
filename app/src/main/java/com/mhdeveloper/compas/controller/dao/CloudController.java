package com.mhdeveloper.compas.controller.dao;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

/**
 * @author : Misael Harinero
 */
public class CloudController{
    private static FirebaseStorage storage = null;

    /**
     * Metodo en el que instanciamos con firebase Cloud
     */
    private static void instanceFirebase(){
        storage = FirebaseStorage.getInstance();
    }
    /**
     * Metodo en el que realizamos la carga de una foto en la cloud de firebase 
     */
    public static void chargePhoto(final ImageButton imageButton, String url, Context context){
        instanceFirebase();
        StorageReference reference = storage.getReference();
        try {
            final File tempFile = File.createTempFile("images","jpg");
            reference.child(DatabaseStrings.COLLECTION_PHOTOS+url).getFile(tempFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if (tempFile.exists()){
                        imageButton.setImageURI(Uri.fromFile(tempFile));
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo con el cual guardamos una foto que recogemos del dispositivo
     * @param photo
     * @param url
     */
    public static void savePhoto(Uri photo,String url){
        instanceFirebase();
        StorageReference reference = storage.getReference();
        reference.child(DatabaseStrings.COLLECTION_PHOTOS+url).putFile(photo);
    }

    public static FirebaseStorage getStorage() {
        return storage;
    }

    public static void setStorage(FirebaseStorage storage) {
        CloudController.storage = storage;
    }

    /**
     * Metodo con el cual cargamos una foto obtenida en la Cloud en una ImageView
     * @param imageView
     * @param url
     * @param context
     */
    public static void chargePhoto(final ImageView imageView, String url, Context context){
        instanceFirebase();
        StorageReference reference = storage.getReference();
        try {
            final File tempFile = File.createTempFile("images"+System.nanoTime(),"jpg");
            reference.child(DatabaseStrings.COLLECTION_PHOTOS+url).getFile(tempFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if (tempFile.exists()){
                        imageView.setImageURI(Uri.fromFile(tempFile));
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Metodo el cual no se ejecutara debido a que actualmente en firestore no se puede realizar una eliminacion de carpetas, por lo que habria que recorrer todos los mensajes de un ticket para eliminar las fotos,
     * por lo que solo eliminaremos las de los tickets.
     * */
    public static void deleteMediaTicket(String ticketTag){
        storage.getReference(DatabaseStrings.COLLECTION_PHOTOS+ticketTag).delete();
    }
}