package ar.com.syswork.sysmobile.pcargainventario;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.inventariodetalles;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.AlertManager;
import ar.com.syswork.sysmobile.util.IAlertResult;

public class CargaInventarioActivity extends ActionBarActivity implements IAlertResult {
    private boolean incluirEnReparto;
    private boolean esModificacionDeInventario = false;
    private long _idinventario;
    private long ultimaVezQueSePresionoBack;
    private boolean esModificacionDePedido = false;
    private PantallaManagerCargaInventario pantallaManagerCargaInventario;
    private LogicaCargaInventario logicaCargaInventario;
    private int posicionItemSeleccionado;
    private int ALERTA_SOLICITA_REPARTO = 999;

    private boolean auxEnviarAutomaticamente;
    private boolean auxFacturar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_inventario);
        incluirEnReparto = false;

        String codCliente = "";
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("esModificacionDeInventario")) {
                Log.d("SW", "esModificacionDeInventario");

                esModificacionDeInventario = true;
                _idinventario =Integer.valueOf(extras.get("_idinvnetario").toString());

                Log.d("SW", "_idInventario ::: " + _idinventario);
            } else {
                if (extras.containsKey("cliente"))
                    codCliente = extras.getString("cliente");
            }
        }
        // Creo el Listener
        ListenerCargaInventario listenerCargaInventario = new ListenerCargaInventario();


//Creo el PantallaManager
        pantallaManagerCargaInventario = new PantallaManagerCargaInventario(this, listenerCargaInventario);
        pantallaManagerCargaInventario.seteaListener();

        listenerCargaInventario.setPantallaManagerCargaPedidos(pantallaManagerCargaInventario);

        //Creo la Logica
        logicaCargaInventario = new LogicaCargaInventario(this, pantallaManagerCargaInventario);

        //Seteo la Logica al Listener
        listenerCargaInventario.setLogica(logicaCargaInventario);

        //Seteo el adapter
        ListView lv = (ListView) this.findViewById(R.id.lstCargaInventario);
        lv.setAdapter(logicaCargaInventario.getAdapterCargaInventario());
        registerForContextMenu(lv);


        if (esModificacionDeInventario) {
            logicaCargaInventario.cargaDatosInventario(_idinventario);

        } else {
            //Inicializo datos
            logicaCargaInventario.inicializaDatos(codCliente);


        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_carga_inventario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.mnu_grabar_inventario:

                if (AppSysMobile.getSolicitaIncluirEnReparto())
                {
                    auxEnviarAutomaticamente = false;
                    auxFacturar = false;
                    pantallaManagerCargaInventario.muestraAlertaIncluirEnReparto();
                }
                else
                {
                    logicaCargaInventario.grabarInventario(false,false,_idinventario,incluirEnReparto);
                }

                return true;





            case R.id.mnu_cancelar_inventario:

                if (logicaCargaInventario.getAdapterCargaInventario().getCount()==0)
                {
                    pantallaManagerCargaInventario.finalizaActivityCargaInventario();
                    return true;
                }
                else
                {
                    pantallaManagerCargaInventario.muestraAlertaCancelarInventario();
                    return true;        				}

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if ((v.getId() == R.id.lstCargaInventario))
        {
            menu.setHeaderTitle(R.string.que_hacer_con_el_item);
            menu.add(1, 1, 1, R.string.eliminar_item);
            menu.add(1, 2, 2, R.string.consultarStockOnLine);
            menu.add(1, 3, 3, R.string.cancelar_item);

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicionItemSeleccionado = info.position;

        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 1:
                logicaCargaInventario.removerItemListaItems(posicionItemSeleccionado);
                break;
            case 2:

                inventariodetalles inventariodetalles = logicaCargaInventario.getAdapterCargaInventario().getItem(posicionItemSeleccionado);
                pantallaManagerCargaInventario.consultarStock(inventariodetalles.getCodproducto());

                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (this.ultimaVezQueSePresionoBack < System.currentTimeMillis() - 4000)
        {
            pantallaManagerCargaInventario.solicitaConfirmacionDeSalida();
            this.ultimaVezQueSePresionoBack = System.currentTimeMillis();
        }
        else
        {
            super.onBackPressed();
            /*}*/
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Consulta de Articulos
        if (resultCode == RESULT_OK)
        {
            String codArticulo = null;

            switch (requestCode)
            {
                case AppSysMobile.DESDE_CARGA_DE_PEDIDOS:

                    codArticulo = data.getExtras().getString("codArticulo");
                    pantallaManagerCargaInventario.setCodigoIntroducido(codArticulo);

                    // Completo el codigo de articulo y automaticamente
                    logicaCargaInventario.validaCodigoArticulo();

                    break;
                case AppSysMobile.DESDE_SCANNER:

                    codArticulo = data.getStringExtra("SCAN_RESULT");
					/*String formatName = data.getStringExtra("SCAN_RESULT_FORMAT");
					byte[] rawBytes = data.getByteArrayExtra("SCAN_RESULT_BYTES");
					int intentOrientation = data.getIntExtra("SCAN_RESULT_ORIENTATION", Integer.MIN_VALUE);
					Integer orientation = intentOrientation == Integer.MIN_VALUE ? null : intentOrientation;
					String errorCorrectionLevel = data.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");
					*/
                    pantallaManagerCargaInventario.setCodigoIntroducido(codArticulo);

                    // Completo el codigo de articulo y automaticamente
                    logicaCargaInventario.validaCodigoArticulo();

            }

        }
    }

    @Override
    public void onAlertResult(int idAlert, int which)
    {
        if (idAlert == R.id.mnu_cancelar_pedido)
        {
            switch (which)
            {
                case AlertManager.BUTTON_POSITIVE :
                    pantallaManagerCargaInventario.finalizaActivityCargaInventario();
                    break;
            }
        }
        else if(idAlert == ALERTA_SOLICITA_REPARTO)
        {
            incluirEnReparto = (which == AlertManager.BUTTON_POSITIVE );
            logicaCargaInventario.grabarInventario(auxEnviarAutomaticamente,auxFacturar ,_idinventario,incluirEnReparto);
        }
    }

}


