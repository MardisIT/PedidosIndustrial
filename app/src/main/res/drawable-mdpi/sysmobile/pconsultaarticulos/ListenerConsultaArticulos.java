package ar.com.syswork.sysmobile.pconsultaarticulos;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.Articulo;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class ListenerConsultaArticulos  implements OnClickListener, OnItemClickListener,OnItemLongClickListener, OnItemSelectedListener {

	private LogicaConsultaArticulos logicaConsultaArticulos;
	private PantallaManagerConsultaArticulos pantallaManagerConsultaArticulos;
	private int origenDeLaConsulta = AppSysMobile.DESDE_MENU_PRINCIPAL;
	String rubro = "";
	public void setPantallaManager(PantallaManagerConsultaArticulos pantallaManagerConsultaArticulos) {
		this.pantallaManagerConsultaArticulos= pantallaManagerConsultaArticulos;
	}
	public void setLogica(LogicaConsultaArticulos logicaConsultaArticulos) {
	                             			this.logicaConsultaArticulos= logicaConsultaArticulos;
		
	}
	public void setOrigenDeLaConsulta(int origenDeLaConsulta)
	{
		this.origenDeLaConsulta = origenDeLaConsulta;
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int posicion, long arg3) {
		AdapterConsultaArticulos lAdapter = (AdapterConsultaArticulos) adapter.getAdapter();
		Articulo articulo = lAdapter.getItem(posicion);
		
		switch (origenDeLaConsulta)
		{
			case AppSysMobile.DESDE_MENU_PRINCIPAL:
				pantallaManagerConsultaArticulos.muestraDetalleArticulo(articulo);
				break;
			case AppSysMobile.DESDE_CARGA_DE_PEDIDOS:
				this.logicaConsultaArticulos.seteaCodigoArticuloCargaPedidos(articulo.getIdArticulo());
				break;
		}

	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> adapter, View v, int posicion,long arg3) {
		return true;
	}

	@Override
	public void onClick(View v) {
		
		if (v.getId()==R.id.imgBuscar)
		{
			String parametro;
			String campoBusqueda;
			
			parametro = pantallaManagerConsultaArticulos.getParametroBusqueda();
			campoBusqueda = logicaConsultaArticulos.getOrderBy();

			if (!parametro.equals(""))
			{
				
				if (campoBusqueda.equals("Descripcion"))
				{
					if (parametro.length()<4)
					{
						pantallaManagerConsultaArticulos.muestraAlertaError("Error","Debe informar al menos 4 caracteres");
						return;
					}
					else
					{
						if (parametro.indexOf(" ") != 0)
						{
							parametro = parametro.trim();
							parametro = parametro.replace(" ", "%");
							parametro = parametro.replace("%%", "%");
						}
					}
				}
					
				logicaConsultaArticulos.vaciaLista();
				logicaConsultaArticulos.setNoRecargar(true);
				
				// envio -1 para que no limite la cantidad de registros que devuelve
				logicaConsultaArticulos.cargarRegistros(-1,campoBusqueda + " LIKE '%" + parametro + "%'");
				logicaConsultaArticulos.notificarCambiosAdapter();
			}
			else
			{
				logicaConsultaArticulos.vaciaLista();
				logicaConsultaArticulos.setNoRecargar(false);
				logicaConsultaArticulos.cargarRegistros(0,"");
				logicaConsultaArticulos.notificarCambiosAdapter();
				
			}
		}
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
	{

		switch (parent.getId()) {
			case R.id.spnRubros:
				rubro = parent.getItemAtPosition(position).toString();
				System.out.println("MENSAJE " + rubro);
				break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}


}
