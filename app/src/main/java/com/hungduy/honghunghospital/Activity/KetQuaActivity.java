package com.hungduy.honghunghospital.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hungduy.honghunghospital.Adapter.KhaiBaoYTeAdapter;
import com.hungduy.honghunghospital.Database.Model.KetQuaLuu;
import com.hungduy.honghunghospital.Model.ResponseModel;
import com.hungduy.honghunghospital.Model.extModel.CauHoiKhaiBaoYTeEXT;
import com.hungduy.honghunghospital.Model.getModel.getCauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Model.getModel.getMaTen;
import com.hungduy.honghunghospital.Model.setModel.setDangKyKham;
import com.hungduy.honghunghospital.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jrizani.jrspinner.JRSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KetQuaActivity extends BaseActivity {

    private Button btnLuu;
    private ConstraintLayout Layout_Sang_Loc;
    private boolean isTestCovid =false;
    private TextView txtKetQuaKham;
    private String noidungkham = "";
    private String dv = "";
    private int loai;
    private ImageView imgBarCode;
    private String QR="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua);

        Intent LoginIntent= getIntent();
        Bundle bundle = LoginIntent.getExtras();
        if(bundle!=null)
        {
            isTestCovid =(boolean) bundle.get("isTestCovid");
            noidungkham =(String) bundle.get("noidungkham");
            dv = (String) bundle.get("dv");
            loai = (int) bundle.get("loai");
            QR = (String) bundle.get("QR");
        }
        mapView();


        if(!noidungkham.isEmpty()){
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    txtKetQuaKham.setText(Html.fromHtml(noidungkham, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    txtKetQuaKham.setText(Html.fromHtml(noidungkham));
                }
            }catch (Exception ex){

            }
        }
        Layout_Sang_Loc.setVisibility( isTestCovid ? View.VISIBLE : View.GONE);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ketQuaLuuDAO.insert(new KetQuaLuu(QR,noidungkham,isTestCovid? 1:0,loai));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });
                    }
                }).start();
            }
        });

        try {
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap =new HashMap<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            int width =300,height = 300;
            int smallestDimension = width < height ? width : height;
            CreateQRCode(QR, charset, hintMap, smallestDimension, smallestDimension);
        }catch (Exception ex){

        }
    }

    public  void CreateQRCode(String qrCodeData, String charset, Map hintMap, int qrCodeheight, int qrCodewidth){
        try {
            //generating qr code in bitmatrix type
            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
            //converting bitmatrix to bitmap

            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            // All are 0, or black, by default
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    //pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
                    pixels[offset + x] = matrix.get(x, y) ?
                            ResourcesCompat.getColor(getResources(),R.color.black,null) :
                            ResourcesCompat.getColor(getResources(), android.R.color.transparent,null);
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            //setting bitmap to image view

            Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.ic_laucher);

            imgBarCode.setImageBitmap(bitmap);

        }catch (Exception er){
            Log.e("QrGenerate",er.getMessage());
        }
    }

    private void mapView() {
        btnLuu = findViewById(R.id.btnLuu);
        txtKetQuaKham = findViewById(R.id.txtKetQuaKham);
        imgBarCode = findViewById(R.id.imgBarCode);
        Layout_Sang_Loc = findViewById(R.id.Layout_Sang_Loc);
    }
}