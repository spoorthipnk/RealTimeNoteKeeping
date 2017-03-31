package com.example.spoor.realtimenotekeeping;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.spoor.realtimenotekeeping.BarcodeModule.BarcodeScannerActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ImageView imgBarCodeScanner;
    private ImageView imgPictureCapture;
    private static final int RC_BARCODE_CAPTURE = 9001;
    String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageToUploadUri;
    private ImageView imageViewOcr, imageViewLocation;
    Button buttonExistingNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        imgBarCodeScanner = (ImageView) findViewById(R.id.imageViewBarCodeScanner);
        imgBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BarcodeScannerActivity.class);
                startActivityForResult(intent, RC_BARCODE_CAPTURE);

            }
        });

        imgPictureCapture = (ImageView) findViewById(R.id.imageViewCamera);
        imgPictureCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dispatchTakePictureIntent();
            }
        });

        imageViewOcr = (ImageView)findViewById(R.id.imageViewOcr);
        imageViewOcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OcrActivity.class);
                startActivity(intent);
            }
        });

        imageViewLocation = (ImageView)findViewById(R.id.imageViewLocation);
        imageViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LocationActivity.class);
                startActivity(intent);
            }
        });

/*        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images");
        StorageReference spaceRef = storageRef.child("images/space.jpg");
        String fileName = "space.jpg";
        spaceRef = imagesRef.child(fileName);
        String path = spaceRef.getPath();


*/

    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.spoor.realtimenotekeeping",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeScannerActivity.BarcodeObject);
                    Intent intent = new Intent(MainActivity.this, BarcodeActivity.class);
                    String keyword = barcode.displayValue;
                    intent.putExtra("barcode", keyword);
                    startActivity(intent);
                } else {
                    Log.d("msg", "No barcode captured, intent data is null");
                }
            } else {
                Log.d("msg", String.format("Error reading barcode: %1s", CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == CommonStatusCodes.SUCCESS){
                Intent intent = new Intent(MainActivity.this,PictureActivity.class);
                if(data!=null){

                }
            }
        }
    }
}
