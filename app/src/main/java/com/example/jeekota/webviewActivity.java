package com.example.jeekota;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class webviewActivity extends AppCompatActivity {


    WebView view;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        view=findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);

        String url=null;
        String pdfName=null;
        if(getIntent()!=null){
           url= getIntent().getStringExtra("muri");
           pdfName=getIntent().getStringExtra("pdf_name");
        }
        else{
            Toast.makeText(this,"netowrk problem",Toast.LENGTH_LONG).show();
        }


        final ProgressDialog p= new ProgressDialog(this);
        p.setTitle(pdfName);
        p.setMessage("file is opening....!!");

        view.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url)
                ;
                p.dismiss();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                p.show();

            }
        });

        String murl="";
        try {
            murl= URLEncoder.encode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        // view.loadUrl(url);
        view.loadUrl("http://docs.google.com/gview?embedded=true&url="+murl);
    }
}