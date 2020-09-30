package ar.com.syswork.sysmobile.psincronizar;

import android.view.View;
import android.view.View.OnClickListener;
import ar.com.syswork.sysmobile.R;

public class ListenerSincronizacion implements OnClickListener {
	
	private LogicaSincronizacion logicaSincronizacion;
	private PantallaManagerSincronizacion pantallaManagerSincronizacion;
	
	public ListenerSincronizacion(LogicaSincronizacion logicaSincronizacion) {
		this.logicaSincronizacion = logicaSincronizacion;
	}

	public void seteaListener(View v)
	{
		v.setOnClickListener(this);
	}
		
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.btnSincronizar:
				logicaSincronizacion.sincronizar();
				break;
			case R.id.btncargarlocalesCL:
				logicaSincronizacion.sincronizarconfiguracion();
				break;
			case R.id.btnCerrarSincronizacion:
				
				if (logicaSincronizacion.huboErroes())
					pantallaManagerSincronizacion.cierraDialogoSincronizacion();
					//pantallaManagerSincronizacion.finalizarActivityConErrores();
				else
					pantallaManagerSincronizacion.finalizarActivitySinErrores();
				break;
			
		}
	}

	public void setPantallaManager(PantallaManagerSincronizacion pantallaManagerSincronizacion) {
		this.pantallaManagerSincronizacion= pantallaManagerSincronizacion; 
		
	}
}
