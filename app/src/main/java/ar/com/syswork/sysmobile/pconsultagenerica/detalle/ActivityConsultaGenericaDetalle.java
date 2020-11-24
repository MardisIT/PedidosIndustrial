package ar.com.syswork.sysmobile.pconsultagenerica.detalle;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.Tracking.JavaRestClient;
import ar.com.syswork.sysmobile.Tracking.User;
import ar.com.syswork.sysmobile.daos.DaoCliente;
import ar.com.syswork.sysmobile.daos.DaoConfiguracion;
import ar.com.syswork.sysmobile.daos.DaoToken;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.entities.ConfiguracionDB;
import ar.com.syswork.sysmobile.entities.ItemClaveValor;
import ar.com.syswork.sysmobile.entities.Pagos;
import ar.com.syswork.sysmobile.entities.Token;
import ar.com.syswork.sysmobile.industrial.MapsActivity;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class ActivityConsultaGenericaDetalle extends AppCompatActivity {
	public EditText edcodigol1,edcodigol2,ednombrelocal,ednombres,edacelular,edcedula,edapellidos,eddireccion,edlocalidad,edruta,edtelefono,edreferencia,edemail,edpropietario,edlongitud,edlatitude;
	public CheckBox ediva;
	private DaoToken daoToken;
	private static LocationManager locationManager;
	double longitudeGPS, latitudeGPS;
	private AppSysMobile app;
	private DataManager dataManager;
	private String codigoVendedor;
	private DaoConfiguracion daoConfiguracion;
	private DaoCliente daoCliente;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_generica_detalle);
		app = (AppSysMobile) this.getApplication();
		codigoVendedor = app.getVendedorLogueado();
		dataManager = app.getDataManager();
		daoCliente = dataManager.getDaoCliente();
		daoToken=dataManager.getDaoToken();
		daoConfiguracion=dataManager.getDaoConfiguracion();
		edcodigol1 = (EditText) this.findViewById(R.id.edcodigol1);
		edcodigol2 = (EditText) this.findViewById(R.id.edcodigol2);
		ednombrelocal = (EditText) this.findViewById(R.id.ednombrelocal);
		eddireccion = (EditText) this.findViewById(R.id.eddireccion);
		edlocalidad = (EditText) this.findViewById(R.id.edlocalidad);
		edruta = (EditText) this.findViewById(R.id.edruta);
		edtelefono = (EditText) this.findViewById(R.id.edtelefono);
		edemail = (EditText) this.findViewById(R.id.edemail);
		edpropietario = (EditText) this.findViewById(R.id.edpropietario);
		edlongitud = (EditText) this.findViewById(R.id.edlongitud);
		edlatitude = (EditText) this.findViewById(R.id.edlatitude);
		ediva = (CheckBox) this.findViewById(R.id.ediva);
		ValidToken();
		edreferencia = (EditText) this.findViewById(R.id.edreferencia);
		ednombres = (EditText) this.findViewById(R.id.ednombres);
		edapellidos = (EditText) this.findViewById(R.id.edapellidos);
		edcedula = (EditText) this.findViewById(R.id.edcedula);
		edacelular = (EditText) this.findViewById(R.id.edacelular);




		locationManager = (LocationManager)  getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		// Creo una Lista
		List<ItemClaveValor> lista = new ArrayList<ItemClaveValor>();
		
		// Creo el Adapter
		AdapterConsultaGenericaDetalle adapter = new AdapterConsultaGenericaDetalle(this, lista);
		
		// Seteo el Adapter
		ListView lv = (ListView) this.findViewById(R.id.lstDetalleConsulta);
		LinearLayout _LinearLayout=(LinearLayout) this.findViewById(R.id.lstcliente) ;
		lv.setAdapter(adapter);
		lv.setDividerHeight(0);

		
		// Lleno la lista
		AppSysMobile app = (AppSysMobile) getApplication();
		List<ItemClaveValor> listaTmp = new ArrayList<ItemClaveValor>();
		listaTmp = app.getListaClaveValor();
		Iterator<ItemClaveValor> i = listaTmp.iterator();
		while (i.hasNext())
		{
			lista.add(i.next());
		}
		if(lista.size()>0 && lista.size()>13){
			edcodigol1.setText(lista.get(0).getValorString());
			edcodigol2.setText(lista.get(1).getValorString());
			ednombrelocal.setText(lista.get(2).getValorString());
			eddireccion.setText(lista.get(3).getValorString());
			edlocalidad.setText(lista.get(4).getValorString());
			edruta.setText(lista.get(5).getValorString());
			edtelefono.setText(lista.get(7).getValorString());
			edemail.setText(lista.get(8).getValorString());
			edpropietario.setText(lista.get(9).getValorString());
			edlongitud.setText(lista.get(10).getValorString());
			edlatitude.setText(lista.get(11).getValorString());
			edreferencia.setText(lista.get(12).getValorString());
			ednombres.setText(lista.get(13).getValorString());
			edapellidos.setText(lista.get(14).getValorString());
			edcedula.setText(lista.get(15).getValorString());
			edacelular.setText(lista.get(16).getValorString());
			if(lista.get(6).getValorString()!="0")
				ediva.setChecked(true);
			for (ConfiguracionDB da : daoConfiguracion.getAll("")
			) {
				edruta.setText(da.getFormaBusqueda());
			}
			lv.setVisibility(View.GONE);
			_LinearLayout.setVisibility(View.VISIBLE);

		}else{
			lv.setVisibility(View.VISIBLE);
			_LinearLayout.setVisibility(View.GONE);
		}

		if(!edcodigol1.getText().toString().equals("")){
			edcodigol1.setEnabled(false);
			edcodigol2.setEnabled(false);
		}
		adapter.notifyDataSetChanged();
		
		// Seteo la Imagen del detalle
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		int icono_detalle = extras.getInt("icono_detalle");
		String titulo = extras.getString("titulo");
		this.setTitle(titulo);
		
		ImageView iv = (ImageView) findViewById(R.id.imgConsultaGenerica);
		iv.setImageResource(icono_detalle);
		
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menusave, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
		{

			case R.id.mnu_grabar:
					guardarcliente();
				return true;
			case R.id.mnu_ubicacion:
				toggleGPSUpdates();
				return true;
			case R.id.mnu_cancelar:
				finish();
			default:
				return super.onOptionsItemSelected(item);
		}
	}


public void guardarcliente(){
	try {
		Cliente cliente = new Cliente();
		cliente.setCodigo(edcodigol1.getText().toString());
		cliente.setCodigoOpcional(edcodigol2.getText().toString());
		cliente.setRazonSocial(ednombrelocal.getText().toString());
		cliente.setCalleNroPisoDpto(eddireccion.getText().toString());
		cliente.setLocalidad(edlocalidad.getText().toString());
		cliente.setCuit(edruta.getText().toString());
		cliente.setIva((byte) (ediva.isChecked() == true ? 1 : 0));
		cliente.setClaseDePrecio((byte) 0);
		cliente.setPorcDto(0);
		cliente.setCpteDefault("");
		cliente.setIdVendedor(codigoVendedor);
		cliente.setTelefono(edtelefono.getText().toString());
		cliente.setLatitudeBranch(edlatitude.getText().toString());
		cliente.setLenghtBranch(edlongitud.getText().toString());
		cliente.setMail(edemail.getText().toString());
		cliente.setPropietario(edpropietario.getText().toString());
		cliente.setReference(edreferencia.getText().toString());
		cliente.setNombre(ednombres.getText().toString());
		cliente.setApellido(edapellidos.getText().toString());
		cliente.setCedula(edcedula.getText().toString());
		cliente.setCelular(edacelular.getText().toString());
		cliente.setParroquia("GUAYAQUIL");
		cliente.setCanton("GUAYAQUIL");
		cliente.setProvincia("GUAYAS");

		cliente.setImeI_ID(obterImeid(this));

		cliente.setEstadoenvio("P");
			if(!edruta.getText().toString().equals("")&&!ednombres.getText().toString().equals("")&&!edapellidos.getText().toString().equals("")
				&&!edcodigol1.getText().toString().equals("")&&!edcodigol2.getText().toString().equals("")&&!ednombrelocal.getText().toString().equals("")
				&&!edcedula.getText().toString().equals("")&&!edlatitude.getText().toString().equals("")&&!edlongitud.getText().toString().equals("")
				&&!eddireccion.getText().toString().equals("")&&!edreferencia.getText().toString().equals("")&&!edacelular.getText().toString().equals("")) {


			Cliente _auxC = daoCliente.getByKey(cliente.getCodigo());
			if (_auxC != null)
				Toast.makeText(app, "Ya existe un cliente con el código ingresado", Toast.LENGTH_SHORT).show();
			else {
				if(cliente.getPropietario().equals("")){
					cliente.setPropietario(cliente.getApellido() + " " + cliente.getNombre());
				}

				daoCliente.save(cliente);
				List<Cliente> lstcClientes = new ArrayList<>();
				lstcClientes = daoCliente.getAll(" estadoenvio = 'P'");
				String token = "";
				for (Token a : daoToken.getAll("")
				) {
					token = a.getToken();
				}
				for (Cliente c : lstcClientes
				) {
					envioclientenuevo fetchJsonTask = new envioclientenuevo(this);
					fetchJsonTask.execute(obtieneJsonCliente(c.getCodigo()), token);
				}
				finish();
			}

		}else {
			Toast.makeText(app, "Completar campos obligatorios", Toast.LENGTH_SHORT).show();
		}



	}catch (Exception e){
		Toast.makeText(app, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
	}

}
	private String obtieneJsonCliente(String codigo) {
		String jSonCliente1 = "";
		String aconnt="0";
		for (ConfiguracionDB da : daoConfiguracion.getAll("")
		) {
			aconnt=da.getId_cuenta();
		}
		JSONObject jsonCliente;
				jsonCliente= new JSONObject();
				try {
					jsonCliente.put("account", Integer.valueOf(aconnt));
					jsonCliente.put("iduser", "1");
					jsonCliente.put("option", 1);
					jsonCliente.put("_route", obtieneJsonDetalleDeRuta(codigo));
				} catch (JSONException e) {
					e.printStackTrace();
				}

		return jsonCliente.toString();
	}
	private void ValidToken(){
		JavaRestClient tarea = new JavaRestClient(this);
		String date="";
		for (Token a:daoToken.getAll("")
		) {
			date= a.getFecha();
		}

		try {
			Date datetoken=new SimpleDateFormat("dd-MM-yyyy").parse(date);
			Date dateNow = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(datetoken); // Configuramos la fecha que se recibe
			calendar.add(Calendar.DAY_OF_YEAR, 5);  // numero de días a añadir, o restar en caso de días<0
			Date ExpToke=calendar.getTime();
			if(dateNow.compareTo(ExpToke)>0 ){
				User _user =new User();
				tarea.getToken2(_user,this);
			}

		}catch (Exception e){
			User _user =new User();
			tarea.getToken2(_user,this);

		}
	}
	public JSONObject obtieneJsonDetalleDeRuta(String codigo){
		Cliente cliente;
		JSONObject jsonCliente;
		cliente = daoCliente.getByKey(codigo);
		jsonCliente= new JSONObject();
		if (cliente!= null) {
				try {
					jsonCliente.put("Codigo_Encuesta", cliente.getCodigo());
					jsonCliente.put("PT_indice", cliente.getCodigo());
					jsonCliente.put("Tipo", cliente.getLocalidad());
					jsonCliente.put("local", cliente.getRazonSocial());
					jsonCliente.put("Dirección", cliente.getCalleNroPisoDpto());
					jsonCliente.put("Referencia", cliente.getReference());
					jsonCliente.put("Nombres", cliente.getNombre());
					jsonCliente.put("Apellidos", cliente.getApellido());
					jsonCliente.put("Mail", cliente.getMail().equals("")?"NA":cliente.getMail().equals(""));
					jsonCliente.put("Cédula", cliente.getCedula());
					jsonCliente.put("Celular", cliente.getCelular());
					jsonCliente.put("Telefono", cliente.getTelefono().equals("")?cliente.getCelular():cliente.getTelefono().equals(""));
					jsonCliente.put("Latitud", cliente.getLatitudeBranch());
					jsonCliente.put("Longitud", cliente.getLenghtBranch());
					jsonCliente.put("Provincia", "GUAYAS");
					jsonCliente.put("Canton", "GUAYAQUIL");
					jsonCliente.put("Parroquia", "GUAYAQUIL");
					jsonCliente.put("CLUSTER", app.getVendedorLogueado().toString());
					jsonCliente.put("Estado", "Activo");
					jsonCliente.put("RUTA", cliente.getCuit());
					jsonCliente.put("IMEI", cliente.getImeI_ID());







				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		return jsonCliente;
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
	@SuppressLint("MissingPermission")
	public String  obterImeid(Activity a) {

		try {
			TelephonyManager tm = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
			TelephonyManager mTelephony = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
			String simSerialNo="";
			String  myIMEI = mTelephony.getDeviceId();

			if (myIMEI == null) {
				SubscriptionManager subsManager = (SubscriptionManager) a.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

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
		}catch (Exception e){
			Log.d("Traking Imei: ",e.getMessage());
			return "";
		}

	}
	private final LocationListener locationListenerGPS = new LocationListener() {
		public void onLocationChanged(Location location) {
			longitudeGPS = location.getLongitude();
			latitudeGPS = location.getLatitude();

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					edlongitud.setText(longitudeGPS + "");
					edlatitude.setText(latitudeGPS + "");
					Toast.makeText(getApplication(), "GPS Provider update", Toast.LENGTH_SHORT).show();

				}
			});
		}
		@Override
		public void onStatusChanged(String s, int i, Bundle bundle) {
		}

		@Override
		public void onProviderEnabled(String s) {
		}
		@Override
		public void onProviderDisabled(String s) {
		}
	};
	private boolean checkLocation() {
		if (!isLocationEnabled())
			showAlert();
		return isLocationEnabled();
	}
	private boolean isLocationEnabled() {
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
				locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}
	private void showAlert() {
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Enable Location")
				.setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
						"usa esta app")
				.setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface, int paramInt) {
						Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(myIntent);
					}
				})
				.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface, int paramInt) {
					}
				});
		dialog.show();
	}
	@SuppressLint("MissingPermission")
	public void toggleGPSUpdates() {
		if (!checkLocation())
			return;
		locationManager.requestLocationUpdates(
		LocationManager.GPS_PROVIDER, 2 * 20 * 1000, 10, locationListenerGPS);


	}
}
