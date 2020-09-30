package ar.com.syswork.sysmobile.gps;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class GpsListener implements LocationListener {

	private int ultimaLecturaX;
	private int ultimaLecturaY;
	
	
	@Override
	public void onLocationChanged(Location location) 
	{
		ultimaLecturaX = (int) (location.getLatitude() * 1E6);
		ultimaLecturaY = (int) (location.getLongitude() * 1E6);
		
		Log.d("SW","ultimaLecturaX: " + ultimaLecturaX);
		Log.d("SW","ultimaLecturaY: " + ultimaLecturaY);

	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		Log.d("SW","Se deshabilito el GPS");
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
		Log.d("SW","Se Habilito el GPS");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		Log.d("SW","Se cambio el estado del GPS");
		
	}

	public int getUltimaLecturaX() 
	{
		return ultimaLecturaX;
	}

	public int getUltimaLecturaY() 
	{
		return ultimaLecturaY;
	}

}
