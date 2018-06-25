package com.nixinninsights.venkatesh.vehiclehost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class DataforStorage extends SQLiteOpenHelper {
     static final String Tablename="Vehiclehostdetails";
     static final String Usertabel="Userdetails";


    String Hosdetailsquery="create table "+Tablename + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,ROUTENO" +
            " TEXT,FROMADDRESS TEXT,TOADDRESS TEXT,VEHICLEREGITRAATIONNO TEXT,DRIVERNAME TEXT)";
    String signintableQuery="create table "+Tablename + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,ROUTENO" +
            " TEXT,FROMADDRESS TEXT,TOADDRESS TEXT,VEHICLEREGITRAATIONNO TEXT,DRIVERNAME TEXT)";

    public DataforStorage(Context context)
    {
        super(context,"VehicleHost",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Hosdetailsquery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Tablename);
        onCreate(db);
    }
    public boolean Insertdata(String ROUTENO,String FromAddress,String ToAddress,String VehicleRegistrationNo,String DriverNo)
    {   SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        Cursor result=getdata();
        long Result=0;
        if(result.getCount()==0) {


            contentValues.put("ROUTENO", ROUTENO.toUpperCase());
            contentValues.put("FROMADDRESS", FromAddress.toUpperCase());
            contentValues.put("TOADDRESS", ToAddress.toUpperCase());
            contentValues.put("VEHICLEREGITRAATIONNO", VehicleRegistrationNo.toUpperCase());
            contentValues.put("DRIVERNAME", DriverNo.toUpperCase());
             Result = db.insert(Tablename, null, contentValues);

        }
        else
        {
           if( UpdateData(ROUTENO,FromAddress,ToAddress,VehicleRegistrationNo,DriverNo))
           {
               Log.e("Dataupdate","DataupdatedSuccessfully");
             return true;
           }
        }
        if(Result==-1)
        {
            return false;
        }
        else
        {
            Log.e("Datainsert","DataInsertedSuccesssfully");
            return true;
        }
    }
    public boolean UpdateData(String ROUTENO,String FromAddress,String ToAddress,String VehicleRegistrationNo,String DriverNo)
    {   String id="1";
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ROUTENO",ROUTENO.toUpperCase());
        contentValues.put("FROMADDRESS",FromAddress.toUpperCase());
        contentValues.put("TOADDRESS",ToAddress.toUpperCase());
        contentValues.put("VEHICLEREGITRAATIONNO",VehicleRegistrationNo.toUpperCase());
        contentValues.put("DRIVERNAME",DriverNo.toUpperCase());
        db.update(Tablename,contentValues,"ID=?",new String[]{id});
        return true;
    }
    public int deletedata()
    {
        String id="1";
        SQLiteDatabase db=this.getWritableDatabase();
        Log.e("delete data","deleteddata");
        return db.delete(Tablename,"ID=?",new String[]{id});

    }
    public Cursor getdata()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("Select * from "+Tablename+" where ID=1 ",null);
    }

}
