package ar.com.syswork.sysmobile.util;

import android.app.Activity;
import android.widget.Toast;

public class DialogManager {

	public void muestraToastGenerico(Activity a, String texto,boolean duracionLarga)
	{
		int duracion= Toast.LENGTH_SHORT;
		if (duracionLarga)
			duracion = Toast.LENGTH_LONG;
		
		Toast notificacionToast=Toast.makeText(a, texto,duracion);
		notificacionToast.show();

	}

}
