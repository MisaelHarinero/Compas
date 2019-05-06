package com.mhdeveloper.compas.controller.notifications;

import com.mhdeveloper.compas.MainActivity;

public class NtOneSelected implements INt {

    private static MainActivity activity;

    public NtOneSelected() {
        action();
    }

    public static  MainActivity getActivity() {
        return activity;
    }

    public static  void setActivity(MainActivity activity) {
        NtOneSelected.activity = activity;
    }

    @Override
    public void action() {
        if (activity != null){
            activity.firstTimeCharge();
        }
    }
}
