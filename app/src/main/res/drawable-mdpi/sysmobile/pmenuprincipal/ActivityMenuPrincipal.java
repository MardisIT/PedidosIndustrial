package ar.com.syswork.sysmobile.pmenuprincipal;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.ItemMenuPrincipal;
import ar.com.syswork.sysmobile.plogin.ActivityLogin;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class ActivityMenuPrincipal extends ActionBarActivity {
	
	
	private LogicaMenuPrincipal logicaMenuPrincipal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
		
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

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pmenu_principal, menu);
		return true;
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
						Intent i = new Intent(this,ActivityLogin.class);
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
					Intent i = new Intent(this,ActivityLogin.class);
					startActivity(i);
					finish();
				}
				break;
				
		}
		
	}
	
}
