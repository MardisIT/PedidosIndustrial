package ar.com.syswork.sysmobile.pcargapedidos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.PedidoItem;

public class AdapterCargaPedidos extends ArrayAdapter<PedidoItem> {
	
	private LayoutInflater lInflater;
	//private LogicaCargaPedidos logicaCargaPedidos;
	
	public AdapterCargaPedidos(Context context, ArrayList<PedidoItem> listaPedidoItem,LogicaCargaPedidos logicaCargaPedidos) {
		super(context,0, listaPedidoItem);
		lInflater = LayoutInflater.from(context);
		//this.logicaCargaPedidos = logicaCargaPedidos;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		EnvoltorioAux envoltorio = null;

		if (convertView == null){
			view = lInflater.inflate(R.layout.item_carga_pedido,null);
			
			envoltorio = new EnvoltorioAux();

			envoltorio.txtDescripcionArticulo= (TextView) view.findViewById(R.id.txtDescripcionArticulo);
			envoltorio.txtCantidad = (TextView) view.findViewById(R.id.txtCantidad);
			envoltorio.txtUnitario = (TextView) view.findViewById(R.id.txtUnitario);
			envoltorio.txtTotal = (TextView) view.findViewById(R.id.txtTotal);
			envoltorio.txtDesPpago=(TextView)view.findViewById(R.id.txtdesprontopago);
			envoltorio.txtDesNespecial=(TextView)view.findViewById(R.id.txtdesnespeciald);
			view.setTag(envoltorio);
			
		}
		else{
			
			view = convertView;
			envoltorio = (EnvoltorioAux) view.getTag();
			
		}
		
		PedidoItem pedidoItem = getItem(position);

		envoltorio.txtDescripcionArticulo.setText(pedidoItem.getAuxDescripcionArticulo());
		envoltorio.txtCantidad.setText(Double.toString(pedidoItem.getCantidad()));
		envoltorio.txtUnitario.setText(Double.toString(pedidoItem.getImporteUnitario()));
		envoltorio.txtTotal.setText(Double.toString(pedidoItem.getTotal()));
		envoltorio.txtDesPpago.setText(Double.toString(pedidoItem.getPpago()));
		envoltorio.txtDesNespecial.setText(Double.toString(pedidoItem.getNespecial()));


		return view;
	}

	private class EnvoltorioAux {
		TextView txtDescripcionArticulo;
		TextView txtCantidad;
		TextView txtUnitario;
		TextView txtTotal;
		TextView txtDesPpago;
		TextView txtDesNespecial;
	}

}

