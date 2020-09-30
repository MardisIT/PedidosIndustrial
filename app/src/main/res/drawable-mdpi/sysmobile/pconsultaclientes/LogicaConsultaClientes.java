package ar.com.syswork.sysmobile.pconsultaclientes;

import android.app.Activity;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.syswork.sysmobile.daos.DaoCliente;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class LogicaConsultaClientes {
	
	private Activity a;

	private ArrayList<Cliente> listaClientes;
	private ArrayList<Cliente> listaLocalidades;
	//private ArrayList<Cliente> listaZonas;

	private PantallaManagerConsultaClientes pantallaManagerConsultaClientes;
	private AdapterConsultaClientes adapterConsultaClientes;
	
	private AppSysMobile app;
	
	private DataManager dataManager;
	private DaoCliente daoCliente;
	private Cliente cliente;
	
	private	boolean estaCargandoRegistros=false;

	private String orderBy;
	
	public LogicaConsultaClientes(Activity a,PantallaManagerConsultaClientes pantallaManagerConsultaClientes){
		
		this.a = a;
		
		listaClientes = new ArrayList<Cliente>();
		listaLocalidades = new ArrayList<Cliente>();
		//listaZonas = new ArrayList<Cliente>();  //a cancelo porque no uso zona//

		//Creo el Adapter
		this.adapterConsultaClientes = new AdapterConsultaClientes(this.a,listaClientes,this);

		app = (AppSysMobile) this.a.getApplication();
		dataManager = app.getDataManager();
		daoCliente = dataManager.getDaoCliente();

		
		setOrderBy("RazonSocial");
		this.pantallaManagerConsultaClientes= pantallaManagerConsultaClientes; 
		
	}

	public void cargarRegistros(int desde, String where){
		
		final int d = desde;
		final String w = where;
		
		if (estaCargandoRegistros==false){
			
			estaCargandoRegistros=true;
			
			new Handler().post(new Runnable() {
				
				@Override
				public void run() {
					List<Cliente> listaTmp = new ArrayList<Cliente>();
					listaTmp = daoCliente.getAllWithLimit(w,d,orderBy);

					Iterator<Cliente> i = listaTmp.iterator();
					
					while (i.hasNext()){
						cliente = i.next();
						listaClientes.add(cliente);
					}
					
					if ( listaTmp.size()>0 ){
						notificarCambiosAdapter();
					}
				}
			});

			estaCargandoRegistros=false;
		}
	}


	//consulta de localidades
	public void cargarLocalidades(final Activity a, final Spinner spnloc){


			new Handler().post(new Runnable() {

				@Override
				public void run() {
					List<Cliente> listaTmp = new ArrayList<Cliente>();
					listaTmp = daoCliente.getAllLocalidades();

					Iterator<Cliente> i = listaTmp.iterator();

					while (i.hasNext()){
						cliente = i.next();
						listaLocalidades.add(cliente);
					}
                   llenaloc(a,spnloc);

				}
			});


	}
	////

	//consulta de zonas
	/*public void cargarZonas(final Activity a, final Spinner spnzon){


		new Handler().post(new Runnable() {

			@Override
			public void run() {
				List<Cliente> listaTmp = new ArrayList<Cliente>();
				listaTmp = daoCliente.getAllZonas();

				Iterator<Cliente> i = listaTmp.iterator();

				while (i.hasNext()){
					cliente = i.next();
					listaZonas.add(cliente);
				}
				llenazonas(a,spnzon);

			}
		});


	}*/


	public String getOrderBy() {
		if (orderBy.equals("")){
			orderBy = "RazonSocial";
		}
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void vaciaLista()
	{
		listaClientes.clear();
	}
	public void notificarCambiosAdapter(){
		adapterConsultaClientes.notifyDataSetChanged();
	}
	
	public void setNoRecargar(boolean noRecargar){
		adapterConsultaClientes.setNoRecargar(noRecargar);
	}
	
	public boolean getNoRecargar(){
		return adapterConsultaClientes.getNoRecargar();
	}
	
	public void cambiarCriterioBusqueda(String criterio){
		
		setOrderBy(criterio);
		pantallaManagerConsultaClientes.setHintParametroBusqueda(criterio);
		pantallaManagerConsultaClientes.setParametroBusqueda("");
		vaciaLista();
		setNoRecargar(false);
		cargarRegistros(0,"");
		notificarCambiosAdapter();

	}
	
	public AdapterConsultaClientes getAdapterConsultaClientes(){
		return this.adapterConsultaClientes;
	}
	
	public List<Cliente> getListaClientes(){
		return this.listaClientes;
	}

	public void llenaloc(Activity a,Spinner spnloc)
	{
		List<String> lables = new ArrayList<String>();
        lables.add("Seleccione una Localidad");
		System.out.println("tama?o lista "+listaLocalidades.size());
		for (int i = 0; i < listaLocalidades.size(); i++)
		{
			// Get the position
      System.out.println("obtiene localidad");
			Cliente cliente = listaLocalidades.get(i);
			lables.add(cliente.getLocalidad());
		}

		// Creating adapter for spinner
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(a,android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spnloc.setAdapter(spinnerAdapter);


	}


	/*public void llenazonas(Activity a,Spinner spnzon)
	{
		List<String> lables = new ArrayList<String>();
		lables.add("Seleccione una Zona");

		for (int i = 0; i < listaZonas.size(); i++)
		{
			// Get the position
			System.out.println("obtiene zona");
			Cliente cliente = listaZonas.get(i);
			lables.add(cliente.getZona());
		}

		// Creating adapter for spinner
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(a,android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spnzon.setAdapter(spinnerAdapter);


	}*/

}
