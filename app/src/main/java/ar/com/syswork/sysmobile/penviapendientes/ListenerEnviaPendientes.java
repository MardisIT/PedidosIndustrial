package ar.com.syswork.sysmobile.penviapendientes;

import android.view.View;
import android.view.View.OnClickListener;
import ar.com.syswork.sysmobile.R;

public class ListenerEnviaPendientes implements OnClickListener{
	
	private LogicaEnviaPendientes logicaEnviaPendientes;
	private PantallaManagerEnviaPendientes pantallaManagerEnviaPendientes;
	
	@Override
	public void onClick(View v) {

		
		switch (v.getId())
		{
			case R.id.btnEnviarPendientes:
				logicaEnviaPendientes.enviarPedidosPendientes();
				break;
			case R.id.btnEnviarPendientesPagos:
				logicaEnviaPendientes.enviarPagosPendientes();
				break;
			case R.id.btnEnviarPendientesInventario:
				logicaEnviaPendientes.enviarInventarioPendientes();
				break;
			case R.id.btnEnviarPendientesvisitas:
				logicaEnviaPendientes.enviarVisistasPendientes();
				break;
			case R.id.btnCerrarEnvioPendientes:
				pantallaManagerEnviaPendientes.cerrarActivity();
				break;
		}
		
	}

	public void seteaLogica(LogicaEnviaPendientes logicaEnviaPendientes) {
		this.logicaEnviaPendientes = logicaEnviaPendientes;
		
	}

	public void seteaPantallaMager(PantallaManagerEnviaPendientes pantallaManagerEnviaPendientes) 
	{
		this.pantallaManagerEnviaPendientes = pantallaManagerEnviaPendientes;
	}

}
