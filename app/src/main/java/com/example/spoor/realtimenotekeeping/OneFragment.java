package com.example.spoor.realtimenotekeeping;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.spoor.realtimenotekeeping.BarcodeModule.BarcodeScannerActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {
    private ImageView imgBarCodeScanner;
    private ImageView imgPictureCapture;
    private static final int RC_BARCODE_CAPTURE = 9001;
    String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageToUploadUri;
    private ImageView imageViewOcr, imageViewLocation;
    Button buttonExistingNotes;

    public OneFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_one, container, false);
        imgBarCodeScanner = (ImageView) view.findViewById(R.id.imageViewBarCodeScanner);
        imgBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BarcodeScannerActivity.class);
                startActivityForResult(intent, RC_BARCODE_CAPTURE);

            }
        });

        imgPictureCapture = (ImageView) view.findViewById(R.id.imageViewCamera);
        imgPictureCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dispatchTakePictureIntent();
            }
        });

        imageViewOcr = (ImageView)view.findViewById(R.id.imageViewOcr);
        imageViewOcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),OcrActivity.class);
                startActivity(intent);
            }
        });

        imageViewLocation = (ImageView)view.findViewById(R.id.imageViewLocation);
        imageViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LocationActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeScannerActivity.BarcodeObject);
                    Intent intent = new Intent(getActivity(), BarcodeActivity.class);
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

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                Intent intent = new Intent(getActivity(), PictureActivity.class);
                if (data != null) {

                }
            }
        }
    }

}
