package ar.com.syswork.sysmobile.Tracking;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class SetTracking  {
    private List<Location> locations = new ArrayList<>();

    private static final long FIFTEEN_MINUTES_PERIOD = 900000;
    private static final long ONE_MINUTES_PERIOD = 300000;
    private static final long ONE_HOUR_PERIOD = 3600000;
    private static final long SIX_HOURS_PERIOD = 21600000;
    private static final long ONE_DAY_PERIOD = 86400000;
    public static final String TAG = "SetTracking";



}
