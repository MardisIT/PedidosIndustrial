package ar.com.syswork.sysmobile.pconsultaclientes;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.entities.ItemClaveValor;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class ListenerConsultaClientes implements OnClickListener, OnItemClickListener,OnItemLongClickListener,OnItemSelectedListener {

	private LogicaConsultaClientes logicaConsultaClientes;
	private PantallaManagerConsultaClientes pantallaManagerConsultaClientes;
	private int origenDeLaConsulta = AppSysMobile.DESDE_MENU_PRINCIPAL;
	String localidad = "";
	public void setPantallaManager(PantallaManagerConsultaClientes pantallaManagerConsultaClientes) {
		this.pantallaManagerConsultaClientes = pantallaManagerConsultaClientes;
		
	}
	public void setLogica(LogicaConsultaClientes logicaConsultaClientes) {
		this.logicaConsultaClientes = logicaConsultaClientes;
		
	}
	public void setOrigenDeLaConsulta(int origenDeLaConsulta)
	{
		this.origenDeLaConsulta = origenDeLaConsulta;
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int posicion, long arg3) 
	{
		AdapterConsultaClientes lAdapter = (AdapterConsultaClientes) adapter.getAdapter();
		Cliente cliente = lAdapter.getItem(posicion);
		
		switch (origenDeLaConsulta)
		{
			case AppSysMobile.DESDE_MENU_PRINCIPAL:
				pantallaManagerConsultaClientes.muestraDetalleCliente(cliente);
				break;
			
			case AppSysMobile.DESDE_CONSULTA_DE_CUENTA_CORRIENTE:
				pantallaManagerConsultaClientes.llamarConsultaCtaCte(cliente);
				break;
			case AppSysMobile.DESDE_CARGA_DE_PEDIDOS:
				pantallaManagerConsultaClientes.llamarCargaPedido(cliente);
				
				break;
			case AppSysMobile.DESDE_CARGA_INVENTARIO:
				pantallaManagerConsultaClientes.llamarCargaInventario(cliente);

				break;
            case AppSysMobile.DESDE_CARGA_VISITAS:
                pantallaManagerConsultaClientes.llamarCargaVisitas(cliente);

                break;
		
		}
		
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> adapter, View v, int posicion,long arg3) {
		Log.d("SW","onItemLongClick()");
		return true;
	}

	@Override
	public void onClick(View v) {
		
		if (v.getId()==R.id.imgBuscar)
		{
			String parametro;
			String campoBusqueda;

			parametro = pantallaManagerConsultaClientes.getParametroBusqueda();
			campoBusqueda = logicaConsultaClientes.getOrderBy();


			//podemos buscar por razon social, y localidad, razon social, localidad

			//cuando busca por localidad y razon social
			if(!localidad.equals("Seleccione una Localidad") && !parametro.equals(""))
			{
                //evaluamos si el valor del parametro es menos que 4 caracteres le mandamos mensaje y regresamos
				if (parametro.length()<4){
					pantallaManagerConsultaClientes.muestraAlertaError("Error","Debe informar al menos 4 caracteres");
					return;
				}

				//continua
				logicaConsultaClientes.vaciaLista();
				logicaConsultaClientes.setNoRecargar(true);

				logicaConsultaClientes.cargarRegistros(-1,"localidad = '"+localidad+"' and "+campoBusqueda + " LIKE '%" + parametro + "%'");
				logicaConsultaClientes.notificarCambiosAdapter();


			}

            //si busca nada mas por rfc
			if (!parametro.equals("") && localidad.equals("Seleccione una Localidad"))
			{
				
				if (campoBusqueda.equals("RazonSocial")){
					if (parametro.length()<4){
						pantallaManagerConsultaClientes.muestraAlertaError("Error","Debe informar al menos 4 caracteres");
						return;
					} 
				}
				logicaConsultaClientes.vaciaLista();
				logicaConsultaClientes.setNoRecargar(true);
				
				// envio -1 para que no limite la cantidad de registros que devuelve
				//evaluamos que busqueda hara

				System.out.println("CAMPOBUSQUEDA "+campoBusqueda );
				System.out.println("PARAMETRO " + parametro);


				logicaConsultaClientes.cargarRegistros(-1,campoBusqueda + " LIKE '%" + parametro + "%'");
				logicaConsultaClientes.notificarCambiosAdapter();
			}

			//si busca nada mas por localidad
			if (parametro.equals("") && !localidad.equals("Seleccione una Localidad"))
			{
				logicaConsultaClientes.vaciaLista();
				logicaConsultaClientes.setNoRecargar(true);

				// envio -1 para que no limite la cantidad de registros que devuelve
				//evaluamos que busqueda hara



				logicaConsultaClientes.cargarRegistros(-1,"Localidad = '"+localidad+"'");
				logicaConsultaClientes.notificarCambiosAdapter();
			}

			//si el usuario no indica localidad ni rfc
			if (parametro.equals("") && localidad.equals("Seleccione una Localidad"))

			{
				logicaConsultaClientes.vaciaLista();
				logicaConsultaClientes.setNoRecargar(false);
				logicaConsultaClientes.cargarRegistros(0,"");
				logicaConsultaClientes.notificarCambiosAdapter();
				
			}
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
	{

		switch (parent.getId()) {
			case R.id.spnlocalidad:



				localidad = parent.getItemAtPosition(position).toString();


				System.out.println("MENSAJE " + localidad);
              break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
}
