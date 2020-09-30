package ar.com.syswork.sysmobile.plistavisitas;

import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

import ar.com.syswork.sysmobile.R;


public class PantallaManagerListavisita {

    private Activity a;
    ListenerListaVisita listenerListaVisita;
    private ListView lstConsultavisitas;
    private TextView txtCantidadDevisitas;
    private String strTextoCantidadvisitas;
    public PantallaManagerListavisita(Activity a ,  ListenerListaVisita listenerListaVisita) {

        this.a = a;
        this.listenerListaVisita = listenerListaVisita;
        lstConsultavisitas = (ListView) this.a.findViewById(R.id.lstConsultavisitas1);
        txtCantidadDevisitas = (TextView) this.a.findViewById(R.id.txtCantidadDevisitas);
        strTextoCantidadvisitas = this.a.getString(R.string.cantidad_de_items);
    }

    public void seteaListener()
    {
        lstConsultavisitas.setOnItemClickListener(listenerListaVisita);
    }

    public ListView getLstConsultainventario()
    {
        return lstConsultavisitas;
    }

    public void setTextoCantidadDeinventario(int cantidad)
    {
        String texto = strTextoCantidadvisitas + " " + cantidad;
        txtCantidadDevisitas.setText(texto);
    }

}
