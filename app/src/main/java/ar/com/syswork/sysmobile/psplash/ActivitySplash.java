package ar.com.syswork.sysmobile.psplash;

import java.util.Timer;
import java.util.TimerTask;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobManagerCreateException;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoCodigosNuevos;
import ar.com.syswork.sysmobile.daos.DaoVendedor;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.industrial.DemoJobCreator;
import ar.com.syswork.sysmobile.pconsultagenerica.detalle.obtenerCodigos;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class ActivitySplash extends Activity {

	private Timer timer;
	private DataManager dm;
	private AppSysMobile app;
	DaoCodigosNuevos daoCodigosNuevos;
	private static LocationManager locationManager;
	private static final int REQUEST_PERMISSION_CAMERA = 1001;
	private static final int REQUEST_PERMISSION_WRITE = 1002;
	private static final int REQUEST_PERMISSION_RED_PHONE = 1003;
	private static final int REQUEST_PERMISSION_RED_LOCATION = 1004;
	private boolean permissionGranted;

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		if (!permissionGranted){
			checkPermissionsCAM();
			//return;
		}
		//Instancio el DataManager
		dm = new DataManager(this);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		checkLocation();
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
	private boolean checkPermissionsCAM() {

		int permissionCheck = ContextCompat.checkSelfPermission(this,
				Manifest.permission.CAMERA);
		int permissionCheck2 = ContextCompat.checkSelfPermission(this,
				Manifest.permission.READ_PHONE_STATE);
		int permissionCheck3 = ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION);
		int permissionCheck1 = ContextCompat.checkSelfPermission(this,
				Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permissionCheck2 != PackageManager.PERMISSION_GRANTED  ) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION_RED_PHONE);
		}


		if(permissionCheck1 != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE);
		}

		return true;
	}

	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String permissions[],
										   @NonNull int[] grantResults) {
		if( requestCode == REQUEST_PERMISSION_WRITE|| requestCode == REQUEST_PERMISSION_RED_PHONE  ){ //case REQUEST_PERMISSION_CAMERA:
			if (grantResults.length > 0
					&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				permissionGranted = true;
				//Toast.makeText(this, "Permiso de cámara otorgado",						Toast.LENGTH_SHORT).show();
			} else {
				//Toast.makeText(this, "Debes otorgar permiso de cámara!", Toast.LENGTH_SHORT).show();
			}
			//break;
			//case REQUEST_PERMISSION_WRITE:
			if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
				permissionGranted = true;
				//Toast.makeText(this, "Permiso de almacenamiento externo otorgado", Toast.LENGTH_SHORT).show();
			}else {
				//Toast.makeText(this, "Debes otorgar el permiso de memoria", Toast.LENGTH_SHORT).show();
			}
			// break;
		}
	}
	private void lanzarActivity(){
		DaoVendedor daoVendedor = dm.getDaoVendedor();
		Intent i ;
		try {
			JobManager
					.create(this)
					.addJobCreator(new DemoJobCreator());
		} catch (JobManagerCreateException e) {
			Log.d("",e.getMessage());
		}
		daoCodigosNuevos=dm.getDaoCodigosNuevos();
		if(daoCodigosNuevos.getAll("uri=''").size()==0)
		{

			obtenerCodigos fetchJsonTask = new obtenerCodigos(ActivitySplash.this);
			fetchJsonTask.execute("", "");
		}
		if (daoVendedor.getCount()==0)
		{
			i = new Intent(ActivitySplash.this, ar.com.syswork.sysmobile.pmenuprincipal.ActivityMenuPrincipal.class);
			app.setVendedorLogueado("");
		}
		else
		{
			i = new Intent(ActivitySplash.this, ar.com.syswork.sysmobile.plogin.ActivityLogin.class);
		}
		
		startActivity(i);
		timer.cancel();
		finish();
		
	}


	private boolean checkLocation() {
		if (!isLocationEnabled())
			showAlert();
		return isLocationEnabled();
	}
	private boolean isLocationEnabled() {
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
				locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}
	private void showAlert() {
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Enable Location")
				.setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
						"usa esta app")
				.setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface, int paramInt) {
						Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(myIntent);
					}
				})
				.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface, int paramInt) {
					}
				});
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
}
