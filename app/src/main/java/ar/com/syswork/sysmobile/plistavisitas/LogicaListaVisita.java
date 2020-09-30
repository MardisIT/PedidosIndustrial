package ar.com.syswork.sysmobile.plistavisitas;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Inventario;
import ar.com.syswork.sysmobile.entities.VisitasUio;
import ar.com.syswork.sysmobile.plistainventario.AdapterListaInventario;
import ar.com.syswork.sysmobile.plistavisitas.PantallaManagerListavisita;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class LogicaListaVisita {
    PantallaManagerListavisita pantallaManagerListavisita;
    private Activity a;
    private ArrayList<VisitasUio> listavisitasUios;
    private DataManager dm;
    private AppSysMobile appSysmobile;
    private AdapterListaVisita adapterListaVisita;

    public LogicaListaVisita(Activity a, PantallaManagerListavisita pantallaManagerListavisita) {
        this.a = a;
        this.pantallaManagerListavisita = pantallaManagerListavisita;
        appSysmobile = (AppSysMobile) this.a.getApplication();
        dm = appSysmobile.getDataManager();
        listavisitasUios = new ArrayList<VisitasUio>();
        adapterListaVisita = new AdapterListaVisita(this.a, listavisitasUios);
        this.pantallaManagerListavisita.getLstConsultainventario().setAdapter(adapterListaVisita);
    }

    public void cargarListaVisitas()
    {
        listavisitasUios.clear();
        List<VisitasUio> tmpLista = dm.getDaoVisitasUio().getAll("");
        Iterator<VisitasUio> i = tmpLista.iterator();
        while (i.hasNext())
        {
            listavisitasUios.add(i.next());
        }
        adapterListaVisita.notifyDataSetChanged();

        totalizar();
    }

    public void consultarVisita(Long _idvisita)
    {
        Log.d("SW","En la logica: idvisita = " + _idvisita);


    }

    public void totalizar()
    {
        pantallaManagerListavisita.setTextoCantidadDeinventario(listavisitasUios.size());
    }
}
