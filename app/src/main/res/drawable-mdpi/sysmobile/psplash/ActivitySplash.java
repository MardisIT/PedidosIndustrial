package ar.com.syswork.sysmobile.psplash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import java.util.Timer;
import java.util.TimerTask;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoVendedor;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.plogin.ActivityLogin;
import ar.com.syswork.sysmobile.pmenuprincipal.ActivityMenuPrincipal;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class ActivitySplash extends Activity {

	private Timer timer;
	private DataManager dm;
	private AppSysMobile app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		//Instancio el DataManager
		dm = new DataManager(this);
		
		//Lo Seteo a la Aplicacion
		app = (AppSysMobile) getApplication();
		app.setDataManager(dm);
		
		//inicializo las SharedPreferens
		app.iniciaConfiguracion();
		
		//inicializo el GPS
		/*
		GpsListener gpsListener = new GpsListener();
		GpsReader gpsReader = new GpsReader(this,gpsListener);
		app.setGpsReader(gpsReader);
		*/
		timer = new Timer();
		
		TimerTask tareaTimer = new TimerTask(){
			
			@Override
			public void run() {
				lanzarActivity();
			}
		};
	
		timer.schedule(tareaTimer, 3000);
	
	
	}
	
	private void lanzarActivity(){
		DaoVendedor daoVendedor = dm.getDaoVendedor();
		Intent i ;
		
		if (daoVendedor.getCount()==0)
		{
			i = new Intent(ActivitySplash.this, ActivityMenuPrincipal.class);
			app.setVendedorLogueado("");
		}
		else
		{
			i = new Intent(ActivitySplash.this, ActivityLogin.class);
		}
		
		startActivity(i);
		timer.cancel();
		finish();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
}
