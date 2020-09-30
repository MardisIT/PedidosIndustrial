package ar.com.syswork.sysmobile.pconsultastock;

import android.os.Bundle;
import android.app.Activity;
import ar.com.syswork.sysmobile.R;

public class ActivityConsultaStock extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_stock);
		
		//Creo el Pantalla Manager
		PantallaManagerConsultaStock pantallaManagerConsultaStock  = new PantallaManagerConsultaStock (this);
		
		//Creo la Logica
		LogicaConsultaStock logicaConsultaStock = new LogicaConsultaStock(pantallaManagerConsultaStock); 
		
		//Seteo el Nombre del Articulo
		pantallaManagerConsultaStock.seteaNombreArticulo();
		
		//Lanzo el Thread para consultar los movimientos
		logicaConsultaStock.consultarStock();
		
	}
}
