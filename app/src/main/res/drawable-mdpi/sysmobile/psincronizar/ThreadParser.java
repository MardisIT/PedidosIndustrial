package ar.com.syswork.sysmobile.psincronizar;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ar.com.syswork.sysmobile.daos.DaoArticulo;
import ar.com.syswork.sysmobile.daos.DaoCartera;
import ar.com.syswork.sysmobile.daos.DaoCliente;
import ar.com.syswork.sysmobile.daos.DaoCuenta;
import ar.com.syswork.sysmobile.daos.DaoDESCUENTO_AVENA;
import ar.com.syswork.sysmobile.daos.DaoDESCUENTO_FORMAPAGO;
import ar.com.syswork.sysmobile.daos.DaoDESCUENTO_VOLUMEN;
import ar.com.syswork.sysmobile.daos.DaoDeposito;
import ar.com.syswork.sysmobile.daos.DaoPRECIO_ESCALA;
import ar.com.syswork.sysmobile.daos.DaoRubro;
import ar.com.syswork.sysmobile.daos.DaoVendedor;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Articulo;
import ar.com.syswork.sysmobile.entities.Capania;
import ar.com.syswork.sysmobile.entities.Cartera;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.entities.DESCUENTO_AVENA;
import ar.com.syswork.sysmobile.entities.DESCUENTO_FORMAPAGO;
import ar.com.syswork.sysmobile.entities.DESCUENTO_VOLUMEN;
import ar.com.syswork.sysmobile.entities.Deposito;
import ar.com.syswork.sysmobile.entities.PRECIO_ESCALA;
import ar.com.syswork.sysmobile.entities.Rubro;
import ar.com.syswork.sysmobile.entities.Vendedor;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class ThreadParser implements Runnable{

	private int tipoParser;
	private Handler h;
	
	private AppSysMobile app;
	private DataManager dataManager;
	
	private DaoCliente daoCliente;
	private DaoArticulo daoArticulo;
	private DaoRubro daoRubro;
	private DaoVendedor daoVendedor;
	private DaoDeposito daoDeposito;
	private DaoCuenta daoCuenta;

	private DaoDESCUENTO_AVENA daoDESCUENTO_avena;
	private DaoDESCUENTO_FORMAPAGO daoDESCUENTO_formapago;
	private DaoDESCUENTO_VOLUMEN daoDESCUENTO_volumen;
	private DaoPRECIO_ESCALA daoPRECIO_escala;
	private DaoCartera daoCartera;
	
	
	private Cliente cliente;
	private Articulo articulo;
	private Rubro rubro;
	private Deposito deposito;
	private Vendedor vendedor;
	private Capania capania;

	private JSONArray arrayJson;
	private JSONObject jsObject;
	
	private int pagina;
	
	private Activity a;
	private String msgJson;
	
	public ThreadParser(Handler h,int tipoParser,Activity a,String msgJson,int pagina)
	{
		try{
			this.h = h;
			this.a=a;
			this.msgJson = msgJson;
			this.pagina = pagina;

			this.tipoParser = tipoParser;

			app = (AppSysMobile) this.a.getApplication();

			dataManager = app.getDataManager();
			daoCliente = dataManager.getDaoCliente();
			daoArticulo = dataManager.getDaoArticulo();
			daoRubro = dataManager.getDaoRubro();
			daoDeposito = dataManager.getDaoDeposito();
			daoVendedor = dataManager.getDaoVendedor();
			daoCuenta=dataManager.getDaoCuenta();

			daoDESCUENTO_avena=dataManager.getDaoDESCUENTO_avena();
			daoDESCUENTO_formapago=dataManager.getDaoDESCUENTO_formapago();
			daoDESCUENTO_volumen=dataManager.getDaoDESCUENTO_volumen();
			daoPRECIO_escala=dataManager.getDaoPRECIO_escala();
			daoCartera=dataManager.getDaoCartera();

		}catch (Exception ex){
			Log.d("SW","Error ejecutar guardado");
		}

	}
	
	@Override
	public void run() {
		
		switch (tipoParser)
		{
			case AppSysMobile.WS_VENDEDORES:
				grabaVendedores();
				Log.d("SW","grabo Vendedores()");
				break;
			case AppSysMobile.WS_RUBROS:
				grabaRubros();
				Log.d("SW","grabo Rubros()");
				break;
			case AppSysMobile.WS_DEPOSITOS:
				grabaDepositos();
				Log.d("SW","grabo Depositos()");
				break;
			case AppSysMobile.WS_CLIENTES:
				grabaClientes();
				Log.d("SW","grabo Clientes()");
				break;
			case AppSysMobile.WS_ARTICULOS:
				grabaArticulos();
				Log.d("SW","grabo Articulos()");
				break;
			case AppSysMobile.WS_CUENTA:
				grabarCuenta();
				Log.d("SW","grabo Cuenta()");
				break;
			case AppSysMobile.WS_DESAVENA:
				grabarAvena();
				Log.d("SW","grabo Avena()");
				break;
			case AppSysMobile.WS_DESFORMAPAGO:
				grabarDesFormaPago();
				Log.d("SW","grabo forma pago()");
				break;
			case AppSysMobile.WS_DESVOLUMEN:
				grabarDesVolumen();
				Log.d("SW","grabo volumen()");
				break;
			case AppSysMobile.WS_DESPRECIOESCALA:
				grabarDesPrecioEscala();
				Log.d("SW","grabo escala()");
				break;
			case AppSysMobile.WS_CARTERA:
				grabarCartera();
				Log.d("SW","grabo cartera()");
				break;
		}
		
		enviaMensaje("TERMINO", tipoParser);

	}
	private  void grabarCartera(){
		if(msgJson.equals("")==false){
			if (pagina==1)
				daoCartera.deleteAll();

			Cartera cartera    = new Cartera();

			/*try {
				msgJson = Utilidades.Decompress(msgJson);

			} catch (IOException e1) {
				e1.printStackTrace();
				Log.d("SW","Error Decompress VENDEDORES");
			}*/



			try
			{
				arrayJson = new JSONArray(msgJson);
				for (int x = 0; x<arrayJson.length() ;x++)
				{
					jsObject = arrayJson.getJSONObject(x);
                    cartera.setID(jsObject.getInt("id"));
					cartera.setCodcli(jsObject.getString("codcli").trim());
					cartera.setNombre_cliente(jsObject.getString("nombreCliente").trim());
					cartera.setFe_fac(jsObject.getString("feFac").trim());
					cartera.setFe_des(jsObject.getString("feDes").trim());
					cartera.setTp_d(jsObject.getString("tpD").trim());
					cartera.setLine(jsObject.getString("line").trim());
					cartera.setNro_docm(jsObject.getString("nroDocm").trim());
					cartera.setTi_desp(jsObject.getString("tiDesp").trim());
					cartera.setFe_vecto(jsObject.getString("feVecto").trim());
					cartera.setCorriente(jsObject.getDouble("corriente"));
					cartera.setVcdo1_15(jsObject.getDouble("vcdo115"));
					cartera.setVcdo16_30(jsObject.getDouble("vcdo1630"));
					cartera.setVcdo31_60(jsObject.getDouble("vcdo3160"));
					cartera.setVcdo61mayor(jsObject.getDouble("vcdo61mayor"));
					cartera.setVendedor(jsObject.getString("vendedor").trim());
					cartera.setCodvendedor(jsObject.getString("codvendedor").trim());
					daoCartera.save(cartera);
				}
			}
			catch(JSONException e)
			{
				Log.d("SW","Error ejecutar guardado precio escala");
			}
		}
	}
	private  void grabarAvena(){
		if(msgJson.equals("")==false){
			if (pagina==1)
				daoDESCUENTO_avena.deleteAll();

			DESCUENTO_AVENA descuento_avena  = new DESCUENTO_AVENA();

			/*try {
				msgJson = Utilidades.Decompress(msgJson);

			} catch (IOException e1) {
				e1.printStackTrace();
				Log.d("SW","Error Decompress VENDEDORES");
			}*/



			try
			{
				arrayJson = new JSONArray(msgJson);
				for (int x = 0; x<arrayJson.length() ;x++)
				{
					jsObject = arrayJson.getJSONObject(x);


					descuento_avena.setCodigoSKU(jsObject.getString("codigoSku").toString().trim());
					descuento_avena.setSKU_NOMBRE(jsObject.getString("skuNombre"));
					descuento_avena.setCANTIDAD(jsObject.getInt("cantidad"));
					descuento_avena.setDESCUENTO(jsObject.getDouble("descuento"));
					descuento_avena.setCONDICION(jsObject.getString("condicion"));

					daoDESCUENTO_avena.save(descuento_avena);
				}
			}
			catch(JSONException e)
			{
				e.printStackTrace();
			}
		}
	}

	private  void grabarDesFormaPago(){
		if(msgJson.equals("")==false){
			if (pagina==1)
				daoDESCUENTO_formapago.deleteAll();

			DESCUENTO_FORMAPAGO descuento_formapago  = new DESCUENTO_FORMAPAGO();

			/*try {
				msgJson = Utilidades.Decompress(msgJson);

			} catch (IOException e1) {
				e1.printStackTrace();
				Log.d("SW","Error Decompress VENDEDORES");
			}*/



			try
			{
				arrayJson = new JSONArray(msgJson);
				for (int x = 0; x<arrayJson.length() ;x++)
				{
					jsObject = arrayJson.getJSONObject(x);
					descuento_formapago.setCodigoSKU(jsObject.getString("codigoSku"));
					descuento_formapago.setPorcentaje(jsObject.getDouble("porcentaje"));
					daoDESCUENTO_formapago.save(descuento_formapago);
				}
			}
			catch(JSONException e)
			{
				Log.d("SW","Error ejecutar guardado vendedores");
			}
		}
	}
	private  void grabarDesVolumen(){
		if(msgJson.equals("")==false){
			if (pagina==1)
				daoDESCUENTO_volumen.deleteAll();

			DESCUENTO_VOLUMEN descuento_volumen   = new DESCUENTO_VOLUMEN();

			/*try {
				msgJson = Utilidades.Decompress(msgJson);

			} catch (IOException e1) {
				e1.printStackTrace();
				Log.d("SW","Error Decompress VENDEDORES");
			}*/



			try
			{
				arrayJson = new JSONArray(msgJson);
				for (int x = 0; x<arrayJson.length() ;x++)
				{
					jsObject = arrayJson.getJSONObject(x);
					descuento_volumen.setCodigosSKU(jsObject.getString("codigosSku"));
					descuento_volumen.setSKU_NOMBRE(jsObject.getString("skuNombre"));
					descuento_volumen.setUNIDADES_DESCUENTO(jsObject.getInt("unidadesDescuento"));
					descuento_volumen.setUNIDADES_HASTA(jsObject.getString("unidadesHasta"));
					descuento_volumen.setDESCUENTO(jsObject.getDouble("descuento"));
					daoDESCUENTO_volumen.save(descuento_volumen);
				}
			}
			catch(JSONException e)
			{
				Log.d("SW","Error ejecutar guardado descuento volumen");
			}
		}
	}
	private  void grabarDesPrecioEscala(){
		if(msgJson.equals("")==false){
			if (pagina==1)
				daoPRECIO_escala.deleteAll();

			PRECIO_ESCALA precio_escala    = new PRECIO_ESCALA();

			/*try {
				msgJson = Utilidades.Decompress(msgJson);

			} catch (IOException e1) {
				e1.printStackTrace();
				Log.d("SW","Error Decompress VENDEDORES");
			}*/



			try
			{
				arrayJson = new JSONArray(msgJson);
				for (int x = 0; x<arrayJson.length() ;x++)
				{
					jsObject = arrayJson.getJSONObject(x);
					precio_escala.setCodigosSKU(jsObject.getString("codigosSku"));
					precio_escala.setSKU(jsObject.getString("sku"));
					precio_escala.setUNIDADESXCAJA(jsObject.getInt("unidadesxcaja"));
					precio_escala.setUNIDADES(jsObject.getString("unidades"));
					precio_escala.setPRECIO_UNITARIO(jsObject.getDouble("precioUnitario"));
					precio_escala.setUNIDADES_VALIDAR(jsObject.getInt("unidadesValidar"));
					precio_escala.setPRECIO2(jsObject.getDouble("precio2"));
					daoPRECIO_escala.save(precio_escala);
				}
			}
			catch(JSONException e)
			{
				Log.d("SW","Error ejecutar guardado precio escala");
			}
		}
	}


	private  void grabarCuenta(){
		if(msgJson.equals("")==false){
			if (pagina==1)
				daoCuenta.deleteAll();

			Capania capania = new Capania();

			/*try {
				msgJson = Utilidades.Decompress(msgJson);

			} catch (IOException e1) {
				e1.printStackTrace();
				Log.d("SW","Error Decompress VENDEDORES");
			}*/



					try
					{
						arrayJson = new JSONArray(msgJson);
						for (int x = 0; x<arrayJson.length() ;x++)
						{
							jsObject = arrayJson.getJSONObject(x);

							capania.setIdAccount(jsObject.getString("idAccount"));
							capania.setAccountNombre(jsObject.getString("accountName"));
							capania.setIdCampania(jsObject.getString("id"));
							capania.setCampaniaNombre(jsObject.getString("name"));

							daoCuenta.save(capania);
							AppSysMobile.WS_ESTADOSIN="OK";
						}
					}
					catch(JSONException e)
					{
						Log.d("SW","Error ejecutar guardado cuenta");
					}
			}
	}
	private void grabaArticulos() {
		
		if (pagina == 1) 
			daoArticulo.deleteAll();
		
		articulo = new Articulo();
		
		/*try {
			msgJson = Utilidades.Decompress(msgJson);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/
		
		try
		{
			arrayJson = new JSONArray(msgJson);
			for (int x = 0; x<arrayJson.length() ;x++)
			{

				jsObject = arrayJson.getJSONObject(x);
				
				articulo.setIdArticulo(jsObject.getString("idArticulo"));
				articulo.setDescripcion(jsObject.getString("descripcion"));
				articulo.setIdRubro(jsObject.getString("idRubro"));
				
				articulo.setIva(jsObject.getDouble("iva"));
				articulo.setImpuestosInternos(jsObject.getDouble("impuestosInternos"));
				articulo.setExento(jsObject.getBoolean("exento"));
				
				articulo.setPrecio1(jsObject.getDouble("precio1"));
				articulo.setPrecio2(jsObject.getDouble("precio2"));
				articulo.setPrecio3(jsObject.getDouble("precio3"));
				articulo.setPrecio4(jsObject.getDouble("precio4"));
				articulo.setPrecio5(jsObject.getDouble("precio5"));
				articulo.setPrecio6(jsObject.getDouble("precio6"));
				articulo.setPrecio7(jsObject.getDouble("precio7"));
				articulo.setPrecio8(jsObject.getDouble("precio8"));
				articulo.setPrecio9(jsObject.getDouble("precio9"));
				articulo.setPrecio10(jsObject.getDouble("precio10"));

				daoArticulo.save(articulo);
				
			}
		}
		catch(JSONException e)
		{
			Log.d("SW","Error ejecutar guardado articulo");
		}	
		
	}

	private void grabaClientes() {

		if (this.pagina == 1)
			daoCliente.deleteAll();
		
		cliente = new Cliente();
		
		// msgJson viene comprimido, lo descomprimo y continuo
		/*try {
			msgJson = Utilidades.Decompress(msgJson);
			
		} catch (IOException e1) {
			e1.printStackTrace();
			Log.d("SW","Error Decompress CLIENTES!!!");
		}*/
		
		try
		{
			arrayJson = new JSONArray(msgJson);
			for (int x = 0; x<arrayJson.length() ;x++)
			{
				jsObject = arrayJson.getJSONObject(x);
			
				
				cliente.setCodigo(jsObject.getString("code"));
				cliente.setCodigoOpcional(jsObject.getString("externalCode"));
				cliente.setRazonSocial(jsObject.getString("name"));
				cliente.setCalleNroPisoDpto(jsObject.getString("mainStreet"));
				cliente.setLocalidad (jsObject.getString("typeBusiness"));
				cliente.setCuit(jsObject.getString("rutaaggregate")) ;
				cliente.setIva((byte)0);
				cliente.setClaseDePrecio((byte)0);
				cliente.setPorcDto(0);
				cliente.setCpteDefault("");
				cliente.setIdVendedor(jsObject.getString("imeI_ID"));
				cliente.setTelefono("");
				cliente.setMail("");
				
				daoCliente.save(cliente);
			}
		}
		catch(JSONException e)
		{
			Log.d("SW","Error ejecutar guardado cliente");
		}
		
		
	}

	private void grabaVendedores()
	{
		if (pagina==1)
			daoVendedor.deleteAll();

		vendedor = new Vendedor();

		/*try {
			msgJson = Utilidades.Decompress(msgJson);
			
		} catch (IOException e1) {
			e1.printStackTrace();
			Log.d("SW","Error Decompress VENDEDORES");
		}*/
		
		try
		{
			arrayJson = new JSONArray(msgJson);
			for (int x = 0; x<arrayJson.length() ;x++)
			{
				jsObject = arrayJson.getJSONObject(x);
				
				vendedor.setIdVendedor(jsObject.getString("idVendedor"));
				vendedor.setNombre(jsObject.getString("nombre"));
				vendedor.setCodigoDeValidacion(jsObject.getString("codigoDeValidacion"));
				
				daoVendedor.save(vendedor);
                AppSysMobile.WS_ESTADOSIN="SC";
			}
		}
		catch(JSONException e)
		{
			Log.d("SW","Error ejecutar guardado cendedor ");
		}

	}
	
	private void grabaRubros()
	{
		if (this.pagina==1)
			daoRubro.deleteAll();
		
		rubro = new Rubro();
		
		/*try {
			msgJson = Utilidades.Decompress(msgJson);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.d("SW","Error Decompress RUBROS");
		}*/

		try
		{
			arrayJson = new JSONArray(msgJson);
			for (int x = 0; x < arrayJson.length() ;x++)
			{
				jsObject = arrayJson.getJSONObject(x);
				
				rubro.setIdRubro(jsObject.getString("idRubro"));
				rubro.setDescripcion(jsObject.getString("descripcion"));
				
				daoRubro.save(rubro);
			}
		}
		catch(JSONException e)
		{
			Log.d("SW","Error ejecutar guardado rubros");
		}		
		
	}
	
	private void grabaDepositos()
	{
		if (pagina == 1)
			daoDeposito.deleteAll();

		deposito = new Deposito();
		
		/*try {
			msgJson = Utilidades.Decompress(msgJson);
			
		} catch (IOException e1) {
			e1.printStackTrace();
			Log.d("SW","Error Decompress DEPOSITOS");
		}*/

		try
		{
			arrayJson = new JSONArray(msgJson);
			for (int x = 0; x < arrayJson.length() ;x++)
			{
				jsObject = arrayJson.getJSONObject(x);

				deposito.setIdDeposito(jsObject.getString("idDeposito"));
				deposito.setDescripcion(jsObject.getString("descripcion"));

				daoDeposito.save(deposito);
			}
		}
		catch(JSONException e)
		{
			Log.d("SW","Error ejecutar deposrotp");
		}		
		
	}
	
	private void enviaMensaje(String mensaje, int tipoParser)
	{
		Message message = new Message();
		message.arg1 = tipoParser;
		message.arg2 = pagina;
		
		message.obj = mensaje;
		h.sendMessage(message);
	}
	
}
