package com.example.spoor.realtimenotekeeping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OcrTextActivity extends AppCompatActivity {

    TextView textViewOcrText;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_text);

        textViewOcrText = (TextView)findViewById(R.id.textViewOcrText);

        if(getIntent().getStringExtra("ocrText")!=null){
            text = getIntent().getStringExtra("ocrText");
            textViewOcrText.setText(text);
        }
    }
}
