package com.nixinninsights.venkatesh.vehiclehost.CustomArrayList;

public class VehicleData  {
    String VehicleNo,FromAddress,ToAddress,Hostpersonname,RouteNo;
    VehicleData(String VehilcleNo,String FromAddress,String ToAddress,String HostPersonName,String Routeno)
    {
        this.FromAddress=FromAddress;
        this.VehicleNo=VehilcleNo;
        this.ToAddress=ToAddress;
        this.Hostpersonname=HostPersonName;
        this.RouteNo=Routeno;
    }

    public String getFromAddress() {
        return FromAddress;
    }

    public String getHostpersonname() {
        return Hostpersonname;
    }

    public String getRouteNo() {
        return RouteNo;
    }

    public String getToAddress() {
        return ToAddress;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setFromAddress(String fromAddress) {
        FromAddress = fromAddress;
    }

    public void setHostpersonname(String hostpersonname) {
        Hostpersonname = hostpersonname;
    }

    public void setRouteNo(String routeNo) {
        RouteNo = routeNo;
    }

    public void setToAddress(String toAddress) {
        ToAddress = toAddress;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }
}
