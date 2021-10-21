package com.hungduy.honghunghospital.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hungduy.honghunghospital.R;

public class WebviewActivity extends BaseActivity {
    private WebView webView;
    private Button btnThoat;
    private String url;
    private ImageView imgLogoBVHH,imgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mapView();

        imgLogoBVHH = findViewById(R.id.imgLogoBVHH);
        imgLogoBVHH.setEnabled(false);

        imgUser = findViewById(R.id.imgUser);
        imgUser.setEnabled(false);

        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            try{
                url =(String) bundle.get("url");
                //  isConnected = (Boolean) BundleLogin.get("isConnected");
            }catch (Exception ex){
                url = "https://honghunghospital.com.vn";
            }
        }
        if(url==null || url.isEmpty()){
            url = "https://honghunghospital.com.vn";
        }
        webView.setBackgroundColor(Color.TRANSPARENT);
    //    webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        webView.getSettings().setJavaScriptEnabled(true); // enable javascript

        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebviewActivity.this, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        webView.loadUrl(url);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void mapView() {
        webView = findViewById(R.id.webView);
        btnThoat = findViewById(R.id.btnThoat);
    }
}