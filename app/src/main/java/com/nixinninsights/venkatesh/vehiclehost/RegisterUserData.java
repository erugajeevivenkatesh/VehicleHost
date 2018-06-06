package com.nixinninsights.venkatesh.vehiclehost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import static android.view.Gravity.END;

public class RegisterUserData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_data);
        setupactivityanimations();
    }

    public void RegisterUserdata(View view) {
    }
    void setupactivityanimations()
    {
        Slide enterSlide = new Slide(END);
        enterSlide.setDuration(700);
        enterSlide.addTarget(R.id.OTPpassLayout);
        enterSlide.setInterpolator(new DecelerateInterpolator(2));
        getWindow().setEnterTransition(enterSlide);

        Slide returnSlide = new Slide(END);
        returnSlide.setDuration(700);
        returnSlide.addTarget(R.id.OTPpassLayout);
        returnSlide.setInterpolator(new DecelerateInterpolator());
        getWindow().setReturnTransition(returnSlide);

    }
}
