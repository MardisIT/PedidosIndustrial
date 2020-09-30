package ar.com.syswork.sysmobile.pconsultagenerica.detalle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.ItemClaveValor;

;

public class AdapterConsultaGenericaDetalle extends ArrayAdapter<ItemClaveValor> {
	
	private LayoutInflater lInflater;
	
	public AdapterConsultaGenericaDetalle(Context context, List<ItemClaveValor> lista) {
		
		super(context,0, lista);
		lInflater = LayoutInflater.from(context);

	}

	@Override
	public int getItemViewType(int position) {
		ItemClaveValor i = getItem(position);
		
		if (i.getTipo() == ItemClaveValor.TIPO_BOOLEAN) 
			return 0;
		
		return 1;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		EnvoltorioAuxClaveValor envoltorio = null;
		ItemClaveValor itemClaveValor = getItem(position);
		
		
		if (convertView == null){
			
			envoltorio = new EnvoltorioAuxClaveValor();
			
			// Dependiendo del tipo de dato es el LayOut que cargo.-
			switch (itemClaveValor.getTipo()){
				case ItemClaveValor.TIPO_BOOLEAN: 
					view = lInflater.inflate(R.layout.item_clave_valor_bool,null);
					envoltorio.chkValor= (CheckBox) view.findViewById(R.id.checkValor);
					break;
				case ItemClaveValor.TIPO_STRING: 
					view = lInflater.inflate(R.layout.item_clave_valor_text, null);
					envoltorio.edtValor= (EditText) view.findViewById(R.id.txtValor);
					break;
				default:
					break;
			}

			envoltorio.txtClave = (TextView) view.findViewById(R.id.txtClave);
			envoltorio.tipo = itemClaveValor.getTipo();
			
			view.setTag(envoltorio);
			
		}
		else
		{
			
			view = convertView;
			envoltorio = (EnvoltorioAuxClaveValor) view.getTag();
		}
		
		
		envoltorio.txtClave.setText(itemClaveValor.getClave());
		switch (itemClaveValor.getTipo())
		{
			case ItemClaveValor.TIPO_BOOLEAN: 
				envoltorio.chkValor.setChecked(itemClaveValor.getValorBoolean());
				break;
			case ItemClaveValor.TIPO_STRING: 
				envoltorio.edtValor.setText(itemClaveValor.getValorString() );
				break;
			default:
				break;
		}
		
		envoltorio.tipo = itemClaveValor.getTipo();

		return view;
	
	}


	public class EnvoltorioAuxClaveValor {
		TextView txtClave;
		EditText edtValor;
		CheckBox chkValor;
		int tipo;
	}

}
