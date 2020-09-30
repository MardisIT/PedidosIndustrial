package ar.com.syswork.sysmobile.pconsultactacte;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ar.com.syswork.sysmobile.R;

public class ActivityConsultaCtaCte extends Activity {
	
	private LogicaConsultaCtaCte logicaConsultaCtaCte;
	private PantallaManagerConsultaCtaCte pantallaManagerConsultaCtaCte;
	private String codCliente;
	private int posicionItemSeleccionado;
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_cta_cte);
		
		codCliente="";
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		if (extras !=null)
			if (extras.containsKey("cliente"))
				codCliente = extras.getString("cliente");

		ListenerConsultaCtaCte listenerConsultaCtaCte = new ListenerConsultaCtaCte();
		
		pantallaManagerConsultaCtaCte = new PantallaManagerConsultaCtaCte(this,listenerConsultaCtaCte);

		logicaConsultaCtaCte = new LogicaConsultaCtaCte(this,pantallaManagerConsultaCtaCte);
		
		logicaConsultaCtaCte.seteaNombreCliente(codCliente);
		
		listenerConsultaCtaCte.seteaLogicaConsultaCtaCte(logicaConsultaCtaCte,pantallaManagerConsultaCtaCte);
		
		// por default muestra los ultimos 15 dias
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		
		Calendar cal = Calendar.getInstance();
		String fechaHasta = df.format(cal.getTime());
		
		cal.add(Calendar.DATE, -15);
		String fechaDesde= df.format(cal.getTime());

		logicaConsultaCtaCte.consultarMovimientos(codCliente, fechaDesde, fechaHasta);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_consulta_cta_cte, menu);
		return true;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {    
		
		Calendar cal = Calendar.getInstance();
		int diasRestar;
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		
		String fechaHasta = df.format(cal.getTime());
		String fechaDesde = "";

		switch (item.getItemId()) 
		{        
		
			case R.id.ultimos_quince_dias: 
				diasRestar = 15;
				break;        
			
			case R.id.ultimos_treinta_dias:
				diasRestar = 30;
				break;        
			
			case R.id.ultimos_sesenta_dias:
				diasRestar = 60;
				break;        
			
			case R.id.avanzado:
				pantallaManagerConsultaCtaCte.muestraDialogoConsultaFechaDesdeHasta();
				return true;        
			default:
				return super.onOptionsItemSelected(item);    
		}
		
		cal.add(Calendar.DATE, diasRestar*-1);
		fechaDesde= df.format(cal.getTime());
		
		logicaConsultaCtaCte.consultarMovimientos(codCliente, fechaDesde, fechaHasta);
		
		return true;
		
	}
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if ((v.getId() == R.id.lvlistacheques))
        {
            menu.setHeaderTitle(R.string.que_hacer_con_el_item);
            menu.add(1, 1, 1, R.string.eliminar_item);
            menu.add(1, 2, 2, R.string.consultarStockOnLine);
            menu.add(1, 3, 3, R.string.cancelar_item);

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicionItemSeleccionado = info.position;

        }
    }
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
			case 1:
				logicaConsultaCtaCte.removerItemListaItems(posicionItemSeleccionado);
				break;
			case 2:

				//PedidoItem pedidoItem = logicaCargaPedidos.getAdapterCargaPedidos().getItem(posicionItemSeleccionado);
				//pantallaManagerCargaPedidos.consultarStock(pedidoItem.getIdArticulo());

				break;
		}
		return super.onContextItemSelected(item);
	}

	
	
}
