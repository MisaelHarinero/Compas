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
import com.mhdeveloper.compas.R;
import com.mhdeveloper.compas.controller.dao.CloudController;
import com.mhdeveloper.compas.controller.dao.DatabaseStrings;
import com.mhdeveloper.compas.controller.dao.FirestoreController;
import com.mhdeveloper.compas.controller.managements.MngRooms;
import com.mhdeveloper.compas.model.Ticket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdapterRecyclerTicketAll extends RecyclerView.Adapter<AdapterRecyclerTicketAll.MyView>{
    private ArrayList<Ticket>tickets;
    private Context context;
    public AdapterRecyclerTicketAll(ArrayList<Ticket>tickets,Context context) {
        this.tickets = tickets;
        this.context = context;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tickets_not_selected,parent,false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, final int position) {
        holder.title.setText(this.tickets.get(position).getTitle());
        holder.description.setText(this.tickets.get(position).getDescription());
        holder.importance.setText(Integer.toString(this.tickets.get(position).getImportance()));
        SimpleDateFormat format  = new SimpleDateFormat();
        holder.date.setText(format.format(this.tickets.get(position).getDate().toDate()));
        if (this.tickets.get(position).getUriPhoto() != null){
            CloudController.chargePhoto(holder.photo, DatabaseStrings.COLLECTION_PHOTOS_TICKETS+this.tickets.get(position).getUriPhoto(),context);

        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickets.get(position).setTagUserAttended(MngRooms.getUser().getTag());
                FirestoreController.saveTicket(tickets.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }


    public class MyView extends  RecyclerView.ViewHolder{
    TextView title;
    TextView importance;
    ImageView photo;
    TextView description;
    TextView date;
    Button button;

    public MyView(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.titleTicket);
        description = itemView.findViewById(R.id.description);
        date = itemView.findViewById(R.id.date);
        importance = itemView.findViewById(R.id.importance);
        photo = itemView.findViewById(R.id.imageTicket);
        button = itemView.findViewById(R.id.accept);

    }
}
}
