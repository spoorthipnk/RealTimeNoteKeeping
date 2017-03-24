package com.example.spoor.realtimenotekeeping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesBarcodeActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private ListView listViewBarcodeNotes;
    ArrayList<String> barcodeList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_barcode);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        listViewBarcodeNotes = (ListView)findViewById(R.id.listViewBarcodeNotes);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,barcodeList);


       DatabaseReference barcode_notes = databaseReference.child("Ocr");
       barcode_notes.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {

               String value = dataSnapshot.getValue(String.class);
               barcodeList.add(value);
               arrayAdapter.notifyDataSetChanged();
           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });


        Log.i("spoorthi",String.valueOf(barcodeList.size()));

        listViewBarcodeNotes.setAdapter(arrayAdapter);

    }


}
