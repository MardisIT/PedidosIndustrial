package ar.com.syswork.sysmobile.pconsultaclientes;

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
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.entities.ItemClaveValor;
import ar.com.syswork.sysmobile.pcargainventario.CargaInventarioActivity;
import ar.com.syswork.sysmobile.pcargapedidos.ActivityCargaPedidos;
import ar.com.syswork.sysmobile.pconsultactacte.ActivityConsultaCtaCte;
import ar.com.syswork.sysmobile.pconsultagenerica.detalle.ConsultaClaveValorGenerica;
import ar.com.syswork.sysmobile.visita;

public class PantallaManagerConsultaClientes {
	
	private Activity a;
	private ListenerConsultaClientes listenerConsultaCliente;
	private EditText et;
	private Spinner spnlocalidad;
	
	public PantallaManagerConsultaClientes(Activity a,ListenerConsultaClientes listenerConsultaCliente) {
		this.a = a;
		this.listenerConsultaCliente=listenerConsultaCliente;
		et = (EditText) a.findViewById(R.id.txtBuscar);
		spnlocalidad = (Spinner) a.findViewById(R.id.spnlocalidad);
	}

	public void seteaListener() {
		ListView lv = (ListView) a.findViewById(R.id.lstConsultaCliente);
		ImageView img = (ImageView) a.findViewById(R.id.imgBuscar);
		
		lv.setOnItemClickListener(listenerConsultaCliente);
		spnlocalidad.setOnItemSelectedListener(listenerConsultaCliente);
		//lv.setOnItemLongClickListener(listenerConsultaCliente);
		img.setOnClickListener(listenerConsultaCliente);
		
	}
	public String getParametroBusqueda(){
		return et.getText().toString();
	}
	public void setParametroBusqueda(String queTexto){
		et.setText(queTexto);
	}
	
	public void setHintParametroBusqueda(String queHint){
		String lHint="";
		
		if (queHint.equals("RazonSocial"))
		{
			lHint = a.getString(R.string.hint_busqueda_razon_social);
		}
		
		if (queHint.equals("CodigoOpcional"))
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

	public void muestraDetalleCliente(Cliente cliente)
	{
		ItemClaveValor item=null;
		List<ItemClaveValor> listaClaves = new ArrayList<ItemClaveValor>();
		
		item = new ItemClaveValor("Cuenta Contable",cliente.getCodigo());
		listaClaves.add(item);
		item = new ItemClaveValor("Codigo",cliente.getCodigoOpcional());
		listaClaves.add(item);
		item = new ItemClaveValor("Razon Social",cliente.getRazonSocial());
		listaClaves.add(item);
		item = new ItemClaveValor("Direccion",cliente.getCalleNroPisoDpto());
		listaClaves.add(item);
		item = new ItemClaveValor("Localidad",cliente.getLocalidad());
		listaClaves.add(item);
		item = new ItemClaveValor("CUIT",cliente.getCuit());
		listaClaves.add(item);
		item = new ItemClaveValor("IVA",Byte.toString(cliente.getIva()));
		listaClaves.add(item);
		item = new ItemClaveValor("Telefono",cliente.getTelefono());
		listaClaves.add(item);
		item = new ItemClaveValor("E-m@il",cliente.getMail());
		listaClaves.add(item);

		ConsultaClaveValorGenerica consultaClaveValorGenerica = new ConsultaClaveValorGenerica(a, listaClaves);
		consultaClaveValorGenerica.setIcono(R.drawable.icono_cliente_grande);
		consultaClaveValorGenerica.setTitulo("Datos del Cliente");
		
		consultaClaveValorGenerica.lanzarActivity();
		
	}

	public void llamarCargaPedido(Cliente cliente) {
		
		Intent i = new Intent(a,ActivityCargaPedidos.class);
		i.putExtra("cliente", cliente.getCodigo());
		a.startActivity(i);
		a.finish();
	}

	public void llamarCargaInventario(Cliente cliente) {

		Intent i = new Intent(a,CargaInventarioActivity.class);
		i.putExtra("cliente", cliente.getCodigo());
		a.startActivity(i);
		a.finish();
	}

    public void llamarCargaVisitas(Cliente cliente) {

        Intent i = new Intent(a,visita.class);
        i.putExtra("cliente", cliente.getCodigo());
        a.startActivity(i);
        a.finish();
    }


	public void llamarConsultaCtaCte(Cliente cliente) {
		Intent i = new Intent(a,ActivityConsultaCtaCte.class);
		i.putExtra("cliente", cliente.getCodigo());
		a.startActivity(i);
		a.finish();
		
	}
	
	
}
