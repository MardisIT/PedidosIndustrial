package ar.com.syswork.sysmobile.Tracking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.List;

import ar.com.syswork.sysmobile.entities.ConfiguracionDB;


public class TrackingBussiness {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public   void  SetLocationMerch(Activity c, ConfiguracionDB configuracionDB){
        try {
            JavaRestClient tarea = new JavaRestClient(c);
            String deviceId = obterImeid((Activity) c);
            String imei = deviceId;
            imei = imei.replace("imei:", "");
            imei = imei.replace("android_id:", "");
            GPSTracker _track = new GPSTracker(c.getApplicationContext());
            Tracking _traTracking = new Tracking(_track.getLongitude(), _track.getLatitude(), _track.getAccurency(), configuracionDB.getCampaniaNombre(), configuracionDB.getCampaniaNombre(), imei, LeveLBatery((Activity) c));
            tarea.SetTracking(_traTracking);
        }catch (Exception e){
            Log.d("Traking: ",e.getMessage());
        }

    }
    private int LeveLBatery(Activity a)
    {
        try {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = a.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        int batteryPct = level * 100 / scale;
        return  batteryPct;
        }catch (Exception e){
            Log.d("Traking LeveLBatery: ",e.getMessage());
            return 0;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @SuppressLint("MissingPermission")
    public String  obterImeid(Activity a) {

        try {
        TelephonyManager tm = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
        TelephonyManager mTelephony = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNo="";
        String  myIMEI = mTelephony.getDeviceId();

        if (myIMEI == null) {
            SubscriptionManager subsManager = (SubscriptionManager) a.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

            List<SubscriptionInfo> subsList = subsManager.getActiveSubscriptionInfoList();

            if (subsList!=null) {
                for (SubscriptionInfo subsInfo : subsList) {
                    if (subsInfo != null) {
                        simSerialNo  = subsInfo.getIccId();
                    }
                }

            }

            myIMEI=simSerialNo;
        }


     /* String  deviceId = new PropertyManager(Collect.getInstance().getApplicationContext())
                .getSingularProperty(PropertyManager.withUri(PropertyManager.PROPMGR_DEVICE_ID));*/
        return myIMEI;
        }catch (Exception e){
            Log.d("Traking Imei: ",e.getMessage());
            return "";
        }

    }

}
