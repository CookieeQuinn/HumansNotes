package com.jnu.lxq.dataprocessor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.jnu.lxq.R;

import java.util.Calendar;

public class PayUpdateActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextMoney;
    private EditText editTextDate;

    private int position;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_update_pay);

        initData();

        initView();

        initButton();
    }

    private void initData() {
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);


    }

    private void initView(){

        editTextName= PayUpdateActivity.this.findViewById(R.id.edit_text_name);
        editTextMoney= PayUpdateActivity.this.findViewById(R.id.edit_text_money);
        editTextDate= PayUpdateActivity.this.findViewById(R.id.edit_text_date);

        position=getIntent().getIntExtra("position",0);
        String name = getIntent().getStringExtra("name");
        String money = getIntent().getStringExtra("money");
        String date = getIntent().getStringExtra("date");
        if(null!= name)
            editTextName.setText(name);
        if(null!= money)
            editTextMoney.setText(money);
        if(null!= date)
            editTextDate.setText(date);
    }

    private void initButton() {
        //确定键
        Button buttonOk = (Button)findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("name",editTextName.getText().toString());
            intent.putExtra("money",editTextMoney.getText().toString());
            intent.putExtra("date",editTextDate.getText().toString());
            intent.putExtra("position",position);
            setResult(RESULT_OK, intent);
            finish();
        });

        //日期选择器
        Button button_setdate = (Button)findViewById(R.id.button_setdate);
        button_setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 调用时间选择器
                new DatePickerDialog(v.getContext(),onDateSetListener, mYear, mMonth, mDay).show();
            }
        });
    }

    private final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }
            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }
            }
            editTextDate.setText(days);
    }
};
}