package com.mhdeveloper.compas.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mhdeveloper.compas.R;

import java.util.ArrayList;

public class AdapterRecyclerArea  extends RecyclerView.Adapter<AdapterRecyclerArea.MyView> {
    private ArrayList<String>cadenas = new ArrayList<>();
    private Context context;
    public AdapterRecyclerArea(Context context){
        cadenas.add("Uno");
        cadenas.add("Dos");
        cadenas.add("Tres");
        cadenas.add("Cuatro");
        this.context = context;

    }
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_navigator,parent,false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, final int position) {
        holder.button.setOnClickListener(new View.OnClickListener() {
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
        ImageButton button;

        public MyView(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.buttonRoom);

        }
    }
}
