package com.example.equalopportuna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class AA_recyclerViewAdapter extends RecyclerView.Adapter<AA_recyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Model> ModelIs;

    public AA_recyclerViewAdapter(Context context, ArrayList<Model> ModelIS){
        this.context = context;
        this.ModelIs = ModelIS;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view__row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvName.setText(ModelIs.get(position).getName());
        holder.tvFeedback.setText(ModelIs.get(position).getFeedback());
        holder.tvDate.setText(ModelIs.get(position).getDate());
        holder.imageView.setImageResource(ModelIs.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return ModelIs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tvName, tvDate, tvFeedback;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView4);
            tvName = itemView.findViewById(R.id.textView8);
            tvDate = itemView.findViewById(R.id.textView6);
            tvFeedback = itemView.findViewById(R.id.textView9);
        }
    }


}
