package com.example.amazonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazonapp.common.Common;
import com.example.amazonapp.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText lphone, lpass;
    private ProgressDialog mDialog;
    Button login_btn;
    private String parentDbName = "Users";
    TextView text_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDialog = new ProgressDialog(this);
        lphone = (EditText) findViewById(R.id.log_phone);
        lpass = (EditText) findViewById(R.id.log_pass);
        login_btn = (Button) findViewById(R.id.log_btn);
        text_admin = (TextView) findViewById(R.id.txtAdmin);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        text_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn.setText("Login As Admin");
                text_admin.setVisibility(View.INVISIBLE);
                parentDbName = "Admins";
            }
        });
    }

    private void LoginUser() {
        String phone = lphone.getText().toString();
        String pass = lpass.getText().toString();
        if (phone.isEmpty() && pass.isEmpty()) {
            Toast.makeText(this, "enter all the fields !!", Toast.LENGTH_SHORT).show();
        } else {
            mDialog.setTitle("Login Account");
            mDialog.setMessage("please wait logging in");
            mDialog.show();
            AllowLoginMethod(phone, pass);
        }
    }

    private void AllowLoginMethod(final String phone, final String pass) {
        final DatabaseReference mRef;
        mRef = FirebaseDatabase.getInstance().getReference();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()) {
                    Users userdata = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if (userdata.getPhone().equals(phone)) {
                        if (userdata.getPassword().equals(pass)) {
                            Log.i("phone and pass", parentDbName + "and " + phone + " and" + pass);
                            mDialog.dismiss();
                            if (parentDbName.equals("Admins")) {
                                Intent i = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(i);
                                finish();
                                Toast.makeText(LoginActivity.this, "Login as Admin", Toast.LENGTH_SHORT).show();
                            } else if (parentDbName.equals("Users")) {
                                Intent ii = new Intent(LoginActivity.this, HomeActivity.class);
                                Common.currentOnlineUser = userdata;
                                 startActivity(ii);
                                Toast.makeText(LoginActivity.this, "Login as User", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                } else {
                    mDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Account not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Please connect to internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

