package com.mhdeveloper.compas.controller.notifications;

import androidx.fragment.app.Fragment;
import com.mhdeveloper.compas.R;
import com.mhdeveloper.compas.controller.dao.AuthController;
import com.mhdeveloper.compas.controller.dao.FirestoreController;
import com.mhdeveloper.compas.model.User;
import com.mhdeveloper.compas.view.FragmentWait;

public class NtRegister implements INt {
    private static Fragment  fragment;
    public static  void register(String mail,String passwd, User user){
        FragmentWait fragmentWait = new FragmentWait();
        fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentWait).commit();
        new AuthController().registerUser(mail,passwd,user);

    }
    @Override
    public void action() {
        NtCharge.chargeDataUser(new AuthController().getUser().getUid());
    }

    public static Fragment getFragment() {
        return fragment;
    }

    public static void setFragment(Fragment fragment) {
        NtRegister.fragment = fragment;
    }
}
