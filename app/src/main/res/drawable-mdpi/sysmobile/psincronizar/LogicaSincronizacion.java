package ar.com.syswork.sysmobile.psincronizar;

import android.app.Activity;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoCuenta;
import ar.com.syswork.sysmobile.daos.DaoVendedor;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Capania;
import ar.com.syswork.sysmobile.entities.Registro;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.Utilidades;

public class LogicaSincronizacion implements Callback{
	
	private PantallaManagerSincronizacion pantallaManagerSincronizacion;
	private ThreadPoolExecutor executor;
	private ThreadSincronizacion ts;
	
	private ArrayList<Registro> listaRegistros;
	private String jSonRegistrosTablas;

	private ArrayList<String> alJsonArticulos;
	private String jSonArticulos;
	
	private ArrayList<String> alJsonClientes;
	private String jSonClientes;

	private ArrayList<String> alJsonCuenta;
	private String jSonCuenta;
	
	private int paginasClientes;
	private int paginasArticulos;
	
	private String jSonVendedores;
	private String jSonRubros;
	private String jSonDepositos;

	private String jSonDesavena;
	private String jSonDesformapago;
	private String jSonDesvolumen;
	private String jSondesprecioescala;

	private String jSonCartera;



	private String imei;

	private boolean huboErrores;
	private Activity a;
	private int completadas=0;
	
	private String strErrorDeComunicacion;
	private String strDescargaExitosa;
	private String strDescargando;
	private String strConectando;
	private String strErrorDeConexionAlWebService;
	private String strIntentandoConectarAlWebService;
	
	private AppSysMobile app;
	private DataManager dm;
	private DaoVendedor daoVendedor;
	private DaoCuenta daoCuenta;
	
	private int cantVendedores;
	
	private final int OBTENER_REGISTROS = 1;
	private final int OBTENER_JSONS = 2;
	private final int 	OBTENER_ENGINE=3;
	
	private int tipoLlamada;
	
	public LogicaSincronizacion (Activity a) {
		this.a = a;

		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

		this.strErrorDeComunicacion = this.a.getString(R.string.seProdujoUnErrorDeComunicacion);
		this.strDescargaExitosa = this.a.getString(R.string.descarga_exitosa);
		this.strConectando = this.a.getString(R.string.conectando);
		this.strDescargando = this.a.getString(R.string.descargando);
		this.strErrorDeConexionAlWebService = this.a.getString(R.string.errorDeConexionAlWebService);
		this.strIntentandoConectarAlWebService = this.a.getString(R.string.strIntentandoConectarAlWebService);

		app = (AppSysMobile) a.getApplication();
		dm = app.getDataManager();
		daoVendedor = dm.getDaoVendedor();
		daoCuenta = dm.getDaoCuenta();

		setCantVendedores(daoVendedor.getCount());
		ArrayAdapter<Capania> adaptador;
	}
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	public void sincronizar()
	{
		pantallaManagerSincronizacion.seteaBotonSincronizarVisible(false);
		pantallaManagerSincronizacion.seteaPrgEstadoConexionVisible(true);
		pantallaManagerSincronizacion.seteaTxtEstadoConexionVisible(true);
		pantallaManagerSincronizacion.seteaTxtEstadoConexion(strIntentandoConectarAlWebService);
		
		tipoLlamada = OBTENER_REGISTROS;
		Handler h = new Handler(this);
		
		ThreadSincronizacion tc = new ThreadSincronizacion(h,AppSysMobile.WS_REGISTROS,0);
        Thread t = new Thread(tc);
        t.start();
		
	}
	public void sincronizarconfiguracion()
	{
		pantallaManagerSincronizacion.setImei_id(getImei());
		pantallaManagerSincronizacion.seteaBotonSincronizarVisible(false);
		pantallaManagerSincronizacion.seteaPrgEstadoConexionVisible(true);
		pantallaManagerSincronizacion.seteaTxtEstadoConexionVisible(true);
		pantallaManagerSincronizacion.seteaTxtEstadoConexion(strIntentandoConectarAlWebService);

		tipoLlamada = OBTENER_ENGINE;
		Handler h = new Handler(this);

		ThreadSincronizacion tc = new ThreadSincronizacion(h,AppSysMobile.WS_CUENTA,0);
		Thread t = new Thread(tc);
		t.start();
	}
	
	public void obtenerJsonsYProcesar() {
		try {


		jSonArticulos= "";
		
		alJsonArticulos = new ArrayList<String>();
		alJsonArticulos.clear();
		
		alJsonClientes = new ArrayList<String>();
		alJsonClientes.clear();
		
		jSonVendedores = "";
		jSonRubros = "";
		jSonDepositos = "";
		jSonClientes = "";

		tipoLlamada = OBTENER_JSONS;
		
		Log.d("SW","Entra en Obtener Json");
		
		huboErrores = false;
		
		Handler h = new Handler(this);
		
		pantallaManagerSincronizacion.muestraDialogoSincronizacion();

		for (int pos = 0; (pos < listaRegistros.size()); pos++)
		{

			if (listaRegistros.get(pos).getTabla().equals("Cuenta"))
			{
				pantallaManagerSincronizacion.seteatxtResultadoCuenta(strConectando);
				ts = new ThreadSincronizacion(h,AppSysMobile.WS_CUENTA,0);

				Log.d("SW","Lanza Thread Cuenta");

				executor.execute(ts);
			}
			if (listaRegistros.get(pos).getTabla().equals("wsSysMobileDepositos"))
			{
				//pantallaManagerSincronizacion.seteatxtResultadoRubros(strConectando);
				ts = new ThreadSincronizacion(h,AppSysMobile.WS_DEPOSITOS,0);
				executor.execute(ts);
				
				Log.d("SW","Lanza Thread Depositos");
			}
			
			if (listaRegistros.get(pos).getTabla().equals("wsSysMobileRubros"))
			{
				pantallaManagerSincronizacion.seteatxtResultadoRubros(strConectando);
				ts = new ThreadSincronizacion(h,AppSysMobile.WS_RUBROS,0);
				
				Log.d("SW","Lanza Thread Rubros");

				executor.execute(ts);
			}
			
			if (listaRegistros.get(pos).getTabla().equals("wsSysMobileVendedores"))
			{
				pantallaManagerSincronizacion.seteatxtResultadoVendedores(strConectando);
				ts = new ThreadSincronizacion(h,AppSysMobile.WS_VENDEDORES,0);
				
				Log.d("SW","Lanza Thread Vendedores");
				
				executor.execute(ts);
			}

			if (listaRegistros.get(pos).getTabla().equals("DESCUENTO_AVENA"))
			{
				pantallaManagerSincronizacion.seteatxtResultadodesavena(strConectando);
				ts = new ThreadSincronizacion(h,AppSysMobile.WS_DESAVENA,0);

				Log.d("SW","Lanza Thread DESCUENTO_AVENA");

				executor.execute(ts);
			}
			if (listaRegistros.get(pos).getTabla().equals("DESCUENTO_FORMAPAGO"))
			{
				pantallaManagerSincronizacion.seteatxtResultadodesformapago(strConectando);
				ts = new ThreadSincronizacion(h,AppSysMobile.WS_DESFORMAPAGO,0);

				Log.d("SW","Lanza Thread DESCUENTO_FORMAPAGO");

				executor.execute(ts);
			}
			if (listaRegistros.get(pos).getTabla().equals("DESCUENTO_VOLUMEN"))
			{
				pantallaManagerSincronizacion.seteatxtResultadodesvolumen(strConectando);
				ts = new ThreadSincronizacion(h,AppSysMobile.WS_DESVOLUMEN,0);

				Log.d("SW","Lanza Thread DESCUENTO_VOLUMEN");

				executor.execute(ts);
			}
			if (listaRegistros.get(pos).getTabla().equals("PRECIO_ESCALA"))
			{
				pantallaManagerSincronizacion.seteatxtResultadoprecioescala(strConectando);
				ts = new ThreadSincronizacion(h,AppSysMobile.WS_DESPRECIOESCALA,0);

				Log.d("SW","Lanza Thread PRECIO_ESCALA");

				executor.execute(ts);
			}

			if (listaRegistros.get(pos).getTabla().equals("CARTERA"))
			{
				pantallaManagerSincronizacion.seteatxtResultadoprecioescala(strConectando);
				ts = new ThreadSincronizacion(h,AppSysMobile.WS_CARTERA,0);

				Log.d("SW","Lanza Thread CARTERA");

				executor.execute(ts);
			}

			
			
			if (listaRegistros.get(pos).getTabla().equals("wsSysMobileArticulos"))
			{
				Log.d("SW","Lanza Thread Articulos // paginas:" + listaRegistros.get(pos).getPaginas());
				
				for (int pagina = 1; pagina<= listaRegistros.get(pos).getPaginas(); pagina++)
				{
					pantallaManagerSincronizacion.seteaTxtResultadoArticulos(strConectando);		
					ts = new ThreadSincronizacion(h,AppSysMobile.WS_ARTICULOS,pagina);
					
					Log.d("SW","Ejecuta Thread bajar pagina: " + pagina + " de Articulos");
					
					executor.execute(ts);
				}
			}
			

			if (listaRegistros.get(pos).getTabla().equals("wsSysMobileClientes"))
			{
				for (int pagina = 1; pagina<= listaRegistros.get(pos).getPaginas(); pagina++)
				{
					pantallaManagerSincronizacion.seteatxtResultadoClientes(strConectando);
					ts = new ThreadSincronizacion(h,AppSysMobile.WS_CLIENTES,pagina);
					Log.d("SW","Ejecuta Thread bajar pagina: " + pagina + " de Clientes");
					
					executor.execute(ts);
				}
			}

		}
		}catch (Exception ex)
		{
			Log.d("SW","Error ejecutar proceso obtenerJsonsYProcesar error"+ex.getMessage());

		}

	}

	@Override
	public boolean handleMessage(Message msg) {
		try {
		String resultado ;

		if(tipoLlamada==OBTENER_ENGINE){
			if (msg.arg1 == AppSysMobile.WS_RECIBE_DATOS)
			{
				resultado = (String)  msg.obj;
				jSonRegistrosTablas = resultado;

				AppSysMobile.setRegistrosPaginacion(100);
				parseaCantidadRegistrosCuenta();
				obtenerJsonsYProcesar();
			}
			else
			{
				resultado = (String)  msg.obj;

				pantallaManagerSincronizacion.seteaTxtEstadoConexion(strErrorDeConexionAlWebService + " " + resultado);
				pantallaManagerSincronizacion.seteaBotonSincronizarVisible(true);
				pantallaManagerSincronizacion.seteaPrgEstadoConexionVisible(false);

			}
		}
		if (tipoLlamada == OBTENER_REGISTROS) 
		{
			if (msg.arg1 == AppSysMobile.WS_RECIBE_DATOS)
			{
				resultado = (String)  msg.obj;
				jSonRegistrosTablas = resultado;
				
				AppSysMobile.setRegistrosPaginacion(100);

				parseaCantidadRegistros();
				
				
				paginasClientes = obtienePaginasTabla("wsSysMobileClientes");
				paginasArticulos = obtienePaginasTabla("wsSysMobileArticulos");
				
				obtenerJsonsYProcesar();
			}
			else
			{
				resultado = (String)  msg.obj;

				pantallaManagerSincronizacion.seteaTxtEstadoConexion(strErrorDeConexionAlWebService + " " + resultado);
				pantallaManagerSincronizacion.seteaBotonSincronizarVisible(true);
				pantallaManagerSincronizacion.seteaPrgEstadoConexionVisible(false);
				
			}
		}
		else
		{
			switch (msg.arg1) { // RECIBO DATOS
				case AppSysMobile.WS_RECIBE_DATOS: 
	
					resultado = (String)  msg.obj;
				
					switch (msg.arg2)
					{
						case AppSysMobile.WS_CUENTA:
							jSonCuenta=resultado;
							pantallaManagerSincronizacion.seteatxtResultadoCuenta(strDescargaExitosa);
							pantallaManagerSincronizacion.seteaValorCuenta(true);
							break;



						case AppSysMobile.WS_DESAVENA:
							jSonDesavena=resultado;
							pantallaManagerSincronizacion.seteatxtResultadodesavena(strDescargaExitosa);
							pantallaManagerSincronizacion.seteaValordesavena(true);
							break;
						case AppSysMobile.WS_DESFORMAPAGO:
							jSonDesformapago=resultado;
							pantallaManagerSincronizacion.seteatxtResultadodesformapago(strDescargaExitosa);
							pantallaManagerSincronizacion.seteaValordesformapago(true);
							break;
						case AppSysMobile.WS_DESVOLUMEN:
							jSonDesvolumen=resultado;
							pantallaManagerSincronizacion.seteatxtResultadodesvolumen(strDescargaExitosa);
							pantallaManagerSincronizacion.seteaValordesvolumen(true);
							break;
						case AppSysMobile.WS_DESPRECIOESCALA:
							jSondesprecioescala=resultado;
							pantallaManagerSincronizacion.seteatxtResultadoprecioescala(strDescargaExitosa);
							pantallaManagerSincronizacion.seteaValorpresioescala(true);
							break;
						case AppSysMobile.WS_CARTERA:
							jSonCartera=resultado;
							pantallaManagerSincronizacion.seteatxtResultadoprecioescala(strDescargaExitosa);
							pantallaManagerSincronizacion.seteaValorCartera(true);
							break;


						case AppSysMobile.WS_DEPOSITOS:
							jSonDepositos = resultado;
							//pantallaManagerSincronizacion.seteatxtResultadoRubros(strDescargaExitosa);
							//pantallaManagerSincronizacion.seteaValorChkRubros(true);
							break;
							
						case AppSysMobile.WS_RUBROS:
							jSonRubros = resultado;
							pantallaManagerSincronizacion.seteatxtResultadoRubros(strDescargaExitosa);
							pantallaManagerSincronizacion.seteaValorChkRubros(true);
							break;
		
						case AppSysMobile.WS_VENDEDORES:
							jSonVendedores= resultado;
							pantallaManagerSincronizacion.seteatxtResultadoVendedores(strDescargaExitosa);
							pantallaManagerSincronizacion.seteaValorChkVendedores(true);
							break;
							
						case AppSysMobile.WS_ARTICULOS:
							
							jSonArticulos= resultado;
							alJsonArticulos.add(jSonArticulos);
							
							pantallaManagerSincronizacion.seteaTxtResultadoArticulos(strDescargando + " " + Utilidades.obtienePorcentaje(paginasArticulos,alJsonArticulos.size()) + " %");
							if (paginasArticulos == alJsonArticulos.size()) 
							{
								pantallaManagerSincronizacion.seteaTxtResultadoArticulos(strDescargaExitosa);
								pantallaManagerSincronizacion.seteaValorChkArticulos(true);
							}
							break;
							
						case AppSysMobile.WS_CLIENTES:
							
							jSonClientes= resultado;
							alJsonClientes.add(jSonClientes);
							
							pantallaManagerSincronizacion.seteatxtResultadoClientes(strDescargando + " " + Utilidades.obtienePorcentaje(paginasClientes,alJsonClientes.size()) + " %");
							if (paginasClientes == alJsonClientes.size()) 
							{
								pantallaManagerSincronizacion.seteatxtResultadoClientes(strDescargaExitosa);
								pantallaManagerSincronizacion.seteaValorChkClientes(true);
								
							}
							
							break;
					}
					break;
			
				case AppSysMobile.WS_RECIBE_ERRORES: // RECIBO ERRORES
					
					resultado = (String)  msg.obj;
					huboErrores=true;
					executor.shutdownNow();

					switch (msg.arg2)
					{
						case AppSysMobile.WS_RUBROS:
							Log.d("SW","error rubros");
							pantallaManagerSincronizacion.seteatxtResultadoRubros(strErrorDeComunicacion + " (" + resultado + ")");
							break;
							
						case AppSysMobile.WS_DEPOSITOS:
							Log.d("SW","error depositos");
							//pantallaManagerSincronizacion.seteatxtResultadoRubros(strErrorDeComunicacion + " (" + resultado + ")");
							break;
		
						case AppSysMobile.WS_VENDEDORES:
							Log.d("SW","error vendedores");
							pantallaManagerSincronizacion.seteatxtResultadoVendedores(strErrorDeComunicacion + " (" + resultado + ")");
							break;
							
						case AppSysMobile.WS_ARTICULOS:
							Log.d("SW","error articulos");
							pantallaManagerSincronizacion.seteaTxtResultadoArticulos(strErrorDeComunicacion + " (" + resultado + ")");
							break;
							
						case AppSysMobile.WS_CLIENTES:
							Log.d("SW","error clientes");
							pantallaManagerSincronizacion.seteatxtResultadoClientes(strErrorDeComunicacion + " (" + resultado + ")");
							break;
						case AppSysMobile.WS_CUENTA:
								Log.d("SW","error cuenta");
								pantallaManagerSincronizacion.seteatxtResultadoClientes(strErrorDeComunicacion + " (" + resultado + ")");
								break;
						case AppSysMobile.WS_DESAVENA:
							Log.d("SW","error AVENA");
							pantallaManagerSincronizacion.seteatxtResultadoClientes(strErrorDeComunicacion + " (" + resultado + ")");
							break;
						case AppSysMobile.WS_DESFORMAPAGO:
							Log.d("SW","error FORMA PAGO");
							pantallaManagerSincronizacion.seteatxtResultadoClientes(strErrorDeComunicacion + " (" + resultado + ")");
							break;
						case AppSysMobile.WS_DESVOLUMEN:
							Log.d("SW","error VOLUMEN");
							pantallaManagerSincronizacion.seteatxtResultadoClientes(strErrorDeComunicacion + " (" + resultado + ")");
							break;
						case AppSysMobile.WS_DESPRECIOESCALA:
							Log.d("SW","error PRECIO ESCALA");
							pantallaManagerSincronizacion.seteatxtResultadoClientes(strErrorDeComunicacion + " (" + resultado + ")");
							break;
						case AppSysMobile.WS_CARTERA:
							Log.d("SW","error CARTERA");
							pantallaManagerSincronizacion.seteatxtResultadoCartera(strErrorDeComunicacion + " (" + resultado + ")");
							break;
					}
				
				break;
			}
			
			
			completadas++;
			
			Log.d("SW","completadas:" + completadas + " de " + executor.getTaskCount());
			if ((executor.getTaskCount()== completadas) || huboErrores)
			{
				if (huboErrores)
				{
					// NO PARSEO NADA. ASI QUE PERMITO QUE CIERRE LA PANTALLA
					pantallaManagerSincronizacion.setVisibleBtnCerrarSincronizacion(true);
					pantallaManagerSincronizacion.seteaImgSincronizarVisible(true);
					pantallaManagerSincronizacion.seteaProgressBarVisible(false);
					
				}	
				else
					{
						// PROCESO LOS JSON'S RECIBIDOS
						ProcesaJson procesaJson = new ProcesaJson(this.a);
						
						procesaJson.setPantallaManager(pantallaManagerSincronizacion);
						
						procesaJson.setalJsonClientes(alJsonClientes);
						procesaJson.setalJsonArticulos(alJsonArticulos);
						
						procesaJson.setjSonRubros(jSonRubros);
						procesaJson.setjSonVendedores(jSonVendedores);
						procesaJson.setjSonDepositos(jSonDepositos);
						procesaJson.setjSonCuenta(jSonCuenta);

						procesaJson.setjSonDesavena(jSonDesavena);
						procesaJson.setjSonDesformapago(jSonDesformapago);
						procesaJson.setjSonDesvolumen(jSonDesvolumen);
						procesaJson.setjSondesprecioescala(jSondesprecioescala);
						procesaJson.setjSonCartera(jSonCartera);

						
						procesaJson.procesarJson();
	
					}
			}

		}
		
		
		return false;
		}catch (Exception ex){
			Log.d("SW","Error ejecutar proceso error"+ex.getMessage());
			return false;

		}

	}
	public void setPantallaManager(PantallaManagerSincronizacion pantallaManagerSincronizacion) {
		this.pantallaManagerSincronizacion = pantallaManagerSincronizacion;
	}
	
	public boolean huboErroes()
	{
		return this.huboErrores;
	}
	public int getCantVendedores() {
		return cantVendedores;
	}
	public void setCantVendedores(int cantVendedores) {
		this.cantVendedores = cantVendedores;
	}
	
	public void parseaCantidadRegistros()
	{
		listaRegistros = new ArrayList<Registro>();
		listaRegistros.clear();
		
		JSONArray arrayJson;
		JSONObject jsObject;
		
		
		try
		{
			arrayJson = new JSONArray(jSonRegistrosTablas);
			for (int x = 0; x < arrayJson.length() ;x++)
			{
				jsObject = arrayJson.getJSONObject(x);
				
				Registro registro = new Registro();
				registro.setTabla(jsObject.getString("tabla"));
				
				registro.setCantidadRegistros(jsObject.getLong("cantidadRegistros"));
				registro.setPaginas((int) (registro.getCantidadRegistros() / AppSysMobile.getRegistrosPaginacion()));

				if ((registro.getCantidadRegistros() % AppSysMobile.getRegistrosPaginacion()) > 0)
				registro.setPaginas(registro.getPaginas() + 1);
				
				listaRegistros.add(registro);
			
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}		
		
	}

	public void parseaCantidadRegistrosCuenta()
	{
		listaRegistros = new ArrayList<Registro>();
		listaRegistros.clear();
		try
		{

			Registro registro = new Registro();
			registro.setCantidadRegistros(1);
			registro.setPaginas(1);
			registro.setTabla("Cuenta");
			listaRegistros.add(registro);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}




	
	private int obtienePaginasTabla(String tabla)
	{
	
		for (int pos = 0; pos < listaRegistros.size() ; pos++)
		{
			if (listaRegistros.get(pos).getTabla().equals(tabla))
			{
				return listaRegistros.get(pos).getPaginas();
			}
			
		}

		return 0;
	}

}
