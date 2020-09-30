package ar.com.syswork.sysmobile.plistainventario;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Inventario;
import ar.com.syswork.sysmobile.pcargainventario.CargaInventarioActivity;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class LogicaListainventario {
    PantallaManagerListaInventario pantallaManagerListaInventario;
    private Activity a;
    private ArrayList<Inventario> listaInventario;
    private DataManager dm;
    private AppSysMobile appSysmobile;
    private AdapterListaInventario adapterListaInventario;

    public LogicaListainventario(Activity a, PantallaManagerListaInventario pantallaManagerListaInventario) {
        this.a = a;
        this.pantallaManagerListaInventario = pantallaManagerListaInventario;
        appSysmobile = (AppSysMobile) this.a.getApplication();
        dm = appSysmobile.getDataManager();
        listaInventario = new ArrayList<Inventario>();
        adapterListaInventario = new AdapterListaInventario(this.a, listaInventario);
        this.pantallaManagerListaInventario.getLstConsultainventario().setAdapter(adapterListaInventario);
    }

    public void cargarListaInventario()
    {
        listaInventario.clear();
        List<Inventario> tmpLista = dm.getDaoInventario().getAll("");
        Iterator<Inventario> i = tmpLista.iterator();
        while (i.hasNext())
        {
            listaInventario.add(i.next());
        }
        adapterListaInventario.notifyDataSetChanged();

        totalizar();
    }

    public void consultarInventario(Long _idinventanrio)
    {
        Log.d("SW","En la logica: _idinvnetario = " + _idinventanrio);

        Intent intent = new Intent(this.a, CargaInventarioActivity.class);
        intent.putExtra("esModificacionDeInventario", true);
        intent.putExtra("_idinvnetario", _idinventanrio);

        a.startActivity(intent);
    }

    public void totalizar()
    {
        pantallaManagerListaInventario.setTextoCantidadDeinventario(listaInventario.size());
    }
}
