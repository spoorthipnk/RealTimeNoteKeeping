package com.example.spoor.realtimenotekeeping;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiEnterpriseConfig;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BarcodeActivity extends AppCompatActivity {

    String barcode;
    WebView webViewBarcode;
    Button buttonSave, buttonDiscard;
    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        if(getIntent().getStringExtra("barcode")!=null){
            barcode = getIntent().getStringExtra("barcode");
        }
        setUpWebView(barcode);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        ValueEventListener barcodeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                BarcodeObject barcodeObject = dataSnapshot.getValue(BarcodeObject.class);
                Log.d("hello",barcodeObject.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("msg", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabaseReference.addValueEventListener(barcodeListener);

    }



    private void setUpWebView(final String barcode) {
        String url = "https://www.barcodelookup.com/"+barcode;

        webViewBarcode = (WebView)findViewById(R.id.webViewBarcode);
        webViewBarcode.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webViewBarcode.loadUrl(url);
        buttonSave = (Button)findViewById(R.id.buttonSaveBarcodeNote);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference newRef = mDatabaseReference.child("Barcode").push();
                newRef.setValue(barcode);
                Intent intent = new Intent(BarcodeActivity.this,MainScreenActivity.class);
                startActivity(intent);
            }
        });
        buttonDiscard = (Button)findViewById(R.id.buttonDiscardBarcodeNote);
        buttonDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BarcodeActivity.this.finish();
            }
        });

    }
}
