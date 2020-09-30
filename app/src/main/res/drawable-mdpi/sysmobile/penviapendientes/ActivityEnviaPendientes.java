package ar.com.syswork.sysmobile.penviapendientes;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import ar.com.syswork.sysmobile.R;


public class ActivityEnviaPendientes extends ActionBarActivity {
	
	private LogicaEnviaPendientes logicaEnviaPendientes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_envia_pendientes);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// Creo el Listener
		ListenerEnviaPendientes listenerEnviaPendientes = new ListenerEnviaPendientes ();
		
		//Creo el PantallaManager y le paso el Listener y la Activity
		PantallaManagerEnviaPendientes pantallaManagerEnviaPendientes = new PantallaManagerEnviaPendientes (this, listenerEnviaPendientes);
		
		// Creo la Logica y le paso la Activity y el PantallaManager
		logicaEnviaPendientes = new LogicaEnviaPendientes(this,pantallaManagerEnviaPendientes);
		
		//Seteo la Logica al listener
		listenerEnviaPendientes.seteaLogica(logicaEnviaPendientes);
		listenerEnviaPendientes.seteaPantallaMager(pantallaManagerEnviaPendientes);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
