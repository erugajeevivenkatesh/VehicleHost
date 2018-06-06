package com.nixinninsights.venkatesh.vehiclehost;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import static android.view.Gravity.END;
import static android.view.Gravity.RIGHT;

public class OTPPhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);
         setupactivityanimations();
    }
    public void VerifyOTP(View view) {
     //  startActivity(new Intent(getApplicationContext(),RegisterUserData.class));
        Intent intent=new Intent(getApplicationContext(),RegisterUserData.class);
        ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent, options.toBundle());

    }
    public void sendOTP(View view) {

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
