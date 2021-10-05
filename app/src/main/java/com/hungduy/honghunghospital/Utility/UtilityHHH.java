package com.hungduy.honghunghospital.Utility;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.hungduy.honghunghospital.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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


    static DateFormat dateFormatwithTime = new SimpleDateFormat("MM/dd/yyyy hh:mm");
    static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    static DateFormat dateFormatForUser = new SimpleDateFormat("dd/MM/yyyy");
    static DateFormat dateFormatForUserWithTime = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    static String ngaybatdau;
    public static void datePicker(Activity a, final boolean callTime, EditText edtTime, EditText edtEnableNext, Date minDate, Date maxDate, String ngay){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(a,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ngaybatdau= (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                        if(!callTime) {
                            edtTime.setEnabled(true);
                            try {
                                if(edtEnableNext != null) edtEnableNext.setEnabled(true);
                                edtTime.setText(dateFormatForUser.format(dateFormat.parse(ngaybatdau)));
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
