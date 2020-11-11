package ar.com.syswork.sysmobile.pmenuprincipal;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoInventario;
import ar.com.syswork.sysmobile.daos.DaoPedido;
import ar.com.syswork.sysmobile.daos.DaoVendedor;
import ar.com.syswork.sysmobile.daos.DaoVisitasUio;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.ItemMenuPrincipal;
import ar.com.syswork.sysmobile.shared.AppSysMobile;



public class LogicaMenuPrincipal {
	
	private Activity a;
	private ItemMenuPrincipal itemMP;
	private List<ItemMenuPrincipal> listaOpciones;
	private AppSysMobile app;
	private DataManager dm;
	private DaoVendedor daoVendedor;
	private DaoPedido daoPedido;
	private DaoVisitasUio daoVisitasUio;
	private DaoInventario daoInventario;
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
		daoVisitasUio=dm.getDaoVisitasUio();
		daoInventario=dm.getDaoInventario();
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
		itemMP.setTitulo("Visita Cliente");
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.mapageo);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_VISITAS);
		int cantPendienteV = daoVisitasUio.getCount("");
		itemMP.setCantidad(cantPendienteV);

		listaOpciones.add(itemMP);

		itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo("Editar "+a.getString(R.string.item_menu_principal_carga_pedidos));
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.icono_pedido);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_CARGA_PEDIDOS);

		int cantPendienteE = daoPedido.getCount(" transferido = 0");
		itemMP.setCantidad(cantPendienteE);
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
		
		/*itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo(a.getString(R.string.item_menu_principal_consulta_cuenta_corriente));
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.icono_cuenta_corriente);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_CUENTA_CORRIENTE);
		listaOpciones.add(itemMP);*/

		/*itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo("Reporte Pagos");
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.icono_cuenta_corriente);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_REPORTE);
		listaOpciones.add(itemMP);*/
		itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo("Reporte Pedidos");
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.icono_cuenta_corriente);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_RPEDIDOS);
		listaOpciones.add(itemMP);


		itemMP = new ItemMenuPrincipal();
		itemMP.setTitulo("Devoluciones Productos");
		itemMP.setCantidad(0);
		itemMP.setImagen(R.drawable.icono_pedido_pendientes);
		itemMP.setAccion(AppSysMobile.OPC_MENU_PRINCIPAL_INVENTARIO);
		int cantPendienteD = daoInventario.getCount("");
		itemMP.setCantidad(cantPendienteD);

		listaOpciones.add(itemMP);



	}

	public void lanzarActivity(int accion) {
		
		Intent i = null;

		switch (accion)
		{
			case AppSysMobile.OPC_MENU_PRINCIPAL_SINCRONIZAR:
				
				i = new Intent(a,ar.com.syswork.sysmobile.psincronizar.ActivitySincronizar.class);
				break;
			
			case AppSysMobile.OPC_MENU_PRINCIPAL_CARGA_PEDIDOS:
			
				i = new Intent(a,ar.com.syswork.sysmobile.plistapedidos.ActivityListaPedidos.class);
				
				break;
			
			case AppSysMobile.OPC_MENU_PRINCIPAL_ENVIAR_PENDIENTES:

				i = new Intent(a,ar.com.syswork.sysmobile.penviapendientes.ActivityEnviaPendientes.class);
				break;

			case AppSysMobile.OPC_MENU_PRINCIPAL_CONSULTA_ARTICULOS:
				
				i = new Intent(a,ar.com.syswork.sysmobile.pconsultaarticulos.ActivityConsultaArticulos.class);
				break;

			case AppSysMobile.OPC_MENU_PRINCIPAL_CONSULTA_CLIENTES:				
				
				i = new Intent(a,ar.com.syswork.sysmobile.pconsultaclientes.ActivityConsultaClientes.class);
				break;
			
			case AppSysMobile.OPC_MENU_PRINCIPAL_CUENTA_CORRIENTE:				
				
				i = new Intent(a,ar.com.syswork.sysmobile.pconsultaclientes.ActivityConsultaClientes.class);
				i.putExtra("origenDeLaConsulta", AppSysMobile.DESDE_CONSULTA_DE_CUENTA_CORRIENTE);
				break;
			case AppSysMobile.OPC_MENU_PRINCIPAL_REPORTE:

				i = new Intent(a,ar.com.syswork.sysmobile.pconsultactacte.reporte_pagos.class);
				break;
			case AppSysMobile.OPC_MENU_PRINCIPAL_INVENTARIO:

				i = new Intent(a,ar.com.syswork.sysmobile.plistainventario.InventarioActivity.class);

				break;
			case AppSysMobile.OPC_MENU_PRINCIPAL_RPEDIDOS:

				i = new Intent(a,ar.com.syswork.sysmobile.reportepedidos.ReporteActivity.class);

				break;
			case AppSysMobile.OPC_MENU_PRINCIPAL_VISITAS:

				i = new Intent(a,ar.com.syswork.sysmobile.plistavisitas.VisitaActivity.class);

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
		Intent i = new Intent(a,ar.com.syswork.sysmobile.pconfig.ActivityConfig.class);
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
