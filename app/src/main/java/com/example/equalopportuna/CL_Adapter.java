package com.example.equalopportuna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CL_Adapter extends RecyclerView.Adapter<CL_icon_view> {
    Context context;
    List<itemCourseLisiting> items;


    public CL_Adapter(Context context,List items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CL_icon_view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CL_icon_view(LayoutInflater.from(context).inflate(R.layout.cl_icon_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CL_icon_view holder, int position) {
        holder.nameView.setText(items.get(position).getNameOfCourse());
        holder.feedbackView.setText(items.get(position).getFeedbackOfCourese());
        holder.imageView.setImageResource(items.get(position).getIcon());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
