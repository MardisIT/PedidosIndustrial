package ar.com.syswork.sysmobile.pconsultactacte;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler.Callback;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoCartera;
import ar.com.syswork.sysmobile.daos.DaoChequePagos;
import ar.com.syswork.sysmobile.daos.DaoCliente;
import ar.com.syswork.sysmobile.daos.DaoPagos;
import ar.com.syswork.sysmobile.daos.DaoPagosDetalles;
import ar.com.syswork.sysmobile.daos.DaoPedido;
import ar.com.syswork.sysmobile.daos.DaoPedidoItem;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Cartera;
import ar.com.syswork.sysmobile.entities.ChequePagos;
import ar.com.syswork.sysmobile.entities.Cheques;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.entities.ItemCtaCte;
import ar.com.syswork.sysmobile.entities.Pagos;
import ar.com.syswork.sysmobile.entities.PagosDetalles;
import ar.com.syswork.sysmobile.entities.PedidoItem;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.DialogManager;

public class LogicaConsultaCtaCte implements Callback{
	
	private PantallaManagerConsultaCtaCte pantallaManagerConsultaCtaCte;
	private Activity a;
	private DialogManager utilDialogos;
	private ArrayList<ItemCtaCte> listaCtaCte;
	private ArrayList<Cheques> listacheques;
	private AdapterConsultaCtaCte adapter ;
	private AdapterChequesCta adapterch;
	private AppSysMobile app;
	private DataManager dm;
	private DaoCliente daoCliente;
	private Cliente cliente;
	private DaoCartera daoCartera;
	private Spinner cmbformapagofactura;
	public  static  String ValorTotalPago="0.00";
	public  static  String ValorTotalAgregado="0.00";

	public EditText txtvalortotal,txtvalorefectivo;
	public Button btnguardar,btnagregar;
	OperacionesPagos obj= new OperacionesPagos();
	private DaoPagos daoPagos;
	private DaoPagosDetalles daoPagosDetalles;
	private DaoChequePagos daoChequePagos;
	final ListView lch;
	private static long idpago=0;
	private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/FotosPagos/";
    private File file = new File(ruta_fotos);
   private Button botonfotos,botonverfoto;
   public String codigoVendedor;
	public LogicaConsultaCtaCte(final Activity a, final PantallaManagerConsultaCtaCte pantallaManagerConsultaCtaCte)
	{
		this.pantallaManagerConsultaCtaCte = pantallaManagerConsultaCtaCte;
		this.a = a;

		
		// Creo la Lista
		listaCtaCte = new ArrayList<ItemCtaCte>();
		listacheques=new ArrayList<Cheques>();

		
		// Creo el Adapter
		adapter = new AdapterConsultaCtaCte(this.a, listaCtaCte);
		adapterch= new AdapterChequesCta(this.a,listacheques);
		
		app = (AppSysMobile) a.getApplication();
		dm = app.getDataManager();
		daoCliente = dm.getDaoCliente();
		daoCartera=dm.getDaoCartera();
		daoChequePagos=dm.getDaoChequePagos();

		utilDialogos = new DialogManager ();
		codigoVendedor = app.getVendedorLogueado();
		daoPagos = dm.getDaoPagos();
		daoPagosDetalles = dm.getDaoPagosDetalles();

		ArrayList<String> opcionespago= new ArrayList<>();
		opcionespago.add("Cheque");
		opcionespago.add("Deposito");

		cmbformapagofactura=(Spinner)a.findViewById(R.id.cmbformapagofactura);
		btnguardar=(Button)a.findViewById(R.id.guardar);
		btnagregar=(Button)a.findViewById(R.id.btnagregarche);
		botonfotos = (Button) a.findViewById(R.id.btnTomaFoto);
		botonverfoto=(Button) a.findViewById(R.id.btnverFoto) ;
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(a, android.R.layout.simple_spinner_item, opcionespago);

		txtvalortotal=(EditText) a.findViewById(R.id.totaltexto);
		txtvalorefectivo=(EditText) a.findViewById(R.id.totalefectivo);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cmbformapagofactura.setAdapter(dataAdapter);


		final ListView lv = (ListView) a.findViewById(R.id.lvConsultaCtaCte);
		lv.setAdapter(this.adapter);
		lch= (ListView)a.findViewById(R.id.lvlistacheques);
		lch.setAdapter(this.adapterch);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
				ItemCtaCte  selrccionado= (ItemCtaCte)parent.getItemAtPosition(position);
				String consulta= selrccionado.getConcepto();

			}
		});

		obj.setFotostomadas("");
		obj.setValortotal(0.00);
		obj.setValoringresado(0.0);
		txtvalortotal.setText("");
		txtvalorefectivo.setText("");
		idpago=0;

		if(obj.getFotostomadas()!=null ) {
			if(obj.getFotostomadas().toString().equals("")==false)
				botonverfoto.setVisibility(View.VISIBLE);
			else
				botonverfoto.setVisibility(View.INVISIBLE);
		}
		else
			botonverfoto.setVisibility(View.INVISIBLE);


		txtvalortotal.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					final int position = v.getId();
					final EditText Caption = (EditText) v;
					obj.setValortotal(Double.valueOf(Caption.getText().toString().equals("")?"0.00":Caption.getText().toString()));
					if(Caption.getText().toString().equals("")) {
						txtvalortotal.setFocusable(true);
					}
				}

			}

		});
		file.mkdirs();
		botonfotos.setOnClickListener(new View.OnClickListener() {

		    @Override
    public void onClick(View v) {
				     String file = ruta_fotos + getCode() + ".jpg";
				     File mi_foto = new File( file );
				     try {
				     	mi_foto.createNewFile();
				     } catch (IOException ex) {
				     	Log.e("ERROR ", "Error:" + ex);
				     }
				              //
						Uri uri = Uri.fromFile( mi_foto );
				              //Abre la camara para tomar la foto
						Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				              //Guarda imagen
						cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				              //Retorna a la actividad
						a.startActivityForResult(cameraIntent, 0);
						obj.setFotostomadas(file);
						if(obj.getFotostomadas()!=null || obj.getFotostomadas()=="")
							botonverfoto.setVisibility(View.VISIBLE);
						else
							botonverfoto.setVisibility(View.INVISIBLE);
							}

		   });
		btnagregar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			    //agregarcheque();
				if(txtvalortotal.getText().toString().equals("")){
					pantallaManagerConsultaCtaCte.muestraerrovalidacion("Ingrese el valor de pago..!!");
					return;
				}
                pantallaManagerConsultaCtaCte.mostrarDialogoChequePagos();
			}
		});
		botonverfoto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//agregarcheque();
				pantallaManagerConsultaCtaCte.mostrardialogoimagen();
				pantallaManagerConsultaCtaCte.cargarfoto(obj.getFotostomadas());
			}
		});
		btnguardar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (sumartotales() == false) {
					return;
				}else {
					adapterch.notifyDataSetChanged();
					if(txtvalortotal.getText().toString().equals("")&& txtvalorefectivo.getText().toString().equals("")){
						pantallaManagerConsultaCtaCte.muestraerrovalidacion("Ingrese el valor de pago..!!");
						return;
					}
					if(obj.getFotostomadas()==null){
						pantallaManagerConsultaCtaCte.muestraerrovalidacion("Tomar foto del pago..!!");
						return;
					}else{
						if(obj.getFotostomadas().toString().equals("")) {
							pantallaManagerConsultaCtaCte.muestraerrovalidacion("Tomar foto del pago..!!");
							return;
						}

					}
					obj.setValortotal(Double.valueOf(txtvalortotal.getText().toString().equals("")?"0":txtvalortotal.getText().toString()));
					double valorefectivo=0;
					if(txtvalorefectivo.getText().toString().equals("")==false && txtvalorefectivo.getText().toString().equals("0")==false ){
						valorefectivo=Double.valueOf(txtvalorefectivo.getText().toString());
					}

					if ((obj.getValortotal()+valorefectivo) > 0) {
						if ((obj.getValortotal()+ valorefectivo) < obj.getValoringresado()) {
							pantallaManagerConsultaCtaCte.muestraerrovalidacion("El valor recibido es nemor ingresado en por facturas..!!");
							return;
						} else {
							if ((obj.getValortotal()+ valorefectivo)  > obj.getValoringresado()) {
								pantallaManagerConsultaCtaCte.muestraerrovalidacion("El valor recibido es mayor al  ingresado  por facturas..!!");
								return;
							} else {
							    double TOTAL=(obj.getValortotal()+ valorefectivo);
                                if ((TOTAL==(obj.getValoringresado()))) {
                                	if(txtvalortotal.getText().toString().equals("")==false && txtvalortotal.getText().toString().equals("0.0")==false ) {
										//listo para guardar

										if (cmbformapagofactura.getSelectedItem().toString().equals("Cheque")) {
											llenarlistacheque();
											for (Cheques c : listacheques) {
												if (c.getNumcheque().equals("") || c.getBanco().equals("") || c.getFecha().equals("") || c.getValor() <= 0) {
													pantallaManagerConsultaCtaCte.muestraerrovalidacion("Datos obligatorios pago en cheque..!!");
													return;
												}
											}
											if (listacheques.size() == 0) {
												pantallaManagerConsultaCtaCte.muestraerrovalidacion("Agregar cheques para poder guardar..!!");
												return;
											}

										}
									}
								}
							}
						}
					}

				}
				guardarpagos();
				pantallaManagerConsultaCtaCte.muestraerrovalidacion("Ingrese el valor total de pago..!!");
			}

		});
		cmbformapagofactura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				//stuff here to handle item selection
				String opcion =(String)parent.getItemAtPosition(position);
				if(opcion.equals("Cheque")){
					lch.setVisibility(View.VISIBLE);
					//agregarcheque();
					btnagregar.setVisibility(View.VISIBLE);
				}else {
					adapterch.clear();
					listacheques.clear();
					lch.clearChoices();
					lch.setVisibility(View.INVISIBLE);
					btnagregar.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});




	}

	public void seteaNombreCliente(String codCliente) {
		cliente = daoCliente.getByKey(codCliente);
		pantallaManagerConsultaCtaCte.seteaNombreCliente(cliente.getRazonSocial());
	}
	public void removerItemListaItems(int posicionItemSeleccionado) {
		pantallaManagerConsultaCtaCte.removerItemListaItems(posicionItemSeleccionado);
	}

	public void refreshAdapter()
	{
		adapter.notifyDataSetChanged();
	}
	private String getCode()
  {
		   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
		   String date = dateFormat.format(new Date() );
		   String photoCode = "pic_" + date;
		   return photoCode;
  }


	/*public void agregarcheque(){
		Cheques cheques= new Cheques();
		cheques.setValor(0.00);
		cheques.setFecha("");
		cheques.setNumcheque("");
		cheques.setBanco("");

		listacheques.add(cheques);
		//adapterch.add(cheques);
		adapterch.notifyDataSetChanged();
	}*/
public void llenarlistacheque(){
		obj.setValortotal(Double.valueOf(txtvalortotal.getText().toString()));
		listacheques=pantallaManagerConsultaCtaCte.RetornaLista(obj.getValortotal());

}

	public boolean sumartotales(){
		obj.setValoringresado(0.00);
		for (ItemCtaCte x:listaCtaCte) {
			if(x.getValor_pago()>0) {
				obj.setValoringresado(obj.getValoringresado() + x.getValor_pago());
				if (x.getValor_pago() > x.getImporte()) {
					x.setMsjerror("El valor de pago no debe ser mayor al total de factura..!!!");
					pantallaManagerConsultaCtaCte.muestraerrovalidacion("El valor de pago no debe ser mayor al total de factura..!!!");
					return false;
				}
			}
		}
		return true;
	}
	public void consultarMovimientos(String cliente,String fechaDesde,String fechaHasta)
	{
		try {
		/*pantallaManagerConsultaCtaCte.muestraDialogoConsultCtaCte();
		
		Handler h = new Handler(this);
		ThreadConsultaCtaCte tc = new ThreadConsultaCtaCte (h,cliente,fechaDesde,fechaHasta);
        Thread t = new Thread(tc);
        t.start();*/
			listaCtaCte.clear();
			listacheques.clear();
			List<Cartera> listac = new ArrayList<>();
			listac = daoCartera.getAll(" Codcli='" + cliente + "'");
			ArrayList<ItemCtaCte> tmpListaCtaCte = new ArrayList<ItemCtaCte>();
			ArrayList<Cheques> tmpListaCheque = new ArrayList<>();

			tmpListaCtaCte = parseaCtaCteconlista(listac);
			if (tmpListaCtaCte.size() > 0) {
				Iterator<ItemCtaCte> i = tmpListaCtaCte.iterator();
				while (i.hasNext()) {
					listaCtaCte.add(i.next());
				}
			}
			//Cheques nuevoch = new Cheques();



			refreshAdapter();
		} catch(Exception e)
			{
				e.printStackTrace();
			}

	}

	
	public void procesaResultado(String jsonCtaCte)
	{

		// Inicializo la lista
		listaCtaCte.clear();
		
		// Obtengo una lista Auxiliar en base al JsonRecibido
		ArrayList<ItemCtaCte> tmpListaCtaCte = new ArrayList<ItemCtaCte>();
		tmpListaCtaCte = parseaCtaCte(jsonCtaCte);
		if (tmpListaCtaCte.size()>0)
		{
			Iterator<ItemCtaCte> i = tmpListaCtaCte.iterator();
			while (i.hasNext())
			{
				listaCtaCte.add(i.next());
			}
		}
		
		refreshAdapter();
		
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		
		pantallaManagerConsultaCtaCte.cierraDialogoConsultCtaCte();
		String resultado = (String)  msg.obj;

		switch(msg.arg1)
		{
		case 1:
			procesaResultado(resultado);
			break;
		case 2:
			
			// Inicializo la lista
			listaCtaCte.clear();
			refreshAdapter();
			pantallaManagerConsultaCtaCte.muestraToastErrorDeConexion();
			break;
		}
		return false;
	}
	
	public  ArrayList<ItemCtaCte> parseaCtaCte(String jsonCtaCte)
	{
		JSONArray arrayJson;
		JSONObject jsObject;

		ItemCtaCte itemCtaCte = null;
		ArrayList<ItemCtaCte> listaCtaCte = new ArrayList<ItemCtaCte>();

		try
		{
			arrayJson = new JSONArray(jsonCtaCte);
			for (int x = 0; x < arrayJson.length() ;x++)
			{
				jsObject = arrayJson.getJSONObject(x);
				
				itemCtaCte = new ItemCtaCte();
				itemCtaCte.setConcepto(jsObject.getString("detalle"));
				itemCtaCte.setFecha(jsObject.getString("fecha"));
				itemCtaCte.setTc(jsObject.getString("tc"));
				itemCtaCte.setSucNroLetra(jsObject.getString("sucNroLetra"));
				itemCtaCte.setImporte(jsObject.getDouble("importe"));
				
				listaCtaCte.add(itemCtaCte);
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}		
		
		return listaCtaCte;
		
	}


	public  ArrayList<ItemCtaCte> parseaCtaCteconlista(List<Cartera>result)
	{


		ItemCtaCte itemCtaCte = null;
		ArrayList<ItemCtaCte> listaCtaCte = new ArrayList<ItemCtaCte>();
		Pagos pagos;

		try
		{
			for (Cartera a:result) {

				itemCtaCte = new ItemCtaCte();
				itemCtaCte.setConcepto(a.getNro_docm());
				itemCtaCte.setFecha(a.getFe_fac());
				itemCtaCte.setTc("");
				itemCtaCte.setValor_pago(0.00);
				itemCtaCte.setSucNroLetra("");
				PagosDetalles valor=daoPagosDetalles.getByIdnumero(a.getNro_docm());
				if(valor!=null){
					itemCtaCte.setValor_pago(valor.getPagoRegistrado());
					itemCtaCte.setTc(String.valueOf(valor.getIdpagodetalle()));
					idpago=valor.getIdpago();
				}



				itemCtaCte.setSucNroLetra("");
				if(a.getVcdo1_15()>0)
					itemCtaCte.setImporte(a.getVcdo1_15());
				if(a.getVcdo16_30()>0)
					itemCtaCte.setImporte(a.getVcdo16_30());
				if(a.getVcdo31_60()>0)
					itemCtaCte.setImporte(a.getVcdo31_60());
				if(a.getVcdo61mayor()>0)
				    itemCtaCte.setImporte(a.getVcdo61mayor());
				if(a.getCorriente()>0)
					itemCtaCte.setImporte(a.getCorriente());

				listaCtaCte.add(itemCtaCte);
			}
			if(idpago!=0){
				pagos=daoPagos.getById(idpago);
				//txtvalortotal.setText(String.valueOf(pagos.getValorTotalPago()));
				obj.setFotostomadas(pagos.getFotos());
				cmbformapagofactura.setSelection(obtenerPosicionItem(cmbformapagofactura, pagos.getFormaPago()));
				if(cmbformapagofactura.getSelectedItem().equals("Cheque")){
					lch.setVisibility(View.VISIBLE);
					btnagregar.setVisibility(View.VISIBLE);
					pantallaManagerConsultaCtaCte.cargarcheques(pagos.getChequePagos());
					double valorcheques=0;
					for (ChequePagos c:pagos.getChequePagos()
						 ) {
						if(c.getBanco().equals("Efectivo")){
							txtvalorefectivo.setText(String.valueOf(c.getValor()));
						}else{
							valorcheques=valorcheques+c.getValor();
						}
					}
					txtvalortotal.setText(String.valueOf(valorcheques));
				}else {
					listacheques.clear();
					lch.setVisibility(View.INVISIBLE);
					btnagregar.setVisibility(View.INVISIBLE);
				}
				if(obj.getFotostomadas()!=null || obj.getFotostomadas()=="")
					botonverfoto.setVisibility(View.VISIBLE);
				else
					botonverfoto.setVisibility(View.INVISIBLE);


			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return listaCtaCte;

	}


	public static int obtenerPosicionItem(Spinner spinner, String fruta) {
		//Creamos la variable posicion y lo inicializamos en 0
		int posicion = 0;
		//Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
		//que lo pasaremos posteriormente
		for (int i = 0; i < spinner.getCount(); i++) {
			//Almacena la posición del ítem que coincida con la búsqueda
			if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(fruta)) {
				posicion = i;
			}
		}
		//Devuelve un valor entero (si encontro una coincidencia devuelve la
		// posición 0 o N, de lo contrario devuelve 0 = posición inicial)
		return posicion;
	}
	public void tomarParametrosFechaDesdeHasta() {
		
		
		String fechaDesde = pantallaManagerConsultaCtaCte.obtieneFechaDesdeYYYYMMDD();
		String fechaHasta = pantallaManagerConsultaCtaCte.obtieneFechaHastaYYYYMMDD();
		
		if (fechaDesde.compareTo(fechaHasta)>0)
		{
			pantallaManagerConsultaCtaCte.muestraToastErrorDesdeMayorQueHasta();
			return;
		}
		
		consultarMovimientos(cliente.getCodigo(),fechaDesde,fechaHasta);
		
		pantallaManagerConsultaCtaCte.cierraDialogoConsultaFechaDesdeHasta();
	
	}
	public void guardarpagos(){
		long idPagos;
		long idPagosItem;
		long idChequesPagos;
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		String fecha = df.format(date);


		if(idpago>0){
			daoPagos.deleteAll("Idpago="+idpago);
			daoPagosDetalles.deleteAll("idpago="+idpago);
			daoChequePagos.deleteAll("idpago="+idpago);
		}

		Pagos pagos = new Pagos();
		double pagoefectivo=0;
		if(txtvalorefectivo.getText().toString().equals("")==false ) {
		pagoefectivo=Double.valueOf(txtvalorefectivo.getText().toString());
		}
		pagos.setValorTotalPago(obj.getValortotal()+pagoefectivo);
		pagos.setFecha(fecha);
		pagos.setFormaPago(cmbformapagofactura.getSelectedItem().toString());
		pagos.setCodCliente(cliente.getCodigo());
		pagos.setFotos(obj.getFotostomadas());
		pagos.setIdvendedor(codigoVendedor);
		idPagos=daoPagos.save(pagos);
		PagosDetalles pagosDetalles= new PagosDetalles();
		ItemCtaCte  itemCtaCte= new ItemCtaCte();
		ChequePagos chequePagos = new ChequePagos();
		Cheques cheques=new Cheques();
		if (idPagos!=-1) {
			Iterator<ItemCtaCte> i = listaCtaCte.iterator();
			while (i.hasNext()) {
				itemCtaCte = i.next();
				if(itemCtaCte.getValor_pago()>0) {
					pagosDetalles.setTotalFactura(itemCtaCte.getImporte());
					pagosDetalles.setPagoRegistrado(itemCtaCte.getValor_pago());
					pagosDetalles.setNumfactura(itemCtaCte.getConcepto());
					pagosDetalles.setIdpago(idPagos);
					idPagosItem = daoPagosDetalles.save(pagosDetalles);
					if (idPagosItem == -1) {
						utilDialogos.muestraToastGenerico(a, "Error al grabar pagos..", false);
						// Si ocurrio un error elimino
						daoPagos.delete(pagos);
						daoPagosDetalles.deleteByIdPago(idPagos);
						return;
					}
				}

			}
			Iterator<Cheques> c=listacheques.iterator();
			while (c.hasNext()){
				cheques=c.next();
				if(cheques.getNumcheque().equals("")==false) {
					chequePagos.setBanco(cheques.getBanco());
					chequePagos.setFecha(cheques.getFecha());
					chequePagos.setValor(cheques.getValor());
					chequePagos.setNumerocheque(cheques.getNumcheque());
					chequePagos.setIdpago(idPagos);
					idChequesPagos = daoChequePagos.save(chequePagos);
					if (idChequesPagos == -1) {
						utilDialogos.muestraToastGenerico(a, "Error al grabar pagos..", false);
						// Si ocurrio un error elimino
						daoPagos.delete(pagos);
						daoPagosDetalles.deleteByIdPago(idPagos);
						daoChequePagos.deleteByIdPago(idPagos);
						return;
					}
				}

			}
			if(txtvalorefectivo.getText().toString().equals("")==false) {
				chequePagos.setBanco("Efectivo");
				chequePagos.setFecha(fecha);
				chequePagos.setValor(Double.valueOf(txtvalorefectivo.getText().toString()));
				chequePagos.setNumerocheque("Efectivo");
				chequePagos.setIdpago(idPagos);
				daoChequePagos.save(chequePagos);
			}
			utilDialogos.muestraToastGenerico(a, "Pagos guardados con Exitos..", false);
			obj.setFotostomadas("");
			obj.setValortotal(0.00);
			obj.setValoringresado(0.0);
			txtvalortotal.setText("");
			Intent iprincipal = null;
			iprincipal = new Intent(a,ar.com.syswork.sysmobile.pconsultaclientes.ActivityConsultaClientes.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			iprincipal.putExtra("origenDeLaConsulta", AppSysMobile.DESDE_CONSULTA_DE_CUENTA_CORRIENTE);
			a.startActivity(iprincipal);
			a.finish();

		}



	}
	
	
}