package com.nixinninsights.venkatesh.vehiclehost.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nixinninsights.venkatesh.vehiclehost.DataforStorage;
import com.nixinninsights.venkatesh.vehiclehost.R;
import com.nixinninsights.venkatesh.vehiclehost.Validations.FormsValidations;

public class SaveVehicleDetails extends Fragment {
    FloatingActionButton flaoatbutton;

    EditText ROUTENO,FROMADDRESS,TOADDRESS,VEHICLEREGISTRATIONNO,DRIVERNAME;
    LinearLayout swapFromandTOaddress;
    DataforStorage dataforStorage;
    int Count=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.modifyroutedetails,container,false);
        dataforStorage=new DataforStorage(getContext());
        ROUTENO=view.findViewById(R.id.RouteNo);
        FROMADDRESS=view.findViewById(R.id.FromPlace);
        TOADDRESS=view.findViewById(R.id.Toplace);
        VEHICLEREGISTRATIONNO=view.findViewById(R.id.RegistrationNo);
        DRIVERNAME=view.findViewById(R.id.drivername);
        AlreadyInserted();
        swapFromandTOaddress=view.findViewById(R.id.SwapFromaddress);
        EditText[] Textsare={ROUTENO,FROMADDRESS,TOADDRESS,VEHICLEREGISTRATIONNO,DRIVERNAME};
        Editexteditable(Textsare,false);

        flaoatbutton=view.findViewById(R.id.editablebutton);
        flaoatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Count++;
                if(Count==1)
                {
                EditText[] Textsare={ROUTENO,FROMADDRESS,TOADDRESS,VEHICLEREGISTRATIONNO,DRIVERNAME};
                Editexteditable(Textsare,true);

                flaoatbutton.setImageResource(R.drawable.done);
               // flaoatbutton.setVisibility(View.INVISIBLE);
                }
                if(Count==2)
                {
                    SaveHostdetails();
                    Count=0;
                }
            }
        });

        swapFromandTOaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwapFromTO(FROMADDRESS,TOADDRESS);
            }
        });
        return view;
    }

    private void AlreadyInserted() {
        Cursor result=dataforStorage.getdata();
        if(result.getCount()==0)
        {
            Toast.makeText(getContext(), "Nodata", Toast.LENGTH_SHORT).show();
            EditText[] Textsare={ROUTENO,FROMADDRESS,TOADDRESS,VEHICLEREGISTRATIONNO,DRIVERNAME};
            Editexteditable(Textsare,true);
        }
        else {

            while (result.moveToNext())
            {
                ROUTENO.setText(result.getString(1));
                FROMADDRESS.setText(result.getString(2));
                TOADDRESS.setText(result.getString(3));
                VEHICLEREGISTRATIONNO.setText(result.getString(4));
                DRIVERNAME.setText(result.getString(5));

            }
            //  transistionOfFragments.Modifiyingdetails(getFragmentManager());
        }
    }

    private void SwapFromTO(EditText FROMADDRESS,EditText TOADDRESS) {
        FROMADDRESS.setAlpha(0.5f);
        TOADDRESS.setAlpha(0.5f);
        String Temp=TOADDRESS.getText().toString();
        TOADDRESS.setText(FROMADDRESS.getText().toString());
        FROMADDRESS.setText(Temp);
        FROMADDRESS.setAlpha(1f);
        TOADDRESS.setAlpha(1f);
    }
    public void Editexteditable(EditText[] editTexts,boolean b)
    {
        if(!b)
        {for(EditText a:editTexts) {

            a.setFocusable(false);
            a.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            a.setClickable(false);
            a.setAlpha(0.2f);// user navigates with wheel and selects widget
        }
        }
        else
        {
            for(EditText a:editTexts) {

            a.setFocusable(true);
            a.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
            a.setClickable(true);
            a.setAlpha(1);// user navigates with wheel and selects widget
        }
        }
    }
    public void SaveHostdetails()
    {
        FormsValidations formsValidations=new FormsValidations();
        if(formsValidations.ValidateHostdetails(ROUTENO,FROMADDRESS,TOADDRESS,VEHICLEREGISTRATIONNO,DRIVERNAME)) {

            Cursor data = dataforStorage.getdata();
            if (data.getCount() == 0) {
                boolean result = dataforStorage.Insertdata(ROUTENO.getText().toString(), FROMADDRESS.getText().toString(),
                        TOADDRESS.getText().toString(), VEHICLEREGISTRATIONNO.getText().toString(), DRIVERNAME.getText().toString());
                if (result) {

                    Toast.makeText(getContext(),"dataInsertedSuccessfully", Toast.LENGTH_SHORT).show();
                }
            } else {
                boolean result = dataforStorage.Insertdata(ROUTENO.getText().toString(), FROMADDRESS.getText().toString(),
                        TOADDRESS.getText().toString(), VEHICLEREGISTRATIONNO.getText().toString(), DRIVERNAME.getText().toString());
                if (result) {
                     Toast.makeText(getContext(),"updated sucessfully", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }
            }
        }
    }



}
