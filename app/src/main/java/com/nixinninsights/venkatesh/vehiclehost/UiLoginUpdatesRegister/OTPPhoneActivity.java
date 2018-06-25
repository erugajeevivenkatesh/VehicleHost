package com.nixinninsights.venkatesh.vehiclehost.UiLoginUpdatesRegister;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Slide;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nixinninsights.venkatesh.vehiclehost.R;

import java.util.Random;

import static android.view.Gravity.END;

public class OTPPhoneActivity extends AppCompatActivity {

    Random random;
      static int picknumber;
      CardView OtpVisiblility;
      EditText Otp;
      TextView otperro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);
        otperro=findViewById(R.id.verifyOTPP);
        otperro.setVisibility(View.GONE);
        OtpVisiblility=findViewById(R.id.otpVisibility);
        OtpVisiblility.setVisibility(View.INVISIBLE);
        Otp=findViewById(R.id.OTP);
         random=new Random();
         setupactivityanimations();
    }
    public void VerifyOTP(View view) {
     //  startActivity(new Intent(getApplicationContext(),RegisterUserData.class));
        if(picknumber==Integer.parseInt(Otp.getText().toString())) {


            Intent intent = new Intent(getApplicationContext(), RegisterUserData.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
            startActivity(intent, options.toBundle());
        }
        else
        otperro.setVisibility(View.VISIBLE);
    }
    public void sendOTP(View view) {
         picknumber=random.nextInt(9999)+1001;
         OtpVisiblility.setVisibility(View.VISIBLE);
        Toast.makeText(this,Integer.toString(picknumber) , Toast.LENGTH_SHORT).show();
    }

    void setupactivityanimations()
    {
        Slide enterSlide = new Slide(END);
        enterSlide.setDuration(700);
        enterSlide.addTarget(R.id.Signuplayout);
        enterSlide.setInterpolator(new DecelerateInterpolator(2));
        getWindow().setEnterTransition(enterSlide);

        Slide returnSlide = new Slide(END);
        returnSlide.setDuration(700);
        returnSlide.addTarget(R.id.Signuplayout);
        returnSlide.setInterpolator(new DecelerateInterpolator());
        getWindow().setReturnTransition(returnSlide);

    }

}
