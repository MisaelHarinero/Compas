package com.mhdeveloper.compas.controller.dao;

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
     * Insert an Event
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
    public void logOut(){
        if (isUserSignIn()){
            instance.signOut();
        }
    }

    public void registerUser(String mail, String passwd, final User user){
        this.instance.createUserWithEmailAndPassword(mail,passwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                chargeUser();
                if (isUserSignIn()){
                    FirestoreController.saveUser(user,getUser().getUid());
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