package ar.com.syswork.sysmobile.pmenuprincipal;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Intent;
import android.content.res.Configuration;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;;

import javax.annotation.Nonnull;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.ItemMenuPrincipal;
import ar.com.syswork.sysmobile.shared.AppSysMobile;


public class ActivityMenuPrincipal extends AppCompatActivity {


	private static final int REQUEST_PERMISSION_CAMERA = 1001;
	private static final int REQUEST_PERMISSION_WRITE = 1002;
	private static final int REQUEST_PERMISSION_RED_PHONE = 1003;
	private static final int REQUEST_PERMISSION_RED_LOCATION = 1004;

	private boolean permissionGranted;

	private LogicaMenuPrincipal logicaMenuPrincipal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
		if (!permissionGranted){
			checkPermissionsCAM();
			//return;
		}
		ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, /* Este codigo es para identificar tu request */ 1);
		// Creo la Lista
		List<ItemMenuPrincipal> listaOpciones = new ArrayList<ItemMenuPrincipal>();
		
		// Creo el Adapter
		AdapterMenuPrincipal adapter = new AdapterMenuPrincipal(this, listaOpciones);
		
		//Creo el Listener
		ListenerMenuPrincipal listenerMenuPrincipal = new ListenerMenuPrincipal();
		
		//Creo la Logica
		logicaMenuPrincipal = new LogicaMenuPrincipal(this, adapter);
		
		//Creo el Pantalla Manager
		PantallaManagerMenuPrincipal pantallaManagerMenuPrincipal = new PantallaManagerMenuPrincipal(this,listenerMenuPrincipal);
		pantallaManagerMenuPrincipal.seteaListener();
		
		//Seteo la Logica al Listener
		listenerMenuPrincipal.setLogica(logicaMenuPrincipal);
		
		//Seteo el Adapter
		ListView lv = (ListView) this.findViewById(R.id.listViewMenuPrincipal);
		lv.setAdapter(adapter);
		
		logicaMenuPrincipal.seteaListaOpciones(listaOpciones);
		logicaMenuPrincipal.seteaPantallaManager(pantallaManagerMenuPrincipal);
		logicaMenuPrincipal.creaItemsMenuPrincipal();
		
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
		if (permissionCheck != PackageManager.PERMISSION_GRANTED  ) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
		}
		if (permissionCheck2 != PackageManager.PERMISSION_GRANTED  ) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION_RED_PHONE);
		}

		if(permissionCheck3 != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_RED_LOCATION);
		}
		if(permissionCheck1 != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE);
		}

		return true;
	}
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String permissions[],
										   @NonNull int[] grantResults) {
		//switch (requestCode) {
		if(requestCode == REQUEST_PERMISSION_CAMERA || requestCode == REQUEST_PERMISSION_WRITE|| requestCode == REQUEST_PERMISSION_RED_PHONE || requestCode == REQUEST_PERMISSION_RED_LOCATION ){ //case REQUEST_PERMISSION_CAMERA:
			if (grantResults.length > 0
					&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				permissionGranted = true;
				Toast.makeText(this, "Permiso de cámara otorgado",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Debes otorgar permiso de cámara!", Toast.LENGTH_SHORT).show();
			}
			//break;
			//case REQUEST_PERMISSION_WRITE:
			if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
				permissionGranted = true;
				Toast.makeText(this, "Permiso de almacenamiento externo otorgado", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(this, "Debes otorgar el permiso de memoria", Toast.LENGTH_SHORT).show();
			}
			// break;
		}
	}





	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) 
		{        
			case R.id.mnuConfiguracionMenuPrincipal:
				logicaMenuPrincipal.lanzarActivityConfiguracion();
				return true;        
			
			default:
				return super.onOptionsItemSelected(item);    
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode)
		{
			case AppSysMobile.OPC_MENU_PRINCIPAL_CARGA_PEDIDOS:
				logicaMenuPrincipal.actualizaCantidadPedidosPendientes();
				break;
				
			case AppSysMobile.OPC_MENU_PRINCIPAL_SINCRONIZAR:
				if (logicaMenuPrincipal.getForzarLogueo())
				{
					if (resultCode == RESULT_OK && AppSysMobile.WS_ESTADOSIN!="OK"){
						Intent i = new Intent(this,ar.com.syswork.sysmobile.plogin.ActivityLogin.class);
						startActivity(i);
						finish();
					}
					break;
				}
				break;
			case AppSysMobile.OPC_MENU_PRINCIPAL_ENVIAR_PENDIENTES:
				
				logicaMenuPrincipal.actualizaCantidadPedidosPendientes();
				break;
				
			case AppSysMobile.OPC_MENU_PRINCIPAL_CONFIGURACION:
				
				if (resultCode == RESULT_OK){
					Intent i = new Intent(this,ar.com.syswork.sysmobile.plogin.ActivityLogin.class);
					startActivity(i);
					finish();
				}
				break;
				
		}
		
	}
	
}
