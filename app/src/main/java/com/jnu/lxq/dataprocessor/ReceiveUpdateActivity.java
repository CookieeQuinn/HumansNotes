package com.jnu.lxq.dataprocessor;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jnu.lxq.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.sql.Types.NULL;

public class ReceiveUpdateActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextMoney;
    private EditText editTextDate;
    private Spinner spinnertext;

    private int reason_position;
    private int position;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_update_reaceive);

        initData();

        initView();

        initSpinner();

        initButton();
    }

    private void initData() {
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

    }

    private void initView(){

        spinnertext = ReceiveUpdateActivity.this.findViewById(R.id.spinner_receive_reason);
        editTextName= ReceiveUpdateActivity.this.findViewById(R.id.edit_text_name);
        editTextMoney= ReceiveUpdateActivity.this.findViewById(R.id.edit_text_money);
        editTextDate= ReceiveUpdateActivity.this.findViewById(R.id.edit_text_date);

        position=getIntent().getIntExtra("position",0);
        String name = getIntent().getStringExtra("name");
        String money = getIntent().getStringExtra("money");
        String date = getIntent().getStringExtra("date");
        reason_position = getIntent().getIntExtra("reason",0);

        if(null!= name)
            editTextName.setText(name);
        if(null!= money)
            editTextMoney.setText(money);
        if(null!= date)
            editTextDate.setText(date);
        if(NULL != reason_position)
            spinnertext.setSelection(reason_position);
    }

    private void initSpinner() {
        //第一步：定义下拉列表内容
        ReasonDataBank reasonDataBank = new ReasonDataBank(this);
        reasonDataBank.Load();
        ArrayList<String> list = reasonDataBank.getReasonList();
        //第二步：为下拉列表定义一个适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        //第三步：设置下拉列表下拉时的菜单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinnertext.setAdapter(adapter);
        //第五步：添加监听器，为下拉列表设置事件的响应
        spinnertext.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /* 将 spinnertext 显示^*/
                parent.setVisibility(View.VISIBLE);
                reason_position = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initButton() {
        //确定键
        Button buttonOk = (Button)findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("name",editTextName.getText().toString());
            intent.putExtra("money",editTextMoney.getText().toString());
            intent.putExtra("date",editTextDate.getText().toString());
            intent.putExtra("reason",reason_position);
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