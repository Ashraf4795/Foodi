package com.city.coding.restaurant3.Activites;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.city.coding.restaurant3.Helper.requestQueueHelper;
import com.city.coding.restaurant3.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class signUp_entry extends AppCompatActivity {
    private Button signIn;
    private EditText firstName, lastName, email, phone, password;
    private TextView errorTextView, dateOfBirth;
    private String fn, ln, em, ph, pass, date;
    private static final String TAG = "RegisterActivity";
    private static final String URL_FOR_REGISTRATION = "http://honeydewpos.com/loycher/api/reg_new_user.php?";
    ProgressDialog progressDialog;
    ActionBar bar;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_entry);
        //set Action bar
        bar = getSupportActionBar();
        if (bar != null) {
            bar.setElevation(0);
            bar.setDisplayHomeAsUpEnabled(true);
        }

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        signIn = findViewById(R.id.signIn_id);
        firstName = findViewById(R.id.signup_first_name_id);
        lastName = findViewById(R.id.signup_last_name_id);
        email = findViewById(R.id.signup_email_id);
        phone = findViewById(R.id.complete_signup_phone_id);
        password = findViewById(R.id.complete_signup_password_id);
        dateOfBirth = findViewById(R.id.signup_dateOfBirth_id);
        errorTextView = findViewById(R.id.error_message_signUp_id);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fn = firstName.getText().toString();
                ln = lastName.getText().toString();
                em = email.getText().toString();
                pass = password.getText().toString();
                ph = phone.getText().toString();
                date = dateOfBirth.getText().toString();

                if (checkValidation(fn, ln, pass, ph, em, date)) {
                    registerUser(fn, ln, em, ph, pass, date);
                } else {
                    Toast.makeText(signUp_entry.this, "make sure that you Enter your data correctly ", Toast.LENGTH_LONG).show();
                }
            }
        });


        //date listener
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        signUp_entry.this
                        , android.R.style.Theme_Holo_Dialog_MinWidth
                        , mDateSetListener
                        , year, month, day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();


            }
        });
        // once date is selected
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateOfBirth.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
        };

    }


    //check validation input from user
    public boolean checkValidation(String fn, String ln, String pass, String ph, String em, String date) {
        if (fn.length() < 3 || ln.length() < 3) {
            errorTextView.setText(getResources().getString(R.string.short_name));
        } else if (pass.length() <= 6) {
            errorTextView.setText(getResources().getString(R.string.short_password));
        } else if (ph.equals("") || ph.length() < 10) {
            errorTextView.setText(getResources().getString(R.string.wrong_phone_number));
        } else if (em.equals("") || !em.contains(".com") || !em.contains("@")) {
            errorTextView.setText(getResources().getString(R.string.wrong_email));
        } else if (TextUtils.isEmpty(date)) {
            errorTextView.setText(getResources().getString(R.string.date_not_defined));
        } else {
            return true;
        }
        return false;
    }

    //,move to homeScreen
    private void moveToHome() {
        Intent intent = new Intent(signUp_entry.this, homeActivity.class);
        homeActivity.setIsOwner(true);
        startActivity(intent);
    }

    public void registerUser(final String firstName, final String lastName,
                             final String email, final String phone, final String password , final String date) {
        String cancel_req_tag = "register";

        String URL_WITH_PARAMS = URL_FOR_REGISTRATION + "email=" + email + "&pass=" + password + "&phone_no="
                + phone + "&first_name=" + firstName + "&last_name=" + lastName + "&date_of_birth=" + date;
        progressDialog.setMessage("Adding you ...");
        progressDialog.setIndeterminate(true);
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL_WITH_PARAMS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String result = jObj.getString("reg_result");
                    Log.e(TAG, "onResponse: " + result);
                    if (result.equals("Successful")) {
                        //String user = jObj.getJSONObject("user").getString("name");
                        Toast.makeText(signUp_entry.this, "Hi , You are successfully Added!", Toast.LENGTH_SHORT).show();
                        saveSignUpdataInSharedPreference(em, ph, date,fn+" "+ln);
                        // Launch login activity
                        Intent intent = new Intent(signUp_entry.this, login.class);
                        intent.putExtra("email", em);
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("reg_result");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(signUp_entry.this,
                        "no connection", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("first_name", firstName);
                params.put("last_name", lastName);
                params.put("email", email);
                params.put("pass", password);
                params.put("phone_no", phone);
                params.put("date_of_birth", date);
                Log.e(TAG, "getParams: " + params.toString());
                return params;
            }
        };
        // Adding request to request queue
        requestQueueHelper.getInstance(signUp_entry.this).addToRequestQueue(strReq, cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    //save signUp data in sharedPreference
    private void saveSignUpdataInSharedPreference(String email, String phone, String dateOfBirth,String name) {
        String SHARED_PREF = "signUp_data";
        String EMAIL = "email";
        String PHONE = "phone";
        String DATE_OF_BIRTH = "dateOfBirth";
        String userName = "name";
        SharedPreferences sp = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(EMAIL, email);
        edit.putString(PHONE, phone);
        edit.putString(DATE_OF_BIRTH, dateOfBirth);
        edit.putString(userName ,name);
        edit.apply();
        Log.e(TAG, "saveSignUpdataInSharedPreference: " + sp.getString(EMAIL, null)
                + " " + sp.getString(PHONE, null) + " " + sp.getString(DATE_OF_BIRTH, null));
    }

    //menu item option

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(signUp_entry.this,splash.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

