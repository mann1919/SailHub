package com.example.sailhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.sailhub.PasswordHashing.doHashing;

/*
This class is for the login page
which validates the users and gives and option to register
 */
public class MainActivity extends AppCompatActivity {

    EditText userId,password;
    Button logIn;
    TextView Register;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // link variable to XML object
        userId = (EditText) findViewById(R.id.etUserId);
        password = (EditText) findViewById(R.id.etPassword);
        logIn = (Button) findViewById(R.id.btnLogIn);
        Register = (TextView) findViewById(R.id.tvRegister) ;
        // get instance of DB
        DB = DBHelper.getInstance(this);

        // onclick listener for log in button
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = userId.getText().toString();
                String pass = password.getText().toString();
                // hashing password
                String hPassword = doHashing(pass);
                // validation for input
                if(user.equals("")||pass.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkUser = DB.checkUserId(user);

                    Boolean checkUserPass = DB.checkUserIdPassword(user, hPassword);
                    if(checkUserPass){
                        Toast.makeText(MainActivity.this,
                                "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(),
                                ListSeries.class);
                        startActivity(intent);
                    }
                    else if(checkUser){
                        Toast.makeText(MainActivity.this, "Please register with this ID", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // onclick listener for register textview
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, SignUp.class);
                startActivity(registerIntent);
            }
        });
    }
}