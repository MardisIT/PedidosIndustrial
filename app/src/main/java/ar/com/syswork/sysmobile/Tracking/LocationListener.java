package ar.com.syswork.sysmobile.Tracking;


import android.location.Location;
import android.os.Bundle;

public interface LocationListener {
    void onLocationChanged(Location var1);

    /** @deprecated */
    @Deprecated
    void onStatusChanged(String var1, int var2, Bundle var3);

    void onProviderEnabled(String var1);

    void onProviderDisabled(String var1);
}
