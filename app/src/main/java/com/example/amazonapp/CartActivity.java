package com.example.amazonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazonapp.common.Common;
import com.example.amazonapp.model.Cart;
import com.example.amazonapp.viewholder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {

    RecyclerView cartRecycle;
    Button nextButton;
    TextView txtPrice;
    RecyclerView.LayoutManager layoutManager;
    private int overTotalPrice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartRecycle = findViewById(R.id.cartRecycle);
        cartRecycle.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        cartRecycle.setLayoutManager(layoutManager);
        nextButton = (Button) findViewById(R.id.nextButton);
        txtPrice = (TextView)findViewById(R.id.txtPrice);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPrice.setText("Total price is"+String.valueOf(overTotalPrice));
                Intent i = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                i.putExtra("TotalPrice",String.valueOf(overTotalPrice));
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartRef.child("UserView")
                        .child(Common.currentOnlineUser.getPhone())
                        .child("Products"),Cart.class)
                .build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {
                holder.txtProName.setText("Product name=" + model.getPname());
                holder.txtProPrice.setText("Product price="+model.getPprice());
                holder.txtProQuan.setText("product quantity="+model.getQuantity());
                int oneProPrice = ((Integer.valueOf(model.getPprice()))* Integer.valueOf(model.getQuantity()));
                overTotalPrice = overTotalPrice + oneProPrice;

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]{
                            "Edit","Remove"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                        if( i== 0){
                                            Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                            intent.putExtra("pid",model.getPid());
                                            startActivity(intent);
                                        }
                                        if(i==1){
                                            cartRef.child("UserView")
                                                    .child(Common.currentOnlineUser.getPhone()).child("Products")
                                                    .child(model.getPid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    Toast.makeText(CartActivity.this, "Product Removed !!", Toast.LENGTH_SHORT).show();
                                                                }
                                                                else
                                                                {
                                                                    Toast.makeText(CartActivity.this, "Product Not removed !!", Toast.LENGTH_SHORT).show();
                                                                }
                                                        }
                                                    });
                                        }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitems_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        cartRecycle.setAdapter(adapter);
        adapter.startListening();

    }
}
