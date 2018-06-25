package com.nixinninsights.venkatesh.vehiclehost.Transisions;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;

import com.nixinninsights.venkatesh.vehiclehost.Fragments.AccountInfo;
import com.nixinninsights.venkatesh.vehiclehost.Fragments.SaveVehicleDetails;
import com.nixinninsights.venkatesh.vehiclehost.Fragments.VehicleInfo;
import com.nixinninsights.venkatesh.vehiclehost.R;

public class TransistionOfFragments   {
    public void MovingNewfragment(String Hostname ,FragmentManager fm)
    {
        Bundle bundle=new Bundle();
        //passing Host person name for searching the database i
        bundle.putString("HostPerson",Hostname);
        VehicleInfo vehicle=new VehicleInfo();

        vehicle.setArguments(bundle);
        Slide slideTransition = new Slide(Gravity.END);
        slideTransition.setDuration(150);
        Slide exitleft=new Slide(Gravity.START);
        exitleft.setDuration(150);
        ChangeBounds changeBoundsTransition = new ChangeBounds();
        changeBoundsTransition.setDuration(150);

        vehicle.setEnterTransition(slideTransition);
        vehicle.setAllowEnterTransitionOverlap(false);
        vehicle.setExitTransition(exitleft);
        vehicle.setAllowReturnTransitionOverlap(false);
        vehicle.setSharedElementEnterTransition(changeBoundsTransition);
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.drawer_layout,vehicle).addToBackStack(null).commit();
    }
    public void AccountTransistion(String Hostname ,FragmentManager fm)
    {
        Bundle bundle=new Bundle();
        //passing Host person name for searching the database i
        bundle.putString("AccountInformation",Hostname);
        AccountInfo vehicle=new AccountInfo();

        vehicle.setArguments(bundle);
        Slide slideTransition = new Slide(Gravity.END);
        slideTransition.setDuration(150);
        Slide exitleft=new Slide(Gravity.START);
        exitleft.setDuration(150);
        ChangeBounds changeBoundsTransition = new ChangeBounds();
        changeBoundsTransition.setDuration(150);

        vehicle.setEnterTransition(slideTransition);
        vehicle.setAllowEnterTransitionOverlap(false);
        vehicle.setExitTransition(exitleft);
        vehicle.setAllowReturnTransitionOverlap(false);
        vehicle.setSharedElementEnterTransition(changeBoundsTransition);
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.drawer_layout,vehicle).addToBackStack(null).commit();
    }
    public void Modifiyingdetails(FragmentManager fm)
    {
       // Bundle bundle=new Bundle();
        //passing Host person name for searching the database i
       // bundle.putString("AccountInformation",Hostname);
        SaveVehicleDetails vehicle=new SaveVehicleDetails();

        //vehicle.setArguments(bundle);
        Slide slideTransition = new Slide(Gravity.END);
        slideTransition.setDuration(150);
        Slide exitleft=new Slide(Gravity.START);
        exitleft.setDuration(150);
        ChangeBounds changeBoundsTransition = new ChangeBounds();
        changeBoundsTransition.setDuration(150);

        vehicle.setEnterTransition(slideTransition);
        vehicle.setAllowEnterTransitionOverlap(false);
        vehicle.setExitTransition(exitleft);
        vehicle.setAllowReturnTransitionOverlap(false);
        vehicle.setSharedElementEnterTransition(changeBoundsTransition);
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.drawer_layout,vehicle).addToBackStack(null).commit();
    }
}
