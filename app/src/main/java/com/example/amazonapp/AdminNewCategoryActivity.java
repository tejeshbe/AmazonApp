package com.example.amazonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amazonapp.common.Common;
import com.example.amazonapp.model.AdminOrders;
import com.example.amazonapp.model.Cart;
import com.example.amazonapp.viewholder.AdminOrdersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewCategoryActivity extends AppCompatActivity {
    RecyclerView orderRecycle;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_category);
        orderRecycle = (RecyclerView)findViewById(R.id.orderRecycle);
        mRef = FirebaseDatabase.getInstance().getReference("BuyList");
        orderRecycle.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(mRef,AdminOrders.class)
                        .build();
        FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewHolder> adapter =
              new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                  @Override
                  protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull AdminOrders model) {
                        holder.order_user_name.setText("Name :"+model.getName());
                        holder.order_address_city.setText("City "+model.getCity());
                        holder.order_date_time.setText("Time "+model.getTime());
                        holder.order_total_price.setText("Total Amount "+model.getTotalAmont());
                        holder.order_phone_number.setText("Phone number "+model.getPhone());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                    "yes", "no"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewCategoryActivity.this);
                                builder.setTitle("Have you Shipped this item ?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if(i==0){
                                            String uId = getRef(position).getKey();
                                            removeOrder(uId);
                                        }
                                        else
                                        {
                                            finish();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });

                  }

                  @NonNull
                  @Override
                  public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                      return new AdminOrdersViewHolder(view);
                  }
              };
        orderRecycle.setAdapter(adapter);
        adapter.startListening();
        }

        private void removeOrder(String uId) {
            mRef.child(uId).removeValue();
        }



    }


