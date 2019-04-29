package com.mhdeveloper.compas.controller.notifications;

import com.mhdeveloper.compas.view.adapters.AdapterRecyclerArea;

public class NtRechargeAdapter implements INt {
    private static AdapterRecyclerArea adapter;
    @Override
    public void action(){
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public static AdapterRecyclerArea getAdapter() {
        return adapter;
    }

    public static void setAdapter(AdapterRecyclerArea adapter) {
        NtRechargeAdapter.adapter = adapter;
    }
}
