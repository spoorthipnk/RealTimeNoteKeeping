package com.example.spoor.realtimenotekeeping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ExistingNotesActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_notes);

        mTextView = (TextView)findViewById(R.id.textViewBarcodeValue);
        Long value = getIntent().getLongExtra("value",0);
        mTextView.setText(value.toString());
    }
}
