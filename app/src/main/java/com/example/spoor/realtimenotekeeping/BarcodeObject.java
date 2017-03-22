package com.example.spoor.realtimenotekeeping;

/**
 * Created by spoor on 2/13/2017.
 */

public class BarcodeObject {

    public String barcode_name;
    public Long barcode_value;

    public String getBarcode_name() {
        return barcode_name;
    }

    public void setBarcode_name(String barcode_name) {
        this.barcode_name = barcode_name;
    }

    public Long getBarcode_value() {
        return barcode_value;
    }

    public void setBarcode_value(Long barcode_value) {
        this.barcode_value = barcode_value;
    }

    public BarcodeObject(){}
    public BarcodeObject(String barcode_name, Long barcode_value) {
        this.barcode_name = barcode_name;
        this.barcode_value = barcode_value;
    }
}
