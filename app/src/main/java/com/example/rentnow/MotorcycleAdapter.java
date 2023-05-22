package com.example.rentnow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MotorcycleAdapter extends RecyclerView.Adapter<MotorcycleAdapter.ViewHolder> {

    private List<Motorcycle> motorcyclesList;
    private Context context;
    private OnMotorcycleClickListener onMotorcycleClickListener;


    public MotorcycleAdapter(Context context, List<Motorcycle> motorcyclesList, OnMotorcycleClickListener onRepoClickListener) {
        this.context = context;
        this.motorcyclesList = motorcyclesList;
        this.onMotorcycleClickListener = onRepoClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view, onMotorcycleClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Motorcycle motorcycle = motorcyclesList.get(position);

        int imageResId = context.getResources().getIdentifier(motorcycle.getImage(), "drawable", context.getPackageName());

        holder.name.setText(motorcycle.getName());
        holder.displacement.setText(motorcycle.getDisplacement() + "cc");
        holder.rate.setText(motorcycle.getRate().toString());
        holder.price.setText("R$ "+ motorcycle.getPrice().toString());
        holder.image.setImageResource(imageResId);
    }

    @Override
    public int getItemCount() {
        return motorcyclesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView displacement;
        TextView rate;
        TextView price;
        ImageView image;

        OnMotorcycleClickListener onMotorcycleClickListener;

        public ViewHolder(@NonNull View itemView, OnMotorcycleClickListener onMotorcycleClickListener) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            displacement = itemView.findViewById(R.id.displacement);
            rate = itemView.findViewById(R.id.rate);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);

            this.onMotorcycleClickListener = onMotorcycleClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMotorcycleClickListener.onMotorcycleClick(getAdapterPosition());
        }
    }

    public interface OnMotorcycleClickListener {
        void onMotorcycleClick(int position);
    }

    public void updateMotorcycles(List<Motorcycle> newMotorcycles) {
        this.motorcyclesList = newMotorcycles;
        notifyDataSetChanged();
    }

}