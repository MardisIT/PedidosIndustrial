package ar.com.syswork.sysmobile.pconsultaclientes;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.IntentManager;


public class ActivityConsultaClientes extends ActionBarActivity {
	
	private LogicaConsultaClientes logicaConsultaClientes;
	private int posicionItemSeleccionado;
	private AdapterConsultaClientes adapterConsultaClientes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_clientes);

		// Obtengo el Origen de la Llamada a la consulta.
		int origenDeLaConsulta = AppSysMobile.DESDE_MENU_PRINCIPAL;
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras !=null)
		{
			if (extras.containsKey("origenDeLaConsulta")){
				origenDeLaConsulta = extras.getInt("origenDeLaConsulta");
			}
		}
		
		
		//Creo el Listener
		ListenerConsultaClientes listenerConsultaClientes = new ListenerConsultaClientes();
		listenerConsultaClientes.setOrigenDeLaConsulta(origenDeLaConsulta);
		
		//Creo el pantallaManager
		PantallaManagerConsultaClientes pantallaManagerConsultaClientes = new PantallaManagerConsultaClientes(this, listenerConsultaClientes);
		pantallaManagerConsultaClientes.seteaListener();
		
		listenerConsultaClientes.setPantallaManager(pantallaManagerConsultaClientes);
		
		//Creo la Logica y le paso la Activity y el PantallaManager
		logicaConsultaClientes = new LogicaConsultaClientes(this,pantallaManagerConsultaClientes);
		
		//Seteo la Logica al Listener
		listenerConsultaClientes.setLogica(logicaConsultaClientes);

		//Seteo el adapter
		ListView lv = (ListView) this.findViewById(R.id.lstConsultaCliente);
		lv.setAdapter(logicaConsultaClientes.getAdapterConsultaClientes());

		Spinner spnlocalidad = (Spinner) this.findViewById(R.id.spnlocalidad);   //a//
		//Spinner spnzona = (Spinner) this.findViewById(R.id.spnzona);

		// Me lo guardo para el menu contextual
		adapterConsultaClientes = logicaConsultaClientes.getAdapterConsultaClientes();
		
		if (origenDeLaConsulta == AppSysMobile.DESDE_MENU_PRINCIPAL)
		{
			registerForContextMenu(lv);
		}
				
		logicaConsultaClientes.cargarRegistros(0, "");
		logicaConsultaClientes.notificarCambiosAdapter();

		//cargamos el spinner localidades
		logicaConsultaClientes.cargarLocalidades(ActivityConsultaClientes.this,spnlocalidad); //a//

		//cargamos el spinner zonas
	//	logicaConsultaClientes.cargarZonas(ActivityConsultaClientes.this,spnzona);

	}



	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		
		if ((v.getId() == R.id.lstConsultaCliente))
		{
			menu.setHeaderTitle(R.string.que_hacer_con_el_item);
			menu.add(1, 1, 1, R.string.comoLlegarAlcliente);
			menu.add(1, 2, 2, R.string.llamarPorTelefono);
			menu.add(1, 3, 3, R.string.enviarMail);
			menu.add(1, 4, 4, R.string.cancelar);

			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			posicionItemSeleccionado = info.position;

		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {		
		
		Cliente cliente = adapterConsultaClientes.getItem(posicionItemSeleccionado);
		
		// TODO Auto-generated method stub
		switch (item.getItemId())
		{
		case 1: // como llegar
			
			String direccion = 	cliente.getCalleNroPisoDpto() + "," + cliente.getLocalidad();
			direccion = direccion.replace(" ", "+");

			IntentManager.routeTo(this , direccion);
			break;
			
		case 2: // llamar
			
			IntentManager.callPhoneNumber(this, cliente.getTelefono());
			break;
		
		case 3: // mail
			
			IntentManager.sendEmail(this, cliente.getMail());
			break;
		}

		return super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_consulta_clientes, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {    
		switch (item.getItemId()) 
		{        
		
			case R.id.mnu_ordenar_razon_social: 
				// Si ya no estaba seteado por razon social, lo cambio
				if (!logicaConsultaClientes.getOrderBy().equals("RazonSocial")){
					logicaConsultaClientes.cambiarCriterioBusqueda("RazonSocial");
				}
				
				return true;        
			
			case R.id.mnu_ordenar_codigo:
				// Si ya no estaba seteado por Codigo, lo cambio
				if (!logicaConsultaClientes.getOrderBy().equals("CodigoOpcional")){
					logicaConsultaClientes.cambiarCriterioBusqueda("CodigoOpcional");
				}
				return true;        
			
			case R.id.mnu_borrar_parametros_orden:
				
				logicaConsultaClientes.cambiarCriterioBusqueda("RazonSocial");
				return true;        
			
				
			default:
				return super.onOptionsItemSelected(item);    
		}
	}

}