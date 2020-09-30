package ar.com.syswork.sysmobile.plistapedidos;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Pedido;
import ar.com.syswork.sysmobile.pcargapedidos.ActivityCargaPedidos;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class LogicaListaPedidos {
	
	private PantallaManagerListaPedidos pantallaManagerListaPedidos;
	private Activity a;
	private ArrayList<Pedido> listaPedidos;
	private DataManager dm;
	private AppSysMobile appSysmobile;
	private AdapterListaPedidos adapterListaPedidos;
	
	public LogicaListaPedidos(Activity a, PantallaManagerListaPedidos pantallaManagerListaPedidos) {
		this.a = a;
		this.pantallaManagerListaPedidos = pantallaManagerListaPedidos;
		appSysmobile = (AppSysMobile) this.a.getApplication();
		dm = appSysmobile.getDataManager();
		listaPedidos = new ArrayList<Pedido>();
		adapterListaPedidos = new AdapterListaPedidos(this.a, listaPedidos);
		this.pantallaManagerListaPedidos.getLstConsultaPedidos().setAdapter(adapterListaPedidos);
	}

	public void cargarListaPedidos()
	{
		listaPedidos.clear();
		List<Pedido> tmpLista = dm.getDaoPedido().getAll("");
		Iterator<Pedido> i = tmpLista.iterator();
		while (i.hasNext())
		{
			listaPedidos.add(i.next());
		}
		adapterListaPedidos.notifyDataSetChanged();
		
		totalizar();
	}
	
	public void consultarPedido(int _idPedido)
	{
		Log.d("SW","En la logica: _idPedido = " + _idPedido);
		
		Intent intent = new Intent(this.a, ActivityCargaPedidos.class);
		intent.putExtra("esModificacionDePedido", true);
		intent.putExtra("_idPedido", _idPedido);
		
		a.startActivity(intent);
	}
	
	public void totalizar()
	{
		pantallaManagerListaPedidos.setTextoCantidadDePedidos(listaPedidos.size());
	}
}
