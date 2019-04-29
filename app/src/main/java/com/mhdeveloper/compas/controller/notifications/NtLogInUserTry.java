package com.mhdeveloper.compas.controller.notifications;

import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

public class NtLogInUserTry implements INt{
    private static EditText email;
    private static EditText passwd;
    public NtLogInUserTry() {
        action();
    }
    @Override
    public void action() {
        if (email != null){
            email.setText("");
            Snackbar.make(email.getRootView(), "Email or Password not correct", Snackbar.LENGTH_LONG).show();
        }
        if (passwd != null){
            passwd.setText("");
        }
    }

    public static EditText getEmail() {
        return email;
    }

    public static void setEmail(EditText email) {
        NtLogInUserTry.email = email;
    }

    public static EditText getPasswd() {
        return passwd;
    }

    public static void setPasswd(EditText passwd) {
        NtLogInUserTry.passwd = passwd;
    }
}
