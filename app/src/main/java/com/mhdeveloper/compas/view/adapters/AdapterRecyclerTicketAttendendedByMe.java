package com.mhdeveloper.compas.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mhdeveloper.compas.MainActivity;
import com.mhdeveloper.compas.R;
import com.mhdeveloper.compas.controller.dao.CloudController;
import com.mhdeveloper.compas.controller.dao.DatabaseStrings;
import com.mhdeveloper.compas.model.Ticket;
import com.mhdeveloper.compas.view.ColorSelect;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdapterRecyclerTicketAttendendedByMe extends RecyclerView.Adapter<AdapterRecyclerTicketAttendendedByMe.MyView> {
    private ArrayList<Ticket> tickets;
    private Context context;
    private MainActivity activity;

    public AdapterRecyclerTicketAttendendedByMe(ArrayList<Ticket> tickets, Context context, MainActivity activity) {
        this.tickets = tickets;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterRecyclerTicketAttendendedByMe.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_ticket_view, parent, false);
        return new AdapterRecyclerTicketAttendendedByMe.MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerTicketAttendendedByMe.MyView holder, final int position) {
        final AdapterRecyclerTicketAttendendedByMe adapter = this;
        holder.title.setText(this.tickets.get(position).getTitle());
        holder.description.setText(this.tickets.get(position).getDescription());
        holder.importance.setText(Integer.toString(this.tickets.get(position).getImportance()));
        SimpleDateFormat format = new SimpleDateFormat();
        holder.date.setText(format.format(this.tickets.get(position).getDate().toDate()));
        if (this.tickets.get(position).getUriPhoto() != null) {
            CloudController.chargePhoto(holder.photo, DatabaseStrings.COLLECTION_PHOTOS_TICKETS + this.tickets.get(position).getUriPhoto(), context);

        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tickets.get(position).isFinished()){
                    activity.chargeMssgActivity(tickets.get(position).getTag());
                }
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (v != null && !tickets.get(position).isFinished()){
                    activity.showPopUpReader(tickets.get(position),holder.date,adapter);
                    return  true;
                }else{
                    if (v != null && tickets.get(position).isFinished()){
                        activity.showPopUpReaderClosed(tickets.get(position),holder.date,adapter);
                        return true;
                    }
                    return false;
                }
            }
        });
        ColorSelect.ticketColorSelect(holder.view,tickets.get(position));
        ColorSelect.setState(holder.state,tickets.get(position).isFinished());
        ColorSelect.setColorImportance(holder.importance,tickets.get(position).getImportance());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }


    public class MyView extends RecyclerView.ViewHolder {
        TextView title;
        TextView importance;
        ImageView photo;
        TextView description;
        ImageView state;
        TextView date;
        View view;

        public MyView(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTicket);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            importance = itemView.findViewById(R.id.importance);
            state = itemView.findViewById(R.id.state);
            photo = itemView.findViewById(R.id.imageTicket);
            this.view = itemView;

        }
    }
}
