package ar.com.syswork.sysmobile.psincronizar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import java.util.List;

import ar.com.syswork.sysmobile.R;


public class ActivitySincronizar extends ActionBarActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sincronizar);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		LogicaSincronizacion logicaSincronizacion = new LogicaSincronizacion(this);
		logicaSincronizacion.setImei(obterImeid());
		
		ListenerSincronizacion listenerSincronizacion = new ListenerSincronizacion(logicaSincronizacion);
		
		PantallaManagerSincronizacion pantallaManagerSincronizacion = new PantallaManagerSincronizacion(this, listenerSincronizacion);
		pantallaManagerSincronizacion.seteaListeners();
		
		listenerSincronizacion.setPantallaManager(pantallaManagerSincronizacion);
		
		logicaSincronizacion.setPantallaManager(pantallaManagerSincronizacion);
	}
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
	@SuppressLint("MissingPermission")
	public String  obterImeid() {
		final String androidIdName = Settings.Secure.ANDROID_ID;

		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String simSerialNo="";
		String  myIMEI = mTelephony.getDeviceId();

		if (myIMEI == null) {
			SubscriptionManager subsManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

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

	}
			

}
