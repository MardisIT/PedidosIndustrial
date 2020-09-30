package ar.com.syswork.sysmobile.pcargapedidos;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.pconsultaarticulos.ActivityConsultaArticulos;
import ar.com.syswork.sysmobile.pconsultastock.ActivityConsultaStock;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.AlertManager;
import ar.com.syswork.sysmobile.util.DialogManager;
import ar.com.syswork.sysmobile.util.IAlertResult;

public class PantallaManagerCargaPedidos {
	
	private Activity a;
	private ListenerCargaPedidos listenerCargaPedidos;
	private Dialog dialog;
	private Dialog dialogClaseDePrecio;
	
	// Se usan en el Dialog
	private TextView txtCodArticulo;
	private TextView txtDescArticulo;
	private Button btnAgregar;
	private Button btnCancelarAgregar;
	private Button btnAceptarClaseDePrecio;
	private Spinner spClasesDePrecio;
	
	private EditText edtCantidadDlg;
	
	// Estan en la activity de Carga de Pedidos
	private TextView txtCliente;
	private TextView txtCantidadItems;
	private TextView txtImportePedido;
	private EditText edtCodigoProducto;
	private  Spinner cmbformapago;
	private  Spinner cmbunidades;


	private String strCantidadItems = "";
	private String strImportePedido = "";

	private  String strProntopago="";
	private  String strNegoEspescial="";
	
	private String textoTituloInstalarBarcodeScanner;
	private String textoMensajeInstalarBarcodeScanner;
	private String strIncluirEnReparto;
	private String strSi;
	private String strNo;
	private String strAceptar;
	
	private String haCargadoItemsEnElPedidoRealmenteDeseaSalir;
	private String avisoAlOperador;
	private String strDebeSeleccionarLaClaseDePrecio;
	
	public PantallaManagerCargaPedidos(Activity a, ListenerCargaPedidos listenerCargaPedidos)
	{
		this.a = a;
		this.listenerCargaPedidos = listenerCargaPedidos;
		
		creaDialogoSolicitaCantidad();
		
		creaDialogoSolicitaClaseDePrecio();
		
		txtCliente = (TextView) a.findViewById(R.id.txtCliente);
		
		txtCantidadItems = (TextView) a.findViewById(R.id.txtCantidadItems);

		txtImportePedido = (TextView) a.findViewById(R.id.txtImportePedido);
		
		edtCodigoProducto = (EditText) a.findViewById(R.id.edtCodProducto);
		
		strCantidadItems = this.a.getString(R.string.cantidad_de_items);
		strImportePedido = this.a.getString(R.string.importe_total_pedido);
		strIncluirEnReparto = this.a.getString(R.string.incluirEnReparto);
		
		TextView tmpTextView;

		
		tmpTextView = (TextView) a.findViewById(R.id.txtDescCantidad);
		tmpTextView.setText(this.a.getString(R.string.abreviatura_cantidad));
		
		tmpTextView = (TextView) a.findViewById(R.id.txtDescUnitario);
		tmpTextView.setText(this.a.getString(R.string.abreviatura_unitario));

		tmpTextView = (TextView) a.findViewById(R.id.txtDescPpago);
		tmpTextView.setText("Pronto-Pago");
		tmpTextView = (TextView) a.findViewById(R.id.txtDescNespecial);
		tmpTextView.setText("Descuento");



		tmpTextView = (TextView) a.findViewById(R.id.txtDescTotal);
		tmpTextView.setText(this.a.getString(R.string.abreviatura_total));

		textoTituloInstalarBarcodeScanner = this.a.getString(R.string.textoTituloInstalarBarcodeScanner);
		textoMensajeInstalarBarcodeScanner = this.a.getString(R.string.textoMensajeInstalarBarcodeScanner )	;
		strSi = this.a.getString(R.string.SI);
		strNo = this.a.getString(R.string.NO);
		strAceptar = this.a.getString(R.string.aceptar);
		
		avisoAlOperador = this.a.getString(R.string.avisoAlOperador);
		haCargadoItemsEnElPedidoRealmenteDeseaSalir = this.a.getString(R.string.haCargadoItemsEnElPedidoRealmenteDeseaSalir);
		strDebeSeleccionarLaClaseDePrecio = this.a.getString(R.string.debeSeleccionarLaClaseDePrecio);
	}
	public void creaDialogoSolicitaCantidad()
	{

		List<String> formapago= new ArrayList<>();
		formapago.add("Contado");
		formapago.add("Credito");

		List<String> unidades= new ArrayList<>();
		unidades.add("Unidades");
		unidades.add("Cajas");


		dialog = new Dialog(a);
		dialog.setContentView(R.layout.dialog_solicita_cantidad);
			dialog.setTitle(a.getString(R.string.ingrese_la_cantidad));
		dialog.setCancelable(false);
		
		txtCodArticulo = (TextView) dialog.findViewById(R.id.txtCodArticulo);
		txtDescArticulo = (TextView) dialog.findViewById(R.id.txtDescArticulo);
		cmbformapago=(Spinner)dialog.findViewById(R.id.cmbformapago);
		cmbunidades=(Spinner)dialog.findViewById(R.id.cmbunidad);
		ArrayAdapter<String> adaptador;
		adaptador = new ArrayAdapter<String>(a, R.layout.support_simple_spinner_dropdown_item, formapago);
		ArrayAdapter<String> adaptadoru;
		adaptadoru = new ArrayAdapter<String>(a, R.layout.support_simple_spinner_dropdown_item, unidades);

		cmbformapago.setAdapter(adaptador);
		cmbunidades.setAdapter(adaptadoru);
		
		btnAgregar = (Button) dialog.findViewById(R.id.btnAgregar);
		btnCancelarAgregar = (Button) dialog.findViewById(R.id.btnCancelarAgregar);

		edtCantidadDlg = (EditText) dialog.findViewById(R.id.edtCantidadDlg);
		
	}
	
	public void creaDialogoSolicitaClaseDePrecio()
	{
		dialogClaseDePrecio = new Dialog(a);
		dialogClaseDePrecio.setContentView(R.layout.dialog_solicita_clase_precio);
		dialogClaseDePrecio.setTitle("Seleccione la clase de precio");
		dialogClaseDePrecio.setCancelable(false);
		
		btnAceptarClaseDePrecio = (Button) dialogClaseDePrecio.findViewById(R.id.btnAceptarClaseDePrecio);
		spClasesDePrecio = (Spinner) dialogClaseDePrecio.findViewById(R.id.spClasesDePrecio);
	}
	
	
	
	public void seteaListener() {
		
		ImageButton imbOk = (ImageButton) a.findViewById(R.id.imgBtnOk);
		imbOk.setOnClickListener(listenerCargaPedidos);
		
		ImageButton imbBuscar = (ImageButton) a.findViewById(R.id.imgBtnBuscar);
		imbBuscar.setOnClickListener(listenerCargaPedidos);
		
		ImageButton imbScan = (ImageButton) a.findViewById(R.id.imgBtnScan);
		imbScan.setOnClickListener(listenerCargaPedidos);
		
		btnAgregar.setOnClickListener(listenerCargaPedidos);
		btnCancelarAgregar.setOnClickListener(listenerCargaPedidos);
		btnAceptarClaseDePrecio.setOnClickListener(listenerCargaPedidos);

		
	}

	public void mostrarDialogoSolicitaCantidad(String codigoProducto,String descripcionProducto)
	{
		txtCodArticulo.setText(a.getString(R.string.abreviatura_codigo) + " " + codigoProducto);
		txtDescArticulo.setText(descripcionProducto);
		dialog.show();

	}
	
	public void mostrarDialogoSolicitaClaseDePrecio()
	{
		dialogClaseDePrecio.show();

	}
	
	public void lanzarActivityConsultaArticulos()
	{
		Intent i;
		i = new Intent(a,ActivityConsultaArticulos.class);
		i.putExtra("origenDeLaConsulta", AppSysMobile.DESDE_CARGA_DE_PEDIDOS);
		a.startActivityForResult(i, AppSysMobile.DESDE_CARGA_DE_PEDIDOS);
	}
	
	public void consultarStock(String idArticulo)
	{
		Intent i = new Intent(a,ActivityConsultaStock.class);
		i.putExtra("idArticulo", idArticulo);
		a.startActivity(i);
	}

	public void setDatosCliente(Cliente cliente) {
		txtCliente.setText(cliente.getRazonSocial());
	}
	
	public void setCantidadItems(int cant)
	{
		txtCantidadItems.setText(strCantidadItems + " " + cant);
	}
	
	public void setImporteTotalPedido(double importe)
	{
		txtImportePedido.setText(strImportePedido + " $ " + importe);
	}

	public String getCodigoIntroducido() 
	{
		return edtCodigoProducto.getText().toString().trim();
	}
	
	public void setCodigoIntroducido(String codigo) 
	{
		edtCodigoProducto.setText(codigo);
	}
	
	public String getCantidadIntroducida() 
	{
		return edtCantidadDlg.getText().toString().trim();
	}

	public String getFormadePagoIntroducida()
	{
		return cmbformapago.getSelectedItem().toString().trim();
	}
	public String getUnidadesIntroducida()
	{
		return cmbunidades.getSelectedItem().toString().trim();
	}

	
	public void cerrarDialogoSolicitaCantidad()
	{
		edtCantidadDlg.setText("");
		dialog.dismiss();
	}

	public void cerrarDialogoSolicitaClaseDePrecio()
	{
		dialogClaseDePrecio.dismiss();
	}

	public void finalizaActivityCargaPedidos() {
		a.finish();
	}
	
	public void solicitaConfirmacionDeSalida()
	{
		DialogManager utlDlg = new DialogManager();
		utlDlg.muestraToastGenerico(a, a.getString(R.string.confirmacionSalir), false);
	}
	public void muestraAlertaDescargaBarcodeScanner(LogicaCargaPedidos logicaCargaPedidos) {
		
		AlertManager am = new AlertManager(this.a ,logicaCargaPedidos,0);
		am.setTitle(textoTituloInstalarBarcodeScanner);
		am.setMessage(textoMensajeInstalarBarcodeScanner);
		am.setPositiveButton(strSi);
		am.setNegativeButton(strNo);
		am.ShowAlert();
		
	}
	
	public void muestraAlertaCancelarPedido() {
		
		AlertManager am = new AlertManager(this.a ,(IAlertResult) this.a ,R.id.mnu_cancelar_pedido);
		
		am.setTitle(avisoAlOperador);
		am.setMessage(haCargadoItemsEnElPedidoRealmenteDeseaSalir);
		am.setPositiveButton(strSi);
		am.setNegativeButton(strNo);
		am.ShowAlert();
		
	}
	
	public void muestraAlertaIncluirEnReparto() 
	{
		AlertManager am = new AlertManager(this.a ,(IAlertResult) this.a ,999);
		am.setTitle(avisoAlOperador);
		am.setMessage(strIncluirEnReparto);
		am.setPositiveButton(strSi);
		am.setNegativeButton(strNo);
		am.ShowAlert();
	}
	
	public Spinner getSpClasesDePrecio()
	{
		return spClasesDePrecio;
	}
	public void muestraAlertaDebeSeleccionarUnaClaseDePrecio() 
	{
		AlertManager am = new AlertManager(this.a ,(IAlertResult) this.a ,R.id.mnu_cancelar_pedido);
		
		am.setTitle(avisoAlOperador);
		am.setMessage(strDebeSeleccionarLaClaseDePrecio);
		am.setNeutralButton(strAceptar);
		am.ShowAlert();
		
	}
}
