package ar.com.syswork.sysmobile.pconsultaclientes;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.Cliente;

public class AdapterConsultaClientes extends ArrayAdapter<Cliente> {
	
	private LayoutInflater lInflater;
	private LogicaConsultaClientes logicaConsultaClientes;
	private boolean noRecargar;
	
	public AdapterConsultaClientes(Context context, ArrayList<Cliente> listaClientes,LogicaConsultaClientes logicaConsultaClientes) {
		super(context,0, listaClientes);
		lInflater = LayoutInflater.from(context);
		this.logicaConsultaClientes = logicaConsultaClientes;

	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		EnvoltorioAux envoltorio = null;
		
		
		if (convertView == null){
			view = lInflater.inflate(R.layout.item_consulta_clientes,null);
			
			envoltorio = new EnvoltorioAux();
			envoltorio.txtRazonSocial= (TextView) view.findViewById(R.id.txtRazonSocialCliente);
			envoltorio.txtCodigo= (TextView) view.findViewById(R.id.txtCodigo);
			
			view.setTag(envoltorio);
			
		}
		else{
			
			view = convertView;
			envoltorio = (EnvoltorioAux) view.getTag();
			
		}
		
		Cliente cliente = getItem(position);

		envoltorio.txtRazonSocial.setText(cliente.getRazonSocial());
		envoltorio.txtCodigo.setText(cliente.getCodigo() + " / " + cliente.getCodigoOpcional());

		// Si es el Ultimo, RECARGO
		if ( ((position +1 )==getCount()) & (!noRecargar)){
			logicaConsultaClientes.cargarRegistros(position +1 ,"");
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
		TextView txtRazonSocial;
		TextView txtCodigo;
	}

}
