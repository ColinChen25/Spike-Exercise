package com.example.spike_exercise;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HivesAdapter extends RecyclerView.Adapter<HivesAdapter.MyViewHolder> {
    private Context context;
    private List<Hives> list;
    public HivesAdapter(Context context, List<Hives> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_view_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HivesAdapter.MyViewHolder holder, final int position) {

        holder.hive_Name.setText(list.get(position).getHivename());
        holder.address_linear_layout_info.setText(list.get(position).getAddress());
        holder.hive_Health.setText("" + list.get(position).getHealth());
        holder.queen_production_linear_layout_info.setText(list.get(position).getQueen_production() + "");

        holder.item_view_constraint_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HiveInfo.class);
                intent.putExtra("hive_name", list.get(position).getHivename());
                intent.putExtra("index", position);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView hive_Name;
        TextView address_linear_layout_info;
        TextView hive_Health;
        TextView queen_production_linear_layout_info;
        ConstraintLayout item_view_constraint_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            hive_Name = itemView.findViewById(R.id.hive_Name);
            address_linear_layout_info = itemView.findViewById(R.id.address_linear_layout_info);
            hive_Health = itemView.findViewById(R.id.hive_Health);
            queen_production_linear_layout_info = itemView.findViewById(R.id.queen_production_linear_layout_info);
            item_view_constraint_layout = itemView.findViewById(R.id.item_view_constraint_layout);
        }
    }
}
