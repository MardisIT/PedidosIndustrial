package ar.com.syswork.sysmobile.pmenuprincipal;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.syswork.sysmobile.entities.ItemMenuPrincipal;
import ar.com.syswork.sysmobile.R;

public class AdapterMenuPrincipal extends ArrayAdapter<ItemMenuPrincipal>{

	private LayoutInflater lInflater;
	
	public AdapterMenuPrincipal(Context context,  List<ItemMenuPrincipal> lista) {
		
		super( context, 0, lista );
		lInflater = LayoutInflater.from(context);
		
	}
	

	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		EnvoltorioAux envoltorio = null;
		
		if (convertView == null){
			view = lInflater.inflate(R.layout.item_menu_principal,null);
			
			envoltorio = new EnvoltorioAux();
			envoltorio.txtDescripcion = (TextView) view.findViewById(R.id.txtDescripcion);
			envoltorio.txtSinEnviar= (TextView) view.findViewById(R.id.txtSinEnviar);
			envoltorio.imagenOpcion= (ImageView) view.findViewById(R.id.imagenOpcion);
			
			view.setTag(envoltorio);
			
		}
		else{
			
			view = convertView;
			envoltorio = (EnvoltorioAux) view.getTag();
			
		}

		ItemMenuPrincipal item = getItem(position);

		envoltorio.txtDescripcion.setText(item.getTitulo());
		if (item.getCantidad() == 0){
			envoltorio.txtSinEnviar.setText("");
		} 
		else{
			envoltorio.txtSinEnviar.setText("("+ Integer.toString(item.getCantidad()) + ")");
		}
		envoltorio.imagenOpcion.setImageResource(item.getImagen());
		
		return view;
	
	}
	
		private class EnvoltorioAux {
		TextView txtDescripcion;
		TextView txtSinEnviar;
		ImageView imagenOpcion;
	}

	
}
