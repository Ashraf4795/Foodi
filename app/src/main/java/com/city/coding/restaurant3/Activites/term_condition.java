package com.city.coding.restaurant3.Activites;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.city.coding.restaurant3.Helper.Helper;
import com.city.coding.restaurant3.R;

import java.io.BufferedReader;
import java.io.File;


public class term_condition extends AppCompatActivity {
    private File file;
    private BufferedReader br;
    private StringBuilder sb;
    private TextView text;
    private ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_condition);
        bar = getSupportActionBar();
        if(bar != null)
        {
            bar.setDisplayHomeAsUpEnabled(true);
        }
        text = findViewById(R.id.term_condition_id);
        text.setText(Helper.term);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home :
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}