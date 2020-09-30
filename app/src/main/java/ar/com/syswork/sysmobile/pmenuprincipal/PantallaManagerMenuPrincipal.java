package ar.com.syswork.sysmobile.pmenuprincipal;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.ListView;
import ar.com.syswork.sysmobile.R;

public class PantallaManagerMenuPrincipal {
	
	private Activity a;
	private ListenerMenuPrincipal listenerMenuPrincipal;
	
	public PantallaManagerMenuPrincipal(Activity a, ListenerMenuPrincipal listenerMenuPrincipal) {
		this.a = a;
		this.listenerMenuPrincipal = listenerMenuPrincipal;
	}

	public void seteaListener() {
		ListView lv = (ListView) a.findViewById(R.id.listViewMenuPrincipal);
		lv.setOnItemClickListener(listenerMenuPrincipal);
		
	}
	
	public void muestraAlertaFaltanVendedores(){
		AlertDialog alertDialog = new AlertDialog.Builder(a).create();
		alertDialog.setTitle(a.getString(R.string.avisoAlOperador));
		alertDialog.setMessage(a.getString(R.string.avisoVendedorVacio));		
		alertDialog.setIcon(R.drawable.simbolo_informacion);
		alertDialog.show();	
	}

	

}
