package ar.com.syswork.sysmobile.plistavisitas;

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

public class AdapterListaVisita extends ArrayAdapter<VisitasUio>
{
    private LayoutInflater lInflater;

    public AdapterListaVisita(Context context, List<VisitasUio> visitasUios)
    {
        super(context,0, visitasUios);
        lInflater = LayoutInflater.from(context);
    }
    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        EnvoltorioAux envoltorio = null;


        if (convertView == null){
            view = lInflater.inflate(R.layout.activity_item_carga_visitas,null);

            envoltorio = new EnvoltorioAux();
            envoltorio.txtRazonSocialCliente = (TextView) view.findViewById(R.id.txtRazonSocialClienteinventario);
            envoltorio.txtFecha = (TextView) view.findViewById(R.id.txtFechainvnetario);


            view.setTag(envoltorio);

        }
        else{

            view = convertView;
            envoltorio = (EnvoltorioAux) view.getTag();

        }

        VisitasUio visitasUio = getItem(position);

        envoltorio.txtRazonSocialCliente.setText(visitasUio.getCliente().getRazonSocial());
        envoltorio.txtFecha.setText(visitasUio.getFechavisita());



        return view;
    }

    @Override
    public VisitasUio getItem(int position)
    {
        return super.getItem(position);
    }

    private class EnvoltorioAux {
        TextView txtRazonSocialCliente;
        TextView txtFecha;

    }



}
