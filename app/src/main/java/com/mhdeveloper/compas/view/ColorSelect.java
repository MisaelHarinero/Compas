package com.mhdeveloper.compas.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mhdeveloper.compas.R;
import com.mhdeveloper.compas.model.Ticket;

public class ColorSelect {
    public static void ticketColorSelect(View view, Ticket ticket){
        LinearLayout layout = view.findViewById(R.id.back);
        if (ticket.isFinished()){
            layout.setBackgroundResource(R.color.grayBack);
        }else{
            layout.setBackgroundResource(R.color.clear);
        }
    }
    public static void setState(ImageView view, boolean state){
        if (state){
            view.setImageResource(R.drawable.locked);
        }else{
            view.setImageResource(R.drawable.unlocked);
        }
    }
    public static void setColorImportance(TextView text, int  importance){
        switch (importance){
            case 1: {
                text.setBackgroundResource(R.color.lowImportance);
                break;
            }
            case 2: {
                text.setBackgroundResource(R.color.lowImportance);
                break;
            }
            case 3: {
                text.setBackgroundResource(R.color.midImportance);
                break;
            }
            case 4: {
                text.setBackgroundResource(R.color.midImportance);
                break;
            }
            case 5: {
                text.setBackgroundResource(R.color.highImportance);
                break;
            }

        }
    }
}
