package ar.com.syswork.sysmobile.plogin;

import ar.com.syswork.sysmobile.R;
import android.view.View;
import android.view.View.OnClickListener;

	

public class ListenerLogin implements OnClickListener{
	
	private LogicaLogin logicaLogin;
	private PantallaManagerLogin pantallaManagerLogin;
	
	public ListenerLogin(LogicaLogin logicaLogin, PantallaManagerLogin pantallaManagerLogin ) {

		this.logicaLogin= logicaLogin;
		this.pantallaManagerLogin = pantallaManagerLogin;
	}

	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.btnLogin)
		{

			String usuario = pantallaManagerLogin.getUsuario();
			String passWord = pantallaManagerLogin.getPassword();
			
			logicaLogin.validaUsuario(usuario,passWord);
			
		}
		
	}

}

