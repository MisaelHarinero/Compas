package com.mhdeveloper.compas.controller.notifications;

import com.mhdeveloper.compas.view.AdapterRecyclerArea;

public class NtRechargeAdapter implements INt {
    public static AdapterRecyclerArea adapter;
    @Override
    public void action(){
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


}
