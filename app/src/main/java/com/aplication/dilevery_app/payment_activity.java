package com.aplication.dilevery_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class payment_activity extends AppCompatActivity {

    private TextView mTotal_Price , mSubtotal_Price;
   private ImageView back;
   private EditText ePhone_Number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();

    }

    private  void init( ) {
        this.ePhone_Number = findViewById(R.id.phone_number_editTxt);
        this.mTotal_Price = findViewById(R.id.total_cost_TV);
        this.mSubtotal_Price = findViewById(R.id.sub_total_cost_TV);
        this.back = findViewById(R.id.back_from_payment);

        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        this.mSubtotal_Price.setText(getIntent().getExtras().getString("total_price"));
        this.mTotal_Price.setText(getIntent().getExtras().getString("total_price"));


        this.ePhone_Number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pattern pattern = Pattern.compile("@\"^\\d{10}$\"");
                Matcher matcher = pattern.matcher(ePhone_Number.getText());
                if(matcher.matches()) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });


    }
}