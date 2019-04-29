package com.mhdeveloper.compas.controller.dao;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageButton;
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
    public static void savePhoto(Uri photo,String url){
        instanceFirebase();
        StorageReference reference = storage.getReference();
        reference.child(DatabaseStrings.COLLECTION_PHOTOS+url).putFile(photo);
    }
}