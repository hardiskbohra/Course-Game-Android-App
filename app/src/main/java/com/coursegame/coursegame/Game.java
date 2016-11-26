package com.coursegame.coursegame;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final AlertDialog builder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater1 = getLayoutInflater();


        View customView = inflater1.inflate(R.layout.loading_dialog, null);
        TextView tv=(TextView)customView.findViewById(R.id.loading_text);
        tv.setText("Loading Game");
        builder.setView(customView);
        builder.show();

        Intent in=getIntent();
        String url=in.getStringExtra("game_link");

        WebView mWebView = (WebView) findViewById(R.id.game_view);
        mWebView.loadUrl(url);


        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                builder.dismiss();
            }
        });
    }
}
