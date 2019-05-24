package com.mhdeveloper.compas.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mhdeveloper.compas.MainActivity;
import com.mhdeveloper.compas.R;
import com.mhdeveloper.compas.controller.dao.CloudController;
import com.mhdeveloper.compas.controller.dao.DatabaseStrings;
import com.mhdeveloper.compas.controller.managements.MngRooms;
import com.mhdeveloper.compas.controller.notifications.NtRechargeAdapter;
import com.mhdeveloper.compas.model.Permission;
import com.mhdeveloper.compas.model.Room;

import java.util.ArrayList;

public class AdapterRecyclerArea  extends RecyclerView.Adapter<AdapterRecyclerArea.MyView> {
    private ArrayList<Room> rooms =null;
    private Context context;
    private MainActivity activity;
    public AdapterRecyclerArea(Context context, MainActivity activity){
        rooms = MngRooms.getRoomCharged();
        this.context = context;
        this.activity = activity;
        NtRechargeAdapter.setAdapter(this);

    }
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_navigator,parent,false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, final int position) {
        if (rooms.get(position).getUrlImage()!= null){
            CloudController.chargePhoto(holder.button, DatabaseStrings.COLLECTION_PHOTOS_ROOMS+rooms.get(position).getUrlImage(),context);
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MngRooms.setRoomSelected(rooms.get(position));
               MngRooms.chargePermissions();
               activity.chargeRoom();
            }
        });
        holder.name.setText(rooms.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class MyView extends  RecyclerView.ViewHolder{
        ImageButton button;
        TextView name;
        View view;

        public MyView(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.buttonRoom);
            name = itemView.findViewById(R.id.nameRoom);
            view = itemView;

        }
    }
}
