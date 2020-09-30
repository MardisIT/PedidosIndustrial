package ar.com.syswork.sysmobile.psincronizar;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoCuenta;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Capania;
import ar.com.syswork.sysmobile.entities.CuentaSession;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class PantallaManagerSincronizacion {
	
	private ActivitySincronizar a;
	private ListenerSincronizacion listenerSincronizacion;
	private Dialog dialog;

	private CheckBox chkVendedores;
	private CheckBox chkRubros;
	private CheckBox chkArticulos;
	private CheckBox chkClientes;
	private CheckBox chkCuenta;
	private CheckBox chkDesAvena;
	private CheckBox chkDesFormaPago;
	private CheckBox chkDesVolumen;
	private CheckBox chkDesPrecioEscala;
	private CheckBox chkDesCartera;






	private TextView txtResultadoVendedores;
	private TextView txtResultadoRubros;
	private TextView txtResultadoArticulos;
	private TextView txtResultadoClientes;
	private TextView txtResultadoCuenta;

	private TextView txtResultadodesavena;
	private TextView txtResultadodeformapago;
	private TextView txtResultadodesvolumen;
	private TextView txtResultadodesprecioescala;
	private TextView txtResultadoCartera;

	private ProgressBar prgEstadoConexion;
	private TextView txtEstadoConexion;
	private Button btnSincronizar;

	private  String imei_id;

	private ImageView imgSincronizar;
	private ProgressBar progresBar1;
	private Button btnCerrarSincronizacion;
	private Button btncargarlocalesCL;
	private Spinner cmbcampaniaCL;
	private AppSysMobile app;
	private DataManager dm;
	private DaoCuenta daoCuenta;
	final CuentaSession objcuentaSession= new CuentaSession();
	public PantallaManagerSincronizacion(ActivitySincronizar a, ListenerSincronizacion listenerSincronizacion)
	{
		this.a = a;
		
		this.listenerSincronizacion= listenerSincronizacion;

		prgEstadoConexion= (ProgressBar) this.a.findViewById(R.id.prgbEstadoConexion);
		txtEstadoConexion = (TextView ) this.a.findViewById(R.id.txtEstadoConexion);
		btnSincronizar = (Button) a.findViewById(R.id.btnSincronizar);
		btncargarlocalesCL = (Button) a.findViewById(R.id.btncargarlocalesCL);
		cmbcampaniaCL=(Spinner) a.findViewById(R.id.cmbcampaniaCL);
		app = (AppSysMobile) a.getApplication();
		dm = app.getDataManager();
		daoCuenta=dm.getDaoCuenta();
		List<Capania> listOBJ= daoCuenta.getAll("");
		ArrayAdapter<Capania> adaptador;
		adaptador = new ArrayAdapter<Capania>(a, R.layout.support_simple_spinner_dropdown_item, listOBJ);

		cmbcampaniaCL.setAdapter(adaptador);
		cmbcampaniaCL.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
											   View v, int position, long id) {
						Capania currentLead = (Capania) parent.getItemAtPosition(position);
						objcuentaSession.setCu_ID("");
						objcuentaSession.setCu_idAccount("");
						objcuentaSession.setCu_AccountNombre("");
						objcuentaSession.setCu_idcampania("");
						objcuentaSession.setCu_CampaniaNombre("");

						objcuentaSession.setCu_ID(currentLead.getID());
						objcuentaSession.setCu_idAccount(currentLead.getIdAccount());
						objcuentaSession.setCu_AccountNombre(currentLead.getAccountNombre());
						objcuentaSession.setCu_idcampania(currentLead.getIdCampania());
						objcuentaSession.setCu_CampaniaNombre(currentLead.getCampaniaNombre());
						objcuentaSession.setCu_imail(getImei_id());

					}

					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
		creaDialogoSincronizacion();
		
	}
	
	public void seteaListeners()
	{
		listenerSincronizacion.seteaListener(btnSincronizar);
		listenerSincronizacion.seteaListener(btncargarlocalesCL);

	}

	public String getImei_id() {
		return imei_id;
	}

	public void setImei_id(String imei_id) {
		this.imei_id = imei_id;
	}

	public void creaDialogoSincronizacion()
	{
		dialog = new Dialog(a);
		dialog.setContentView(R.layout.dialog_sincronizar);
		dialog.setTitle(a.getString(R.string.sincronizando));
		dialog.setCancelable(false);
		
		chkVendedores = (CheckBox) dialog.findViewById(R.id.chkVendedores);
		chkRubros = (CheckBox) dialog.findViewById(R.id.chkRubros);
		chkArticulos = (CheckBox) dialog.findViewById(R.id.chkArticulos);
		chkClientes = (CheckBox) dialog.findViewById(R.id.chkClientes);
		chkCuenta= (CheckBox) dialog.findViewById(R.id.chkCuenta);

		chkDesAvena= (CheckBox) dialog.findViewById(R.id.chkdesavena);
		chkDesFormaPago= (CheckBox) dialog.findViewById(R.id.chkdesformapago);
		chkDesVolumen= (CheckBox) dialog.findViewById(R.id.chkdesvolumen);
		chkDesPrecioEscala= (CheckBox) dialog.findViewById(R.id.chkdesprecioescala);
		chkDesCartera= (CheckBox) dialog.findViewById(R.id.chkcartera);

		txtResultadoVendedores = (TextView ) dialog.findViewById(R.id.txtResultadoVendedores);
		txtResultadoRubros = (TextView ) dialog.findViewById(R.id.txtResultadoRubros);
		txtResultadoArticulos = (TextView) dialog.findViewById(R.id.txtResultadoArticulos);
		txtResultadoClientes = (TextView) dialog.findViewById(R.id.txtResultadoClientes);
		txtResultadoCuenta = (TextView) dialog.findViewById(R.id.txtResultadoCuenta);

		txtResultadodesavena = (TextView) dialog.findViewById(R.id.txtResultadoDesAvena);
		txtResultadodeformapago = (TextView) dialog.findViewById(R.id.txtResultadoDesformapago);
		txtResultadodesprecioescala = (TextView) dialog.findViewById(R.id.txtResultadoPrecioescala);
		txtResultadodesvolumen = (TextView) dialog.findViewById(R.id.txtResultadoDesvolumen);
		txtResultadoCartera= (TextView) dialog.findViewById(R.id.txtResultadoCartera);
		
		imgSincronizar = (ImageView ) dialog.findViewById(R.id.imgSincronizar);
		progresBar1 = (ProgressBar ) dialog.findViewById(R.id.progressBar1);
		
		btnCerrarSincronizacion = (Button) dialog.findViewById(R.id.btnCerrarSincronizacion);
		
		listenerSincronizacion.seteaListener(btnCerrarSincronizacion);
		listenerSincronizacion.seteaListener(btncargarlocalesCL);
		cmbcampaniaCL.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
											   View v, int position, long id) {
						Capania currentLead = (Capania) parent.getItemAtPosition(position);
						objcuentaSession.setCu_ID("");
						objcuentaSession.setCu_idAccount("");
						objcuentaSession.setCu_AccountNombre("");
						objcuentaSession.setCu_idcampania("");
						objcuentaSession.setCu_CampaniaNombre("");

						objcuentaSession.setCu_ID(currentLead.getID());
						objcuentaSession.setCu_idAccount(currentLead.getIdAccount());
						objcuentaSession.setCu_AccountNombre(currentLead.getAccountNombre());
						objcuentaSession.setCu_idcampania(currentLead.getIdCampania());
						objcuentaSession.setCu_CampaniaNombre(currentLead.getCampaniaNombre());
						objcuentaSession.setCu_imail(getImei_id());

					}

					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
		
	}
	
	public void muestraDialogoSincronizacion()
	{
		try {


		seteaValorChkVendedores(false) ;
		seteaValorChkRubros(false);
		seteaValorChkArticulos(false) ;
		seteaValorChkClientes(false) ;
		seteaValorCuenta(false);

		seteaValordesavena(false);
		seteaValordesformapago(false);
		seteaValordesvolumen(false);
		seteaValorpresioescala(false);
		seteaValorCartera(false);

		seteatxtResultadoVendedores("");
		seteatxtResultadoRubros("");
		seteatxtResultadoClientes("");
		seteaTxtResultadoArticulos("");
		seteatxtResultadoCuenta("");

		seteatxtResultadodesavena("");
		seteatxtResultadodesformapago("");
		seteatxtResultadodesvolumen("");
		seteatxtResultadoprecioescala("");
		seteatxtResultadoCartera("");

		seteaImgSincronizarVisible(false);
		seteaProgressBarVisible(true);
		seteatxtResultadoCuenta("");
		setVisibleBtnCerrarSincronizacion(false);
		dialog.show();
		}catch (Exception ex)
		{
			Log.d("SW","Error ejecutar proceso muestraDialogoSincronizacion error"+ex.getMessage());

		}
	}
	
	public void setVisibleBtnCerrarSincronizacion(boolean visible)
	{
		btnCerrarSincronizacion.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
	}

	public void finalizarActivitySinErrores()
	{
		a.setResult(Activity.RESULT_OK);
		a.finish();
	}
	
	public void cierraDialogoSincronizacion()
	{
		dialog.dismiss();
	}	
	public void seteaValorChkVendedores(boolean valor)
	{
		chkVendedores.setChecked(valor);
	}



	public void seteaValorCuenta(boolean valor) {
		chkCuenta.setChecked(valor);
	}

	public void seteaValordesavena(boolean valor) {
		chkDesAvena.setChecked(valor);
	}
	public void seteaValordesformapago(boolean valor) {
		chkDesFormaPago.setChecked(valor);
	}
	public void seteaValordesvolumen(boolean valor) {
		chkDesVolumen.setChecked(valor);
	}
	public void seteaValorpresioescala(boolean valor) {
		chkDesPrecioEscala.setChecked(valor);
	}


	public void seteaValorCartera(boolean valor) {
		chkDesCartera.setChecked(valor);
	}


	public void seteaValorChkRubros(boolean valor)
	{
		chkRubros.setChecked(valor);
	}
	public void seteaValorChkArticulos(boolean valor)
	{
		chkArticulos.setChecked(valor);
	}
	public void seteaValorChkClientes(boolean valor)
	{
		chkClientes.setChecked(valor);
	}
	public void seteaTxtResultadoArticulos(String valor)
	{
		txtResultadoArticulos.setText(valor);
	}
	public void seteatxtResultadoVendedores(String valor)
	{
		txtResultadoVendedores.setText(valor);
	}
	public void seteatxtResultadoRubros(String valor)
	{
		txtResultadoRubros.setText(valor);
	}
	public void seteatxtResultadoClientes(String valor)
	{
		txtResultadoClientes.setText(valor);
	}
	public void seteatxtResultadoCuenta(String valor)
	{
		txtResultadoCuenta.setText(valor);
	}

	public void seteatxtResultadodesavena(String valor)
	{
		txtResultadodesavena.setText(valor);
	}
	public void seteatxtResultadodesformapago(String valor)
	{
		txtResultadodeformapago.setText(valor);
	}
	public void seteatxtResultadodesvolumen(String valor)
	{
		txtResultadodesvolumen.setText(valor);
	}
	public void seteatxtResultadoprecioescala(String valor)
	{
		txtResultadodesprecioescala.setText(valor);
	}
	public void seteatxtResultadoCartera(String valor)
	{
		txtResultadoCartera.setText(valor);
	}

	public void seteaImgSincronizarVisible(boolean visible)
	{
		imgSincronizar.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
	}
	public void seteaProgressBarVisible(boolean visible)
	{
		progresBar1.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
	}

	public void finalizarActivityConErrores() {
		a.setResult(Activity.RESULT_CANCELED);
		a.finish();
	}

	public void seteaBotonSincronizarVisible(boolean visible) {
		btnSincronizar.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
		
	}

	public void seteaPrgEstadoConexionVisible(boolean visible) {
		prgEstadoConexion.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
		
	}

	public void seteaTxtEstadoConexionVisible(boolean visible) {
		txtEstadoConexion.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
		
	}

	public void seteaTxtEstadoConexion(String strEstadoConexion) {
		txtEstadoConexion.setText(strEstadoConexion);
		
	}
	
}
