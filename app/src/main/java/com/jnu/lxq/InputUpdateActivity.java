package com.jnu.lxq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InputUpdateActivity extends AppCompatActivity {

    private int position;
    private String payName;
    private EditText editTextCatName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_update);

        position=getIntent().getIntExtra("position",0);
        payName=getIntent().getStringExtra("pay_name");

        editTextCatName= InputUpdateActivity.this.findViewById(R.id.edit_text_name);
        if(null!=payName)
            editTextCatName.setText(payName);

        Button buttonOk = (Button)findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(v -> {
            Intent intent = new Intent();

            intent.putExtra("pay_name",editTextCatName.getText().toString());
            intent.putExtra("pay_position",position);
            setResult(RESULT_OK, intent);

            finish();
        });
    }
}