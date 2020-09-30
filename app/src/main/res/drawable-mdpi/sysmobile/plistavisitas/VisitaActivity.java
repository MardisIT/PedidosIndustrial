package ar.com.syswork.sysmobile.plistavisitas;

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

public class VisitaActivity extends ActionBarActivity {
    private LogicaListaVisita logicaListaVisita;
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listavisita);
        //Creo el Listener
        ListenerListaVisita listenerListaVisita = new ListenerListaVisita();
        // Creo al PantallaManager
        PantallaManagerListavisita pantallaManagerListavisita = new PantallaManagerListavisita(this,listenerListaVisita);
        pantallaManagerListavisita.seteaListener();
        //Creo la Logica
        logicaListaVisita = new LogicaListaVisita(this,pantallaManagerListavisita);
        //Seteo la Logica al Listener
        listenerListaVisita.setLogica(logicaListaVisita);
        logicaListaVisita.cargarListaVisitas();
        registrarBroadcastReceiver();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_lista_vistas, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d("SW","onOptionsItemSelected");
        switch (item.getItemId())
        {
            case R.id.mnu_agregar_visita:
                Intent i = new Intent(this,ActivityConsultaClientes.class);
                i.putExtra("origenDeLaConsulta", AppSysMobile.DESDE_CARGA_VISITAS);
                startActivity(i);
                this.finish();
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
                logicaListaVisita.cargarListaVisitas();
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
