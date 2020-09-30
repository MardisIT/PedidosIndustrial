package ar.com.syswork.sysmobile.plogin;


import android.app.Activity;
import android.content.Intent;
import ar.com.syswork.sysmobile.daos.DaoVendedor;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Vendedor;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.R;


public class LogicaLogin {
	
	private Activity a;
	private PantallaManagerLogin pantallaManagerLogin;
	private Vendedor vendedor;
	private DaoVendedor daoVendedor;
	private DataManager dataManager;
	private AppSysMobile app;
	
	public LogicaLogin(Activity a, PantallaManagerLogin pantallaManagerLogin) {
		this.a = a;
		this.pantallaManagerLogin= pantallaManagerLogin;
	}

	
	public void validaUsuario(String usuario,String passWord){
		
		app = (AppSysMobile) a.getApplication();
		dataManager = app.getDataManager();
		daoVendedor = dataManager.getDaoVendedor();
		
		vendedor = daoVendedor.getByKey(usuario);
		
		if (vendedor == null){
			// No Existe el Nombre del Vendedor, Mostrar Error
			pantallaManagerLogin.muestraAlertaError(a.getString(R.string.titulo_error_validacion_login), a.getString(R.string.mensaje_error_validacion_login));
			pantallaManagerLogin.setUsuario("");
			pantallaManagerLogin.setPassword("");
		}
		else
		{
			// Valido contra la Contrasena
			if (vendedor.getCodigoDeValidacion().equals(passWord))
			{
				app.setVendedorLogueado(usuario);
				lanzarActivity();
			}
			else
			{
				// No coincide usuario y contrasea
				pantallaManagerLogin.muestraAlertaError(a.getString(R.string.titulo_error_validacion_login), a.getString(R.string.mensaje_error_validacion_password_login));
				pantallaManagerLogin.setUsuario("");
				pantallaManagerLogin.setPassword("");
				
			}
		}
	}
	
	private void lanzarActivity(){
		
		Intent i = new Intent(a, ar.com.syswork.sysmobile.pmenuprincipal.ActivityMenuPrincipal.class);
		a.startActivity(i);
		a.finish();
		
	}
	
}
