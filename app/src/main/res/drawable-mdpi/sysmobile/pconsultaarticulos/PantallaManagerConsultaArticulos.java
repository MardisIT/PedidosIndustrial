package ar.com.syswork.sysmobile.pconsultaarticulos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.Articulo;
import ar.com.syswork.sysmobile.entities.ItemClaveValor;
import ar.com.syswork.sysmobile.pconsultagenerica.detalle.ConsultaClaveValorGenerica;
import ar.com.syswork.sysmobile.pconsultastock.ActivityConsultaStock;

public class PantallaManagerConsultaArticulos {

	private Activity a;
	private ListenerConsultaArticulos listenerConsultaArticulo;
	private EditText et;
	private Spinner spnRubros;
	
	public PantallaManagerConsultaArticulos(Activity a,ListenerConsultaArticulos listenerConsultaArticulo) {
		this.a = a;
		this.listenerConsultaArticulo=listenerConsultaArticulo;
		et = (EditText) a.findViewById(R.id.txtBuscar);
		spnRubros=(Spinner)a.findViewById(R.id.spnRubros);
	}

	public void seteaListener() {
		ListView lv = (ListView) a.findViewById(R.id.lstArticulos);
		ImageView img = (ImageView) a.findViewById(R.id.imgBuscar);
		
		lv.setOnItemClickListener(listenerConsultaArticulo);
		spnRubros.setOnItemSelectedListener(listenerConsultaArticulo);
		img.setOnClickListener(listenerConsultaArticulo);
		
	}
	public String getParametroBusqueda(){
		return et.getText().toString();
	}
	public void setParametroBusqueda(String queTexto){
		et.setText(queTexto);
	}
	public void consultarStock(String idArticulo)
	{
		Intent i = new Intent(a,ActivityConsultaStock.class);
		i.putExtra("idArticulo", idArticulo);
		a.startActivity(i);
	}
	public void setHintParametroBusqueda(String queHint){
		String lHint="";
		
		if (queHint.equals("Descripcion"))
		{
			lHint = a.getString(R.string.hint_busqueda_descripcion);
		}
		
		if (queHint.equals("IdArticulo"))
		{
			lHint = a.getString(R.string.hint_busqueda_codigo);
		}
		
		et.setHint(lHint);
	}
	public String getHintParametroBusqueda(){
		return et.getHint().toString();
	}
	
	public void muestraAlertaError(String titulo ,String mensajeError){
		AlertDialog alertDialog = new AlertDialog.Builder(a).create();
		alertDialog.setTitle(titulo);
		alertDialog.setMessage(mensajeError);		
		alertDialog.setIcon(R.drawable.simbolo_error);
		alertDialog.show();	
	}

	public void muestraDetalleArticulo(Articulo articulo)
	{
		ItemClaveValor item=null;
		List<ItemClaveValor> listaClaves = new ArrayList<ItemClaveValor>();
		
		item = new ItemClaveValor("Codigo",articulo.getIdArticulo());
		listaClaves.add(item);
		item = new ItemClaveValor("Descripcion",articulo.getDescripcion());
		listaClaves.add(item);
		item = new ItemClaveValor("Alic Iva",Double.toString(articulo.getIva()));
		listaClaves.add(item);
		item = new ItemClaveValor("Precio 1",Double.toString(articulo.getPrecio1()));
		listaClaves.add(item);
		item = new ItemClaveValor("Precio 2",Double.toString(articulo.getPrecio2()));
		listaClaves.add(item);
		item = new ItemClaveValor("Precio 3",Double.toString(articulo.getPrecio3()));
		listaClaves.add(item);
		item = new ItemClaveValor("Precio 4",Double.toString(articulo.getPrecio4()));
		listaClaves.add(item);
		item = new ItemClaveValor("Precio 5",Double.toString(articulo.getPrecio5()));
		listaClaves.add(item);
		item = new ItemClaveValor("Precio 6",Double.toString(articulo.getPrecio6()));
		listaClaves.add(item);
		item = new ItemClaveValor("Precio 7",Double.toString(articulo.getPrecio7()));
		listaClaves.add(item);
		item = new ItemClaveValor("Precio 8",Double.toString(articulo.getPrecio8()));
		listaClaves.add(item);
		item = new ItemClaveValor("Precio 9",Double.toString(articulo.getPrecio9()));
		listaClaves.add(item);
		item = new ItemClaveValor("Precio 10",Double.toString(articulo.getPrecio10()));
		listaClaves.add(item);

		ConsultaClaveValorGenerica consultaClaveValorGenerica = new ConsultaClaveValorGenerica(a, listaClaves);
		consultaClaveValorGenerica.setIcono(R.drawable.icono_articulos_grande);
		consultaClaveValorGenerica.setTitulo("Datos del Articulo");
		
		consultaClaveValorGenerica.lanzarActivity();
		
	}
	
	
}
