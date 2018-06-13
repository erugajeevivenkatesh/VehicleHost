package com.nixinninsights.venkatesh.vehiclehost.Validations;

import android.text.TextUtils;
import android.widget.EditText;

public class FormsValidations {
    public  boolean  ValidateHostdetails(EditText ROUTENO,EditText FROMADDRESS,EditText TOADDRESS,EditText VEHICLEREGISTRATIONNO,
                                        EditText DRIVERNAME)
    {
        boolean  valid=true;
        if(TextUtils.isEmpty(ROUTENO.getText().toString()))
        {
            ROUTENO.setError("Enter Routedetails");
            valid=false;
        }
        else
        {
            ROUTENO.setError(null);
        }
        if(TextUtils.isEmpty(FROMADDRESS.getText().toString()))
        {
            FROMADDRESS.setError("Enter FromAddress");
            valid=false;
        }
        else
        {
            FROMADDRESS.setError(null);
        }
        if(TextUtils.isEmpty(TOADDRESS.getText().toString()))
        {
            TOADDRESS.setError("Enter Toaddress");
            valid=false;
        }
        else
        {
            TOADDRESS.setError(null);
        }
        if(TextUtils.isEmpty(VEHICLEREGISTRATIONNO.getText().toString()))
        {
            VEHICLEREGISTRATIONNO.setError("Enter VehicleRegistration");
            valid=false;
        }
        else
        {
            VEHICLEREGISTRATIONNO.setError(null);
        }
        if(TextUtils.isEmpty(DRIVERNAME.getText().toString()))
        {
            DRIVERNAME.setError("Enter DriverName");
            valid=false;
        }
        else
        {
            DRIVERNAME.setError(null);
        }
        return valid;
    }
}
