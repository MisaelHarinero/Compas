package com.mhdeveloper.compas.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mhdeveloper.compas.R;

import java.util.ArrayList;

public class AdapterRecyclerNotifications extends RecyclerView.Adapter<AdapterRecyclerNotifications.MyView> {
    private ArrayList<String> cadenas = new ArrayList<>();
    private Context context;
    public AdapterRecyclerNotifications(Context context){
        cadenas.add("Uno");
        cadenas.add("Dos");
        cadenas.add("Tres");
        cadenas.add("Cuatro");
        this.context = context;

    }
    @NonNull
    @Override
    public AdapterRecyclerNotifications.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_element_layout,parent,false);
        return new AdapterRecyclerNotifications.MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerNotifications.MyView holder, final int position) {
        holder.userTag.setText(cadenas.get(position)+"#0000");
        holder.roomName.setText(cadenas.get(position).toUpperCase());
        holder.buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, cadenas.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cadenas.size();
    }

    public class MyView extends  RecyclerView.ViewHolder{
        Button buttonAccept;
        Button buttonDecline;
        TextView userTag;
        TextView roomName;
        public MyView(@NonNull View itemView) {
            super(itemView);
            buttonAccept = itemView.findViewById(R.id.bAccept);
            buttonDecline = itemView.findViewById(R.id.bDecline);
            userTag = itemView.findViewById(R.id.tagUser);
            roomName = itemView.findViewById(R.id.nameRoom);

        }
    }
}