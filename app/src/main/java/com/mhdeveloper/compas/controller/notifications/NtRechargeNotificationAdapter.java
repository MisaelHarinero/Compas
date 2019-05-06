package com.mhdeveloper.compas.controller.notifications;

import com.mhdeveloper.compas.view.adapters.AdapterRecyclerNotifications;

public class NtRechargeNotificationAdapter implements INt {
    private static AdapterRecyclerNotifications adapter;

    public NtRechargeNotificationAdapter() {
    }

    @Override
    public void action() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public static AdapterRecyclerNotifications getAdapter() {
        return adapter;
    }

    public static void setAdapter(AdapterRecyclerNotifications adapter) {
        NtRechargeNotificationAdapter.adapter = adapter;
    }
}
