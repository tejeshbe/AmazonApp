package com.example.amazonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amazonapp.common.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    EditText editConName,editConPhone,editConAdd,editConCity;
    Button confirmButton;
    String totalAmount = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        editConName = (EditText)findViewById(R.id.editConName);
        editConPhone = (EditText)findViewById(R.id.editConPhone);
        editConAdd = (EditText)findViewById(R.id.editConAdd);
        editConCity = (EditText)findViewById(R.id.editConCity);
        confirmButton = (Button)findViewById(R.id.confirmButton);
        totalAmount = getIntent().getStringExtra("TotalPrice");
        Toast.makeText(this, "Total Price is"+ totalAmount, Toast.LENGTH_SHORT).show();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAddress();
            }
        });

    }
    private void confirmAddress( ) {
        String name = editConName.getText().toString();
        String phome = editConPhone.getText().toString();
        String add = editConAdd.getText().toString();
        String city = editConCity.getText().toString();
        if(name.isEmpty() && phome.isEmpty() && add.isEmpty() && city.isEmpty()){
            Toast.makeText(this, "enter all the fields !!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            final String saveCurrentDate,saveCurrentTime;
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calendar.getTime());

            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("BuyList").child(Common.currentOnlineUser.getPhone());

            HashMap<String,Object> orderMap = new HashMap<>();
            orderMap.put("totalAmont",totalAmount);
            orderMap.put("name",name);
            orderMap.put("phone",phome);
            orderMap.put("add",add);
            orderMap.put("city",city);
            orderMap.put("date",saveCurrentDate);
            orderMap.put("time",saveCurrentTime);
            orderMap.put("sate","not shipped");
            mRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        FirebaseDatabase.getInstance().getReference().child("Orders").child("UserView")
                                .child(Common.currentOnlineUser.getPhone()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ConfirmFinalOrderActivity.this, "added to list !", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        });
                    }
                }
            });
        }
    }


}
