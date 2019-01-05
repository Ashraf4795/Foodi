package com.city.coding.restaurant3.Activites;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.city.coding.restaurant3.Helper.Helper;
import com.city.coding.restaurant3.R;

public class registerCompletionActivity extends AppCompatActivity {
    private String TAG = "registerCompletionActivity";
    private ActionBar bar;
    private TextView errorMessage;
    private EditText pass, phone;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_completion);
        bar = getSupportActionBar();
        if (bar != null) {
            bar.setElevation(0);
            bar.setDisplayHomeAsUpEnabled(true);
        }
        errorMessage = findViewById(R.id.complete_error_message_signUp_id);
        pass = findViewById(R.id.complete_signup_password_id);
        phone = findViewById(R.id.complete_signup_phone_id);
        signIn = findViewById(R.id.complete_signIn_id);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("lastGoogleAccount", MODE_PRIVATE);
                if (checkValidation(pass.getText().toString(), phone.getText().toString())) {
                    if (sp != null) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("password",pass.getText().toString());
                        editor.putString("phone",phone.getText().toString());
                        editor.apply();
                        String email = sp.getString("email", null);
                        String password = pass.getText().toString();
                        String fName = sp.getString("givenName",null);
                        String lName = sp.getString("familyName",null);
                        String ph = phone.getText().toString();
                        Helper.registerUser(Helper.Url,fName,lName,email,ph,password,registerCompletionActivity.this,homeActivity.class);

                    } else {
                        //TODO sp is equal to null , handle this case
                    }
                }
            }
        });


    }


    //check validation input from user
    public boolean checkValidation(String pass, String ph) {
        if (pass.length() <= 6) {
            errorMessage.setText(getResources().getString(R.string.short_password));
        } else if (ph.equals("")) {
            errorMessage.setText("Enter phone number");
        } else {
            return true;
        }
        return false;
    }
}
