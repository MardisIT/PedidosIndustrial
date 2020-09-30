package ar.com.syswork.sysmobile.pconsultactacte;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.ItemCtaCte;
import ar.com.syswork.sysmobile.util.DialogManager;

public class AdapterConsultaCtaCte  extends ArrayAdapter<ItemCtaCte> {
	
	private LayoutInflater lInflater;
	private Activity activity;
	String cod="";
	private DialogManager utlDlg;
	public AdapterConsultaCtaCte(Context context, ArrayList<ItemCtaCte> listaCtaCte) {
		super(context,0, listaCtaCte);
		utlDlg = new DialogManager();
		activity= (Activity) context;
		lInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getItemViewType(int position) {
		ItemCtaCte i = getItem(position);
		
		if (!i.getFecha().equals("")) 
			return 0;
		
		return 1;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	public View getView(int position, final View convertView, ViewGroup parent) {

		View view = null;
		EnvoltorioAux envoltorio = null;
		final int pos = position;
		final ItemCtaCte itemCtaCte = getItem(position);
		final OperacionesPagos obj= new OperacionesPagos();

		/* SI LA FECHA VIENE EN "", QUIERE DECIR QUE ES EL SALDO ANTERIOR O EL 
		   SALDO ACTUAL, POR TANTO TENGO QUE CARGAR OTRO LAYOUT PARA EL ITEM
		*/
		
		if (convertView == null){
			if (itemCtaCte.getFecha().equals(""))
			{
				view = lInflater.inflate(R.layout.item_consulta_cta_cte_saldos,null);
			}
			else{
				view = lInflater.inflate(R.layout.item_consulta_cta_cte,null);
			}
			//((EditText) convertView.findViewById(R.id.txtImporte)).addTextChangedListener(new TB_Abono_Watcher(convertView));
			
			envoltorio = new EnvoltorioAux();
			envoltorio.txtDetalle = (TextView) view.findViewById(R.id.txtDescripcionCpte);
			envoltorio.txtFecha = (TextView) view.findViewById(R.id.txtFecha);
			envoltorio.txtTcSucNroLetra = (TextView) view.findViewById(R.id.txtNroCpte);
            envoltorio.txtvalorpagado = (EditText) view.findViewById(R.id.txtvalorpago);
			envoltorio.imgCpte = (ImageView) view.findViewById(R.id.imagen_cpte_item);
			view.setTag(envoltorio);
            envoltorio.txtImporte = (TextView) view.findViewById(R.id.txtImporte);
            envoltorio.msjerror=(TextView) view.findViewById(R.id.resulerror);
		}
		else{
			
			view = convertView;
			envoltorio = (EnvoltorioAux) view.getTag();
			
		}




		envoltorio.txtDetalle.setText(itemCtaCte.getConcepto());
		envoltorio.txtFecha.setText(itemCtaCte.getFecha());
		envoltorio.txtTcSucNroLetra.setText(itemCtaCte.getTc() + itemCtaCte.getSucNroLetra());
		envoltorio.txtImporte.setText("$ " + itemCtaCte.getImporte());
        envoltorio.txtvalorpagado.setText(String.valueOf(itemCtaCte.getValor_pago()));
        envoltorio.msjerror.setText(itemCtaCte.getMsjerror());
        envoltorio.msjerror.setVisibility(View.INVISIBLE);
		envoltorio.txtvalorpagado.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					final int position = v.getId();
					final EditText Caption = (EditText) v;
					if(Caption.getText().toString().equals("")==false)
					itemCtaCte.setValor_pago(Double.valueOf(Caption.getText().toString()));
					else
                        itemCtaCte.setValor_pago(0.0);

				}else{
					final int position = v.getId();
					final EditText Caption = (EditText) v;
					if(Caption.getText().toString().equals("")==false)
						itemCtaCte.setValor_pago(Double.valueOf(Caption.getText().toString()));
					else
						itemCtaCte.setValor_pago(0.0);
				}


			}


		});




		
		if (!itemCtaCte.getFecha().equals(""))
		{
			if (itemCtaCte.getImporte() >= 0)
				envoltorio.imgCpte.setImageResource(R.drawable.factura);
			else
				envoltorio.imgCpte.setImageResource(R.drawable.cobranza);
		}
		
		return view;
	}

	private class EnvoltorioAux {
		TextView txtDetalle;
		TextView txtFecha;
		TextView txtTcSucNroLetra;
		TextView txtImporte;
		EditText txtvalorpagado;
		ImageView imgCpte;
		TextView msjerror;
	}

}
