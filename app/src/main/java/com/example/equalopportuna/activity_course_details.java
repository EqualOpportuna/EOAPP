    package com.example.equalopportuna;
    
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.net.Uri;
    import android.os.Bundle;
    import android.widget.Button;
    import android.widget.SeekBar;
    import android.widget.TextView;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    
    public class activity_course_details extends AppCompatActivity {
    
        String link;
        String description;
        String name;
        String recommended;
        String username;
    
        // SharedPreferences key for storing progress
        private static final String PREF_PROGRESS_PREFIX = "progress_";
    
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course_details);
    
            // Retrieve data from intent extras
            Intent intent = getIntent();
            if (intent != null) {
                name = intent.getStringExtra("name");
                link = intent.getStringExtra("link");
                description = intent.getStringExtra("description");
                recommended = intent.getStringExtra("recommended");
                username = intent.getStringExtra("username");
    
                // Set values to TextViews
                TextView tvHeading = findViewById(R.id.heading);
                TextView tvDescription = findViewById(R.id.description);
                TextView tvTypeValue = findViewById(R.id.type_value);
                TextView tvRecommend = findViewById(R.id.recommendation);
    
                tvHeading.setText(name);
                tvDescription.setText(description);
                tvRecommend.setText(recommended);
                tvTypeValue.setText("Video");
    
                // Watch Youtube button click listener
                Button watchBtn = findViewById(R.id.watchBtn);
                watchBtn.setOnClickListener(view -> {
                    if (link != null && !link.isEmpty()) {
                        // Open the Youtube link
                        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                        startActivity(youtubeIntent);
                    }
                });
            }
    
            // SeekBar for progress tracking
            SeekBar progressSlider = findViewById(R.id.progressSlider);
            int savedProgress = getSavedProgress(); // Retrieve saved progress
            progressSlider.setProgress(savedProgress);
            progressSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // Update the progress as the user slides the SeekBar
                    // You may want to display the progress in a TextView or handle it as needed
                }
    
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Handle the start of tracking (if needed)
                }
    
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // Save the final progress when the user stops sliding
                    saveProgress(seekBar.getProgress(), name); // Save progress with course name as key
                }
            });
        }

        private int getSavedProgress() {
            // Retrieve and return the user's saved progress for the current course and username
            SharedPreferences preferences = getSharedPreferences("progress_pref_" + username, Context.MODE_PRIVATE);
            return preferences.getInt(PREF_PROGRESS_PREFIX + name, 0);
        }

        private void saveProgress(int progress, String courseName) {
            // Save the user's progress for the current course and username
            SharedPreferences preferences = getSharedPreferences("progress_pref_" + username, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(PREF_PROGRESS_PREFIX + courseName, progress);
            editor.apply();
        }
    }
