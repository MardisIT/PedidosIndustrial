package ar.com.syswork.sysmobile.plistainventario;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import ar.com.syswork.sysmobile.entities.Inventario;
import ar.com.syswork.sysmobile.entities.Pedido;
import ar.com.syswork.sysmobile.plistapedidos.AdapterListaPedidos;
import ar.com.syswork.sysmobile.plistapedidos.LogicaListaPedidos;

public class ListenerListaInventario  implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private LogicaListainventario logicaListainventario;

    @Override
    public boolean onItemLongClick(AdapterView<?> adapter, View view, int pos, long arg3)
    {
        return false;
    }

    public void setLogica(LogicaListainventario logicaListainventario)
    {
        this.logicaListainventario=logicaListainventario;
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View arg1, int pos, long arg3)
    {
        Log.d("SW","onItemClick");

        AdapterListaInventario lAdapter = (AdapterListaInventario) adapter.getAdapter();
        Inventario inventario = lAdapter.getItem(pos);

        Log.d("SW","inventario.id = " + inventario.getId());

        logicaListainventario.consultarInventario(inventario.getId());
    }


}
