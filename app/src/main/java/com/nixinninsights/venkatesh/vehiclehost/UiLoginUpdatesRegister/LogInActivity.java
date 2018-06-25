package com.nixinninsights.venkatesh.vehiclehost.UiLoginUpdatesRegister;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nixinninsights.venkatesh.vehiclehost.MapActivity;
import com.nixinninsights.venkatesh.vehiclehost.R;
import com.nixinninsights.venkatesh.vehiclehost.SharedPreference.UserDetailsLogin;
import com.nixinninsights.venkatesh.vehiclehost.Validations.FormsValidations;

public class LogInActivity extends AppCompatActivity {

    EditText Email,Password;
    TextView Errorid;
    UserDetailsLogin Usersession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Errorid=findViewById(R.id.ErrorId);
        Errorid.setVisibility(View.GONE);
        Email=findViewById(R.id.UserEmail);
        Password=findViewById(R.id.UserPass);
        Usersession=new UserDetailsLogin(getApplicationContext());
    }
    public void SignIn(View view) {
        FormsValidations formsValidations=new FormsValidations();
        if(formsValidations.UserEmailpassValiedation(Email,Password))
        {
            ckeckdetails(Email.getText().toString(),Password.getText().toString());

        }
        //startActivity(new Intent(getApplicationContext(),MapActivity.class));
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
    private void ckeckdetails(String Email,String pass)
    {
        //senddata to database and check the details of the database;
        Usersession.CreateUserLogin(Email,pass);
        startActivity(new Intent(getApplicationContext(),MapActivity.class));
        // startActivity(new Intent(getApplicationContext(),MapActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
