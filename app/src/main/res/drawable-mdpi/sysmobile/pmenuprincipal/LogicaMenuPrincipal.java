package ar.com.syswork.sysmobile.pmenuprincipal;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoPedido;
import ar.com.syswork.sysmobile.daos.DaoVendedor;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.ItemMenuPrincipal;
import ar.com.syswork.sysmobile.pconfig.ActivityConfig;
import ar.com.syswork.sysmobile.pconsultaarticulos.ActivityConsultaArticulos;
import ar.com.syswork.sysmobile.pconsultaclientes.ActivityConsultaClientes;
import ar.com.syswork.sysmobile.pconsultactacte.reporte_pagos;
import ar.com.syswork.sysmobile.penviapendientes.ActivityEnviaPendientes;
import ar.com.syswork.sysmobile.plistainventario.InventarioActivity;
import ar.com.syswork.sysmobile.plistapedidos.ActivityListaPedidos;
import ar.com.syswork.sysmobile.plistavisitas.VisitaActivity;
import ar.com.syswork.sysmobile.psincronizar.ActivitySincronizar;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class LogicaMenuPrincipal {
	
	private Activity a;
	private ItemMenuPrincipal itemMP;
	private List<ItemMenuPrincipal> listaOpciones;
	private AppSysMobile app;
	private DataManager dm;
	private DaoVendedor daoVendedor;
	private DaoPedido daoPedido;
	private PantallaManagerMenuPrincipal pantallaManagerMenuPrincipal;
	private AdapterMenuPrincipal adapter;
	private boolean forzarLogueo;
	
	
	public LogicaMenuPrincipal(Activity  a, AdapterMenuPrincipal adapter) {
		
		this.a = a;
		this.adapter = adapter;
		
		app = (AppSysMobile) a.getApplication();
		dm = app.getDataManager();
		daoVendedor = dm.getDaoVendedor();
		daoPedido = dm.getDaoPedido();
	}
	
	public void seteaListaOpciones(List<ItemMenuPrincipal> listaOpciones){
		this.listaOpciones = listaOpciones;
	}
	
	public void creaItemsMenuPrincipal(){
	
		itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo(a.getString(R.string.item_menu_principal_sincronizar));
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.icono_sincronizar);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_SINCRONIZAR);
		listaOpciones.add(itemMP);
		
		// SI NO HAY VEDENDORES LA UNICA OPCION QUE HABILITO ES LA DE SINCRONIZAR
		// (PARA BAJAR VENDEDORES) Y LANZO AVISO
		forzarLogueo = (daoVendedor.getCount() == 0);
		if (forzarLogueo)
		{
			pantallaManagerMenuPrincipal.muestraAlertaFaltanVendedores();
			return;
		} 
		
		itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo(a.getString(R.string.item_menu_principal_carga_pedidos));
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.icono_pedido);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_CARGA_PEDIDOS);
		listaOpciones.add(itemMP);

		itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo(a.getString(R.string.item_menu_principal_enviar_pendientes));
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.icono_pedido_pendientes);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_ENVIAR_PENDIENTES);
		
		int cantPendiente = daoPedido.getCount(" transferido = 0");
		
		itemMP.setCantidad(cantPendiente);
		listaOpciones.add(itemMP);
		
		itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo(a.getString(R.string.item_menu_principal_consulta_articulos));
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.icono_articulos);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_CONSULTA_ARTICULOS);

		listaOpciones.add(itemMP);
		itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo(a.getString(R.string.item_menu_principal_consulta_datos_clientes));
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.icono_clientes);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_CONSULTA_CLIENTES);
		listaOpciones.add(itemMP);
		
		itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo(a.getString(R.string.item_menu_principal_consulta_cuenta_corriente));
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.icono_cuenta_corriente);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_CUENTA_CORRIENTE);
		listaOpciones.add(itemMP);

		itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo("Reporte Pagos");
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.icono_cuenta_corriente);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_REPORTE);
		listaOpciones.add(itemMP);

		itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo("Inventario Productos");
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.icono_pedido_pendientes);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_INVENTARIO);
		listaOpciones.add(itemMP);

		itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo("Registro Visita");
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.abc_ic_voice_search_api_mtrl_alpha);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_VISITAS);
		listaOpciones.add(itemMP);

	}

	public void lanzarActivity(int accion) {
		
		Intent i = null;

		switch (accion)
		{
			case AppSysMobile.OPC_MENU_PRINCIPAL_SINCRONIZAR:
				
				i = new Intent(a,ActivitySincronizar.class);
				break;
			
			case AppSysMobile.OPC_MENU_PRINCIPAL_CARGA_PEDIDOS:
			
				i = new Intent(a,ActivityListaPedidos.class);
				
				break;
			
			case AppSysMobile.OPC_MENU_PRINCIPAL_ENVIAR_PENDIENTES:

				i = new Intent(a,ActivityEnviaPendientes.class);
				break;

			case AppSysMobile.OPC_MENU_PRINCIPAL_CONSULTA_ARTICULOS:
				
				i = new Intent(a,ActivityConsultaArticulos.class);
				break;

			case AppSysMobile.OPC_MENU_PRINCIPAL_CONSULTA_CLIENTES:				
				
				i = new Intent(a,ActivityConsultaClientes.class);
				break;
			
			case AppSysMobile.OPC_MENU_PRINCIPAL_CUENTA_CORRIENTE:				
				
				i = new Intent(a,ActivityConsultaClientes.class);
				i.putExtra("origenDeLaConsulta", AppSysMobile.DESDE_CONSULTA_DE_CUENTA_CORRIENTE);
				break;
			case AppSysMobile.OPC_MENU_PRINCIPAL_REPORTE:

				i = new Intent(a,reporte_pagos.class);
				break;
			case AppSysMobile.OPC_MENU_PRINCIPAL_INVENTARIO:

				i = new Intent(a,InventarioActivity.class);

				break;
			case AppSysMobile.OPC_MENU_PRINCIPAL_VISITAS:

				i = new Intent(a,VisitaActivity.class);

				break;
		}
		
		if ((accion == AppSysMobile.OPC_MENU_PRINCIPAL_SINCRONIZAR) || 
				(accion == AppSysMobile.OPC_MENU_PRINCIPAL_CARGA_PEDIDOS) ||
				(accion == AppSysMobile.OPC_MENU_PRINCIPAL_ENVIAR_PENDIENTES)  
			)
		{
			a.startActivityForResult(i,accion);
		}
		else
		{
			a.startActivity(i);
			
		}
	
	}	
	
	public void lanzarActivityConfiguracion()
	{
		Intent i = new Intent(a,ActivityConfig.class);
		a.startActivityForResult(i,AppSysMobile.OPC_MENU_PRINCIPAL_CONFIGURACION);
	}

	public void seteaPantallaManager(PantallaManagerMenuPrincipal pantallaManagerMenuPrincipal) {
		this.pantallaManagerMenuPrincipal = pantallaManagerMenuPrincipal;
	}

	public void actualizaCantidadPedidosPendientes()
	{
		ItemMenuPrincipal itemMenuPrincipal = listaOpciones.get(AppSysMobile.OPC_MENU_PRINCIPAL_ENVIAR_PENDIENTES);
		int cantPendiente = daoPedido.getCount(" transferido = 0");
		itemMenuPrincipal.setCantidad(cantPendiente);
		adapter.notifyDataSetChanged();
	}
	
	public boolean getForzarLogueo()
	{
		return forzarLogueo;
	}
}
