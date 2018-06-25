package com.nixinninsights.venkatesh.vehiclehost.UiLoginUpdatesRegister;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nixinninsights.venkatesh.vehiclehost.R;

import java.util.Random;

public class ForgotPasswordOTP extends AppCompatActivity {

    EditText Phonenumber,Otp;
    Random random;
    TextView otpError;
    CardView otpVisibility;
    static int Randomnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_otp);
        random=new Random();
        otpError=findViewById(R.id.OtpError);
        otpError.setVisibility(View.INVISIBLE);
        otpVisibility=findViewById(R.id.otpVisibilityy);
        otpVisibility.setVisibility(View.INVISIBLE);
        Phonenumber=findViewById(R.id.USERPHONEE);
        Otp=findViewById(R.id.OTP);
    }

    public void VerifyOTP(View view) {
        if(Randomnumber==Integer.parseInt(Otp.getText().toString()))
        {
            Intent intent=new Intent(getApplicationContext(),UpdatePassword.class);
            ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this);
            startActivity(intent,optionsCompat.toBundle());
        }
        else
        { otpError.setVisibility(View.VISIBLE);

        }

    }

    public void sendOTP(View view) {
        Randomnumber=random.nextInt(9999)+1001;
        otpVisibility.setVisibility(View.VISIBLE);
        Toast.makeText(this, Integer.toString(Randomnumber), Toast.LENGTH_SHORT).show();

    }
}
