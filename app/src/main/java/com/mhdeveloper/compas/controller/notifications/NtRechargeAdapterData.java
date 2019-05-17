package com.mhdeveloper.compas.controller.notifications;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NtRechargeAdapterData implements INt {
    private static RecyclerView.Adapter adapter;
    private static int position;
    private  static ArrayList<Object>data;

    public static RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public static int getPosition() {
        return position;
    }

    public static void setPosition(int position) {
        NtRechargeAdapterData.position = position;
    }

    public static ArrayList<Object> getData() {
        return data;
    }

    public static void setData(ArrayList<Object> data) {
        NtRechargeAdapterData.data = data;
    }

    public static void setAdapter(RecyclerView.Adapter adapter) {
        NtRechargeAdapterData.adapter = adapter;
    }
    public NtRechargeAdapterData(){
        action();
    }

    @Override
    public void action() {
        if (position > -1 && adapter != null){
            data.remove(position);
            adapter.notifyDataSetChanged();
        }
    }
    public static void clearAttributes(){
         position = -1;
         data = null;
         adapter = null;
    }
}
