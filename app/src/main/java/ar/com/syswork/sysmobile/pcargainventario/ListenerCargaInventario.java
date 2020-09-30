package ar.com.syswork.sysmobile.pcargainventario;

import android.view.View;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.pcargapedidos.LogicaCargaPedidos;
import ar.com.syswork.sysmobile.pcargapedidos.PantallaManagerCargaPedidos;

public class ListenerCargaInventario implements View.OnClickListener {

    private PantallaManagerCargaInventario pantallaManagerCargaInventario;
    private LogicaCargaInventario logicaCargaInventario;

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.imgBtnOk:

                logicaCargaInventario.validaCodigoArticulo();
                break;

            case R.id.imgBtnBuscar:
                logicaCargaInventario.consultarArticulos();
                break;

            case R.id.imgBtnScan:
                logicaCargaInventario.scanArticulo();
                break;

            case R.id.btnAgregar:

                logicaCargaInventario.validaCantidadIntroducida();
                break;

            case R.id.btnCancelarAgregar:

                logicaCargaInventario.setCodigoProductoActual("");
                pantallaManagerCargaInventario.cerrarDialogoSolicitaCantidad();
                break;

            case R.id.btnAceptarClaseDePrecio:


                break;
        }
    }

    public void setPantallaManagerCargaPedidos(PantallaManagerCargaInventario pantallaManagerCargaInventario) {
        this.pantallaManagerCargaInventario = pantallaManagerCargaInventario;
    }

    public void setLogica(LogicaCargaInventario logicaCargaInventario) {
        this.logicaCargaInventario = logicaCargaInventario;
    }
}
