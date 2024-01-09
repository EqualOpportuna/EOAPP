package com.example.equalopportuna;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class JobPost_adapter extends RecyclerView.Adapter<JobPost_adapter.viewHolder> {

    List<Job> jobList;
    Context context;
    String clickedUser;

    JobPost_adapter(Context ctx, List<Job> jobs, String clickedUser) {
        context = ctx;
        jobList = jobs;
        this.clickedUser = clickedUser;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_jobpost, parent, false);

        final viewHolder holder = new viewHolder(view);

        // Set an OnClickListener for the CardView
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Job clickedJob = jobList.get(position);
                    if(clickedUser.equals(clickedJob.getName())) {
                        showToast("You can't apply for your own job");
                    }
                    // Add this in your else block
                    // ... Your existing code ...

// Add this in your else block
                    else {
                        // Create and show the dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_email_send, null);
                        builder.setView(dialogView);

                        // Get references to UI elements in the dialog
                        TextView textViewFullName = dialogView.findViewById(R.id.name);
                        TextView textViewSubject = dialogView.findViewById(R.id.subject);
                        EditText editTextMessage = dialogView.findViewById(R.id.editTextMessage);
                        Button btnSendEmail = dialogView.findViewById(R.id.btnSendEmail);

                        // Set values based on clickedJob and clickedUser
                        textViewFullName.setText("Your full name: " + clickedUser);
                        textViewSubject.setText("Subject: Applying for " + clickedJob.getJobTitle());

                        // Handle button click in the dialog
                        // Handle button click in the dialog
                        Database database = new Database();

                        // Get the job poster's email from the database
                        String jobPosterEmail = database.getEmailFromName(clickedJob.getName());
                        System.out.println(jobPosterEmail);
                        btnSendEmail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Get values entered by the user
                                String message = editTextMessage.getText().toString();


                                if (jobPosterEmail != null) {
                                    // Now, you can use these values to send the email using JavaMailAPI
                                    // Use jobPosterEmail for the recipient's email, Utils.EMAIL for the sender's email, message, etc.

                                    // Create an instance of JavaMailAPI and execute it
                                    JavaMailAPI javaMailAPI = new JavaMailAPI(context, jobPosterEmail, "Applying for " + clickedJob.getJobTitle(), message);
                                    javaMailAPI.execute();
                                    // Dismiss the dialog
                                } else {
                                    showToast("Error getting job poster's email");
                                }
                            }
                        });


                        // Show the dialog
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }


                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Job job = jobList.get(position);

        holder.title.setText(job.getJobTitle());
        holder.companyName.setText(job.getCompanyName());
        holder.location.setText(job.getJobLocation());
        holder.name.setText(job.getName());

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
        public TextView title, companyName, location, name;
        public ImageView jobLogo;
        public CardView cardView; // Add CardView


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.description);
            companyName = itemView.findViewById(R.id.company_name);
            location = itemView.findViewById(R.id.job_location);
            jobLogo = itemView.findViewById(R.id.job_logo);
            name = itemView.findViewById(R.id.name);
            cardView = itemView.findViewById(R.id.cardView); // Initialize CardView

        }
    }
    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
