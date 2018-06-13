package com.nixinninsights.venkatesh.vehiclehost.UiLoginUpdatesRegister;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nixinninsights.venkatesh.vehiclehost.MapActivity;
import com.nixinninsights.venkatesh.vehiclehost.R;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }
    public void SignIn(View view) {
        startActivity(new Intent(getApplicationContext(),MapActivity.class));
    }
    public void RegisterUser(View view) {
        Intent intent=new Intent(getApplicationContext(),OTPPhoneActivity.class);
        ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent, options.toBundle());
    }
    public void Forgotpassword(View view) {
        Intent intent=new Intent(getApplicationContext(),ForgotPasswordOTP.class);
        ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent, options.toBundle());

    }
}
