package ar.com.syswork.sysmobile.plistavisitas;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import ar.com.syswork.sysmobile.entities.VisitasUio;
public class ListenerListaVisita implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private LogicaListaVisita logicaListaVisita;

    @Override
    public boolean onItemLongClick(AdapterView<?> adapter, View view, int pos, long arg3)
    {
        return false;
    }

    public void setLogica(LogicaListaVisita logicaListaVisita)
    {
        this.logicaListaVisita=logicaListaVisita;
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View arg1, int pos, long arg3)
    {
        Log.d("SW","onItemClick");

        AdapterListaVisita lAdapter = (AdapterListaVisita) adapter.getAdapter();
        VisitasUio visitasUio = lAdapter.getItem(pos);

        Log.d("SW","inventario.id = " + visitasUio.getId());

        logicaListaVisita.consultarVisita(visitasUio.getId());
    }


}
