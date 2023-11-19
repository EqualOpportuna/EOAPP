package com.example.equalopportuna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JobPost_adapter extends RecyclerView.Adapter<JobPost_adapter.viewHolder>{

    String data1[], data2[], data3[];
    int images[];
    Context context;

    JobPost_adapter(Context ctx, String s1[], String s2[], String s3[], int img[]){
        context = ctx;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        images = img;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.jobpost_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.title.setText(data1[position]);
        holder.companyName.setText(data2[position]);
        holder.location.setText(data3[position]);
        holder.logo.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {

        return data2.length;
    }

    class viewHolder extends RecyclerView.ViewHolder{
        public TextView title, companyName, location;
        ImageView logo;
        public viewHolder(@NonNull View itemView){
            super (itemView);
            title = itemView.findViewById(R.id.job_title);
            companyName = itemView.findViewById(R.id.company_name);
            location = itemView.findViewById(R.id.job_location);
            logo = itemView.findViewById(R.id.job_logo);
        }
    }
}
