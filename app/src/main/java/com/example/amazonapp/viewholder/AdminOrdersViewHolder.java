package com.example.amazonapp.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazonapp.R;

public class AdminOrdersViewHolder extends RecyclerView.ViewHolder {
    public TextView order_user_name,order_phone_number,order_total_price,order_address_city,order_date_time;
    public Button show_all_products_btn;
    public AdminOrdersViewHolder(@NonNull View itemView) {
        super(itemView);
        order_user_name = itemView.findViewById(R.id.order_user_name);
        order_phone_number = itemView.findViewById(R.id.order_phone_number);
        order_total_price = itemView.findViewById(R.id.order_total_price);
        order_address_city = itemView.findViewById(R.id.order_address_city);
        order_date_time = itemView.findViewById(R.id.order_date_time);
        show_all_products_btn = itemView.findViewById(R.id.show_all_products_btn);

    }
}
