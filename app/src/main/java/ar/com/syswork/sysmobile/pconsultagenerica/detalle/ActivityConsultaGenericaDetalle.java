package ar.com.syswork.sysmobile.pconsultagenerica.detalle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.ItemClaveValor;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class ActivityConsultaGenericaDetalle extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_generica_detalle);
		
		// Creo una Lista
		List<ItemClaveValor> lista = new ArrayList<ItemClaveValor>();
		
		// Creo el Adapter
		AdapterConsultaGenericaDetalle adapter = new AdapterConsultaGenericaDetalle(this, lista);
		
		// Seteo el Adapter
		ListView lv = (ListView) this.findViewById(R.id.lstDetalleConsulta);
		lv.setAdapter(adapter);
		lv.setDividerHeight(0);

		
		// Lleno la lista
		AppSysMobile app = (AppSysMobile) getApplication();
		List<ItemClaveValor> listaTmp = new ArrayList<ItemClaveValor>();
		listaTmp = app.getListaClaveValor();
		Iterator<ItemClaveValor> i = listaTmp.iterator();
		while (i.hasNext())
		{
			lista.add(i.next());
		}
		
		adapter.notifyDataSetChanged();
		
		// Seteo la Imagen del detalle
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		int icono_detalle = extras.getInt("icono_detalle");
		String titulo = extras.getString("titulo");
		this.setTitle(titulo);
		
		ImageView iv = (ImageView) findViewById(R.id.imgConsultaGenerica);
		iv.setImageResource(icono_detalle);
		
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	
}
