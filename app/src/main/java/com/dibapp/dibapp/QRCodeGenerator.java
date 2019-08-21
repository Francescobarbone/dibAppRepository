package com.dibapp.dibapp;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeGenerator {

    ImageView image;
    
    public QRCodeGenerator(final int id){
        MultiFormatWriter mfw = new MultiFormatWriter();
        try{
            BitMatrix bmx = mfw.encode(Integer.toString(id), BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder be = new BarcodeEncoder();
            Bitmap bmp = be.createBitmap(bmx);
            image.setImageBitmap(bmp);
        } catch(WriterException e){
            e.printStackTrace();
        }
    }
}
