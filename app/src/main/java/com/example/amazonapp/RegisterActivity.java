package com.example.amazonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button createAccountButton;
    private EditText inputName,inputPhone,inputPass;
    private ProgressDialog  lbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createAccountButton = (Button)findViewById(R.id.regi_btn);
        inputName = (EditText)findViewById(R.id.register_name);
        inputPhone = (EditText)findViewById(R.id.register_phone_number_input);
        inputPass = (EditText)findViewById(R.id.register_password_input);
        lbar = new ProgressDialog(this);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }
    private void createAccount() {
        String name = inputName.getText().toString();
        String phone = inputPhone.getText().toString();
        String pass = inputPass.getText().toString();
        if(name.isEmpty() && phone.isEmpty() && pass.isEmpty() ){
            Toast.makeText(this, "enter all the fields !!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            lbar.setTitle("Create Account");
            lbar.setMessage("please wait account is creating !!");
            lbar.setCanceledOnTouchOutside(false);
            lbar.show();
            validatePhoneNumber(name,phone,pass);
        }

    }
    private void validatePhoneNumber( final String name, final String phone, final String pass) {
        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists())){
                    HashMap<String,Object> userdataMap= new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("password",pass);
                    userdataMap.put("name",name);
                    mRef.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Congrats account created !!", Toast.LENGTH_SHORT).show();
                                lbar.dismiss();
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "network error try again !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    lbar.dismiss();
                    Toast.makeText(RegisterActivity.this, "phone number already exist"+phone, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                lbar.dismiss();
            }
        });
    }
}
