package com.example.equalopportuna;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class JobPost_adapter extends RecyclerView.Adapter<JobPost_adapter.viewHolder> {

    List<Job> jobList;
    Context context;

    JobPost_adapter(Context ctx, List<Job> jobs) {
        context = ctx;
        jobList = jobs;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_jobpost, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Job job = jobList.get(position);

        holder.title.setText(job.getJobTitle());
        holder.companyName.setText(job.getCompanyName());
        holder.location.setText(job.getJobLocation());

        // Set the tier image based on the imageURL
        setJobLogo(holder.jobLogo, job.getTier());
    }

    private void setJobLogo(ImageView jobLogo, String imageURL) {
        String tier = getTierFromImageURL(imageURL);

        if (tier != null && !tier.isEmpty()) {
            Log.d("TierDebug", "Tier: " + tier);

            String imageName = tier.toLowerCase() + "tier"; // Assuming the image names are atier, btier, ctier
            int imageResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            Glide.with(context).load(imageResourceId).into(jobLogo);
        } else {
            Log.e("TierDebug", "tier is null or empty: " + imageURL);
        }
    }


    private String getTierFromImageURL(String imageURL) {
        Log.d("TierDebug", "Received imageURL: " + imageURL);

        String trimmedURL = imageURL.trim();
        return trimmedURL;
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        public TextView title, companyName, location;
        public ImageView jobLogo;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.description);
            companyName = itemView.findViewById(R.id.company_name);
            location = itemView.findViewById(R.id.job_location);
            jobLogo = itemView.findViewById(R.id.job_logo);
        }
    }
}
