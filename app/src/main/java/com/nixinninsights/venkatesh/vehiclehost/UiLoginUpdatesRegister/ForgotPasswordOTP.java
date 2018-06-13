package com.nixinninsights.venkatesh.vehiclehost.UiLoginUpdatesRegister;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nixinninsights.venkatesh.vehiclehost.R;

public class ForgotPasswordOTP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_otp);
    }

    public void VerifyOTP(View view) {
        Intent intent=new Intent(getApplicationContext(),UpdatePassword.class);
        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent,optionsCompat.toBundle());
    }

    public void sendOTP(View view) {
    }
}
