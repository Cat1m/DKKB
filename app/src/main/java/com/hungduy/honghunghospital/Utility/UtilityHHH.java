package com.hungduy.honghunghospital.Utility;

import static com.hungduy.honghunghospital.Utility.ApiUtils.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.hungduy.honghunghospital.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UtilityHHH {
    private static String TAG="Utility";
    public static String getSha512fromString(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] digest = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString().toUpperCase();
    }

    public static String getFileFromURI(String URI){
        return URI.split("/")[URI.split("/").length-1];
    }

    public static String Encrypt(String toEncrypt) {
        return  "";
    }

    public static int toInt(String i){
        try{
            return Integer.parseInt(i);
        }catch (Exception e){
            return 0;
        }
    }

    public static BigDecimal getbigDecima(String i){
        try{
            return new BigDecimal(i);
        }catch (Exception e){
            return new BigDecimal(0);
        }
    }

    public static Double toDouble(String i){
        try{
            return Double.parseDouble(i);
        }catch (Exception e){
            return 0.0;
        }
    }

    public interface DataCallback {
        void CalllBack();
    }

    static DateFormat dateFormatwithTime = new SimpleDateFormat("MM/dd/yyyy hh:mm");
    static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    static DateFormat dateFormatForUser = new SimpleDateFormat("dd/MM/yyyy");
    static DateFormat dateFormatForUserWithTime = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    static String ngaybatdau;

    @SuppressLint("ClickableViewAccessibility")
    public static void edtDateWithEnableDate(FragmentManager a, EditText edt, Calendar[] calendar){
        edt.setKeyListener(null);
        edt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                edt.setEnabled(false);
                final Calendar c = Calendar.getInstance();
                int mYear=2022;
                int mMonth=0;
                int mDay=1;
                try{
                    String edtDate = edt.getText().toString();
                    mYear = UtilityHHH.toInt(edtDate.split("/")[2]);
                    mMonth = UtilityHHH.toInt(edtDate.split("/")[1]) - 1;
                    mDay = UtilityHHH.toInt(edtDate.split("/")[0]);
                }catch (Exception e){
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                }
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String date = (dayOfMonth < 10 ? "0"+dayOfMonth : dayOfMonth+"" )+ "/" + (monthOfYear+1 < 10 ? "0"+(monthOfYear+1) : monthOfYear+1+"") + "/" +  year;
                        edt.setText(date);
                        edt.setEnabled(true);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.setSelectableDays(calendar);
                datePickerDialog.setOkText("Chọn");
                datePickerDialog.setCancelText("Hủy");
                datePickerDialog.setLocale(new Locale("vi"));
                datePickerDialog.show(a , "1");
                return false;
            }
        });
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void edtDate(FragmentManager a, EditText edt,boolean nextdayisMin){
        edt.setKeyListener(null);
        edt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                edt.setEnabled(false);
                final Calendar c = Calendar.getInstance();
                int mYear=2022;
                int mMonth=0;
                int mDay=1;
                try{
                    String edtDate = edt.getText().toString();
                    mYear = UtilityHHH.toInt(edtDate.split("/")[2]);
                    mMonth = UtilityHHH.toInt(edtDate.split("/")[1]) - 1;
                    mDay = UtilityHHH.toInt(edtDate.split("/")[0]);
                }catch (Exception e){
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                }
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String date = (dayOfMonth < 10 ? "0"+dayOfMonth : dayOfMonth+"" )+ "/" + (monthOfYear+1 < 10 ? "0"+(monthOfYear+1) : monthOfYear+1+"") + "/" +  year;
                        edt.setText(date);
                        edt.setEnabled(true);
                    }
                }, mYear, mMonth, mDay);
                if(nextdayisMin){
                    if(c.get(Calendar.HOUR_OF_DAY) > 17){
                        c.add(Calendar.DATE,1);
                    }
                    datePickerDialog.setMinDate(c);
                }
                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        edt.setEnabled(true);
                    }
                });
                datePickerDialog.setOkText("Chọn");
                datePickerDialog.setCancelText("Hủy");
                datePickerDialog.setLocale(new Locale("vi"));
                datePickerDialog.show(a, "2");
                return false;
            }
        });
    }


    public static void datePicker(Activity a, final boolean callTime, EditText edtTime, EditText edtEnableNext, Date minDate, Date maxDate, String ngay, DataCallback callback){
        final Calendar c = Calendar.getInstance();
        int mYear=2022;
        int mMonth=0;
        int mDay=1;
        try{
            String edtDate = edtTime.getText().toString();
            if(edtDate.split("/").length == 3 && !callTime){
                mYear = toInt(edtDate.split("/")[2]);
                mMonth = toInt(edtDate.split("/")[1]) - 1;
                mDay = toInt(edtDate.split("/")[0]);
            }else{
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
            }
        }catch (Exception e){
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(a,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ngaybatdau= (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                        if(!callTime) {
                            edtTime.setEnabled(true);
                            try {
                                if(edtEnableNext != null) {
                                    edtEnableNext.setEnabled(true);
                                }
                                if(minDate!=null?dateFormat.parse(ngaybatdau).before(minDate):false ||  (maxDate!=null?dateFormat.parse(ngaybatdau).after(maxDate):false)){
                                    new FancyGifDialog.Builder(a)
                                            .setTitle("Thông tin chưa chính xác")
                                            .setMessage("Ngày "+ngay+" không thể "+(dateFormat.parse(ngaybatdau).before(minDate)?"trước ngày "+
                                                    dateFormatForUser.format(minDate):"sau ngày "+ dateFormatForUser.format(maxDate) +"!!"))
                                            .setPositiveBtnBackground("#FF4081")
                                            .setPositiveBtnText("OK")
                                            .setGifResource(R.drawable.time_shake)
                                            .OnPositiveClicked(new FancyGifDialogListener() {
                                                @Override
                                                public void OnClick() {
                                                    edtTime.setText("Chọn ngày");
                                                    if(edtEnableNext != null) edtEnableNext.setEnabled(false);
                                                }
                                            })
                                            .build();
                                }else{
                                    edtTime.setText(dateFormatForUser.format(dateFormat.parse(ngaybatdau)));
                                    callback.CalllBack();
                                }
                            } catch (Exception e) {
                                Log.d(TAG,e.getMessage());
                            }
                        }else{
                            timePicker(a,edtTime,edtEnableNext, minDate,maxDate,ngay);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                edtTime.setEnabled(true);
                edtTime.setText("Chọn ngày");
            }
        });
        datePickerDialog.show();
    }

    public static void datePicker(Activity a, final boolean callTime, EditText edtTime, EditText edtEnableNext, Date minDate, Date maxDate, String ngay){
        final Calendar c = Calendar.getInstance();
        int mYear=2022;
        int mMonth=0;
        int mDay=1;
        try{
            String edtDate = edtTime.getText().toString();
            if(edtDate.split("/").length == 3 && !callTime){
                mYear = toInt(edtDate.split("/")[2]);
                mMonth = toInt(edtDate.split("/")[1]) - 1;
                mDay = toInt(edtDate.split("/")[0]);
            }else{
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
            }
        }catch (Exception e){
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(a,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ngaybatdau= (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                        if(!callTime) {
                            edtTime.setEnabled(true);
                            try {
                                if(edtEnableNext != null) {
                                    edtEnableNext.setEnabled(true);
                                }
                                if(minDate!=null?dateFormat.parse(ngaybatdau).before(minDate):false ||  (maxDate!=null?dateFormat.parse(ngaybatdau).after(maxDate):false)){
                                    new FancyGifDialog.Builder(a)
                                            .setTitle("Thông tin chưa chính xác")
                                            .setMessage("Ngày "+ngay+" không thể "+(dateFormat.parse(ngaybatdau).before(minDate)?"trước ngày "+
                                                    dateFormatForUser.format(minDate):"sau ngày "+ dateFormatForUser.format(maxDate) +"!!"))
                                            .setPositiveBtnBackground("#FF4081")
                                            .setPositiveBtnText("OK")
                                            .setGifResource(R.drawable.time_shake)
                                            .OnPositiveClicked(new FancyGifDialogListener() {
                                                @Override
                                                public void OnClick() {
                                                    edtTime.setText("Chọn ngày");
                                                    if(edtEnableNext != null) edtEnableNext.setEnabled(false);
                                                }
                                            })
                                            .build();
                                }else{
                                    edtTime.setText(dateFormatForUser.format(dateFormat.parse(ngaybatdau)));
                                }
                            } catch (Exception e) {
                                Log.d(TAG,e.getMessage());
                            }
                        }else{
                            timePicker(a,edtTime,edtEnableNext, minDate,maxDate,ngay);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                edtTime.setEnabled(true);
                edtTime.setText("Chọn ngày");
            }
        });
        datePickerDialog.show();
    }





    public static Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, imageName); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i(TAG, "image saved to >>>" + myImageFile.getAbsolutePath());

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {}
            }
        };
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }

    private static void timePicker(Activity a,EditText edtTime,EditText edtEnableNext,Date minDate,Date maxDate,String ngay){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(a,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.d(TAG,"Ngay bat dau: "+ ngaybatdau +" "+hourOfDay + ":" + minute);
                        ngaybatdau = ngaybatdau+" "+hourOfDay + ":" + minute;
                        edtTime.setEnabled(true);
                        try {
                            if(edtEnableNext != null) edtEnableNext.setEnabled(true);
                            edtTime.setText(dateFormatForUserWithTime.format(dateFormatwithTime.parse(ngaybatdau)));
                            if(dateFormat.parse(ngaybatdau).before(minDate) || (maxDate!=null?dateFormat.parse(ngaybatdau).after(maxDate):false) ){
                                new FancyGifDialog.Builder(a)
                                        .setTitle("Thông tin chưa chính xác")
                                        .setMessage("Ngày "+ngay+" không thể "+(dateFormat.parse(ngaybatdau).before(minDate)?"trước ngày "+
                                                dateFormatForUser.format(minDate):"sau ngày "+ dateFormatForUser.format(maxDate) +"!!"))
                                        .setPositiveBtnBackground("#FF4081")
                                        .setPositiveBtnText("OK")
                                        .setGifResource(R.drawable.time_shake)
                                        .OnPositiveClicked(new FancyGifDialogListener() {
                                            @Override
                                            public void OnClick() {
                                                edtTime.setText("Chọn ngày");
                                                if(edtEnableNext != null) edtEnableNext.setEnabled(false);
                                            }
                                        })
                                        .build();
                            }
                        } catch (Exception e) {
                            Log.d(TAG,e.getMessage());
                        }
                    }
                }, mHour, mMinute, true);
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                edtTime.setEnabled(true);
                edtTime.setText("Chọn ngày");
            }
        });
        timePickerDialog.show();
    }
}
