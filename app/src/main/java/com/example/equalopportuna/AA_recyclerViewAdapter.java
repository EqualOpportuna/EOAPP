package com.example.equalopportuna;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class AA_recyclerViewAdapter extends RecyclerView.Adapter<AA_recyclerViewAdapter.MyViewHolder> {
    Context context;
    List<Model> ModelIs;


    public AA_recyclerViewAdapter(Context context, List<Model> ModelIS){
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


    // 设置点击事件的方法


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.tvName.setText(ModelIs.get(position).getCourseName());
        holder.tvFeedback.setText(ModelIs.get(position).getCourseFeedback());
        holder.tvDate.setText(ModelIs.get(position).getCourseDate());
        holder.imageView.setImageResource(ModelIs.get(position).getImage());
        //position is the corresponding ID in db
        holder.itemView.setOnClickListener((view) -> {
            System.out.println("yes,clicked");
            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, activity_course_details.class);
            intent.putExtra("id",position); // Replace "YOUR_KEY" with your key
            context.startActivity(intent);
        });



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
            tvName = itemView.findViewById(R.id.TVcourseName);
            tvDate = itemView.findViewById(R.id.TVcourseDate);
            tvFeedback = itemView.findViewById(R.id.TVcourseFeedback);
        }
    }


}
