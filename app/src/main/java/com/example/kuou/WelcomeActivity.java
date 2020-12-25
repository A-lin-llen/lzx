package com.example.kuou;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Log.i("info","Activity onCreate");
        Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @NonNull
    @Override
    protected void onStart(){
        super.onStart();
        Log.i("info","Activity onStart");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i("info","Activity onResume");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i("info","Activity onPause");
    }
    @Override
    protected void onStop (){
        super.onStop();
        Log.i("info","Activity onStop");
    }
    @Override
    protected void onDestroy (){
        super.onDestroy();
        Log.i("info","Activity onDestroy");
    }

    }
