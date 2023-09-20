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
        holder.txtName.setText(customBook.getPhone_name());
        holder.txtPhoneNumber.setText(customBook.getPhone_number());
    }

    @Override
    public int getItemCount() {
        return phoneBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPhoneNumber;
        Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);

        }
    }
}
