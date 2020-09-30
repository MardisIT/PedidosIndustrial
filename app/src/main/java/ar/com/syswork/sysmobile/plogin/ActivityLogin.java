package ar.com.syswork.sysmobile.plogin;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import ar.com.syswork.sysmobile.R;

public class ActivityLogin extends Activity {
	
	private ListenerLogin listener;
	private LogicaLogin logicaLogin;
	private PantallaManagerLogin pantallaManagerLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		pantallaManagerLogin = new PantallaManagerLogin(this);
		
		logicaLogin = new LogicaLogin(this,pantallaManagerLogin);
		
		listener = new ListenerLogin(logicaLogin,pantallaManagerLogin);
		
		Button btnLogin = (Button) this.findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(listener);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
