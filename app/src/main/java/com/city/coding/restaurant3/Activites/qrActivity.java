package com.city.coding.restaurant3.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.city.coding.restaurant3.Helper.requestQueueHelper;
import com.city.coding.restaurant3.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class qrActivity extends AppCompatActivity {
    private String TAG = "qrActivity";
    private Intent intent;
    private String voucher_id;
    private static final String URL = "http://honeydewpos.com/loycher/api/get_code.php?voucher_id=";
    ProgressDialog progressDialog;
    private ImageView voucher_code_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        //get id's
        voucher_code_image = findViewById(R.id.qr_image_id);

        //get intent
        intent = getIntent();
        //get voucher id sent from voucher detail or fav list
        voucher_id = intent.getStringExtra("voucher_id");
        Log.e(TAG, "onCreate: voucher_id =" + voucher_id);

        loadQrCodeWithVoucherId(voucher_id);

        Log.e(TAG, "onCreate: intent.getStringExtra = " + intent.getStringExtra("voucher_id"));
    }

    private void loadQrCodeWithVoucherId(final String voucher_id) {

        // Tag used to cancel the request
        String cancel_req_tag = "voucher_data";
        String URL_WITH_PARAMS = URL + voucher_id;
        Log.e(TAG, "loadQrCodeWithVoucherId: URL with voucher id ="+URL_WITH_PARAMS );
        progressDialog.setMessage("loading...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL_WITH_PARAMS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    //TODO check json reponse in case of No voucher code
                    //if it's an array or string

                    JSONObject root = new JSONObject(response);
                    Log.e(TAG, "onResponse: json root is = "+root.toString() );
                    JSONArray voucher_data = root.getJSONArray("voucher_data");
                    Log.e(TAG, "onResponse: voucher data from root is = "+voucher_data.toString() );
                    JSONObject index = voucher_data.getJSONObject(0);
                    Log.e(TAG, "onResponse: voucher index from voucher data is = "+index.toString() );
                    String voucher_code = index.getString("voucher_code");
                    Log.e(TAG, "onResponse: voucher code is = "+voucher_code);
                    generateVoucherCodeImage(voucher_code);
                    Log.e(TAG, voucher_code);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "code Error: " + error.getMessage());
                Toast.makeText(qrActivity.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("voucher_id", voucher_id);
                return params;
            }
        };
        // Adding request to request queue
        requestQueueHelper.getInstance(qrActivity.this).addToRequestQueue(strReq, cancel_req_tag);

    }

    //generate image using string "voucher id" using bitmap and multiFormatwitter
    private void generateVoucherCodeImage(String voucher_code) {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Log.e(TAG, "generateVoucherCodeImage: "+"set MultiformatWritter" );
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(voucher_code, BarcodeFormat.QR_CODE, 600, 600);
            Log.e(TAG, "generateVoucherCodeImage: "+"set bitMatrix of "+voucher_code+" with qr code format" );
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            voucher_code_image.setImageBitmap(bitmap);
            Log.e(TAG, "generateVoucherCodeImage: "+"set voucher image done" );
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}


