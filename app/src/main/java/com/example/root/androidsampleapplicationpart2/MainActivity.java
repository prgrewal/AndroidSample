package com.example.root.androidsampleapplicationpart2;

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

    public void handleRegister (View view) {startRegister();}

    private void startRegister() {
        Intent intent = new Intent(MainActivity.this, CompanyRegister.class);
        startActivityForResult(intent, CompanyRegister.EXTRA_REQUEST);
    }

    public void handleLogin (View view) {startLogin();}

    private void startLogin() {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivityForResult(intent, MenuActivity.EXTRA_REQUEST);
    }
}
