package ar.com.syswork.sysmobile.pcargapedidos;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.PedidoItem;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.AlertManager;
import ar.com.syswork.sysmobile.util.IAlertResult;

public class ActivityCargaPedidos extends ActionBarActivity implements IAlertResult
{
	
	private LogicaCargaPedidos logicaCargaPedidos;
	private int posicionItemSeleccionado;
	private PantallaManagerCargaPedidos pantallaManagerCargaPedidos;
	
	private long ultimaVezQueSePresionoBack;
	private boolean esModificacionDePedido = false;
	private long _idPedido;
	private boolean incluirEnReparto;
	private boolean auxEnviarAutomaticamente;
	private boolean auxFacturar;
	private int ALERTA_SOLICITA_REPARTO = 999;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carga_pedidos);
		
		incluirEnReparto = false;
		
		String codCliente="";
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		if (extras !=null)
		{
			if (extras.containsKey("esModificacionDePedido"))
			{
				Log.d("SW", "esModificacionDePedido");
				
				esModificacionDePedido = true;
				_idPedido = extras.getInt("_idPedido");
				
				Log.d("SW", "_idPedido ::: " + _idPedido);
			}
			else
			{
				if (extras.containsKey("cliente"))
					codCliente = extras.getString("cliente");
			}
		}

		
		// Creo el Listener
		ListenerCargaPedidos listenerCargaPedidos = new ListenerCargaPedidos();

		//Creo el PantallaManager
		pantallaManagerCargaPedidos = new PantallaManagerCargaPedidos(this,listenerCargaPedidos);
		pantallaManagerCargaPedidos.seteaListener();
		
		listenerCargaPedidos.setPantallaManagerCargaPedidos(pantallaManagerCargaPedidos);
		
		//Creo la Logica
		logicaCargaPedidos = new LogicaCargaPedidos(this, pantallaManagerCargaPedidos);
		
		//Seteo la Logica al Listener
		listenerCargaPedidos.setLogica(logicaCargaPedidos);
		
		//Seteo el adapter
		ListView lv = (ListView) this.findViewById(R.id.lstCargaPedido);
		lv.setAdapter(logicaCargaPedidos.getAdapterCargaPedidos());
		registerForContextMenu(lv);
		
		
		if (esModificacionDePedido) 
		{
			logicaCargaPedidos.cargaDatosPedido(_idPedido);

		}
		else
		{
			//Inicializo datos
			logicaCargaPedidos.inicializaDatos(codCliente);
			
			if (logicaCargaPedidos.spSolicitaClaseDePrecio())
			{
				logicaCargaPedidos.cargarSpClasesDePrecio(pantallaManagerCargaPedidos.getSpClasesDePrecio());
				pantallaManagerCargaPedidos.mostrarDialogoSolicitaClaseDePrecio();
			}
		}
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_carga_pedidos, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{        
		
			case R.id.mnu_grabar_pedido: 
				
				if (AppSysMobile.getSolicitaIncluirEnReparto())
				{
					auxEnviarAutomaticamente = false;
					auxFacturar = false;
					pantallaManagerCargaPedidos.muestraAlertaIncluirEnReparto();
				}
				else
				{
					logicaCargaPedidos.grabarPedido(false,false,_idPedido,incluirEnReparto);				
				}
				
				return true;        
				
			case R.id.mnu_grabar_pedido_y_enviar:
				
				if (AppSysMobile.getSolicitaIncluirEnReparto())
				{
					auxEnviarAutomaticamente = true;
					auxFacturar = false;
					pantallaManagerCargaPedidos.muestraAlertaIncluirEnReparto();
				}
				else
				{
					logicaCargaPedidos.grabarPedido(true,false,_idPedido,incluirEnReparto);
				}
				
				return true;        
			
			case R.id.mnu_grabar_pedido_enviar_y_facturar:
				if (AppSysMobile.getSolicitaIncluirEnReparto())
				{
					auxEnviarAutomaticamente = true;
					auxFacturar = true;
					pantallaManagerCargaPedidos.muestraAlertaIncluirEnReparto();
				}
				else
				{
					logicaCargaPedidos.grabarPedido(true,true,_idPedido,incluirEnReparto);
				}
				return true;        
				
			case R.id.mnu_cancelar_pedido:
				
				if (logicaCargaPedidos.getAdapterCargaPedidos().getCount()==0)
				{
					pantallaManagerCargaPedidos.finalizaActivityCargaPedidos();
					return true;        
				}
				else
				{
					pantallaManagerCargaPedidos.muestraAlertaCancelarPedido();
					return true;        				}
			
			default:
				return super.onOptionsItemSelected(item);    
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		if ((v.getId() == R.id.lstCargaPedido))
		{
			menu.setHeaderTitle(R.string.que_hacer_con_el_item);
			menu.add(1, 1, 1, R.string.eliminar_item);
			//menu.add(1, 2, 2, R.string.consultarStockOnLine);
			menu.add(1, 3, 3, R.string.cancelar_item);
			
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			posicionItemSeleccionado = info.position;
			
		}
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
			case 1:
				logicaCargaPedidos.removerItemListaItems(posicionItemSeleccionado);
				break;
			case 2:
				
				PedidoItem pedidoItem = logicaCargaPedidos.getAdapterCargaPedidos().getItem(posicionItemSeleccionado);
				pantallaManagerCargaPedidos.consultarStock(pedidoItem.getIdArticulo());
				
				break;
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
	
	  if (this.ultimaVezQueSePresionoBack < System.currentTimeMillis() - 4000) 
	  {
		  pantallaManagerCargaPedidos.solicitaConfirmacionDeSalida();  
		  this.ultimaVezQueSePresionoBack = System.currentTimeMillis();
	  } 
	  else 
	  {
		   super.onBackPressed();
		    /*}*/
	  }
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		// Consulta de Articulos
		if (resultCode == RESULT_OK) 
		{
			String codArticulo = null;
			
			switch (requestCode)
			{
				case AppSysMobile.DESDE_CARGA_DE_PEDIDOS:
					
					codArticulo = data.getExtras().getString("codArticulo");
					pantallaManagerCargaPedidos.setCodigoIntroducido(codArticulo);
					
					// Completo el codigo de articulo y automaticamente
					logicaCargaPedidos.validaCodigoArticulo();
					
					break;
				case AppSysMobile.DESDE_SCANNER:

					codArticulo = data.getStringExtra("SCAN_RESULT");
					/*String formatName = data.getStringExtra("SCAN_RESULT_FORMAT");
					byte[] rawBytes = data.getByteArrayExtra("SCAN_RESULT_BYTES");
					int intentOrientation = data.getIntExtra("SCAN_RESULT_ORIENTATION", Integer.MIN_VALUE);
					Integer orientation = intentOrientation == Integer.MIN_VALUE ? null : intentOrientation;
					String errorCorrectionLevel = data.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");
					*/
					pantallaManagerCargaPedidos.setCodigoIntroducido(codArticulo);
					
					// Completo el codigo de articulo y automaticamente
					logicaCargaPedidos.validaCodigoArticulo();
			
			}
			
		}
	}

	@Override
	public void onAlertResult(int idAlert, int which) 
	{
		if (idAlert == R.id.mnu_cancelar_pedido)
		{
			switch (which)
			{
				case AlertManager.BUTTON_POSITIVE :
					pantallaManagerCargaPedidos.finalizaActivityCargaPedidos();
					break;
			}
		}
		else if(idAlert == ALERTA_SOLICITA_REPARTO)
		{
			incluirEnReparto = (which == AlertManager.BUTTON_POSITIVE );
			logicaCargaPedidos.grabarPedido(auxEnviarAutomaticamente,auxFacturar ,_idPedido,incluirEnReparto);				
		}
	}

}
