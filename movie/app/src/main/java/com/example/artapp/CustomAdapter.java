package com.example.artapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CustomBook> phoneBooks;

    public CustomAdapter(Context context, ArrayList<CustomBook> phoneBooks) {
        this.context = context;
        this.phoneBooks = phoneBooks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_custom_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CustomBook customBook = phoneBooks.get(position);
        holder.txtName.setText("이름: " + customBook.getPhone_name());
        holder.txtArtworkName.setText("작품명: " + customBook.getArtwork_name());
        int rating = customBook.getRating();
        holder.txtRating.setText("별점: " + String.valueOf(rating));
        holder.txtReview.setText("리뷰: " + customBook.getReview());

        // 현재 시간을 표시하는 TextView 설정
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = sdf.format(currentTime);
        holder.txtTime.setText("작성한 시간: " + formattedTime);
    }

    @Override
    public int getItemCount() {
        return phoneBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtArtworkName, txtRating, txtReview, txtTime;

        Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtArtworkName = itemView.findViewById(R.id.txtArtworkName);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtReview = itemView.findViewById(R.id.txtReview);
            txtTime = itemView.findViewById(R.id.txtTime); // 시간을 표시할 TextView 찾기
        }
    }
}

