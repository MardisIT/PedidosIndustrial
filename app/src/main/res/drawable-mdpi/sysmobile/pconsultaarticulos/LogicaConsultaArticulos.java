package ar.com.syswork.sysmobile.pconsultaarticulos;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.syswork.sysmobile.daos.DaoArticulo;
import ar.com.syswork.sysmobile.daos.DaoRubro;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Articulo;
import ar.com.syswork.sysmobile.entities.Rubro;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class LogicaConsultaArticulos {
	
	private Activity a;

	private ArrayList<Articulo> listaArticulos;
	private ArrayList<Rubro> listaRubros;

	private PantallaManagerConsultaArticulos pantallaManagerConsultaArticulos;
	private AdapterConsultaArticulos adapterConsultaArticulos;
	
	private AppSysMobile app;
	
	private DataManager dataManager;
	private DaoArticulo daoArticulo;
	private Articulo articulo;
	private DaoRubro daoRubro;
	private Rubro rubro;

	private	boolean estaCargandoRegistros=false;

	private String orderBy;
	
	public LogicaConsultaArticulos(Activity a,PantallaManagerConsultaArticulos pantallaManagerConsultaArticulos){
		
		this.a = a;
		
		listaArticulos = new ArrayList<Articulo>();
		listaRubros = new ArrayList<Rubro>();
				
		//Creo el Adapter
		this.adapterConsultaArticulos = new AdapterConsultaArticulos(this.a,listaArticulos,this);

		app = (AppSysMobile) this.a.getApplication();
		dataManager = app.getDataManager();
		daoArticulo = dataManager.getDaoArticulo();
		daoRubro = dataManager.getDaoRubro();

		
		setOrderBy("Descripcion");
		this.pantallaManagerConsultaArticulos = pantallaManagerConsultaArticulos; 
		
	}

	public void cargarRegistros(int desde, String where){
		
		final int d = desde;
		final String w = where;
		if (estaCargandoRegistros==false){
			
			estaCargandoRegistros=true;
			
			new Handler().post(new Runnable() {
				
				@Override
				public void run() {
					List<Articulo> listaTmp = new ArrayList<Articulo>();
					listaTmp = daoArticulo.getAllWithLimit(w,d,orderBy);

					Iterator<Articulo> i = listaTmp.iterator();
					
					while (i.hasNext()){
						articulo = i.next();
						listaArticulos.add(articulo);
					}
					
					if ( listaTmp.size()>0 ){
						notificarCambiosAdapter();
					}
				}
			});

			estaCargandoRegistros=false;
		}
	}

	//consulta de Rubros
	public void cargarRubros(final Activity a, final Spinner spnRub){


		new Handler().post(new Runnable() {

			@Override
			public void run() {
				List<Rubro> listaTmp = new ArrayList<Rubro>();
				listaTmp = daoRubro.getAll("");

				Iterator<Rubro> i = listaTmp.iterator();

				while (i.hasNext()){
					rubro = i.next();
					listaRubros.add(rubro);
				}
				llenaRub(a,spnRub);

			}
		});


	}
	////


	public String getOrderBy() {
		if (orderBy.equals("")){
			orderBy = "Descripcion";
		}
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public void vaciaLista()
	{
		listaArticulos.clear();
	}
	public void notificarCambiosAdapter(){
		adapterConsultaArticulos.notifyDataSetChanged();

	}
	public void setNoRecargar(boolean noRecargar){
		adapterConsultaArticulos.setNoRecargar(noRecargar);
	}
	public boolean getNoRecargar(){
		return adapterConsultaArticulos.getNoRecargar();
	}
	
	public void cambiarCriterioBusqueda(String criterio){
		
		setOrderBy(criterio);
		pantallaManagerConsultaArticulos.setHintParametroBusqueda(criterio);
		pantallaManagerConsultaArticulos.setParametroBusqueda("");
		vaciaLista();
		setNoRecargar(false);
		cargarRegistros(0,"");
		notificarCambiosAdapter();
	}
	
	public AdapterConsultaArticulos getAdapterConsultaArticulos(){
		return this.adapterConsultaArticulos;
	}
	
	public List<Articulo> getListaArticulos(){
		return this.listaArticulos;
	}
	
	public void seteaCodigoArticuloCargaPedidos(String codArticulo)
	{
		
		Intent i = new Intent();
		i.putExtra("codArticulo", codArticulo);
		a.setResult(Activity.RESULT_OK, i);
		a.finish();
		
	}

	public void llenaRub(Activity a, Spinner spnRub)
	{
		List<String> lables = new ArrayList<String>();
		lables.add("Seleccione un Rubro");
		System.out.println("tamaño lista "+listaRubros.size());
		for (int i = 0; i < listaRubros.size(); i++)
		{
			// Get the position
			System.out.println("obtiene Rubro");
			Rubro  rubro = listaRubros.get(i);
			lables.add(articulo.getDescripcion());
		}

		// Creating adapter for spinner
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(a,android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spnRub.setAdapter(spinnerAdapter);
	}
}
