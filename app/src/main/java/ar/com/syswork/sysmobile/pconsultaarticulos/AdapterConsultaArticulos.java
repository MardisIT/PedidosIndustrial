package ar.com.syswork.sysmobile.pconsultaarticulos;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.Articulo;

public class AdapterConsultaArticulos  extends ArrayAdapter<Articulo> {
	
	private LayoutInflater lInflater;
	private LogicaConsultaArticulos logicaConsultaArticulos;
	private boolean noRecargar;
	
	public AdapterConsultaArticulos(Context context, ArrayList<Articulo> listaArticulos,LogicaConsultaArticulos logicaConsultaArticulos) {
		super(context,0, listaArticulos);
		lInflater = LayoutInflater.from(context);
		this.logicaConsultaArticulos= logicaConsultaArticulos;

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		EnvoltorioAux envoltorio = null;
		
		
		if (convertView == null){
			view = lInflater.inflate(R.layout.item_consulta_articulos,null);
			
			envoltorio = new EnvoltorioAux();
			envoltorio.txtDescripcion = (TextView) view.findViewById(R.id.txtDescripcion);
			envoltorio.txtCodigo = (TextView) view.findViewById(R.id.txtCodigo);
			envoltorio.txtPrecio = (TextView) view.findViewById(R.id.txtPrecio);
			
			view.setTag(envoltorio);
			
		}
		else{
			
			view = convertView;
			envoltorio = (EnvoltorioAux) view.getTag();
			
		}
		
		Articulo articulo = getItem(position);

		envoltorio.txtDescripcion.setText(articulo.getDescripcion());
		envoltorio.txtCodigo.setText(articulo.getIdArticulo());
		envoltorio.txtPrecio.setText(Double.toString(articulo.getPrecio1()));


		// Si es el Ultimo, RECARGO
		if ( ((position +1 )==getCount()) & (!noRecargar)){
			logicaConsultaArticulos.cargarRegistros(position +1 ,"");
		}

		return view;
	}

	public boolean getNoRecargar() {
		return noRecargar;
	}

	public void setNoRecargar(boolean noRecargar) {
		this.noRecargar = noRecargar;
	}

	private class EnvoltorioAux {
		TextView txtDescripcion;
		TextView txtPrecio;
		TextView txtCodigo;

	}
}
