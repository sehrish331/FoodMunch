package com.sehrish.foodapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sehrish.FoodApp.R;
import com.sehrish.foodapp.Model.Customer;

public class LoginActivity extends AppCompatActivity {

    EditText edtPhone, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        //Firebase

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_Customer = database.getReference("Customer");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

               table_Customer.addValueEventListener(new ValueEventListener()
               {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                   {
                       //Check if the customer doesn't exist in the database
                       if (dataSnapshot.child(edtPhone.getText().toString()).exists())
                       {
                           // Get Customer Info
                           mDialog.dismiss();

                           Customer customer = dataSnapshot.child(edtPhone.getText().toString()).getValue(Customer.class);
                           if (customer.getPassword().equals(edtPassword.getText().toString())) {
                               Toast.makeText(LoginActivity.this, "Login successful !", Toast.LENGTH_SHORT).show();
                           }
                           else {
                               Toast.makeText(LoginActivity.this, "Login failed !", Toast.LENGTH_SHORT).show();
                           }
                       }
                       else {
                           Toast.makeText(LoginActivity.this, "User doesn't exists", Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
            }
        });

    }
}
