package com.city.coding.restaurant3.Helper;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.city.coding.restaurant3.Model.store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class storeQueryUtils {

    private static final String TAG = "storeQueryUtils";

    //create url
    private static URL createUrl(String url) {
        URL mUrl = null;
        if (!TextUtils.isEmpty(url)) {
            try {
                mUrl = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return mUrl;
    }
    //end

    // make connection
    private static String makeHttpConnection(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        } else {
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(10000);
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                    jsonResponse = readDataFromStream(inputStream);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) connection.disconnect();
                if (inputStream != null) inputStream.close();
            }
        }
        return jsonResponse;
    }
    //end

    //read data from connection stream response
    private static String readDataFromStream(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bf = new BufferedReader(inputStreamReader);
            String line = bf.readLine();
            while (line != null) {
                sb.append(line);
                line = bf.readLine();
            }
        }
        Log.e(TAG, "readDataFromStream: "+sb.toString() );
        return sb.toString();
    }
    //end

    //extract data from json
    private static ArrayList<store> extractDataFromJson(String json) {
        ArrayList<store> listOfStore;
        if (json != null && !TextUtils.isEmpty(json)) {
            listOfStore = new ArrayList<>();
            try {
                JSONObject root = new JSONObject(json);
                JSONArray list = root.getJSONArray("list_of_voucher");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject index = list.getJSONObject(i);
                    store s = getStoreObjectFromIndex(index);
                    listOfStore.add(s);
                }
                return listOfStore;
            } catch (JSONException e) {
                Log.e(TAG, "extractDataFromJson: ", e.fillInStackTrace());
            }
        }
        return null;
    }


    //get store data from json index object
    private static store getStoreObjectFromIndex(JSONObject index) throws JSONException {
        String storeId, storeName, storeAdd, ownerName, ownerId, lati, longi;
        store s;
        if (index != null) {
            storeId = index.getString("store_id");
            storeName = index.getString("store_name");
            storeAdd = index.getString("store_address");
            ownerName = index.getString("owner_fname");
            ownerId = index.getString("owner_id");
            lati = index.getString("lati");
            longi = index.getString("longi");
            s = new store(storeId, ownerId, ownerName, storeName
                    , storeAdd, lati, longi);
            return s;
        } else
            return new store("none", "none", "none"
                    , "none", "none", "none", "none");
    }
    //end

    //fetch data from cloud
    public static ArrayList<store> fetchData (String url){
        URL mUrl = null;
        String json = null;
        ArrayList<store> storeList = new ArrayList<>() ;
        if(!TextUtils.isEmpty(url)){
             mUrl = createUrl(url);
            try {
                json = makeHttpConnection(mUrl);
                storeList = extractDataFromJson(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return storeList;
    }
}
