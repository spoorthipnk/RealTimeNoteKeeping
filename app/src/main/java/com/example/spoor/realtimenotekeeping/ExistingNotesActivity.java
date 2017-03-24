package com.example.spoor.realtimenotekeeping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExistingNotesActivity extends AppCompatActivity {

    private Button barcodeNotesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_notes);

        barcodeNotesButton = (Button)findViewById(R.id.buttonBarcodeNotes);
        barcodeNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExistingNotesActivity.this,NotesBarcodeActivity.class);
                startActivity(intent);
            }
        });
    }
}
