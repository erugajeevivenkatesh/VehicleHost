package com.nixinninsights.venkatesh.vehiclehost.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nixinninsights.venkatesh.vehiclehost.R;
import com.nixinninsights.venkatesh.vehiclehost.SharedPreference.UserDetailsLogin;

public class AccountInfo extends Fragment {
    TextView AccountName,AccountEmail,AccountPhone;
    String AccountInfo;
    Button Logout;
    UserDetailsLogin session;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            AccountInfo=getArguments().getString("AccountInformation");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.accountview,container,false);
        AccountName=view.findViewById(R.id.AccountName);
        session=new UserDetailsLogin(getContext());
        Logout=view.findViewById(R.id.LogoutUser);


        AccountEmail=view.findViewById(R.id.UserEmail);
        AccountPhone=view.findViewById(R.id.UserPhone);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                session.Logoutuser();
            }
        });

        return  view;
    }
}
