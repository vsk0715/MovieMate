package com.project.moviemate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AddEntityTheatreAdapter extends RecyclerView.Adapter<AddEntityTheatreAdapter.TheatreViewHolder> {
    private final List<Theatre> theatres;
    private final Context context;

    public AddEntityTheatreAdapter(List<Theatre> theatres, Context context) {
        this.theatres = theatres;
        this.context = context;
    }

    @NonNull
    @Override
    public AddEntityTheatreAdapter.TheatreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.theatre_item, parent, false);
        return new TheatreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddEntityTheatreAdapter.TheatreViewHolder holder, int position) {
        Theatre theatre = theatres.get(position);
        Glide.with(holder.itemView.getContext())
                .load(theatre.getTheatreImageUrl())
                .placeholder(R.drawable.camera)
                .error(R.drawable.camera)
                .into(holder.addEntityTheatreImage);
        holder.addEntityTheatreName.setText(theatre.getTheatreName());
        holder.addEntityTheatreLocation.setText(theatre.getTheatreLocation());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(context, ShowTheatreAfterAddEntity.class);
                intent.putExtra("TheatreID", theatre.getTheatreID());
                intent.putExtra("imageURL", theatre.getTheatreImageUrl());
                intent.putExtra("theatreName", theatre.getTheatreName());
                intent.putExtra("theatreLocation", theatre.getTheatreLocation());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return theatres.size();
    }

    public class TheatreViewHolder extends RecyclerView.ViewHolder{
        ImageView addEntityTheatreImage;
        TextView addEntityTheatreName, addEntityTheatreLocation;
        public TheatreViewHolder(@NonNull View itemView){
            super(itemView);
            addEntityTheatreImage = itemView.findViewById(R.id.addEntityTheatreImage);
            addEntityTheatreName = itemView.findViewById(R.id.addEntityTheatreName);
            addEntityTheatreLocation = itemView.findViewById(R.id.addEntityTheatreLocation);

        }
    }
}
