package ar.com.syswork.sysmobile.pconsultactacte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.Reporte;

public class AdapterReportePagos  extends ArrayAdapter<Reporte> {

    private LayoutInflater lInflater;

    public AdapterReportePagos(Context context, ArrayList<Reporte> listareporte) {
        super(context,0, listareporte);

        lInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        Reporte i = getItem(position);

        if (!i.getNombrecliente().equals(""))
            return 0;

        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        try {

            View view = null;
            AdapterReportePagos.EnvoltorioAux envoltorio = null;
            final Reporte reporte = getItem(position);

		/* SI LA FECHA VIENE EN "", QUIERE DECIR QUE ES EL SALDO ANTERIOR O EL
		   SALDO ACTUAL, POR TANTO TENGO QUE CARGAR OTRO LAYOUT PARA EL ITEM
		*/

            if (convertView == null) {
                view = lInflater.inflate(R.layout.item_reporte, null);

                envoltorio = new AdapterReportePagos.EnvoltorioAux();

                envoltorio.txtclientepago = (TextView) view.findViewById(R.id.txtclientepago);

                envoltorio.txtfactura = (TextView) view.findViewById(R.id.txtfactura);

                envoltorio.txtImportepago = (TextView) view.findViewById(R.id.txtImportepago);

                envoltorio.txtformapago = (TextView) view.findViewById(R.id.txtformapago);

                view.setTag(envoltorio);

            } else {

                view = convertView;
                envoltorio = (AdapterReportePagos.EnvoltorioAux) view.getTag();

            }

            envoltorio.txtclientepago.setText(reporte.getNombrecliente());
            envoltorio.txtfactura.setText(reporte.getNumerofactura());
            envoltorio.txtImportepago.setText(String.valueOf(reporte.getValorpago()));
            envoltorio.txtformapago.setText(reporte.getFormapago());






            return view;
        }catch (Exception ex){
            return  null;
        }
    }

    private class EnvoltorioAux {

        TextView txtclientepago;

        TextView txtfactura;

        TextView txtImportepago;

        TextView txtformapago;

    }
}
