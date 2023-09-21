package com.example.artapp;

import android.content.Context;
import android.content.Intent;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
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

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    CustomBook bookToDelete = phoneBooks.get(adapterPosition);
                    CustomDB customDB = new CustomDB(context);
                    customDB.deleteArtwork(bookToDelete.getPhone_id()); // 작품 삭제

                    // 데이터 리스트에서 삭제
                    phoneBooks.remove(adapterPosition);
                    // 어댑터에 항목이 삭제되었음을 알림
                    notifyItemRemoved(adapterPosition);
                }
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    CustomBook bookToEdit = phoneBooks.get(adapterPosition);
                    // 아이템 수정 화면으로 이동하고 선택한 아이템의 데이터를 전달
                    openEditActivity(bookToEdit, adapterPosition);
                }
            }
        });

    }

    // 아이템 수정 화면을 띄우는 메서드 (프로젝트에 따라 구현 필요)
    private void openEditActivity(CustomBook customBook, int adapterPosition) {
        // 수정 화면으로 이동하는 Intent 생성
        Intent editIntent = new Intent(context, EditActivity.class);

        // 수정할 아이템의 데이터를 Intent에 추가
        editIntent.putExtra("phone_id", customBook.getPhone_id());
        editIntent.putExtra("name", customBook.getPhone_name());
        editIntent.putExtra("artworkName", customBook.getArtwork_name());
        editIntent.putExtra("rating", customBook.getRating());
        editIntent.putExtra("review", customBook.getReview());

        // 수정 화면을 시작
        context.startActivity(editIntent);
    }
    public void updateItem(int position, CustomBook updatedBook) {
        if (position >= 0 && position < phoneBooks.size()) {
            phoneBooks.set(position, updatedBook);
            notifyItemChanged(position);
        }
    }

    @Override
    public int getItemCount() {
        return phoneBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtArtworkName, txtRating, txtReview, txtTime;
        Button btnDelete, btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtArtworkName = itemView.findViewById(R.id.txtArtworkName);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtReview = itemView.findViewById(R.id.txtReview);
            txtTime = itemView.findViewById(R.id.txtTime); // 시간을 표시할 TextView 찾기
            btnDelete = itemView.findViewById(R.id.btnDelete); // 삭제 버튼 찾기
            btnEdit = itemView.findViewById(R.id.btnEdit);        }
    }
}


