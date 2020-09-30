package ar.com.syswork.sysmobile.plogin;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.EditText;

import ar.com.syswork.sysmobile.R;

public class PantallaManagerLogin {
	
	private Activity a;
	private EditText txtUsuario ;
	private EditText txtPassWord;
	
	public PantallaManagerLogin(Activity a){
		this.a = a;
		txtUsuario =  (EditText) a.findViewById(R.id.txtUsuario);
		txtPassWord = (EditText) a.findViewById(R.id.txtPassword);

	}
	
	public void muestraAlertaError(String titulo ,String mensajeError){
		
		AlertDialog alertDialog = new AlertDialog.Builder(a).create();
		alertDialog.setTitle(titulo);
		alertDialog.setMessage(mensajeError);		
		alertDialog.setIcon(R.drawable.simbolo_error);
		alertDialog.show();	
		
	}

	public String getUsuario() {
		return txtUsuario.getText().toString();
	}

	public String getPassword() {
		return txtPassWord.getText().toString();
	}
	
	public void setUsuario(String texto){
		txtUsuario.setText(texto);
	}
	public void setPassword(String texto){
		txtPassWord.setText(texto);
		
	}
	
}
