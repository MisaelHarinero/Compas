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
import com.mhdeveloper.compas.model.Permission;
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
        chargePhotoPermisses(holder.photoPermisses,holder.permiss.getText().toString());
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

    public void chargePhotoPermisses(ImageView imageView, String namePermission){
        Permission per = getPermission(namePermission);
        if (per != null){
            if (per.isAdminUser()){
                imageView.setImageResource(R.drawable.admin);
            }
        }

    }
    public Permission getPermission(String namePermision){
        for (Permission per: MngRooms.getRoomSelected().getPermissions()) {
            if (per.getName().equals(namePermision)){
                return per;
            }
        }
        return null;
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
