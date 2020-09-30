package ar.com.syswork.sysmobile.plistapedidos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.Pedido;
import ar.com.syswork.sysmobile.util.Utilidades;

public class AdapterListaPedidos  extends ArrayAdapter<Pedido> 
{
	private LayoutInflater lInflater;
	
	public AdapterListaPedidos(Context context, List<Pedido> listaPedidos) 
	{
		super(context,0, listaPedidos);
		lInflater = LayoutInflater.from(context);
	}
	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		EnvoltorioAux envoltorio = null;
		
		
		if (convertView == null){
			view = lInflater.inflate(R.layout.item_lista_pedidos,null);
			
			envoltorio = new EnvoltorioAux();
			envoltorio.txtRazonSocialCliente = (TextView) view.findViewById(R.id.txtRazonSocialCliente);
			envoltorio.txtFecha = (TextView) view.findViewById(R.id.txtFecha);
			envoltorio.txtImporte = (TextView) view.findViewById(R.id.txtImporte);
			
			view.setTag(envoltorio);
			
		}
		else{
			
			view = convertView;
			envoltorio = (EnvoltorioAux) view.getTag();
			
		}
		
		Pedido pedido = getItem(position);

		envoltorio.txtRazonSocialCliente.setText(pedido.getCliente().getRazonSocial());
		envoltorio.txtFecha.setText(Utilidades.fechaYYYYMMDDHHMMSStoDD_MM_YYYY(pedido.getFecha()));
		envoltorio.txtImporte.setText("$ " + Double.toString(Utilidades.Redondear((pedido.getTotalNeto()),2) ));


		return view;
	}

	@Override
	public Pedido getItem(int position) 
	{
		return super.getItem(position);
	}
	
	private class EnvoltorioAux {
		TextView txtRazonSocialCliente;
		TextView txtFecha;
		TextView txtImporte;
	}

	
	
}
