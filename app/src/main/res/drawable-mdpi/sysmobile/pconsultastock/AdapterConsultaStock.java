package ar.com.syswork.sysmobile.pconsultastock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.ItemConsultaStock;

public class AdapterConsultaStock extends ArrayAdapter<ItemConsultaStock>{
	
	private LayoutInflater lInflater;
	
	public AdapterConsultaStock(Context context, ArrayList<ItemConsultaStock> listaStock) {
		super(context,0, listaStock);
		lInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getItemViewType(int position) {
		ItemConsultaStock i = getItem(position);
		
		if (i.getIdDeposito().length()>4) 
			return 0;
		
		return 1;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		EnvoltorioAux envoltorio = null;
		ItemConsultaStock itemConsultaStock = getItem(position);
		
		
		if (convertView == null)
		{
			if (itemConsultaStock.getIdDeposito().length()>4)
			{
				view = lInflater.inflate(R.layout.item_consulta_stock_cabecera,null);
			}
			else
			{
				view = lInflater.inflate(R.layout.item_consulta_stock,null);
			}
			
			envoltorio = new EnvoltorioAux();
			envoltorio.txtDescripcion = (TextView) view.findViewById(R.id.txtDescripcionDeposito);
			envoltorio.txtTotal = (TextView) view.findViewById(R.id.txtTotal);
			envoltorio.img= (ImageView) view.findViewById(R.id.imagen);
			
			if (itemConsultaStock.getIdDeposito().length()>4)
			{
				if (itemConsultaStock.getIdDeposito().equals("@REAL"))
					envoltorio.imgRes = R.drawable.simbolo_informacion;
				if (itemConsultaStock.getIdDeposito().equals("@COMPROMETIDO"))
					envoltorio.imgRes = R.drawable.simbolo_exclamacion;
				if (itemConsultaStock.getIdDeposito().equals("@SUGERIDO"))
					envoltorio.imgRes = R.drawable.symbolo_ok;
			}
			else
			{
				envoltorio.imgRes = R.drawable.deposito;
				
			}
			
			
			view.setTag(envoltorio);
			
		}
		else
		{
			view = convertView;
			envoltorio = (EnvoltorioAux) view.getTag();
		}
		
		envoltorio.txtDescripcion.setText(itemConsultaStock.getDescripcion());
		envoltorio.txtTotal.setText("" + itemConsultaStock.getCantidad());
		envoltorio.img.setImageResource(envoltorio.imgRes);
		
		return view;
	}

	private class EnvoltorioAux {
		ImageView img;
		TextView txtDescripcion;
		TextView txtTotal;
		int imgRes;

	}

}
