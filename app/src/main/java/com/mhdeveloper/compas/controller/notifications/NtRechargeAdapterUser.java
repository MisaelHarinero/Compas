package com.mhdeveloper.compas.controller.notifications;

import com.mhdeveloper.compas.view.FragmentControllRoom;
import com.mhdeveloper.compas.view.adapters.AdapterRecyclerArea;

public class NtRechargeAdapterUser implements INt {
    private static AdapterRecyclerArea adapter;
    private static FragmentControllRoom fragmentControllRoom;

    public NtRechargeAdapterUser() {
        action();
    }

    @Override
    public void action(){
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        if (fragmentControllRoom != null){
            fragmentControllRoom.renewData();
        }
    }



}
