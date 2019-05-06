package com.mhdeveloper.compas.controller.notifications;

import com.mhdeveloper.compas.view.FragmentControllRoom;
import com.mhdeveloper.compas.view.adapters.AdapterRecyclerUser;

public class NtRechargeAdapterUser implements INt {
    private static AdapterRecyclerUser adapter;
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

    public static AdapterRecyclerUser getAdapter() {
        return adapter;
    }

    public static void setAdapter(AdapterRecyclerUser adapter) {
        NtRechargeAdapterUser.adapter = adapter;
    }

    public static FragmentControllRoom getFragmentControllRoom() {
        return fragmentControllRoom;
    }

    public static void setFragmentControllRoom(FragmentControllRoom fragmentControllRoom) {
        NtRechargeAdapterUser.fragmentControllRoom = fragmentControllRoom;
    }
}
