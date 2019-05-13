package com.mhdeveloper.compas.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mhdeveloper.compas.R;
import com.mhdeveloper.compas.controller.managements.MngRooms;
import com.mhdeveloper.compas.model.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdapterMessages extends RecyclerView.Adapter<AdapterMessages.MyView> {
    private ArrayList<Message> menssages;

    public AdapterMessages(ArrayList<Message> messages) {
        this.menssages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        if (this.menssages.get(position).getTagUser().equals(MngRooms.getUser().getTag())){
            return 0;
        }else{
            return 1;
        }
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (i == 0){
            view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_message, viewGroup, false);
        }else{
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.other_message, viewGroup, false);

        }
        MyView myView = new MyView(view);
        return myView;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView myView, int i) {
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

}