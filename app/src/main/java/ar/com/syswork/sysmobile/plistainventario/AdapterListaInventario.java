package ar.com.syswork.sysmobile.plistainventario;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.Inventario;
import ar.com.syswork.sysmobile.entities.Pedido;
import ar.com.syswork.sysmobile.plistapedidos.AdapterListaPedidos;
import ar.com.syswork.sysmobile.util.Utilidades;

public class AdapterListaInventario    extends ArrayAdapter<Inventario>
{
    private LayoutInflater lInflater;

    public AdapterListaInventario(Context context, List<Inventario> listaInventarios)
    {
        super(context,0, listaInventarios);
        lInflater = LayoutInflater.from(context);
    }
    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        EnvoltorioAux envoltorio = null;


        if (convertView == null){
            view = lInflater.inflate(R.layout.item_lista_inventario,null);

            envoltorio = new EnvoltorioAux();
            envoltorio.txtRazonSocialCliente = (TextView) view.findViewById(R.id.txtRazonSocialClienteinventario);
            envoltorio.txtFecha = (TextView) view.findViewById(R.id.txtFechainvnetario);


            view.setTag(envoltorio);

        }
        else{

            view = convertView;
            envoltorio = (EnvoltorioAux) view.getTag();

        }

        Inventario inventario = getItem(position);

        envoltorio.txtRazonSocialCliente.setText(inventario.getCliente().getRazonSocial());
        envoltorio.txtFecha.setText(inventario.getFechainventario());



        return view;
    }

    @Override
    public Inventario getItem(int position)
    {
        return super.getItem(position);
    }

    private class EnvoltorioAux {
        TextView txtRazonSocialCliente;
        TextView txtFecha;

    }



}
