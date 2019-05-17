package com.mhdeveloper.compas.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mhdeveloper.compas.R;
import com.mhdeveloper.compas.controller.dao.CloudController;
import com.mhdeveloper.compas.controller.dao.DatabaseStrings;
import com.mhdeveloper.compas.controller.managements.MngRooms;
import com.mhdeveloper.compas.model.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdapterMessages extends RecyclerView.Adapter<AdapterMessages.MyView> {
    private ArrayList<Message> menssages;
    private Context context;

    public AdapterMessages(ArrayList<Message> messages,Context context) {
        this.menssages = messages;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (this.menssages.get(position).getTagUser().equals(MngRooms.getUser().getTag())){

            if (this.menssages.get(position).getTypeMessage().equals(Message.TYPE_TEXT)){
                return 0;
            }else{
                return 1;
            }
        }else{
            if (this.menssages.get(position).getTypeMessage().equals(Message.TYPE_TEXT)){
                return 2;
            }else{
                return 3;
            }
        }
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        MyView myView = null;
        switch (i){
            case 0:{
                view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_message, viewGroup, false);
                myView = new MyView(view);
                break;
            }
            case 1:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_message_photo, viewGroup, false);
                myView = new MyViewPhoto(view);
                break;
            }
            case 2:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.other_message, viewGroup, false);
                myView = new MyView(view);
                break;
            }
            case 3:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.other_message_photo, viewGroup, false);
                myView = new MyViewPhoto(view);
                break;
            }

        }

        return myView;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView myView, int i) {
        System.out.println(myView.getClass().toString());
        if (myView instanceof MyViewPhoto){
            MyViewPhoto aux = (MyViewPhoto) myView;
            CloudController.chargePhoto(aux.imageView, DatabaseStrings.COLLECTION_PHOTOS_TICKETS+this.menssages.get(i).getTagTicket()+"/"+DatabaseStrings.COLLECTION_PHOTOS_MESSAGES+this.menssages.get(i).getUriPhoto(),context);
        }
        myView.mssg.setText(this.menssages.get(i).getText());
        myView.idUser.setText(this.menssages.get(i).getTagUser());
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        myView.date.setText(format.format(this.menssages.get(i).getDate().toDate()));
    }

    @Override
    public int getItemCount() {
        return menssages.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView mssg;
        TextView idUser;
        TextView date;

        public MyView(@NonNull View view) {
            super(view);
            mssg = view.findViewById(R.id.msg);
            idUser = view.findViewById(R.id.iduser);
            date = view.findViewById(R.id.date);
        }
    }
    public class MyViewPhoto extends AdapterMessages.MyView {
        ImageView imageView;
        public MyViewPhoto(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
        }
    }

}