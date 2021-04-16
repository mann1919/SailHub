package com.example.sailhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.sailhub.PasswordHashing.doHashing;
/*
Class to create the register oage
 */
public class SignUp extends AppCompatActivity {

    EditText userId, password, repassword;
    Button signup;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // link variable to XML object
        userId = (EditText) findViewById(R.id.etUserId);
        password = (EditText) findViewById(R.id.etPassword);
        repassword = (EditText) findViewById(R.id.etRePassword);
        signup = (Button) findViewById(R.id.btnSignUp);

        // get instance of DB
        DB = DBHelper.getInstance(this);

        // onclick listener for sign up button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userId.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                // validation for input
                if(user.equals("")||pass.equals("")||repass.equals("")){
                    Toast.makeText(SignUp.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass.length()<= 7){
                        Toast.makeText(SignUp.this, "Password at least 8 characters long", Toast.LENGTH_LONG).show();
                    }else {
                        if (pass.equals(repass)) {
                            Boolean checkUser = DB.checkUserId(user);
                            if (checkUser) {
                                String hpass = doHashing(pass);
                                Boolean insert = DB.insertUserData(user, hpass);
                                if (insert) {
                                    Toast.makeText(SignUp.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SignUp.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SignUp.this, "Incorrect User ID", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else {
                            Toast.makeText(SignUp.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }
}