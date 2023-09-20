package com.example.artapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

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
        //holder.txtName.setText(customBook.getPhone_name());
        //holder.txtPhoneNumber.setText(customBook.getPhone_number());
        holder.txtName.setText("이름: " + customBook.getPhone_name());
        holder.txtArtworkName.setText("작품명: " + customBook.getArtwork_name());
        // 평점 값을 가져와서 설정
        int rating = customBook.getRating();
        holder.txtRating.setText("별점 :" + String.valueOf(rating));
    }




    @Override
    public int getItemCount() {
        return phoneBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtArtworkName, txtPhoneNumber, txtRating;

        Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtArtworkName = itemView.findViewById(R.id.txtArtworkName); // 수정된 부분
            //txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber); // 수정된 부분
            txtRating = itemView.findViewById(R.id.txtRating);


        }
    }




}
