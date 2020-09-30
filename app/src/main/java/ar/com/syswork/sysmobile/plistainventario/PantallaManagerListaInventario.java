package ar.com.syswork.sysmobile.plistainventario;

import android.app.Activity;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.plistapedidos.ListenerListaPedidos;


public class PantallaManagerListaInventario {

    private Activity a;
    ListenerListaInventario listenerListaInventario;
    private ListView lstConsultainventario;
    private TextView txtCantidadDeInventario;
    private String strTextoCantidadInventario;
    public PantallaManagerListaInventario(Activity a , ListenerListaInventario listenerListaInventario) {

        this.a = a;
        this.listenerListaInventario = listenerListaInventario;
        lstConsultainventario = (ListView) this.a.findViewById(R.id.lstConsultainventario1);
        txtCantidadDeInventario = (TextView) this.a.findViewById(R.id.txtCantidadDeInventario);
        strTextoCantidadInventario = this.a.getString(R.string.cantidad_de_items);
    }

    public void seteaListener()
    {
        lstConsultainventario.setOnItemClickListener(listenerListaInventario);
    }

    public ListView getLstConsultainventario()
    {
        return lstConsultainventario;
    }

    public void setTextoCantidadDeinventario(int cantidad)
    {
        String texto = strTextoCantidadInventario + " " + cantidad;
        txtCantidadDeInventario.setText(texto);
    }

}
