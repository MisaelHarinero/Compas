package com.mhdeveloper.compas.controller.notifications;


import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.mhdeveloper.compas.LogInActivity;
import com.mhdeveloper.compas.R;
import com.mhdeveloper.compas.view.LogInFragment;

/**
 * @author  Misael Harinero
 * Abstract Class that charge a fragment and create a Toast with a error Mssg
 */
public abstract class NtErrorNotification implements INt {
    private static AppCompatActivity activity;
    private  String errorMssg;

    public NtErrorNotification(String errorMssg) {
        this.errorMssg = errorMssg;
        action();
    }

    public String getErrorMssg() {
        return errorMssg;
    }

    public static AppCompatActivity getActivity() {
        return activity;
    }

    public static void setActivity(AppCompatActivity activity) {
        NtErrorNotification.activity = activity;
    }

    public void setErrorMssg(String errorMssg) {
        this.errorMssg = errorMssg;
    }

    @Override
    public void action() {
        LogInFragment fragment  = new LogInFragment();
        Toast.makeText(activity,errorMssg,Toast.LENGTH_LONG);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }
}
