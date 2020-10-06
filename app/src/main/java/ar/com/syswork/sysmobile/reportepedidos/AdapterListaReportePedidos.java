package ar.com.syswork.sysmobile.reportepedidos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.VisitasUio;
import ar.com.syswork.sysmobile.entities.reporteitem;

public class AdapterListaReportePedidos extends ArrayAdapter<reportepedidosE>
{
    private LayoutInflater lInflater;

    public AdapterListaReportePedidos(Context context, List<reportepedidosE> reporteitems)
    {
        super(context,0, reporteitems);
        lInflater = LayoutInflater.from(context);
    }
    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        EnvoltorioAux envoltorio = null;


        if (convertView == null){
            view = lInflater.inflate(R.layout.item_reportepedidos,null);

            envoltorio = new EnvoltorioAux();
            envoltorio.txtcodProductoD = (TextView) view.findViewById(R.id.txtcodProductoD);
            envoltorio.txtDescripcionProd = (TextView) view.findViewById(R.id.txtDescripcionProd);
            envoltorio.txtcantidadpro = (TextView) view.findViewById(R.id.txtcantidadpro);
            envoltorio.txtpreciop = (TextView) view.findViewById(R.id.txtpreciop);
            envoltorio.txttotalp = (TextView) view.findViewById(R.id.txttotalp);
            envoltorio.txtunidades = (TextView) view.findViewById(R.id.txtunidades);


            view.setTag(envoltorio);

        }
        else{

            view = convertView;
            envoltorio = (EnvoltorioAux) view.getTag();

        }

        reportepedidosE visitasUio = getItem(position);

        envoltorio.txtcodProductoD.setText(visitasUio.getCodProductoD());
        envoltorio.txtDescripcionProd.setText(visitasUio.getDescripcionProd());
        envoltorio.txtcantidadpro.setText(String.valueOf(visitasUio.getCantidadpro()));
        envoltorio.txtpreciop.setText(String.valueOf(visitasUio.getPreciop()));
        envoltorio.txttotalp.setText(String.valueOf(visitasUio.getTotalp()));
        envoltorio.txtunidades.setText(String.valueOf(visitasUio.getUnidades()));




        return view;
    }

    @Override
    public reportepedidosE getItem(int position)
    {
        return super.getItem(position);
    }

    private class EnvoltorioAux {
        TextView txtcodProductoD;
        TextView txtDescripcionProd;
        TextView txtcantidadpro;
        TextView txtpreciop;
        TextView txttotalp;
        TextView txtunidades;


    }



}
