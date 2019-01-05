package com.city.coding.restaurant3.Helper;

import android.text.TextUtils;
import android.util.Log;

import com.city.coding.restaurant3.Model.voucherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class voucherQueryUtile {

    static final String Url = "http://honeydewpos.com/loycher/api/voucher_list.php";
    static ArrayList<voucherData> voucherData ;

    //create URL
    private static URL createUrl (String voucherUrl){
        URL url = null;
        if(!voucherUrl.equals("")){
            try {
                url = new URL (voucherUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return url;
    }//end

    //readDataFromStream
    private static String readDataFromStream (InputStream inputStream)throws IOException{
        StringBuilder sb = new StringBuilder();
        if(inputStream !=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = br.readLine();
            while(line!= null){
                sb.append(line);
                line= br.readLine();
            }

        }
        return sb.toString();
    }//end

    //makeConnection
    private static String makeHttpConnection (URL url) throws IOException {
        String jsonResponse ="";
        if(url ==null){ return jsonResponse; }
        else{
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            try{
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);
                connection.connect();

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    inputStream = connection.getInputStream();
                    jsonResponse = readDataFromStream (inputStream);
                }


            }catch(IOException e){
                Log.e("connection message", "makeHttpConnection: "+e.getMessage() );
            }finally{
                if(connection !=null){ connection.disconnect(); }
                if(inputStream !=null){inputStream.close();}
            }
        }
        return jsonResponse;
    }

    //exctract data from jsonResponse
    private static ArrayList<voucherData> exctractDataFromJson(String rootJson){
        if(TextUtils.isEmpty(rootJson)){

        }else{
            voucherData = new ArrayList<>();
            try {
                JSONObject root = new JSONObject(rootJson);
                JSONArray voucherArray = root.getJSONArray("list_of_voucher");
                for(int i = 0 ; i<voucherArray.length() ;i++){
                    JSONObject indext = voucherArray.getJSONObject(i);
                    String title = indext.getString("voucher_name");
                    String qty = indext.getString("voucher_qty");
                    String value = indext.getString("voucher_value");
                    String imgUrl = indext.getString("voucher_img");
                    String voucherId = indext.getString("voucher_id");
                    voucherData.add(new voucherData(title,qty,value,imgUrl,voucherId));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return voucherData;
    }//end


    public static ArrayList<voucherData> fetchData(String voucherUrl) {
        URL url = createUrl(voucherUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpConnection(url);
        }catch(IOException e){
            e.printStackTrace();
        }
        ArrayList<voucherData> voucherData = exctractDataFromJson(jsonResponse);
        if(voucherData!=null){
            Log.e("fetchData", "voucherData length:"+voucherData.size() );
        }
        return voucherData;

    }
}
