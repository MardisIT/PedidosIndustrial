package ar.com.syswork.sysmobile.gps;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class GpsReader {
	
	private LocationListener gpsListener;
	private Activity a;
	private LocationManager locationManager;
	
	public GpsReader (Activity a, LocationListener gpsListener)
	{
		this.a = a;
		this.gpsListener = gpsListener;

		locationManager = (LocationManager) this.a.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this.gpsListener);
	}
	
	
	public Location getLocationByNetwork()
	{
		return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	}
	
	public Location getLocationByGps()
	{
		return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
}
