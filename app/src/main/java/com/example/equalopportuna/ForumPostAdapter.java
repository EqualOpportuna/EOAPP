package com.example.equalopportuna;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Document;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ForumPostAdapter extends RecyclerView.Adapter<ForumPostAdapter.ForumPostViewHolder> {

    private final List<ForumPostNew> forumPosts;
    private final LayoutInflater inflater;
    private OnCommentButtonClickListener onCommentButtonClickListener;

    public interface OnCommentButtonClickListener {
        void onCommentButtonClick(int position, View view);
    }

    public void setOnCommentButtonClickListener(OnCommentButtonClickListener listener) {
        this.onCommentButtonClickListener = listener;
    }

    public ForumPostAdapter(Context context, List<ForumPostNew> forumPosts) {
        this.inflater = LayoutInflater.from(context);
        this.forumPosts = forumPosts;
    }

    @NonNull
    @Override
    public ForumPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_storiesforum, parent, false);
        return new ForumPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumPostViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ForumPostNew currentPost = forumPosts.get(position);
        holder.bind(currentPost);

        holder.cmtBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Trigger the listener callback
                if (onCommentButtonClickListener != null) {
                    onCommentButtonClickListener.onCommentButtonClick(position, view);
                }
            }
        });

        holder.downloadBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call downloadStory with the context from itemView
                downloadStory(position, holder.itemView.getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return forumPosts.size();
    }

    static class ForumPostViewHolder extends RecyclerView.ViewHolder {

        private final TextView usernameTextView;
        private final TextView messageTextView;
        private final TextView cmtBTN;
        private final TextView downloadBTN;

        public ForumPostViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.TVforumUsername);
            messageTextView = itemView.findViewById(R.id.TVforumPost);
            cmtBTN = itemView.findViewById(R.id.cmtBTN);
            downloadBTN = itemView.findViewById(R.id.downloadBTN);
        }

        public void bind(ForumPostNew post) {
            usernameTextView.setText(post.getUsername());
            messageTextView.setText(post.getMessage());
        }
    }

    private void downloadStory(int position, Context context) {
        ForumPostNew currentPost = forumPosts.get(position);

        // Get the data to be included in the PDF
        String publisher = currentPost.getUsername();
        String story = currentPost.getMessage();

        // Create a PDF document
        createPdfDocument(context, publisher, story);
    }


    private void createPdfDocument(Context context, String publisher, String story) {
        PdfDocument pdfDocument = new PdfDocument();

        // Create a page
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 500, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // Get the Canvas object and set the content
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(12);

        // Add content to the PDF
        canvas.drawText("Publisher: " + publisher, 50, 50, paint);
        canvas.drawText("Story: " + story, 50, 70, paint);

        // Finish the page
        pdfDocument.finishPage(page);

        // Generate a unique filename based on the current timestamp
        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileName = "story_" + timestamp + ".pdf";

        // Define the file path where the PDF will be saved in the "Downloads" folder
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;

        try {
            // Save the document to the specified file path
            FileOutputStream fos = new FileOutputStream(filePath);
            pdfDocument.writeTo(fos);
            pdfDocument.close();
            fos.close();

            // Display a Toast to notify the user that the story has been downloaded
            Toast.makeText(context, "Story Downloaded: " + filePath, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
