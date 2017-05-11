package com.example.rakesh.joke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this,DelayedMessageService.class);
        intent.putExtra("me",getResources().getString(R.string.button_response));
        startService(intent);
    }
}

