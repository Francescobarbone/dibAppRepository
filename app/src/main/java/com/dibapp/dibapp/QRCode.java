package com.dibapp.dibapp;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCode {

    ImageView image;

    public QRCode(String str){
        MultiFormatWriter mfw  = new MultiFormatWriter();
        try{
            BitMatrix btmx = mfw.encode(str, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder bce = new BarcodeEncoder();
            Bitmap btp = bce.createBitmap(btmx);
            image.setImageBitmap(btp);
        }catch(WriterException e){
            e.printStackTrace();
        }
    }
}
