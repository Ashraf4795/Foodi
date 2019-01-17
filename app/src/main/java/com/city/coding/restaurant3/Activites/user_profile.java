package com.city.coding.restaurant3.Activites;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.city.coding.restaurant3.R;

public class user_profile extends AppCompatActivity {
    //TODO user's profile image upload or read from cloud
    private static final String TAG = "user_profile";
    private SharedPreferences sp;
    private ImageView backArrow;
    private TextView tName, tEmail, tPhone, tDateOfBirth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_ui);


        mfindViewById();


        //set value to text view's
        setDataToTextViews();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mfindViewById() {
        backArrow = findViewById(R.id.backArrow_id);
        tName = findViewById(R.id.tv_name);
        tPhone = findViewById(R.id.profile_phone_id);
        tDateOfBirth = findViewById(R.id.profile_date_of_birth_id);
        tEmail = findViewById(R.id.profile_email_id);
    }


    //set data the return from sharedPreferences to textView
    private void setDataToTextViews() {
        String name, phone, dateOfBirth, email;
        sp = getSharedPreferences("Login", MODE_PRIVATE);
        if (sp != null) {
            name = sp.getString("name", "no name");
            phone = sp.getString("phone", "xxx-xxx-xxx-xx");
            dateOfBirth = sp.getString("birth_of_date", "xx-xx-xxxx");
            email = sp.getString("Unm", "no email");
            tName.setText(name);
            tPhone.setText(phone);
            tEmail.setText(email);
            tDateOfBirth.setText(dateOfBirth);
            Log.e(TAG, "onStart: " + "name " + name + " phone " + phone);
        } else {
            //TODO ask for data using email and password if no saved data
        }

    }
}
