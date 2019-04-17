package com.mhdeveloper.compas.controller.dao;

import android.content.Context;
import android.widget.ImageButton;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * @author : Misael Harinero
 */
class CloudController{
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
    private static void chargePhoto(ImageButton imageButton, String url, Context context){
        instanceFirebase();
        StorageReference reference = storage.getReference();
        Glide.with(context).load(reference.child(url)).into(imageButton);
    }
}