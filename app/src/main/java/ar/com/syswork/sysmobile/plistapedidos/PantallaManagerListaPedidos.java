package ar.com.syswork.sysmobile.plistapedidos;

import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;
import ar.com.syswork.sysmobile.R;

public class PantallaManagerListaPedidos {
	
	private Activity a;
	private ListenerListaPedidos listenerListaPedidos;
	private ListView lstConsultaPedidos;
	private TextView txtCantidadDePedidos;
	private String strTextoCantidadPedidos;
	public PantallaManagerListaPedidos(Activity a , ListenerListaPedidos listenerListaPedidos) {
		
		this.a = a;
		this.listenerListaPedidos = listenerListaPedidos;
		lstConsultaPedidos = (ListView) this.a.findViewById(R.id.lstConsultaPedidos);
		txtCantidadDePedidos = (TextView) this.a.findViewById(R.id.txtCantidadDePedidos);
		strTextoCantidadPedidos = this.a.getString(R.string.cantidad_de_items);
	}
	
	public void seteaListener()
	{
		lstConsultaPedidos.setOnItemClickListener(listenerListaPedidos);
	}
	
	public ListView getLstConsultaPedidos()
	{
		return lstConsultaPedidos;
	}

	public void setTextoCantidadDePedidos(int cantidad)
	{
		String texto = strTextoCantidadPedidos + " " + cantidad;
		txtCantidadDePedidos.setText(texto);
	}
	
}
