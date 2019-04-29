package com.mhdeveloper.compas.controller.notifications;

import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.mhdeveloper.compas.R;

/**
 * @author  Misael Harinero
 * Abstract Class that charge a fragment and create a Toast with a error Mssg
 */
public abstract class NtErrorNotification implements INt {
    private static Fragment fragment;
    private  String errorMssg;

    public NtErrorNotification(String errorMssg) {
        this.errorMssg = errorMssg;
        action();
    }

    public String getErrorMssg() {
        return errorMssg;
    }

    public static Fragment getFragment() {
        return fragment;
    }

    public static void setFragment(Fragment fragment) {
        NtErrorNotification.fragment = fragment;
    }

    public void setErrorMssg(String errorMssg) {
        this.errorMssg = errorMssg;
    }

    @Override
    public void action() {
        Toast.makeText(fragment.getContext(),errorMssg,Toast.LENGTH_LONG);
        fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }
}
