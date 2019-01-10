package com.city.coding.restaurant3.Activites;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.city.coding.restaurant3.Helper.Helper;
import com.city.coding.restaurant3.Helper.requestQueueHelper;
import com.city.coding.restaurant3.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    //private Button signUp , login ;
    private static final String TAG = "LoginActivity";
    private static final String USER_URL_FOR_LOGIN = "http://honeydewpos.com/loycher/api/get_user.php?";
    private static final String OWNER_URL_FOR_LOGIN = "http://honeydewpos.com/loycher/api/get_owner?";

    private static final int RC_SIGN_IN = 1000;
    ProgressDialog progressDialog;
    private EditText loginInputEmail, loginInputPassword;
    private Button btnlogin;
    private TextView btnSignup;
    private String user_id;
    private String owner_id;
    private Intent intent;
    private String emailFromSignup;
    private TextView errorMessage;
    private SignInButton googleSignIn;
    private CheckBox OwnerCheckBox;
    private String name, email;
    private boolean isOwnerChecked;
    //google integration var
    private GoogleSignInOptions signInOptions;
    private GoogleSignInClient signInClient;
    private GoogleSignInAccount signInAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get intent from signUp activity
        intent = getIntent();
        emailFromSignup = intent.getStringExtra("email");
        Log.e(TAG, "onCreate: email from signup" + emailFromSignup);
        //get id's
        loginInputEmail = (EditText) findViewById(R.id.email_id);
        loginInputPassword = (EditText) findViewById(R.id.password_id);
        btnlogin = (Button) findViewById(R.id.login_id);
        btnSignup = (TextView) findViewById(R.id.signUp_id);
        errorMessage = findViewById(R.id.error_message_login_id);
        OwnerCheckBox = findViewById(R.id.owner_id);
        //googleSignIn = findViewById(R.id.google_signin_id);


        //google sign in operation
        //setSignInOption();
        //getGoolgeSignInClient();
        //set signUp clickListener
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signUp_entry.class);
                startActivity(intent);
            }
        });
        //end

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginInputEmail.getText().toString();
                String password = loginInputPassword.getText().toString();
                isOwnerChecked = OwnerCheckBox.isChecked();
                //Intent intent = new Intent(login.this,homeActivity.class);startActivity(intent);
                if (isValidInput(email, password)) {
                    if (!TextUtils.isEmpty(emailFromSignup)) {
                        loginInputEmail.setText(emailFromSignup);
                        if (isOwnerChecked) {
                            loginOwner(email,password,isOwnerChecked);
                        } else {
                            loginUser(email, password);
                        }
                    } else {
                        Log.e(TAG, "onClick: "+" emial from signup is empty" );
                        if (isOwnerChecked) {
                            Log.e(TAG, "onClick: "+" is owner " );
                            loginOwner(email,password,isOwnerChecked);
                        } else {
                            loginUser(email, password);
                        }

                    }
                    //loginUser(email, password);
                }
            }
        });


        //conferm from user , isOwner or no
        OwnerCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    alertDialog("Hmmm","Are you the Boss ? ","Yes","No");
                }
            }
        });
        //google sign in
    /*    googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });*/

    }


    //on start method
    @Override
    protected void onStart() {
        super.onStart();
        //getGoolgeSignInCurrentAccount(login.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    //get user authentication
    private void loginUser(final String email, final String password) {
        final String EMAIL = "email=";
        final String PASS = "&pass=";

        // Tag used to cancel the request
        String cancel_req_tag = "login";

        //form url with query email and password
        String URL_WITH_PARAMS = USER_URL_FOR_LOGIN + EMAIL + email + PASS + password;
        Log.e(TAG, "loginUser: URL is " + URL_WITH_PARAMS);

        progressDialog.setMessage("Logging you in...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL_WITH_PARAMS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject userData = jObj.getJSONArray("user_data").getJSONObject(0);

                    String result = userData.getString("user_id");
                    user_id = result;
                    name = userData.getString("first_name");
                    name = name + " " + userData.getString("last_name");
                    Log.e(TAG, result);
                    if (!result.equals("Not Exist")) {
                        //save user information in sharedPreference
                        //for future purposes
                        saveUserLoginInfo(email, password);

                        //show user id
                        //Toast.makeText(login.this, result, Toast.LENGTH_LONG).show();

                        // Launch User activity
                        Intent intent = new Intent(
                                login.this,
                                homeActivity.class);
                        //send user id to home to get voucherList for userId
                        intent.putExtra("user_id", result);
                        intent.putExtra("name", name);
                        intent.putExtra("email", email);
                        intent.putExtra("ownerState", isOwnerChecked);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = userData.getString("user_id");
                        Toast.makeText(login.this,
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "no connection", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("pass", password);
                return params;
            }
        };
        // Adding request to request queue
        requestQueueHelper.getInstance(login.this).addToRequestQueue(strReq, cancel_req_tag);
    }

    //get owner authentication
    private void loginOwner(final String email , final String password , final boolean isOwner){
        final String EMAIL = "email=";
        final String PASS = "&pass=";

        // Tag used to cancel the request
        String cancel_req_tag = "login";

        //form url with query email and password
        String URL_WITH_PARAMS = OWNER_URL_FOR_LOGIN + EMAIL + email + PASS + password;
        Log.e(TAG, "loginOwner: URL is " + URL_WITH_PARAMS);

        progressDialog.setMessage("Logging you in...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL_WITH_PARAMS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject userData = jObj.getJSONArray("user_data").getJSONObject(0);

                    String result = userData.getString("owner_id");
                    owner_id = result;
                    name = userData.getString("first_name");
                    name = name + " " + userData.getString("last_name");
                    Log.e(TAG, result);
                    if (!result.equals("Not Exist")) {
                        //save user information in sharedPreference
                        //for future purposes
                        saveUserLoginInfo(email, password,isOwner);

                        //show user id
                        //Toast.makeText(login.this, result, Toast.LENGTH_LONG).show();

                        // Launch User activity
                        Intent intent = new Intent(
                                login.this,
                                homeActivity.class);
                        //send user id to home to get voucherList for userId
                        intent.putExtra("user_id", result);
                        intent.putExtra("name", name);
                        intent.putExtra("email", email);
                        intent.putExtra("ownerState", isOwner);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = userData.getString("user_id");
                        Toast.makeText(login.this,
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(login.this,
                            "Oops", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "no connection", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("pass", password);
                return params;
            }
        };
        // Adding request to request queue
        requestQueueHelper.getInstance(login.this).addToRequestQueue(strReq, cancel_req_tag);

    }
    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


    //save user information for future purposes
    protected void saveUserLoginInfo(String email, String password) {
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("Unm", email);
        Ed.putString("Psw", password);
        Ed.putString("user_id", user_id);

        Ed.apply();
    }

    //save user information for future purposes
    protected void saveUserLoginInfo(String email, String password , boolean isOwner) {
        SharedPreferences sp = getSharedPreferences("loginOwner", MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("Unm", email);
        Ed.putString("Psw", password);
        Ed.putString("user_id", user_id);
        Ed.putBoolean("ownerState",isOwner);
        Ed.apply();
    }

    //check if login input valid or not
    private boolean isValidInput(String email, String pass) {
        if (TextUtils.isEmpty(email) || !email.contains(".com") || TextUtils.isEmpty(pass) || pass.length() < 6) {
            errorMessage.setText(getResources().getString(R.string.error_message));
            return false;
        }
        return true;
    }

    //set google signIn option to request
    private void setSignInOption() {
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    private void getGoolgeSignInCurrentAccount(Context context) {
        signInAccount = GoogleSignIn.getLastSignedInAccount(context);
        if (signInAccount != null) {
            saveInSharedPreference(signInAccount);
            Log.e(TAG, "getGoolgeSignInCurrentAccount: " + "saved");
        }
    }


    //save last sign in account in sharedPreference
    private void saveInSharedPreference(GoogleSignInAccount acc) {
        SharedPreferences sp = getSharedPreferences("lastGoogleAccount", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", acc.getDisplayName());
        editor.putString("email", acc.getEmail());
        editor.putString("image", acc.getPhotoUrl().toString());
        editor.putString("userId", acc.getId());
        editor.putString("familyName", acc.getFamilyName());
        editor.putString("givenName", acc.getGivenName());
        editor.apply();
        //startActivity(new Intent(login.this,registerCompletionActivity.class));
        Log.e(TAG, "saveInSharedPreference: " + sp.getString("name", "none"));
        Log.e(TAG, "saveInSharedPreference: " + sp.getString("givenName", "none"));
        Log.e(TAG, "saveInSharedPreference: " + sp.getString("userId", "none"));
        Log.e(TAG, "saveInSharedPreference: " + sp.getString("email", "none"));
    }

    private void getGoolgeSignInClient() {
        signInClient = GoogleSignIn.getClient(login.this, signInOptions);
    }


    //sign in
    private void signIn() {
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //handle signIn result data from popUp fragment
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            saveInSharedPreference(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }

    //show alert dialog
    private void alertDialog(String title,String message,String pos,String neg){

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(pos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(login.this, "Ok boss", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(neg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(login.this, "welcome", Toast.LENGTH_SHORT).show();
                    }
                })
                .create().show();
    }


}
