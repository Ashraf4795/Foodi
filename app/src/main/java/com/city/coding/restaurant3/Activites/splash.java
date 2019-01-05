package com.city.coding.restaurant3.Activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.city.coding.restaurant3.R;

public class splash extends AppCompatActivity {
    private String TAG = "splash";
    private Button loginButton;
    private TextView signUpButton;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loginButton = findViewById(R.id.splash_login_id);
        signUpButton = findViewById(R.id.splash_signUp_id);

        //move to login activity
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoggedIn()){
                    startActivity(new Intent(splash.this,homeActivity.class));
                }
               startActivity(new Intent(splash.this,login.class));
               finish();
            }
        });

        //move to signUp activity
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(splash.this, signUp_entry.class));

            }
        });



    }


    //on start

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
        if (isLoggedIn()) {
            Log.e(TAG, "onStart: is logged in successfully" );
            startActivity(new Intent(splash.this,homeActivity.class));
        }
    }

   /* protected boolean isLogin() {
        Log.e(TAG, "isLogin: " + " working...");
        SharedPreferences sp1 = getSharedPreferences("Login", MODE_PRIVATE);
        return false;
    }*/

    //check if the user is already logged in
    private boolean isLoggedIn() {
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        if (sp != null) {
            Log.e(TAG, "isLoggedIn: sp not null ");
            String user = sp.getString("Unm", null);
            String pass = sp.getString("Psw", null);
            if (user != null && !TextUtils.isEmpty(user) && pass != null && !TextUtils.isEmpty(pass)) {
                Log.e(TAG, "isLoggedIn: user is "+user );
                Log.e(TAG, "isLoggedIn: pass is "+pass );
                Log.e(TAG, "isLoggedIn: return true" );
                return true;
            }
        }

        return false;
    }
}
