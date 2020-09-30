package ar.com.syswork.sysmobile.pcargapedidos;

import android.view.View;
import android.view.View.OnClickListener;

import ar.com.syswork.sysmobile.R;

public class ListenerCargaPedidos implements OnClickListener{
	
	private PantallaManagerCargaPedidos pantallaManagerCargaPedidos;
	private LogicaCargaPedidos logicaCargaPedidos;
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId())
		{
		case R.id.imgBtnOk:
		
			logicaCargaPedidos.validaCodigoArticulo();
			break;
		
		case R.id.imgBtnBuscar:
			logicaCargaPedidos.consultarArticulos();
			break;
		
		case R.id.imgBtnScan:
			logicaCargaPedidos.scanArticulo();
			break;
		
		case R.id.btnAgregar:
			
			logicaCargaPedidos.validaCantidadIntroducida();
			break;
		
		case R.id.btnCancelarAgregar:
			
			logicaCargaPedidos.setCodigoProductoActual("");
			pantallaManagerCargaPedidos.cerrarDialogoSolicitaCantidad();
			break;
			
		case R.id.btnAceptarClaseDePrecio:
			logicaCargaPedidos.validaSeleccionClaseDePrecio();
			
			break;
		}
	}

	public void setPantallaManagerCargaPedidos(PantallaManagerCargaPedidos pantallaManagerCargaPedidos) {
		this.pantallaManagerCargaPedidos = pantallaManagerCargaPedidos;
	}

	public void setLogica(LogicaCargaPedidos logicaCargaPedidos) {
		this.logicaCargaPedidos = logicaCargaPedidos;
	}
}
