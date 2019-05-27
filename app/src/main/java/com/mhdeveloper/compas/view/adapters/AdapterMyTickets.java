package com.mhdeveloper.compas.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mhdeveloper.compas.MainActivity;
import com.mhdeveloper.compas.R;
import com.mhdeveloper.compas.controller.dao.CloudController;
import com.mhdeveloper.compas.controller.dao.DatabaseStrings;
import com.mhdeveloper.compas.controller.dao.FirestoreController;
import com.mhdeveloper.compas.controller.managements.MngRooms;
import com.mhdeveloper.compas.model.Ticket;
import com.mhdeveloper.compas.view.ColorSelect;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdapterMyTickets extends RecyclerView.Adapter<AdapterMyTickets.MyView>{
    private ArrayList<Ticket> tickets;
    private Context context;
    private MainActivity activity;
    public AdapterMyTickets(ArrayList<Ticket>tickets,Context context,MainActivity activity) {
        this.tickets = tickets;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterMyTickets.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_ticket_view,parent,false);
        return new AdapterMyTickets.MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterMyTickets.MyView holder, final int position) {
        holder.title.setText(this.tickets.get(position).getTitle());
        holder.description.setText(this.tickets.get(position).getDescription());
        holder.importance.setText(Integer.toString(this.tickets.get(position).getImportance()));
        SimpleDateFormat format  = new SimpleDateFormat();
        holder.date.setText(format.format(this.tickets.get(position).getDate().toDate()));
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Open
                if (true){
                    activity.showPopUp(tickets.get(position),holder.date);
                    return  true;
                }else {
                    return false;
                }
            }
        });
        if (this.tickets.get(position).getUriPhoto() != null){
            CloudController.chargePhoto(holder.photo, DatabaseStrings.COLLECTION_PHOTOS_TICKETS+this.tickets.get(position).getUriPhoto(),context);

        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tickets.get(position).getTagUserAttended() != null && !tickets.get(position).isFinished()){
                    activity.chargeMssgActivity(tickets.get(position).getTag());
                }
            }
        });
        ColorSelect.setState(holder.state,tickets.get(position).isFinished());
        ColorSelect.ticketColorSelect(holder.view,tickets.get(position));
        ColorSelect.setColorImportance(holder.importance,tickets.get(position).getImportance());

    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }


    public class MyView extends  RecyclerView.ViewHolder{
        TextView title;
        TextView importance;
        ImageView photo;
        ImageView state;
        TextView description;
        TextView date;
        View view;
        public MyView(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTicket);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            state = itemView.findViewById(R.id.state);
            importance = itemView.findViewById(R.id.importance);
            photo = itemView.findViewById(R.id.imageTicket);
            this.view = itemView;

        }
    }
}