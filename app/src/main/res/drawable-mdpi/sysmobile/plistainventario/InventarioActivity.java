package ar.com.syswork.sysmobile.plistainventario;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.pconsultaclientes.ActivityConsultaClientes;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class InventarioActivity extends ActionBarActivity {
    private LogicaListainventario logicaListainventario;
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        //Creo el Listener
        ListenerListaInventario listenerListaInventario = new ListenerListaInventario();

        // Creo al PantallaManager
        PantallaManagerListaInventario pantallaManagerListaInventario = new PantallaManagerListaInventario(this,listenerListaInventario);
        pantallaManagerListaInventario.seteaListener();

        //Creo la Logica
        logicaListainventario = new LogicaListainventario(this,pantallaManagerListaInventario);

        //Seteo la Logica al Listener
        listenerListaInventario.setLogica(logicaListainventario);

        logicaListainventario.cargarListaInventario();

        registrarBroadcastReceiver();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_lista_inventario, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d("SW","onOptionsItemSelected");
        switch (item.getItemId())
        {
            case R.id.mnu_agregar_inventario:
                Intent i = new Intent(this,ActivityConsultaClientes.class);
                i.putExtra("origenDeLaConsulta", AppSysMobile.DESDE_CARGA_INVENTARIO);
                startActivity(i);
                break;
        }

        return false;
    }
    private void registrarBroadcastReceiver()
    {
        broadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                Log.d("SW","Escucha modificacion invnetario");
                logicaListainventario.cargarListaInventario();
            }
        };

        IntentFilter intentFilter = new IntentFilter(AppSysMobile.INTENT_FILTER_CAMBIOS_LISTA_PEDIDOS);
        registerReceiver(broadcastReceiver,intentFilter);

    }

    @Override
    protected void onDestroy()
    {
        desRegistrarBroadcastReceiver();
        super.onDestroy();
    }

    private void desRegistrarBroadcastReceiver()
    {
        Log.d("SW","desRegistrarBroadcastReceiver()<-->Inventario");
        unregisterReceiver(broadcastReceiver);
    }
}
