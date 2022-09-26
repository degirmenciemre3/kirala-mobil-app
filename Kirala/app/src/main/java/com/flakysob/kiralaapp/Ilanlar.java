package com.flakysob.kiralaapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Ilanlar extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilanlar);
        Intent gelenIntent = getIntent();
        String kategoriID = gelenIntent.getStringExtra("kategoriID");

        Log.w("deneme","Başarılı "+kategoriID);

    }
}