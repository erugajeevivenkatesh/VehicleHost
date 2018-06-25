package com.nixinninsights.venkatesh.vehiclehost.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nixinninsights.venkatesh.vehiclehost.CustomArrayList.VehicleData;
import com.nixinninsights.venkatesh.vehiclehost.MapActivity;
import com.nixinninsights.venkatesh.vehiclehost.R;
import com.nixinninsights.venkatesh.vehiclehost.Urls.UrlData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleInfo extends Fragment {
    String VehiclePerson;
    TextView VehiclerouteNO,VehicleFromAddress,VehicleToaddress,Drivername,VehicleNo;
    ProgressBar loadingbar;
    LinearLayout vehicledata;
    JSONObject VehicleTrackerDetails;
    public List<VehicleData> Vehicleinformation;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            VehiclePerson=getArguments().getString("HostPerson");
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.vehiclleinfofragment,container,false);
        loadingbar=view.findViewById(R.id.LoadingProgressbar);
        vehicledata=view.findViewById(R.id.Vehicledata);
        Vehicleinformation=new ArrayList<>();
        VehiclerouteNO=view.findViewById(R.id.VehicleRouteNo);
        VehicleFromAddress=view.findViewById(R.id.VehicleFromAddress);
        VehicleToaddress=view.findViewById(R.id.VehicleTOaddress);
        Drivername=view.findViewById(R.id.DriverName);
        VehicleNo=view.findViewById(R.id.VehicleNo);
        getHostDeatils();
      //  Toast.makeText(getContext(), VehiclePerson, Toast.LENGTH_SHORT).show();
        return view;
    }
    public void getHostDeatils()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlData.GetHostData, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   progressDialog.dismiss();
                if(response!=null)
                {loadingbar.setVisibility(View.GONE);
                    vehicledata.setVisibility(View.VISIBLE);
                    JsonsData(response);
                   // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                }
            }}
                ,new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        )
        {
            @Override
            protected Map<String,String> getParams()
            {   Map<String,String> params=new HashMap<String, String>();
                params.put("HOST_EMAIL",VehiclePerson);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    public void JsonsData(String vdata)
    {
        try{
            JSONObject jsonObject = new JSONObject(vdata);
            JSONArray result = jsonObject.getJSONArray("result");
            VehicleTrackerDetails=result.getJSONObject(0);

            VehicleToaddress.setText(VehicleTrackerDetails.getString("ToAddress"));
            VehicleFromAddress.setText(VehicleTrackerDetails.getString("FromAddress"));
            Drivername.setText(VehicleTrackerDetails.getString("HostPerson"));
            VehicleNo.setText(VehicleTrackerDetails.getString("VehicleNo"));
            VehiclerouteNO.setText(VehicleTrackerDetails.getString("BusRouteno"));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
