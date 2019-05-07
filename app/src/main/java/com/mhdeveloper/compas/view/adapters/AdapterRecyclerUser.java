package com.mhdeveloper.compas.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mhdeveloper.compas.R;
import com.mhdeveloper.compas.controller.managements.MngRooms;
import com.mhdeveloper.compas.view.FragmentUsers;

public class AdapterRecyclerUser extends RecyclerView.Adapter<AdapterRecyclerUser.MyView>{
    private FragmentUsers fragmentUsers;
    public AdapterRecyclerUser(FragmentUsers fragmentUsers){
    this.fragmentUsers = fragmentUsers;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_element_layout,parent,false);


        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, final int position) {
        holder.name.setText(MngRooms.getRoomSelected().getMembers().get(position));
        holder.permiss.setText(MngRooms.getRoomSelected().getPermissesUser().get(MngRooms.getRoomSelected().getMembers().get(position)));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MngRooms.getPermissions().isAdminUser()){
                    if (!MngRooms.getUser().getTag().equals(MngRooms.getRoomSelected().getMembers().get(position))){
                        fragmentUsers.startFragmentChange(MngRooms.getRoomSelected().getMembers().get(position));
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return MngRooms.getRoomSelected().getMembers().size();
    }
    public class MyView extends  RecyclerView.ViewHolder{
        TextView name;
        TextView permiss;
        ImageView photoPermisses;
        View view;

        public MyView(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userTag);
            permiss = itemView.findViewById(R.id.namePermisses);
            view = itemView;
            photoPermisses = itemView.findViewById(R.id.iconPermisses);

        }
    }
}
