package com.example.amazonapp.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazonapp.ItemClickListener;
import com.example.amazonapp.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProName,txtProPrice,txtProQuan;
    ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProName = itemView.findViewById(R.id.txtProName);
        txtProPrice = itemView.findViewById(R.id.txtProPrice);
        txtProQuan =  itemView.findViewById(R.id.txtProQuan);
    }

   @Override
   public  void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);
   }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
