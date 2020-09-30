package ar.com.syswork.sysmobile.pconsultaarticulos;

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
import ar.com.syswork.sysmobile.daos.DaoRubro;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Articulo;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class ActivityConsultaArticulos extends ActionBarActivity {
	
	private LogicaConsultaArticulos logicaConsultaArticulos; 
	private PantallaManagerConsultaArticulos pantallaManagerConsultaArticulos;
	
	private int posicionItemSeleccionado;
    private AppSysMobile app;
    private DataManager dm;
    private DaoRubro daoRubro;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_articulos);

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
		ListenerConsultaArticulos listenerConsultaArticulos = new ListenerConsultaArticulos();
		listenerConsultaArticulos.setOrigenDeLaConsulta(origenDeLaConsulta);
		try {
			//Creo el pantallaManager
			pantallaManagerConsultaArticulos = new PantallaManagerConsultaArticulos(this, listenerConsultaArticulos);
			pantallaManagerConsultaArticulos.seteaListener();
		}catch (Exception ex){
			String msj=ex.getMessage();
		}
		listenerConsultaArticulos.setPantallaManager(pantallaManagerConsultaArticulos);
		
		//Creo la Logica y le paso la Activity y el PantallaManager
		logicaConsultaArticulos = new LogicaConsultaArticulos (this,pantallaManagerConsultaArticulos);
		
		//Seteo la Logica al Listener
		listenerConsultaArticulos.setLogica(logicaConsultaArticulos);

		//Seteo el adapter
		ListView lv = (ListView) this.findViewById(R.id.lstArticulos);
		lv.setAdapter(logicaConsultaArticulos.getAdapterConsultaArticulos());
		registerForContextMenu(lv);

		Spinner spnRubros = (Spinner) this.findViewById(R.id.spnRubros);   //a//
        //seteo rubros
        /*app = (AppSysMobile) this.getApplication();
        dm = app.getDataManager();
        daoRubro=dm.getDaoRubro();
        List<Rubro> listOBJ= daoRubro.getAll("");
        ArrayAdapter<Rubro> adaptador;
        adaptador = new ArrayAdapter<Rubro>(this, R.layout.support_simple_spinner_dropdown_item, listOBJ);

        spnRubros.setAdapter(adaptador);*/



		logicaConsultaArticulos.cargarRegistros(0,"");
		logicaConsultaArticulos.notificarCambiosAdapter();

		//cargamos el spinner localidades
		//logicaConsultaArticulos.cargarRubros(this,spnRubros);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_consulta_articulos, menu);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		//super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.lstArticulos)
		{
			menu.setHeaderTitle(R.string.que_hacer_con_el_item);
			menu.add(1, 1, 1, R.string.consultarStockOnLine);
			menu.add(1, 2, 2, R.string.cancelar);
		
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			posicionItemSeleccionado = info.position;
			
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		Articulo articulo = logicaConsultaArticulos.getAdapterConsultaArticulos().getItem(posicionItemSeleccionado);
		pantallaManagerConsultaArticulos.consultarStock(articulo.getIdArticulo());
		
		
		return super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {    
		switch (item.getItemId()) 
		{        
		
			case R.id.mnu_ordenar_descripcion: 
				// Si ya no estaba seteado por razon social, lo cambio
				if (!logicaConsultaArticulos.getOrderBy().equals("Descripcion")){
					logicaConsultaArticulos.cambiarCriterioBusqueda("Descripcion");
				}
				
				return true;        
			
			case R.id.mnu_ordenar_codigo:
				// Si ya no estaba seteado por Codigo, lo cambio
				if (!logicaConsultaArticulos.getOrderBy().equals("IdArticulo")){
					logicaConsultaArticulos.cambiarCriterioBusqueda("IdArticulo");
				}
				return true;        
			
			case R.id.mnu_borrar_parametros_orden:
				
				logicaConsultaArticulos.cambiarCriterioBusqueda("Descripcion");
				return true;        
			
				
			default:
				return super.onOptionsItemSelected(item);    
		}
	}	
}
