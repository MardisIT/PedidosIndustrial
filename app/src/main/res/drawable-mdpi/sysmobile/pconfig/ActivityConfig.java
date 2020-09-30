package ar.com.syswork.sysmobile.pconfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class ActivityConfig extends ActionBarActivity implements OnClickListener{
	
	EditText edtRutaAccesoWebService;
	EditText edtPuertoWebService;
	EditText edtTimeout;
	CheckBox chkSolicitaClaseDePrecio;
	EditText edtClasesDePrecioHabilitadas;
	CheckBox chkSolicitaIncluirEnReparto;

	Button btnAceptarConfig;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		
		edtRutaAccesoWebService = (EditText) findViewById(R.id.edtRutaWebService);
		edtPuertoWebService = (EditText) findViewById(R.id.edtPuertoWebService);
		edtTimeout = (EditText) findViewById(R.id.edtTimeout);
		chkSolicitaClaseDePrecio = (CheckBox) findViewById(R.id.chkSolicitaClaseDePrecio);
		edtClasesDePrecioHabilitadas = (EditText) findViewById(R.id.edtClasesDePrecioHabilitadas);
		chkSolicitaIncluirEnReparto = (CheckBox) findViewById(R.id.chkIncluirEnReparto);
		
		btnAceptarConfig = (Button) findViewById(R.id.btnAceptarConfig);
		btnAceptarConfig.setOnClickListener(this);
		
		edtRutaAccesoWebService.setText(AppSysMobile.getRutaWebService());
		edtPuertoWebService.setText(Integer.toString(AppSysMobile.getPuertoWebService()));
		edtTimeout.setText(Integer.toString(AppSysMobile.getTimeOutSockets()));
		chkSolicitaClaseDePrecio.setChecked(AppSysMobile.getSolicitaClaseDePrecio());
		edtClasesDePrecioHabilitadas.setText(AppSysMobile.getClasesDePrecioHabilitadas());
		chkSolicitaIncluirEnReparto.setChecked(AppSysMobile.getSolicitaIncluirEnReparto());
	}

	
	@Override
	public void onClick(View v) {
		
		if (v.getId() == R.id.btnAceptarConfig)
		{
			SharedPreferences prefs = getSharedPreferences("CONFIGURACION_WS",Context.MODE_PRIVATE);
			
			String ruta = edtRutaAccesoWebService.getText().toString();
			int puerto = Integer.parseInt(edtPuertoWebService.getText().toString());
			int timeOut= 30;
			
			if (!edtTimeout.getText().toString().trim().equals(""))
			{
				timeOut = Integer.parseInt(edtTimeout.getText().toString());
			}
			
			String clasesDePrecioHabilitadas = edtClasesDePrecioHabilitadas.getText().toString();
			
			Editor editor = prefs.edit();
			editor.putString("rutaAccesoWebService", ruta);
			editor.putInt("puertoWebService", puerto);
			editor.putInt("timeOut", timeOut);
			editor.putBoolean("solicitaClaseDePrecio", chkSolicitaClaseDePrecio.isChecked());
			editor.putString("clasesDePrecioHabilitadas", clasesDePrecioHabilitadas);
			editor.putBoolean("solicitaIncluirEnReparto", chkSolicitaIncluirEnReparto.isChecked());
			
			editor.commit();
			
			// SI CAMBIO EL TIMEOUT FUERZO A QUE SE RELOGUEE PARA 
			if (AppSysMobile.getTimeOutSockets()!=timeOut)
				//TODO: Mostrar un aviso que cambio el TimeOut 
				setResult(RESULT_OK);
			else
				setResult(RESULT_CANCELED);
			
			AppSysMobile.setRutaWebService(ruta);
			AppSysMobile.setPuertoWebService(puerto);
			AppSysMobile.setTimeOut(timeOut);
			AppSysMobile.setSolicitaClaseDePrecio(chkSolicitaClaseDePrecio.isChecked());
			AppSysMobile.setClasesDePrecioHabilitadas(clasesDePrecioHabilitadas);
			AppSysMobile.setSolicitaIncluirEnReparto(chkSolicitaIncluirEnReparto.isChecked());
			finish();
		}
	}


}
