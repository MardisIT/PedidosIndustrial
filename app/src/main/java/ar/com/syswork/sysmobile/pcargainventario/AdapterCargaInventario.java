package ar.com.syswork.sysmobile.pcargainventario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.PedidoItem;
import ar.com.syswork.sysmobile.entities.inventariodetalles;
import ar.com.syswork.sysmobile.pcargapedidos.AdapterCargaPedidos;
import ar.com.syswork.sysmobile.pcargapedidos.LogicaCargaPedidos;

public class AdapterCargaInventario extends ArrayAdapter<inventariodetalles> {

    private LayoutInflater lInflater;


    public AdapterCargaInventario(Context context, ArrayList<inventariodetalles> listainventariodetalles, LogicaCargaInventario logicaCargaInventario) {
        super(context,0, listainventariodetalles);
        lInflater = LayoutInflater.from(context);
        //this.logicaCargaPedidos = logicaCargaPedidos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        EnvoltorioAux envoltorio = null;

        if (convertView == null){
            view = lInflater.inflate(R.layout.item_carga_inventario,null);

            envoltorio = new EnvoltorioAux();

            envoltorio.txtDescripcionArticulo= (TextView) view.findViewById(R.id.txtDescripcionArticulo);
            envoltorio.txtCantidad = (TextView) view.findViewById(R.id.txtCantidad);
            envoltorio.txtunidad= (TextView) view.findViewById(R.id.txtunidad);

            view.setTag(envoltorio);

        }
        else{

            view = convertView;
            envoltorio = (EnvoltorioAux) view.getTag();

        }

        inventariodetalles _inventariodetalles = getItem(position);
        envoltorio.txtDescripcionArticulo.setText(_inventariodetalles.getArticulo().getDescripcion());
        envoltorio.txtCantidad.setText(Double.toString(_inventariodetalles.getValor()));
        envoltorio.txtunidad.setText(_inventariodetalles.getUnidad());
         return view;
    }

    private class EnvoltorioAux {
        TextView txtDescripcionArticulo;
        TextView txtCantidad;
        TextView txtunidad;
    }

}
