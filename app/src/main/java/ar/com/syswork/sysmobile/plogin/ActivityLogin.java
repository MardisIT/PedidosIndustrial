package ar.com.syswork.sysmobile.plogin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.widget.Button;
import ar.com.syswork.sysmobile.R;

public class ActivityLogin extends Activity {
	
	private ListenerLogin listener;
	private LogicaLogin logicaLogin;
	private PantallaManagerLogin pantallaManagerLogin;
	private static LocationManager locationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		checkLocation();
		pantallaManagerLogin = new PantallaManagerLogin(this);
		
		logicaLogin = new LogicaLogin(this,pantallaManagerLogin);
		
		listener = new ListenerLogin(logicaLogin,pantallaManagerLogin);
		
		Button btnLogin = (Button) this.findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(listener);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//getMenuInflater().inflate(R.menu.login, menu);
		return true;
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

}
