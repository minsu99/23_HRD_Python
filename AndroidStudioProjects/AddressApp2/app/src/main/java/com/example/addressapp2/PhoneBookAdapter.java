package com.example.addressapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PhoneBookAdapter extends RecyclerView.Adapter<PhoneBookAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PhoneBook> phoneBooks;

    public PhoneBookAdapter(Context context, ArrayList<PhoneBook> phoneBooks) {
        this.context = context;
        this.phoneBooks = phoneBooks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_phone_book_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PhoneBook phoneBook = phoneBooks.get(position);
        holder.txtName.setText(phoneBook.getPhone_name());
        holder.txtPhoneNumber.setText(phoneBook.getPhone_number());
    }

    @Override
    public int getItemCount() {
        return phoneBooks.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPhoneNumber;
        Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}