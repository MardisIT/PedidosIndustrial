package ar.com.syswork.sysmobile.shared;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

//import com.evernote.android.job.JobManager;
//import com.evernote.android.job.JobManagerCreateException;

import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.ItemClaveValor;
import ar.com.syswork.sysmobile.gps.GpsReader;
import ar.com.syswork.sysmobile.industrial.DemoJobCreator;

public class AppSysMobile extends Application {
	
	// Utilizado para definir los Orignenes de las consultas
	public final static int DESDE_MENU_PRINCIPAL = 0;
	public final static int DESDE_CARGA_DE_PEDIDOS = 1;
	public final static int DESDE_CONSULTA_DE_CUENTA_CORRIENTE = 2;
	public static final int DESDE_SCANNER = 3;
	public static final int DESDE_CARGA_INVENTARIO = 4;
	public static final int DESDE_CARGA_VISITAS = 5;

	
	// Utilizado para opciones del menu principal
	public final static int OPC_MENU_PRINCIPAL_SINCRONIZAR = 0;
	public final static int OPC_MENU_PRINCIPAL_CARGA_PEDIDOS = 1;
	public final static int OPC_MENU_PRINCIPAL_ENVIAR_PENDIENTES = 2;
	public final static int OPC_MENU_PRINCIPAL_CONSULTA_ARTICULOS = 3;
	public final static int OPC_MENU_PRINCIPAL_CONSULTA_CLIENTES = 4;
	public final static int OPC_MENU_PRINCIPAL_CUENTA_CORRIENTE = 5;
	public final static int OPC_MENU_PRINCIPAL_CONFIGURACION = 6;
	public final static int OPC_MENU_PRINCIPAL_REPORTE= 7;
	public final static int OPC_MENU_PRINCIPAL_INVENTARIO= 8;
	public final static int OPC_MENU_PRINCIPAL_VISITAS= 9;

	public final static int WS_VENDEDORES = 1;
	public final static int WS_RUBROS = 2;
	public final static int WS_CLIENTES = 3;
	public final static int WS_ARTICULOS = 4;
	public final static int WS_DEPOSITOS = 5;
	public final static int WS_REGISTROS = 6;
	public  final  static  int  WS_CUENTA=7;
	public  final  static  int  WS_DESAVENA=8;
	public  final  static  int  WS_DESFORMAPAGO=9;
	public  final  static  int  WS_DESVOLUMEN=10;
	public  final  static  int  WS_DESPRECIOESCALA=11;
	public  final  static  int  WS_CARTERA=12;

	public final static int WS_RECIBE_DATOS = 1;
	public final static int WS_RECIBE_ERRORES = 2;
	
	private static String rutaWebService;
	private static int puertoWebService;
	private static int timeOutSockets;		// En Segundos
	private static int registrosPaginacion;
	private static boolean solicitaClaseDePrecio;
	private static String clasesDePrecioHabilitadas;
	private static boolean solicitaIncluirEnReparto;
	private static AppSysMobile singleton;
	private  static Activity Actividad;
	
	private static final int DEFAULT_TIMEOUT_SOCKETS = 30;
	
	// LLAMADAS A LAS RUTAS DEL WEBSERVICE
	
	//GETTERS
	public final static String WS_ACCESO_VENDEDORES = "/getVendedores";
	public final static String WS_ACCESO_RUBROS  = "/getRubros";
	public final static String WS_ACCESO_CLIENTES = "https://dyvenpro.azurewebsites.net/api/Branch/RouteBranch?";
	public final static String WS_ACCESO_ARTICULOS = "/getArticulos";
	public final static String WS_CONSULTA_CTA_CTE = "/getCtaCte";
	public final static String WS_ACCESO_DEPOSITOS = "/getDepositos";
	public final static String WS_CONSULTA_STOCK = "/getStock";
	public final static String WS_CONSULTA_CANTIDAD_REGISTOS = "/getRegistros";


	public final static String WS_CONSULTA_DESAVENA = "/GetDescuentoAvenas";
	public final static String WS_CONSULTA_DESFORMAPAGO = "/GetDescuentoFormapagos";
	public final static String WS_CONSULTA_DESVOLUMEN = "/GetDescuentoVolumens";
	public final static String WS_CONSULTA_DESPRECIOESCALA = "/GetPrecioEscalas";
	public  final  static  String WS_CONSULTA_CARTERA="/GetCartera";


	public  static String WS_IMAIL="";
	public  static  String WS_ESTADOSIN="";


	//GETTERS ENGIENE
	public  final  static  String WS_CARGARCIENTAS="https://dyvenpro.azurewebsites.net/api/Branch/Campaign";
	public final static String WS_LOCALES_RUTA = "http://geomardis6728.cloudapp.net/servicios/api/Task?";
	//SETTERS
	public final static String WS_ACCESO_GRABAR_PEDIDOS = "https://dyvenpro.azurewebsites.net/api/Order/PEDIDOS";
	public final static String WS_ACCESO_GRABAR_PAGOS = "http://geomardis6728.cloudapp.net/API/api/PAGOS";
	public final static String WS_ACCESO_GRABAR_INVENTARIO = "https://dyvenpro.azurewebsites.net/api/Order/inventario";
	public final static String WS_ACCESO_GRABAR_VISITAS = "http://geomardis6728.cloudapp.net/API/api/visitasUios";
	public final static String WS_ACCESO_GRABA_PEDIDO = "/setPedido";
	
	public final static String INTENT_FILTER_CAMBIOS_LISTA_PEDIDOS = "INTENT_FILTER_CAMBIOS_LISTA_PEDIDOS";
	
	private DataManager dm;
	private GpsReader gpsReader;
	
	private String vendedorLogueado;
	
	private List<ItemClaveValor> listaClaveValor = new ArrayList<ItemClaveValor>();

	public DemoJobCreator DemoJobCreator;
	public AppSysMobile(){
		super();
	}
	@Override
	public void onCreate(){
		super.onCreate();
	}
	
	public DataManager getDataManager() {
		return dm;
	}
	public void setDataManager(DataManager dm) {
		this.dm = dm;
	}
	
	public String getVendedorLogueado() {
		return vendedorLogueado;
	}
	public void setVendedorLogueado(String vendedorLogueado) {
		this.vendedorLogueado = vendedorLogueado;
	}

	public List<ItemClaveValor> getListaClaveValor() {
		return listaClaveValor;
	}
	public void setListaClaveValor(List<ItemClaveValor> listaClaveValor) {
		this.listaClaveValor = listaClaveValor;
	}
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
	@SuppressLint("MissingPermission")
	public String  obterImeid() {
		final String androidIdName = Settings.Secure.ANDROID_ID;

		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String simSerialNo="";
		String  myIMEI = mTelephony.getDeviceId();

		if (myIMEI == null) {
			SubscriptionManager subsManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

			List<SubscriptionInfo> subsList = subsManager.getActiveSubscriptionInfoList();

			if (subsList!=null) {
				for (SubscriptionInfo subsInfo : subsList) {
					if (subsInfo != null) {
						simSerialNo  = subsInfo.getIccId();
					}
				}

			}

				myIMEI=simSerialNo;
		}


     /* String  deviceId = new PropertyManager(Collect.getInstance().getApplicationContext())
                .getSingularProperty(PropertyManager.withUri(PropertyManager.PROPMGR_DEVICE_ID));*/
		return myIMEI;

	}
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
	public void iniciaConfiguracion()
	{

		SharedPreferences prefs = getSharedPreferences("CONFIGURACION_WS",Context.MODE_PRIVATE);
		
		//String ruta = prefs.getString("rutaAccesoWebService", "http://serviciopedidos.azurewebsites.net/api");
		String ruta = prefs.getString("rutaAccesoWebService", "https://dyvenpro.azurewebsites.net/api/Order");

		int puerto = prefs.getInt("puertoWebService", 80);
		int timeOutSockets = prefs.getInt("timeOut", DEFAULT_TIMEOUT_SOCKETS);
		boolean solicitaClaseDePrecio = prefs.getBoolean("solicitaClaseDePrecio", true);
		String clasesDePrecioHabilitadas = prefs.getString("clasesDePrecioHabilitadas", "1");
		boolean solicitaIncluirEnReparto = prefs.getBoolean("solicitaIncluirEnReparto", true);
		WS_IMAIL=obterImeid();
		Editor editor = prefs.edit();
		
		editor.putString("rutaAccesoWebService", ruta);
		editor.putInt("puertoWebService", puerto);
		editor.putInt("timeOut", timeOutSockets);
		editor.putBoolean("solicitaClaseDePrecio", solicitaClaseDePrecio);
		editor.putString("clasesDePrecioHabilitadas", clasesDePrecioHabilitadas);
		editor.putBoolean("solicitaIncluirEnReparto", solicitaIncluirEnReparto);
		
		editor.commit();
		//singleton = this;
		//Actividad=(Activity) this.getApplicationContext();

		/*try {
			JobManager
					.create(this)
					.addJobCreator(DemoJobCreator);
		} catch (JobManagerCreateException e) {

		}*/
		
		setRutaWebService(ruta);
		setPuertoWebService(puerto);
		setTimeOut(timeOutSockets);
		setSolicitaClaseDePrecio(solicitaClaseDePrecio);
		setClasesDePrecioHabilitadas(clasesDePrecioHabilitadas);
		setSolicitaIncluirEnReparto(solicitaIncluirEnReparto);
	}
	public static AppSysMobile getInstance() {
		return singleton;
	}

	public static Activity getActividad() {
		return Actividad;
	}
	
	public static String getRutaWebService() 
	{
		return rutaWebService;
	}
	public static void setRutaWebService(String rutaWebService) 
	{
		AppSysMobile.rutaWebService = rutaWebService;
	}

	public static boolean getSolicitaClaseDePrecio() 
	{
		return solicitaClaseDePrecio;
	}
	public static boolean getSolicitaIncluirEnReparto() 
	{
		return solicitaIncluirEnReparto;
	}
	public static void setSolicitaClaseDePrecio(boolean solicitaClaseDePrecio) 
	{
		AppSysMobile.solicitaClaseDePrecio = solicitaClaseDePrecio;
	}
	public static void setClasesDePrecioHabilitadas(String clasesDePrecioHabilitadas) 
	{
		AppSysMobile.clasesDePrecioHabilitadas = clasesDePrecioHabilitadas;
	}
	
	public static void setSolicitaIncluirEnReparto(boolean solicitaIncluirEnReparto) 
	{
		AppSysMobile.solicitaIncluirEnReparto = solicitaIncluirEnReparto;
	}

	
	public static String getClasesDePrecioHabilitadas() 
	{
		return clasesDePrecioHabilitadas;
	}
	
	public static int getRegistrosPaginacion() {
		return registrosPaginacion;
	}
	public static void setRegistrosPaginacion(int registrosPaginacion) {
		AppSysMobile.registrosPaginacion = registrosPaginacion;
	}
	
	public static int getPuertoWebService() {
		return puertoWebService;
	}
	public static void setPuertoWebService(int puertoWebService) {
		AppSysMobile.puertoWebService = puertoWebService;
	}
	public static int getTimeOutSockets() {
		return timeOutSockets ;
	}
	public static void setTimeOut(int timeOut) {
		AppSysMobile.timeOutSockets = timeOut;
	}
	public GpsReader getGpsReader() {
		return gpsReader;
	}
	public void setGpsReader(GpsReader gpsReader) {
		this.gpsReader = gpsReader;
	}
}