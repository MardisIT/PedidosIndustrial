package ar.com.syswork.sysmobile.plistapedidos;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import ar.com.syswork.sysmobile.entities.Pedido;

public class ListenerListaPedidos implements OnItemClickListener,OnItemLongClickListener{
	
	private LogicaListaPedidos logicaListaPedidos;
	
	@Override
	public boolean onItemLongClick(AdapterView<?> adapter, View view, int pos, long arg3) 
	{
	return false;
	}

	public void setLogica(LogicaListaPedidos logicaListaPedidos) 
	{
		this.logicaListaPedidos=logicaListaPedidos;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int pos, long arg3) 
	{
		Log.d("SW","onItemClick");
		
		AdapterListaPedidos lAdapter = (AdapterListaPedidos) adapter.getAdapter();
		Pedido pedido = lAdapter.getItem(pos);
		
		Log.d("SW","Pedido.idPedi = " + pedido.getIdPedido());
		
		logicaListaPedidos.consultarPedido((int)pedido.getIdPedido());
	}


}
