package ar.com.syswork.sysmobile.pmenuprincipal;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import ar.com.syswork.sysmobile.entities.ItemMenuPrincipal;

public class ListenerMenuPrincipal implements OnItemClickListener{
	
	private LogicaMenuPrincipal logicaMenuPrincipal;
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int posicion, long arg3) {
		
		AdapterMenuPrincipal lAdapter = (AdapterMenuPrincipal) adapter.getAdapter();
		ItemMenuPrincipal item = lAdapter.getItem(posicion);
				
		logicaMenuPrincipal.lanzarActivity(item.getAccion());
		
	}

	public void setLogica(LogicaMenuPrincipal logicaMenuPrincipal) {
		this.logicaMenuPrincipal = logicaMenuPrincipal;
		
	}

}
