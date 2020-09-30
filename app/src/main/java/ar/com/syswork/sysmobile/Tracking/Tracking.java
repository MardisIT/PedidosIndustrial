package ar.com.syswork.sysmobile.Tracking;

import com.google.api.client.util.DateTime;

public class Tracking {

    private double GeoLength;
    private double Geolatitude;
    private double GeoAccuracy;
    private DateTime LastDate;
    private String NameAccount;
    private String Namecampaign;
    private String IdDevice ;
    private int battery_level;

    public  Tracking(double GeoLength,
                     double Geolatitude,
                     double GeoAccuracy,

                     String NameAccount,
                     String Namecampaign,
                     String IdDevice ,
                     int battery_level
                     ){
        this.GeoLength=GeoLength;
        this.Geolatitude=Geolatitude;
        this.GeoAccuracy=GeoAccuracy;

        this.NameAccount=NameAccount;
        this.Namecampaign=Namecampaign;
        this.IdDevice=IdDevice;
        this.battery_level=battery_level;



    }
    public double getGeoLength() {
        return GeoLength;
    }

    public void setGeoLength(double geoLength) {
        GeoLength = geoLength;
    }

    public double getGeolatitude() {
        return Geolatitude;
    }

    public void setGeolatitude(double geolatitude) {
        Geolatitude = geolatitude;
    }

    public double getGeoAccuracy() {
        return GeoAccuracy;
    }

    public void setGeoAccuracy(double geoAccuracy) {
        GeoAccuracy = geoAccuracy;
    }

    public DateTime getLastDate() {
        return LastDate;
    }

    public void setLastDate(DateTime lastDate) {
        LastDate = lastDate;
    }

    public String getNameAccount() {
        return NameAccount;
    }

    public void setNameAccount(String nameAccount) {
        NameAccount = nameAccount;
    }

    public String getNamecampaign() {
        return Namecampaign;
    }

    public void setNamecampaign(String namecampaign) {
        Namecampaign = namecampaign;
    }

    public String getIdDevice() {
        return IdDevice;
    }

    public void setIdDevice(String idDevice) {
        IdDevice = idDevice;
    }

    public int getBattery_level() {
        return battery_level;
    }

    public void setBattery_level(int battery_level) {
        this.battery_level = battery_level;
    }
}
