package com.example.equalopportuna;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CL_icon_view extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameView,feedbackView;
    public CL_icon_view(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        nameView = itemView.findViewById(R.id.name);
        feedbackView = itemView.findViewById(R.id.feedback);
    }
}
