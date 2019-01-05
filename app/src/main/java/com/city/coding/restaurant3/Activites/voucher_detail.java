package com.city.coding.restaurant3.Activites;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.city.coding.restaurant3.R;

import java.util.Objects;

public class voucher_detail extends AppCompatActivity {
    private TextView title, desc;
    private ImageView image;
    private FloatingActionButton qr_button;
    Intent intent;
    private String voucher_id;
    private ActionBar bar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_detail);
        bar = getSupportActionBar();
        if(bar != null) {
            bar.setElevation(0);
            bar.setDisplayHomeAsUpEnabled(true);
        }

        findViewById();
        intent = getIntent();
        voucher_id = intent.getStringExtra("voucher_id");
        addVoucherDetails();
        qr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /* CustomDialogClass dialog = new CustomDialogClass(voucher_detail.this);
            dialog.show();*/
                Intent intent = new Intent(voucher_detail.this, qrActivity.class);
                intent.putExtra("voucher_id", voucher_id);
                startActivity(intent);
            }
        });
    }

    /*click listener to float button to pop up
     * adialog containing   QR image to get the voucher*/

    /*attach views by id's*/
    private void findViewById() {
        title = findViewById(R.id.voucher_title_detail_id);
        desc = findViewById(R.id.voucher_desc_detail_id);
        image = findViewById(R.id.voucher_image_detail_id);
        qr_button = findViewById(R.id.float_qr_code_button_id);
    }

    /*add voucher details to intent and set it to views*/
    private void addVoucherDetails() {
        String imgUrl = intent.getStringExtra("voucher_image");
        Glide.with(voucher_detail.this)
                .load(imgUrl)
                .into(image);
        title.setText(intent.getStringExtra("voucher_title"));
        desc.setText(intent.getStringExtra("voucher_desc"));
    }

    //dialog inner class
    public class CustomDialogClass extends Dialog {

        public Activity context;
        public Dialog dialog;
        public TextView qrNum;
        public ImageView qrImage;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.context = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.qr_dialog);
            qrNum = findViewById(R.id.qr_number_id);
            qrImage = findViewById(R.id.qr_code_id);
            // qrNum.setText(intent.getStringExtra("QRnum"));


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
