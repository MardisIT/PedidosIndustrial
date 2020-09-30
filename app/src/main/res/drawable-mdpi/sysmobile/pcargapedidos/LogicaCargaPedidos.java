package ar.com.syswork.sysmobile.pcargapedidos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoArticulo;
import ar.com.syswork.sysmobile.daos.DaoCliente;
import ar.com.syswork.sysmobile.daos.DaoDESCUENTO_AVENA;
import ar.com.syswork.sysmobile.daos.DaoDESCUENTO_FORMAPAGO;
import ar.com.syswork.sysmobile.daos.DaoDESCUENTO_VOLUMEN;
import ar.com.syswork.sysmobile.daos.DaoPRECIO_ESCALA;
import ar.com.syswork.sysmobile.daos.DaoPedido;
import ar.com.syswork.sysmobile.daos.DaoPedidoItem;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Articulo;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.entities.DESCUENTO_FORMAPAGO;
import ar.com.syswork.sysmobile.entities.DESCUENTO_VOLUMEN;
import ar.com.syswork.sysmobile.entities.PRECIO_ESCALA;
import ar.com.syswork.sysmobile.entities.Pedido;
import ar.com.syswork.sysmobile.entities.PedidoItem;
import ar.com.syswork.sysmobile.penviapendientes.ListenerEnviaPendientes;
import ar.com.syswork.sysmobile.penviapendientes.LogicaEnviaPendientes;
import ar.com.syswork.sysmobile.penviapendientes.PantallaManagerEnviaPendientes;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.AlertManager;
import ar.com.syswork.sysmobile.util.DialogManager;
import ar.com.syswork.sysmobile.util.IAlertResult;
import ar.com.syswork.sysmobile.util.Utilidades;

public class LogicaCargaPedidos implements IAlertResult
{
	private Activity a;
	private PantallaManagerCargaPedidos pantallaManagerCargaPedidos;
	private ArrayList<PedidoItem> listaPedidoItems;
	private DialogManager utilDialogos;

	private AppSysMobile app;
	
	private DataManager dataManager;
	
	private DaoCliente daoCliente;
	private DaoArticulo daoArticulo;
	private DaoPedido daoPedido;
	private DaoPedidoItem daoPedidoItem;
	private  DaoDESCUENTO_FORMAPAGO daoDESCUENTO_formapago;
	private DaoPRECIO_ESCALA daoPRECIO_escala;
	private DaoDESCUENTO_VOLUMEN daoDESCUENTO_volumen;
	private DaoDESCUENTO_AVENA daoDESCUENTO_avena;

	
	private AdapterCargaPedidos adapterCargaPedidos;
	
	private Cliente cliente;
	private String codigoProductoActual;
	private String codigoVendedor;
	private double importeTotalPedido;
	private int claseDePrecioSeleccionada;

	private String textoLecturaCodigoDeBarras;
	private String textoMarketNoInstalado;

	private String elCodigoInformadoNoEsCorrecto;
	private String debeInformarElCodigoDelProducto;
	private String debeInformarLaCantidad;
	private String noHaCargadoProductos;
	private String ocurrioUnErrorAlGrabar;
	private String pedidoGrabadoConExito;
	
	private boolean reintentarObtenerIntentZxing;
	private Intent intentScan ;
	//private String targetAppPackage;
    private static final String BS_PACKAGE = "com.google.zxing.client.android";
    
	public LogicaCargaPedidos(Activity a,PantallaManagerCargaPedidos pantallaManagerCargaPedidos)
	{
		this.a = a;
		this.pantallaManagerCargaPedidos = pantallaManagerCargaPedidos;
		
		app = (AppSysMobile) this.a.getApplication();
		
		dataManager = app.getDataManager();
		daoCliente = dataManager.getDaoCliente();
		daoArticulo = dataManager.getDaoArticulo();
		daoPedido = dataManager.getDaoPedido();
		daoPedidoItem = dataManager.getDaoPedidoItem();
		daoDESCUENTO_formapago=dataManager.getDaoDESCUENTO_formapago();
		daoPRECIO_escala=dataManager.getDaoPRECIO_escala();
		daoDESCUENTO_volumen=dataManager.getDaoDESCUENTO_volumen();
		daoDESCUENTO_avena=dataManager.getDaoDESCUENTO_avena();



		listaPedidoItems = new ArrayList<PedidoItem>();

		
		//Creo el Adapter
		this.adapterCargaPedidos = new AdapterCargaPedidos(this.a,listaPedidoItems,this);
		
		codigoVendedor = app.getVendedorLogueado();
		
		utilDialogos = new DialogManager ();

		intentScan = getZxingIntent(this.a);
		//targetAppPackage = findTargetAppPackage(intentScan);
		
		textoLecturaCodigoDeBarras = this.a.getString(R.string.textoLecturaCodigoDeBarras);
		textoMarketNoInstalado = this.a.getString(R.string.textoMarketNoInstalado);
		elCodigoInformadoNoEsCorrecto= this.a.getString(R.string.elCodigoInformadoNoEsCorrecto);
		debeInformarElCodigoDelProducto= this.a.getString(R.string.debeInformarElCodigoDelProducto);
		debeInformarLaCantidad = this.a.getString(R.string.debeInformarLaCantidad);
		noHaCargadoProductos = this.a.getString(R.string.noHaCargadoProductos);
		ocurrioUnErrorAlGrabar = this.a.getString(R.string.ocurrioUnErrorAlGrabar);
		pedidoGrabadoConExito= this.a.getString(R.string.pedidoGrabadoConExito);
		
		reintentarObtenerIntentZxing = false;
	}
	
	public void vaciaLista()
	{
		this.listaPedidoItems.clear();
	}
	
	public AdapterCargaPedidos getAdapterCargaPedidos() {
		return this.adapterCargaPedidos;
	}

	public void notificarCambiosAdapter(){
		this.adapterCargaPedidos.notifyDataSetChanged();
	}

	public void inicializaDatos(String codigoCliente)
	{
		cliente = daoCliente.getByKey(codigoCliente);
		pantallaManagerCargaPedidos.setDatosCliente(cliente);
		pantallaManagerCargaPedidos.setCantidadItems(0);
		pantallaManagerCargaPedidos.setImporteTotalPedido(0);
		claseDePrecioSeleccionada = cliente.getClaseDePrecio();
	}

	public boolean validaCodigoArticulo() {
		
		String valor = pantallaManagerCargaPedidos.getCodigoIntroducido();
		Articulo articulo;
		
		if (valor.equals("")){
			utilDialogos.muestraToastGenerico(a, debeInformarElCodigoDelProducto, false);
			return false;
		}
		
		articulo = daoArticulo.getByKey(valor);
		if (articulo==null){
			utilDialogos.muestraToastGenerico(a, elCodigoInformadoNoEsCorrecto, false);
			return false;
		}




		// El Articulo esta OK, lo seteo como el articulo actual
		setCodigoProductoActual(articulo.getIdArticulo());
		pantallaManagerCargaPedidos.mostrarDialogoSolicitaCantidad(articulo.getIdArticulo(),articulo.getDescripcion());

		return true;
	}
	double roundTwoDecimals(double d)
	{
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
	public boolean validaCantidadIntroducida() {
		try{


		
		String valor = pantallaManagerCargaPedidos.getCantidadIntroducida();
		String formaPago=pantallaManagerCargaPedidos.getFormadePagoIntroducida();
		String unidadmedida=pantallaManagerCargaPedidos.getUnidadesIntroducida();

		if (valor.equals("")){
			utilDialogos.muestraToastGenerico(a, debeInformarLaCantidad , false);
			return false;
		}

		/*
		 *Aqui validar forma de pago
		 * Efectivo y credito
		 * */



		Articulo articulo = daoArticulo.getByKey(getCodigoProductoActual());
			Double totaldescuentoprontopago=0.00;
			Double preciodelproducto=0.00;

			Double cantidadingresada=0.00;

           String tiponegocio= cliente.getLocalidad();

		if(unidadmedida.toString().trim().equals("Cajas")){
			cantidadingresada=roundTwoDecimals( Double.parseDouble(valor));
		}else{
			cantidadingresada=roundTwoDecimals(Double.parseDouble(valor)/articulo.getPrecio2());
		}

		preciodelproducto=CalculaDescuento(articulo,"E",cantidadingresada,0 );

		double valornegociacionespecial=0;
		//descuento por volumen descuetno negociacion especial
		valornegociacionespecial=CalculaDescuento(articulo,"NE",cantidadingresada,0);


		//calcular descuento pronto pago
		if(formaPago.equals("Contado"))
		{
			totaldescuentoprontopago= CalculaDescuento(articulo,"PP",cantidadingresada,valornegociacionespecial);
		}





		/*Descuento avenas*/
		double valornegociacionespecialavena=0;
		//valornegociacionespecialavena=CalculaDescuento(articulo,"AV",cantidadingresada,0);





		// Agrego a la lista y cierro
		PedidoItem pedidoItem = new PedidoItem();
		
		pedidoItem.setidPedidoItem(-1);
		pedidoItem.setIdPedido(-1);

		pedidoItem.setIdArticulo(articulo.getIdArticulo());
		pedidoItem.setAuxDescripcionArticulo(articulo.getDescripcion());
		pedidoItem.setCantidad(cantidadingresada);
		pedidoItem.setFormaPago(formaPago);
		pedidoItem.setUnidcajas(unidadmedida);
		pedidoItem.setImporteUnitario(preciodelproducto);
		if(valornegociacionespecial==0) {
			pedidoItem.setNespecial(valornegociacionespecialavena);
		}
		else{
			pedidoItem.setNespecial(valornegociacionespecial);
		}
		pedidoItem.setPpago(totaldescuentoprontopago);
		double total = Utilidades.Redondear(((pedidoItem.getCantidad()* articulo.getPrecio2()) * preciodelproducto)-totaldescuentoprontopago-valornegociacionespecial-valornegociacionespecialavena,2);
		total = Utilidades.Redondear(total, 2);
		pedidoItem.setTotal(total);
		
		listaPedidoItems.add(pedidoItem);
		double cantidadmix = 0;
		double cantidamixcafe=0;
		double cantidadmixavena=0;
		double calcularotrodescuentomix=0;
		List<PedidoItem> templistap= new ArrayList<>();
		//List<PedidoItem> templistapcafe= new ArrayList<>();
		List<PedidoItem> templistaavena= new ArrayList<>();
		/*List<String>listadocafe= new ArrayList<>();
		listadocafe.add("08021-7");
		listadocafe.add("08221-7");
		listadocafe.add("08238-7");
		listadocafe.add("08241-1");
		listadocafe.add("08268-8");
		listadocafe.add("08306-3");
		listadocafe.add("08318-8");
		listadocafe.add("08269-7");
		listadocafe.add("08008-6");
		listadocafe.add("08229-9");*/

		List<String> listadoavenaQ= new ArrayList<>();
		listadoavenaQ.add("01201-2");
		listadoavenaQ.add("01211-9");
		listadoavenaQ.add("01214-6");
		listadoavenaQ.add("01216-4");
		listadoavenaQ.add("01302-6");
		listadoavenaQ.add("01311-4");






		for (PedidoItem a :listaPedidoItems) {
			if(a.getIdArticulo().toString().trim().substring(0,4).equals("0301")){
				cantidadmix=cantidadmix+a.getCantidad();
				templistap.add(a);
			}

			for (String cod:listadoavenaQ) {
				if(a.getIdArticulo().toString().trim().equals(cod)){
					cantidadmixavena=cantidadmixavena+a.getCantidad();
					templistaavena.add(a);
				}
			}



		}
		/*descuento mix maizsabrosa*/
		if(cantidadmix>=5){

			//DESCUENTO_VOLUMEN descuento_volumenmix= daoDESCUENTO_volumen.getByKey("03011-5");
			for (PedidoItem temp:templistap ) {
				Articulo articulomix = daoArticulo.getByKey(temp.getIdArticulo());
				double resultadomix=0;
				List<DESCUENTO_VOLUMEN> listades= new ArrayList<>();
				listades = daoDESCUENTO_volumen.getAll("CodigosSKU='"+articulomix.getIdArticulo()+"'");
				for (DESCUENTO_VOLUMEN descuento_volumenmix:listades
						) {
					if(descuento_volumenmix!=null){
						int unidadesdesde=descuento_volumenmix.getUNIDADES_DESCUENTO();
						int unidadhasta=descuento_volumenmix.getUNIDADES_HASTA().toString().trim().equals("adelante")?-1:Integer.valueOf(descuento_volumenmix.getUNIDADES_HASTA().toString().trim());
						if(unidadhasta==-1 && cantidadmix >= unidadesdesde ){
							resultadomix=Utilidades.Redondear((((temp.getCantidad() *temp.getImporteUnitario())-0)*descuento_volumenmix.getDESCUENTO())/100,2);
						}
						if((cantidadmix <= unidadhasta&&unidadhasta!=-1) && cantidadmix>= unidadesdesde ){
							resultadomix=Utilidades.Redondear(((((temp.getCantidad()*articulomix.getPrecio2())*temp.getImporteUnitario())-0)*descuento_volumenmix.getDESCUENTO())/100,2);
						}
					}
					if(temp.getPpago()!=0) {
						DESCUENTO_FORMAPAGO descuento_formapago = daoDESCUENTO_formapago.getByKey(temp.getIdArticulo());
						temp.setPpago(Utilidades.Redondear(((((temp.getCantidad()*articulomix.getPrecio2()) * temp.getImporteUnitario()) - resultadomix) * descuento_formapago.getPorcentaje()) / 100, 2));
					}

					temp.setNespecial(resultadomix);
					temp.setTotal(Utilidades.Redondear(((temp.getCantidad()*articulomix.getPrecio2())* temp.getImporteUnitario())-resultadomix-temp.getPpago(),2));
					//listaPedidoItems.add(temp);
				}


			}
		}

///DESCUENTA MIX CAFE
		/*if(cantidamixcafe>=5){
			DESCUENTO_VOLUMEN descuento_volumenmix= daoDESCUENTO_volumen.getByKey("08229-9");
			for (PedidoItem temp:templistapcafe ) {
				Articulo articulomix = daoArticulo.getByKey(temp.getIdArticulo());
				double resultadomixcafe=0;
				if(descuento_volumenmix!=null){
					int unidadesdesde=descuento_volumenmix.getUNIDADES_DESCUENTO();
					int unidadhasta=descuento_volumenmix.getUNIDADES_HASTA().toString().trim().equals("adelante")?-1:Integer.valueOf(descuento_volumenmix.getUNIDADES_HASTA().toString().trim());
					if(unidadhasta==-1 && cantidamixcafe >= unidadesdesde ){
						resultadomixcafe=Utilidades.Redondear(((((temp.getCantidad()*articulomix.getPrecio2()) *temp.getImporteUnitario())-0)*descuento_volumenmix.getDESCUENTO())/100,2);
					}
					if((cantidamixcafe <= unidadhasta&&unidadhasta!=-1) && cantidamixcafe>= unidadesdesde ){
						resultadomixcafe=Utilidades.Redondear(((((temp.getCantidad()*articulomix.getPrecio2())*temp.getImporteUnitario())-0)*descuento_volumenmix.getDESCUENTO())/100,2);
					}
				}
				if(temp.getPpago()!=0) {
					DESCUENTO_FORMAPAGO descuento_formapago = daoDESCUENTO_formapago.getByKey(temp.getIdArticulo());
					temp.setPpago(Utilidades.Redondear(((((temp.getCantidad()*articulomix.getPrecio2()) * temp.getImporteUnitario()) - resultadomixcafe) * descuento_formapago.getPorcentaje()) / 100, 2));
				}

				temp.setNespecial(resultadomixcafe);
				temp.setTotal(Utilidades.Redondear(((temp.getCantidad()*articulomix.getPrecio2())* temp.getImporteUnitario())-resultadomixcafe-temp.getPpago(),2));
				//listaPedidoItems.add(temp);

			}
		}*/

//validar avena de 500 precio escala
		if(cantidadmixavena>=5){
			double valor_articulo=0.0;
			for (PedidoItem temp:templistaavena ) {
				Articulo articulomixavena = daoArticulo.getByKey(temp.getIdArticulo());
				PRECIO_ESCALA precio_escala = daoPRECIO_escala.getByKey(articulomixavena.getIdArticulo());

				if (precio_escala != null) {
					if (cantidadmixavena >= precio_escala.getUNIDADES_VALIDAR()) {
						valor_articulo =Double.valueOf(precio_escala.getPRECIO2());
					} else {
						valor_articulo = obtienePrecio(articulo);
					}
				} else {
					valor_articulo = obtienePrecio(articulo);
				}
				temp.setImporteUnitario(valor_articulo);
				double valornegociacionespecialave=0;
				//descuento por volumen descuetno negociacion especial
				valornegociacionespecialave=CalculaDescuento(articulomixavena,"NE",temp.getCantidad(),0);


				//calcular descuento pronto pago
				double valor_contadoavena=0;
				if(formaPago.equals("Contado"))
				{
					DESCUENTO_FORMAPAGO descuento_formapago=daoDESCUENTO_formapago.getByKey(articulomixavena.getIdArticulo());
					valor_contadoavena=Utilidades.Redondear(((((temp.getCantidad()*articulo.getPrecio2()) *valor_articulo))*descuento_formapago.getPorcentaje())/100,2) ;
				}
				temp.setNespecial(valornegociacionespecialave);
				temp.setPpago(valor_contadoavena);
				temp.setTotal(Utilidades.Redondear(((temp.getCantidad()*articulomixavena.getPrecio2())* valor_articulo)-valor_contadoavena,2));

			}
		}



		
		this.notificarCambiosAdapter();
		pantallaManagerCargaPedidos.cerrarDialogoSolicitaCantidad();
		pantallaManagerCargaPedidos.setCodigoIntroducido("");
		pantallaManagerCargaPedidos.setCantidadItems(listaPedidoItems.size());
		totalizarPedido();
		return true;
		}catch (Exception ex){
			Log.d("sw",ex.getMessage());
			return  false;
		}
	}

	public String getCodigoProductoActual() {
		return codigoProductoActual;
	}
	public void setCodigoProductoActual(String codigoProductoActual) {
		this.codigoProductoActual = codigoProductoActual;
	}

	public void consultarArticulos() {
		pantallaManagerCargaPedidos.lanzarActivityConsultaArticulos();
	}
public  double CalcularDescuentoMix(String codigoProductoActual,String opcion,double cantidad,double precio){
		double resultado=0;
	DESCUENTO_VOLUMEN descuento_volumen= daoDESCUENTO_volumen.getByKey(codigoProductoActual);
	if(descuento_volumen!=null){
		int unidadesdesde=descuento_volumen.getUNIDADES_DESCUENTO();
		int unidadhasta=descuento_volumen.getUNIDADES_HASTA().toString().trim().equals("adelante")?-1:Integer.valueOf(descuento_volumen.getUNIDADES_HASTA().toString().trim());
		if(unidadhasta==-1 && cantidad >= unidadesdesde ){
			resultado=Utilidades.Redondear((((cantidad *precio))*descuento_volumen.getDESCUENTO())/100,2);
		}
		if((cantidad <= unidadhasta&&unidadhasta!=-1) && cantidad>= unidadesdesde ){
			resultado=Utilidades.Redondear((((cantidad*precio))*descuento_volumen.getDESCUENTO())/100,2);
		}
	}
	return  resultado;

}
	public double  CalculaDescuento(Articulo articulo,String opcion,double cantidad, double ppago){
		double resultado=0;

		switch (opcion) {
			case "E":
				//calcula descuento escalas de precios.
				PRECIO_ESCALA precio_escala= new PRECIO_ESCALA();
				precio_escala=daoPRECIO_escala.getByKey(articulo.getIdArticulo());

				if(precio_escala!=null){
					if(cantidad>=precio_escala.getUNIDADES_VALIDAR()){
						resultado=precio_escala.getPRECIO2();
					}else{
						resultado=obtienePrecio(articulo);
					}
				}else {

				    String tiponegocio = cliente.getLocalidad();
				    if(articulo.getPrecio10()==1){
				        if(tiponegocio.equals("Subdistribuidor")){
				            resultado=articulo.getPrecio3();
                        }else if (tiponegocio.equals("Mayorista")){
				            resultado=articulo.getPrecio4();
                        }else if(tiponegocio.equals("Detallista")){
				            resultado=articulo.getPrecio1();
                        }else {
				            resultado=obtienePrecio(articulo);
                        }
                    }else{
						resultado=obtienePrecio(articulo);
					}
				}
				break;
			case "NE":
				List<DESCUENTO_VOLUMEN> listades= new ArrayList<>();
				listades = daoDESCUENTO_volumen.getAll("CodigosSKU='"+articulo.getIdArticulo()+"'");
				for (DESCUENTO_VOLUMEN descuento_volumen:listades
					 ) {
					if(descuento_volumen!=null){
						int unidadesdesde=descuento_volumen.getUNIDADES_DESCUENTO();
						int unidadhasta=descuento_volumen.getUNIDADES_HASTA().toString().trim().equals("adelante")?-1:Integer.valueOf(descuento_volumen.getUNIDADES_HASTA().toString().trim());
						if(unidadhasta==-1 && cantidad >= unidadesdesde ){
							resultado=Utilidades.Redondear(((((cantidad*articulo.getPrecio2()) *obtienePrecio(articulo))-ppago)*descuento_volumen.getDESCUENTO())/100,2);
						}
						if((cantidad <= unidadhasta&&unidadhasta!=-1) && cantidad>= unidadesdesde ){
							resultado=Utilidades.Redondear(((((cantidad*articulo.getPrecio2())*obtienePrecio(articulo))-ppago)*descuento_volumen.getDESCUENTO())/100,2);
						}
					}
				}


				break;
			case "PP":
				DESCUENTO_FORMAPAGO descuento_formapago=daoDESCUENTO_formapago.getByKey(getCodigoProductoActual());

				String tiponegocio1 = cliente.getLocalidad();
				double precio11=0.00;
				if(articulo.getPrecio10()==1){
					if(tiponegocio1.equals("Subdistribuidor")){
						precio11=articulo.getPrecio3();
					}else if (tiponegocio1.equals("Mayorista")){
						precio11=articulo.getPrecio4();
					}else if(tiponegocio1.equals("Detallista")){
						precio11=articulo.getPrecio1();
					}else {
						precio11=obtienePrecio(articulo);
					}
				}else{
					precio11=obtienePrecio(articulo);
				}

				if(descuento_formapago!=null) {
                    resultado = Utilidades.Redondear(((((cantidad * articulo.getPrecio2()) * precio11) - ppago) * descuento_formapago.getPorcentaje()) / 100, 2);
                }
				break;
			case  "AV":
				/*DESCUENTO_AVENA descuento_avena = daoDESCUENTO_avena.getByKey(getCodigoProductoActual());
				if(descuento_avena!=null){
					if(cantidad>=descuento_avena.getCANTIDAD()){
						resultado=cantidad*descuento_avena.getDESCUENTO();
					}
				}*/
				break;


		}

		return  resultado;

	}



	@SuppressLint("SimpleDateFormat")
	public void grabarPedido(boolean enviarAutomaticamente,boolean facturar,long _idPedidoAEliminar,boolean incluirEnReparto) 
	{
		long idPedido;
		long idPedidoItem;
		
		if (listaPedidoItems.size() == 0)
		{
			utilDialogos.muestraToastGenerico(a, noHaCargadoProductos, false);
			return;			
		}

	    Calendar cal = Calendar.getInstance();
	    Date date = cal.getTime();
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
	    String fecha = df.format(date);
		
	    if (_idPedidoAEliminar>0)
	    {
	    	Pedido tmpPedido = daoPedido.getById((int)_idPedidoAEliminar);
	    	daoPedido.delete(tmpPedido);
	    	
	    	daoPedidoItem.deleteByIdPedido(_idPedidoAEliminar);
	    }
	    
		Pedido pedido = new Pedido();
		pedido.setCodCliente(cliente.getCodigo());
		pedido.setFecha(fecha);
		pedido.setIdVendedor(codigoVendedor);
		pedido.setTotalNeto(importeTotalPedido);
		pedido.setTotalFinal(importeTotalPedido);
		pedido.setTransferido(false);
		pedido.setFacturar(facturar);
		pedido.setIncluirEnReparto(incluirEnReparto);
		
		idPedido = daoPedido.save(pedido);
		
		PedidoItem pedidoItem = null;
		
		if (idPedido!=-1)
		{
			Iterator<PedidoItem> i = listaPedidoItems.iterator();
			while (i.hasNext())
			{
				pedidoItem = i.next();
				
				pedidoItem.setIdPedido(idPedido);
				idPedidoItem = daoPedidoItem.save(pedidoItem);
				
				if (idPedidoItem==-1)
				{
					utilDialogos.muestraToastGenerico(a, ocurrioUnErrorAlGrabar, false);
					
					// Si ocurrio un error elimino
					daoPedido.delete(pedido);
					daoPedidoItem.deleteByIdPedido(idPedido);

					return;
				}
			}
			utilDialogos.muestraToastGenerico(a, pedidoGrabadoConExito, false);
			
			Intent intent = new Intent(AppSysMobile.INTENT_FILTER_CAMBIOS_LISTA_PEDIDOS);
			a.sendBroadcast(intent);

			if (enviarAutomaticamente)
				enviarPedido(idPedido);
			else
				pantallaManagerCargaPedidos.finalizaActivityCargaPedidos();
		
		}
		else
		{
			utilDialogos.muestraToastGenerico(a, ocurrioUnErrorAlGrabar, false);
			return;			
		}
		
		
	}
	
	public void removerItemListaItems(int posicionItemSeleccionado) {
		listaPedidoItems.remove(posicionItemSeleccionado);
		notificarCambiosAdapter();
		pantallaManagerCargaPedidos.setCantidadItems(listaPedidoItems.size());
		totalizarPedido();
	}
	
	public void totalizarPedido()
	{
		double total=0;
		PedidoItem pedidoItem;
		
		Iterator<PedidoItem> i = listaPedidoItems.iterator();
		while (i.hasNext())
		{
			pedidoItem = i.next();
			total = total + pedidoItem.getTotal();
		}
		
		importeTotalPedido = total;
		pantallaManagerCargaPedidos.setImporteTotalPedido(Utilidades.Redondear(total,2));
	}
	
	public double obtienePrecio(Articulo articulo)
	{

		double precio = 0;
 		
		switch (claseDePrecioSeleccionada)
		{
			case 0:
				precio = articulo.getPrecio1();
				break;
			case 1:
				precio = articulo.getPrecio1();
				break;
			case 2:
				precio = articulo.getPrecio2();
				break;
			case 3:
				precio = articulo.getPrecio3();
				break;
			case 4:
				precio = articulo.getPrecio4();
				break;
			case 5:
				precio = articulo.getPrecio5();
				break;
			case 6:
				precio = articulo.getPrecio6();
				break;
			case 7:
				precio = articulo.getPrecio7();
				break;
			case 8:
				precio = articulo.getPrecio8();
				break;
			case 9:
				precio = articulo.getPrecio9();
				break;
			case 10:
				precio = articulo.getPrecio10();
				break;
		}
		
 		return precio;
	}

	public void scanArticulo() 
	{
		
		
		if (reintentarObtenerIntentZxing)
		{
			intentScan = getZxingIntent(this.a);
			Log.d("sw","reintentarObtenerIntentZxing ");
		}

		if (intentScan == null)
		{
			Log.d("sw","intentScan es NULO" );
			mostrarDialogoDescarga();
			reintentarObtenerIntentZxing = true;
		}
		else
	    {
			Log.d("sw","intentScan NO ES NULO" );
			reintentarObtenerIntentZxing = false;
			intentScan.putExtra("PROMPT_MESSAGE", textoLecturaCodigoDeBarras);
			a.startActivityForResult(intentScan, AppSysMobile.DESDE_SCANNER);
	    }
	    
	}
    private void mostrarDialogoDescarga() 
    {
    	pantallaManagerCargaPedidos.muestraAlertaDescargaBarcodeScanner(this);
    }
    public static Intent getZxingIntent(Context context) {
    	
        Intent zxingIntent = new Intent(BS_PACKAGE + ".SCAN");
        
        final PackageManager pm = context.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(zxingIntent,0);
        
        for (int i = 0; i < activityList.size(); i++) 
        {
            ResolveInfo app = activityList.get(i);
        	// PARA QUE SEA LA DE ZXING BARCODE TIENE QUE LLAMARSE com.google.zxing.client.android.CaptureActivity"
        	Log.d("sw","app.activityInfo.name " + app.activityInfo.name.toString());
            
        	if (app.activityInfo.name.contains("zxing")) 
            {
            	zxingIntent.setClassName(app.activityInfo.packageName,app.activityInfo.name);
                return zxingIntent;
            }
        }
        //return zxingIntent;
        return null;
    }

	@Override
	public void onAlertResult(int idAlert, int which) 
	{
		if (idAlert == 0)
		{
			switch (which)
			{
				case AlertManager.BUTTON_POSITIVE :
					  Uri uri = Uri.parse("market://details?id=" + BS_PACKAGE);
					  Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					  try 
					  {
						  a.startActivity(intent);
					  } 
					  catch (ActivityNotFoundException anfe) 
					  {
						  Toast.makeText(a, textoMarketNoInstalado, Toast.LENGTH_LONG).show();
					  }
				break;
			}
		}
	}

	public void enviarPedido(long idPedido)
	{
		ListenerEnviaPendientes listenerEnviaPendientes = new ListenerEnviaPendientes();
		PantallaManagerEnviaPendientes pantallaManagerEnviaPendientes = new PantallaManagerEnviaPendientes(this.a, listenerEnviaPendientes);
		listenerEnviaPendientes.seteaPantallaMager(pantallaManagerEnviaPendientes);
		
		LogicaEnviaPendientes logicaEnviaPendientes = new LogicaEnviaPendientes(this.a,pantallaManagerEnviaPendientes);
		logicaEnviaPendientes.setDesdeCargaDePedidos(true);
		
		logicaEnviaPendientes.setIdPedidoEnviar(idPedido);
		Log.d("sw","enviarPedido" + idPedido);
		
		logicaEnviaPendientes.enviarPedidosPendientes();
	}

	public void cargaDatosPedido(long _idPedido) 
	{
		
		Log.d("SW","_idPedido: " +_idPedido);
		
		Pedido pedido = daoPedido.getById((int)_idPedido);
		
		String codCliente = pedido.getCodCliente();
		inicializaDatos(codCliente);
		
		PedidoItem pedidoItem;
		
		listaPedidoItems.clear();
		List<PedidoItem> lstTemp = daoPedidoItem.getAll(" idPedido = " + pedido.getIdPedido());
		Iterator<PedidoItem> i = lstTemp.iterator();
		while (i.hasNext())
		{
			pedidoItem = i.next();
			pedidoItem.setAuxDescripcionArticulo(daoArticulo.getByKey(pedidoItem.getIdArticulo()).getDescripcion());
			listaPedidoItems.add(pedidoItem);
		}
		
		notificarCambiosAdapter();
	
		totalizarPedido();
	}
	
	
	public void cargarSpClasesDePrecio(Spinner spClasesDePrecio)
	{
		int posicion;
		String listasHabilitadas = AppSysMobile.getClasesDePrecioHabilitadas();
		String[] lista;
		if (listasHabilitadas.equals(""))
		{
			lista = new String[9];
			lista[0] = "(seleccione una clase de precio)";
			
			for (posicion=1;posicion<=lista.length -1;posicion++)
			{
					lista[posicion] = "Clase " + posicion;
			}
		}
		else
		{
			String[] hab = listasHabilitadas.split(";");
			lista = new String[hab.length+1];
			System.arraycopy(hab, 0, lista, 1, hab.length);
			
			lista[0] = "(seleccione una clase de precio)";
			for (posicion=1;posicion<=lista.length -1;posicion++)
			{
					lista[posicion] = "Clase " + hab[posicion-1];
			}
		}
 		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.a, android.R.layout.simple_spinner_item, lista); 
 		spClasesDePrecio.setAdapter(adapter);
	}

	
	public void validaSeleccionClaseDePrecio() 
	{
		int posicionItemSeleccionado = pantallaManagerCargaPedidos.getSpClasesDePrecio().getSelectedItemPosition();

		if (posicionItemSeleccionado == 0)
		{
			pantallaManagerCargaPedidos.muestraAlertaDebeSeleccionarUnaClaseDePrecio();
		}
		else
		{
			String strClase = (String) pantallaManagerCargaPedidos.getSpClasesDePrecio().getItemAtPosition(posicionItemSeleccionado);
			strClase = strClase.replace("Clase", "").trim();
			//claseDePrecioSeleccionada = posicionItemSeleccionado;
			claseDePrecioSeleccionada = Integer.parseInt(strClase);
			pantallaManagerCargaPedidos.cerrarDialogoSolicitaClaseDePrecio();
		}
	}

	public boolean spSolicitaClaseDePrecio() 
	{
		SharedPreferences prefs = this.a.getSharedPreferences("CONFIGURACION_WS",Context.MODE_PRIVATE);
		
		boolean solicitaClaseDePrecio = prefs.getBoolean("solicitaClaseDePrecio", false);
		
		return solicitaClaseDePrecio;
		
	}
}
