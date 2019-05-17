package com.mhdeveloper.compas.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.mhdeveloper.compas.R;
import com.mhdeveloper.compas.model.Ticket;

public class ColorSelect {
    public static void ticketColorSelect(View view, Ticket ticket){
        LinearLayout layout = view.findViewById(R.id.back);
        if (ticket.isFinished()){
            layout.setBackgroundResource(R.color.grayBack);
        }else{
            switch (ticket.getImportance()){

            }
        }
    }
    public static void setState(ImageView view, boolean state){
        if (state){
            view.setImageResource(android.R.drawable.btn_minus);
        }
    }
}
