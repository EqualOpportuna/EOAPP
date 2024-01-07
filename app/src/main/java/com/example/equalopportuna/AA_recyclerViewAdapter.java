package com.example.equalopportuna;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class AA_recyclerViewAdapter extends RecyclerView.Adapter<AA_recyclerViewAdapter.MyViewHolder> {
    Context context;
    List<Model> ModelIs;

    String username;


    public AA_recyclerViewAdapter(Context context, List<Model> ModelIS, String currentUser){
        this.context = context;
        this.ModelIs = ModelIS;
        this.username = currentUser;

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
        holder.tvName.setText(ModelIs.get(position).getCourseName());
        holder.tvFeedback.setText(ModelIs.get(position).getCourseFeedback());
        holder.tvDate.setText(ModelIs.get(position).getCourseDate());
        String name = ModelIs.get(position).getCourseName();
        String link = ModelIs.get(position).getLink();
        String description = ModelIs.get(position).getDescription();
        String recommendedCourses = ModelIs.get(position).getRecommendedCourse();

        holder.itemView.setOnClickListener((view) -> {
            Intent intent = new Intent(context, activity_course_details.class);
            intent.putExtra("name", name);
            intent.putExtra("link", link);
            intent.putExtra("description", description);
            intent.putExtra("recommended", recommendedCourses);
            intent.putExtra("username", username);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ModelIs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvDate, tvFeedback;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.TVcourseName);
            tvDate = itemView.findViewById(R.id.TVcourseDate);
            tvFeedback = itemView.findViewById(R.id.TVcourseFeedback);
        }
    }


}
