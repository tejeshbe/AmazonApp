package com.example.amazonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductActivity extends AppCompatActivity {
    ImageView product_image_maintain;
    EditText product_name_maintain,product_price_maintain,product_description_maintain;
    Button  apply_changes_btn,delete_product_btn;
    String productId = "";
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_product);
        product_image_maintain = (ImageView)findViewById(R.id.product_image_maintain);
        product_name_maintain = (EditText)findViewById(R.id.product_name_maintain);
        product_price_maintain = (EditText)findViewById(R.id.product_price_maintain);
        product_description_maintain = (EditText)findViewById(R.id.product_description_maintain);
        apply_changes_btn = (Button)findViewById(R.id.apply_changes_btn);
        delete_product_btn = (Button)findViewById(R.id.delete_product_btn);
        productId = getIntent().getStringExtra("pid");
        mRef = FirebaseDatabase.getInstance().getReference("Products").child(productId);
        displaySpecificDetails();

        apply_changes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        delete_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });
    }

    private void deleteProduct() {
        mRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AdminMaintainProductActivity.this, "Product deleted !!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminMaintainProductActivity.this, AdminCategoryActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void updateData() {
        String name = product_name_maintain.getText().toString();
        String price = product_price_maintain.getText().toString();
        String desc  = product_description_maintain.getText().toString();

        if(name.isEmpty() && price.isEmpty() && desc.isEmpty()){
            Toast.makeText(this, "is empty !!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", productId);
            productMap.put("description", desc);
           // productMap.put("image", downloadImageUrl);
            productMap.put("price", price);
            productMap.put("pname", name);
            mRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AdminMaintainProductActivity.this, "product updated !!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminMaintainProductActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                }
            });

        }

    }

    private void displaySpecificDetails() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String pname = snapshot.child("pname").getValue().toString();
                    String price = snapshot.child("price").getValue().toString();
                    String desc = snapshot.child("description").getValue().toString();
                    String image = snapshot.child("image").getValue().toString();
                    product_name_maintain.setText(pname);
                    product_price_maintain.setText(price);
                    product_description_maintain.setText(desc);
                    Picasso.get().load(image).into(product_image_maintain);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String msg= error.toString();
                Toast.makeText(AdminMaintainProductActivity.this, msg+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
