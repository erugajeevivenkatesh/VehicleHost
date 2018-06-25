package com.nixinninsights.venkatesh.vehiclehost.SharedPreference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.nixinninsights.venkatesh.vehiclehost.UiLoginUpdatesRegister.LogInActivity;

import java.util.HashMap;

public class UserDetailsLogin  {
    private SharedPreferences Preference;
    private Editor Editor;
   private Context _Context;
    private int PRIVATEMODE=0;
    public static final String PreferName="VehicleLoginDetails";
    public static final String IS_USER_LOGIN="IsUserLoggedIn";
    public static final String KEY_EMAIL="EMAIL";
    public static final String KEY_Pass="PASSWORD";
public UserDetailsLogin(Context context)
{
        this._Context=context;
        Preference=_Context.getSharedPreferences(PreferName,PRIVATEMODE);
        Editor=Preference.edit();
}
public void CreateUserLogin(String Email,String Pass)
{//Storing Value is true
    Editor.putBoolean(IS_USER_LOGIN,true);
    //Storing values in preference
    Editor.putString(KEY_EMAIL,Email);
    Editor.putString(KEY_Pass,Pass);
    Editor.commit();
}
public  boolean CheckLogin()
{
    if(!this.IsUserloggedIn())
    {
        Startactivity();
        return true;
    }
    return false;

}

public HashMap<String,String> GetuserloginDetails()
{
    HashMap<String,String> Userdetails=new HashMap<>();
    Userdetails.put(KEY_EMAIL,Preference.getString(KEY_EMAIL,null));
    Userdetails.put(KEY_Pass,Preference.getString(KEY_Pass,null));
    return Userdetails;
 }

private boolean IsUserloggedIn()
{
    return Preference.getBoolean(IS_USER_LOGIN,false);
}



public void Logoutuser()
{
    //Clear Prefeence Data
        Editor.clear();
        Editor.commit();
        //Start Login Activity
    Startactivity();
}
private void Startactivity()
{
        Intent i=new Intent(_Context, LogInActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _Context.startActivity(i);
}
}
