package com.city.coding.restaurant3;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.city.coding.restaurant3.Activites.homeActivity;
import com.city.coding.restaurant3.Activites.login;
import com.city.coding.restaurant3.Helper.Helper;
import com.city.coding.restaurant3.Helper.requestQueueHelper;
import com.city.coding.restaurant3.Model.voucherData;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class request {

// add voucher to fav list in the cloud


    public static void addToFavList(final Context context, final String userId, final String voucherId) {
        // Tag used to cancel the request
        String cancel_req_tag = "favList";

        String Url = "http://honeydewpos.com/loycher/api/save_voucher.php?user_id=" + userId + "&voucher_id=" + voucherId;

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    String result = jObj.getString("reg_result");
                    if (!result.equals("Failed")) {
                        Toast.makeText(context, "done", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("request", "Login Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userId);
                params.put("voucher_id", voucherId);
                return params;
            }
        };
        // Adding request to request queue
        requestQueueHelper.getInstance(context).addToRequestQueue(strReq, cancel_req_tag);
    }


    public static ArrayList<voucherData> getFavList(final Context context, final String userId) {
        final ArrayList<voucherData> voucherFavList = new ArrayList<>();
        String cancel_req_tag = "favList";
        String Url = "http://honeydewpos.com/loycher/api/get_saved_list.php?user_id=3";
        StringRequest strReq = new StringRequest(Request.Method.GET,
                Url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("json response", response );
                    JSONObject jObj = new JSONObject(response);
                    if (!TextUtils.isEmpty(jObj.toString())) {
                        JSONArray favList = jObj.getJSONArray("list_of_voucher");
                        if (favList != null && favList.length() > 0) {
                            for (int i = 0; i < favList.length(); i++) {
                                JSONObject index = favList.getJSONObject(i);
                                voucherData v = getDataFromJson(index);
                                voucherFavList.add(v);
                            }
                            Log.e("voucherSize", voucherFavList.size()+"");
                        } else {
                            Toast.makeText(context, "list is empty", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "no Response", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("request", "Login Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userId);
                return params;
            }
        };
        // Adding request to request queue
        requestQueueHelper.getInstance(context).addToRequestQueue(strReq, cancel_req_tag);

        return voucherFavList;
    }

    //get Data From json
    private static voucherData getDataFromJson(JSONObject json) throws JSONException {
        String voucherId, voucherName, voucherQty, voucherValue, voucherImage;

        voucherId = json.getString("voucher_id");
        voucherName = json.getString("voucher_name");
        voucherQty = json.getString("voucher_qty");
        voucherValue = json.getString("voucher_value");
        voucherImage = json.getString("voucher_img");

        return new voucherData(voucherName, voucherQty, voucherValue, voucherImage, voucherId);
    }

}
