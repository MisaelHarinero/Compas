package com.mhdeveloper.compas.controller.dao;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mhdeveloper.compas.controller.notifications.NtCharge;
import com.mhdeveloper.compas.controller.notifications.NtErrorLoggin;
import com.mhdeveloper.compas.controller.notifications.NtLogInUserTry;
import com.mhdeveloper.compas.controller.notifications.NtRegister;
import com.mhdeveloper.compas.model.User;

/**
 * Clase con la cual manejamos el inicio de sesion
 * @author Misael Harinero
 */
public class AuthController {
    private FirebaseAuth instance;
    private FirebaseUser user;

    public AuthController() {
        this.instance = FirebaseAuth.getInstance();
        chargeUser();
    }
    private void chargeUser(){
        user =this.instance.getCurrentUser();
    }
    public boolean isUserSignIn(){
        return (user != null ? true : false);
    }
    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    /**
     * Metodo en el cual con los datos introducidos iniciamos sesion
     * @param user
     * @param passwd
     */
    public void signInWithMailPasswd(final String user, String passwd){
        this.instance.signInWithEmailAndPassword(user,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    chargeUser();
                    if (isUserSignIn()){
                        NtCharge.chargeDataUser(getUser().getUid());
                    }else{
                        new NtLogInUserTry();
                    }
                }else{
                    new NtLogInUserTry();
                }
            }
        });
    }
    /**
     * Metodo en el cual  cerramos sesion
     * */
    public void logOut(){
        if (isUserSignIn()){
            instance.signOut();
        }
    }

    /**
     * Metodo con el cual con los datos pasados como parametro Registramos un nuevo usuario en Firebase Auth
     * Si todo sale correctamente sus datos se guardaran en Firestore
     * @param mail
     * @param passwd
     * @param user
     * @param uri
     */
    public void registerUser(String mail, String passwd, final User user, final Uri uri){
        this.instance.createUserWithEmailAndPassword(mail,passwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                chargeUser();
                if (isUserSignIn()){
                    FirestoreController.saveUser(user,getUser().getUid());
                    if (uri != null && user.getImageRoute() != null){
                        CloudController.savePhoto(uri,DatabaseStrings.COLLECTION_PHOTOS_USERS+user.getImageRoute());
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new NtErrorLoggin("ERROR GETTING USER");
            }
        });
    }
}
