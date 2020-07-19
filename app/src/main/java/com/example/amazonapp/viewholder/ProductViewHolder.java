package com.example.amazonapp.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazonapp.ItemClickListener;
import com.example.amazonapp.R;

public class ProductViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductDesc,product_price;
    public  ImageView imageView;
    public ItemClickListener  itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDesc = (TextView)itemView.findViewById(R.id.product_description);
        imageView = (ImageView)itemView.findViewById(R.id.product_image);
        product_price = (TextView)itemView.findViewById(R.id.product_price);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
