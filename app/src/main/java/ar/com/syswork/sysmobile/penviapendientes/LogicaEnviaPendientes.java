package ar.com.syswork.sysmobile.penviapendientes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Handler.Callback;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.widget.Toast;

import com.google.gson.JsonObject;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.Tracking.JavaRestClient;
import ar.com.syswork.sysmobile.Tracking.User;
import ar.com.syswork.sysmobile.daos.DaoArticulo;
import ar.com.syswork.sysmobile.daos.DaoChequePagos;
import ar.com.syswork.sysmobile.daos.DaoCliente;
import ar.com.syswork.sysmobile.daos.DaoCodigosNuevos;
import ar.com.syswork.sysmobile.daos.DaoConfiguracion;
import ar.com.syswork.sysmobile.daos.DaoInventario;
import ar.com.syswork.sysmobile.daos.DaoPagos;
import ar.com.syswork.sysmobile.daos.DaoPagosDetalles;
import ar.com.syswork.sysmobile.daos.DaoPedido;
import ar.com.syswork.sysmobile.daos.DaoPedidoItem;
import ar.com.syswork.sysmobile.daos.DaoToken;
import ar.com.syswork.sysmobile.daos.DaoVisitasUio;
import ar.com.syswork.sysmobile.daos.Daoinventariodetalles;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Articulo;
import ar.com.syswork.sysmobile.entities.ChequePagos;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.entities.CodigosNuevos;
import ar.com.syswork.sysmobile.entities.ConfiguracionDB;
import ar.com.syswork.sysmobile.entities.Inventario;
import ar.com.syswork.sysmobile.entities.Pagos;
import ar.com.syswork.sysmobile.entities.PagosDetalles;
import ar.com.syswork.sysmobile.entities.Pedido;
import ar.com.syswork.sysmobile.entities.PedidoItem;
import ar.com.syswork.sysmobile.entities.Token;
import ar.com.syswork.sysmobile.entities.VisitasUio;
import ar.com.syswork.sysmobile.pconsultactacte.ListenerConsultaCtaCte;
import ar.com.syswork.sysmobile.entities.inventariodetalles;
import ar.com.syswork.sysmobile.pconsultagenerica.detalle.ActivityConsultaGenericaDetalle;
import ar.com.syswork.sysmobile.pconsultagenerica.detalle.envioclientenuevo;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class LogicaEnviaPendientes implements Callback {

	private DataManager dm;
	private AppSysMobile app;
	private DaoPedido daoPedido;
	private DaoCodigosNuevos daoCodigosNuevos;
	private DaoCliente daoCliente;
	private DaoPedidoItem daoPedidoItem;
	private DaoPagos daoPagos;
	private DaoToken daoToken;
	private DaoPagosDetalles daoPagosDetalles;
	private DaoChequePagos daoChequePagos;
	private DaoInventario daoInventario;
	private DaoArticulo daoArticulo;
	private Daoinventariodetalles daoinventariodetalles;
	private DaoConfiguracion daoConfiguracion;
	private DaoVisitasUio daoVisitasUio;
	public static String EnvioOpcion = "";

	public UUID URI=null;

	private Activity a;
	private JSONArray jsonArrayPedidosL;
	private JSONArray jsonArrayDevolucionesL;
	private PantallaManagerEnviaPendientes pantallaManagerEnviaPendientes;

	private boolean desdeCargaDePedidos = false;
	private long idPedidoEnviar = -1;
	private long idPagosEnviar = -1;
	private long idInventarioEnviar = -1;
	private long idVisitasEnviar = -1;

	List<Pedido> listaPedidos;
	List<Pagos> listaPagos;
	List<Inventario> listaInventario;
	List<VisitasUio> listavVisitasUios;

	public LogicaEnviaPendientes(Activity a, PantallaManagerEnviaPendientes pantallaManagerEnviaPendientes) {
		this.a = a;
		this.pantallaManagerEnviaPendientes = pantallaManagerEnviaPendientes;

		app = (AppSysMobile) this.a.getApplication();
		dm = app.getDataManager();
		daoPedido = dm.getDaoPedido();
		daoPedidoItem = dm.getDaoPedidoItem();
		daoPagos = dm.getDaoPagos();
		daoCliente = dm.getDaoCliente();
		daoConfiguracion=dm.getDaoConfiguracion();
		daoToken = dm.getDaoToken();
		daoArticulo=dm.getDaoArticulo();
		daoCodigosNuevos=dm.getDaoCodigosNuevos();
		ValidToken();
		daoPagosDetalles = dm.getDaoPagosDetalles();
		daoChequePagos = dm.getDaoChequePagos();
		daoInventario = dm.getDaoInventario();
		daoinventariodetalles = dm.getDaoinventariodetalles();
		daoVisitasUio=dm.getDaoVisitasUio();

	}

	public void enviarPagosPendientes() {

		//String jSonPedidos = obtieneJsonPedidos();
		String jSonPagos = obtieneJsonPagos();
		EnvioOpcion = "pagos";

		if (jSonPagos.equals("")) {
			pantallaManagerEnviaPendientes.mostrarMensajeNoHayRegistrosPagos();
			return;
		}

		pantallaManagerEnviaPendientes.muestraDialogoEnviaPendientes();
		pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.conectando));
		Handler hp = new Handler(this);
		ThreadEnvioPagos tep = new ThreadEnvioPagos(hp, jSonPagos);
		Thread t1 = new Thread(tep);
		t1.start();
	}

	public void enviarInventarioPendientes() {

		//String jSonPedidos = obtieneJsonPedidos();
		String jSonInventario = obtieneJsonInventario("I");
		EnvioOpcion = "devoluciones";

		if (jSonInventario.equals("")) {
			pantallaManagerEnviaPendientes.mostrarMensajeNoHayRegistrosInventario();
			return;
		}

		pantallaManagerEnviaPendientes.muestraDialogoEnviaPendientes();
		pantallaManagerEnviaPendientes.ChkEnviarPagosPendientes.setText("Devoluciones Pendientes");
		pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.conectando));

		Handler hp = new Handler(this);
		ThredDevolucionesIndustrial tep = new ThredDevolucionesIndustrial(hp, jsonArrayDevolucionesL.toString());
		Thread t1 = new Thread(tep);
		t1.start();



	}

	public void enviarPedidosPendientes() {

		String jSonPedidos = obtieneJsonPedidos("I");
		//String jSonPagos=obtieneJsonPagos();
		List<Cliente> lstcClientes = new ArrayList<>();
		lstcClientes = daoCliente.getAll(" estadoenvio = 'P'");
		String token = "";
		for (Token a : daoToken.getAll("")
		) {
			token = a.getToken();
		}
		if(lstcClientes.size()>0) {
			for (Cliente c : lstcClientes
			) {
				envioclientenuevo fetchJsonTask = new envioclientenuevo(this.a);
				fetchJsonTask.execute(obtieneJsonCliente(c.getCodigo()), token);

			}
			Toast.makeText(app, "Enviado clientes nuevos..!! Enviar pedidos nuevamente ", Toast.LENGTH_SHORT).show();

			return;
		}else {

			if (jSonPedidos.equals("")) {
				pantallaManagerEnviaPendientes.mostrarMensajeNoHayRegistrosPendientes();
				return;
			}
			pantallaManagerEnviaPendientes.muestraDialogoEnviaPendientes();
			pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.conectando));

			EnvioOpcion="servidor";
			Handler hp = new Handler(this);
			ThreadEnvioIndustrial teI = new ThreadEnvioIndustrial(hp, jsonArrayPedidosL.toString());
			Thread tI = new Thread(teI);
			tI.start();






		}

	}
	public void enviarVisistasPendientes() {

		String jSonVisistas = obtieneJsonVisitas();
		//String jSonPagos=obtieneJsonPagos();
		EnvioOpcion = "visitas";
		if (jSonVisistas.equals("")) {
			pantallaManagerEnviaPendientes.mostrarMensajeNoHayRegistrosVisistas();
			return;
		}
		pantallaManagerEnviaPendientes.muestraDialogoEnviaPendientes();
		pantallaManagerEnviaPendientes.ChkEnviarPagosPendientes.setText("Visitas Pendientes");
		pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.conectando));
		Handler h = new Handler(this);
		ThreadEnvioVisitas te = new ThreadEnvioVisitas(h, jSonVisistas);
		Thread t = new Thread(te);
		t.start();


	}


	private String obtieneJsonVisitas(){
		String jSonVisitas = "";
		JSONArray jsonArrayVisitas = null;
		JSONObject jsonVisitas;
		VisitasUio visitasUio;
		if (idVisitasEnviar == -1)
			listavVisitasUios = daoVisitasUio.getAll("");
		else
			listavVisitasUios = daoVisitasUio.getAll(" id = " + idVisitasEnviar);
		if (listavVisitasUios.size() > 0) {
			jsonArrayVisitas = new JSONArray();
			Iterator<VisitasUio> i = listavVisitasUios.iterator();

			while (i.hasNext()) {
				visitasUio = i.next();

				jsonVisitas = new JSONObject();
				try {


					//jsonVisitas.put("id", visitasUio.getId());
					jsonVisitas.put("codcliente", visitasUio.getCodcliente());
					jsonVisitas.put("codvendedor", visitasUio.getCodvendedor());
					jsonVisitas.put("fechavisita", visitasUio.getFechavisita());
					jsonVisitas.put("Latitud", visitasUio.getLatitud());
					jsonVisitas.put("Longitud", visitasUio.getLongitud());
					jsonVisitas.put("Linkfotoexterior", visitasUio.getLinkfotoexterior());
					jsonVisitas.put("Compro", visitasUio.getRealizapedido());
					jsonVisitas.put("Observacion", visitasUio.getObservaciones());
					jsonVisitas.put("estado", visitasUio.getEstado()==null?"":visitasUio.getEstado());

					jsonArrayVisitas.put(jsonVisitas);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			jSonVisitas = jsonArrayVisitas.toString();
		}
		return jSonVisitas;
	}

	private String obtieneJsonInventario(String op) {
		String jSonInventario = "";
		JSONArray jsonArrayInventario = null;

		JSONObject jsonInventario;
		jsonArrayDevolucionesL=null;
		jsonArrayDevolucionesL=new JSONArray();
		Inventario inventario;
		if (idInventarioEnviar == -1) {
			if (op.equals("M"))
				listaInventario = daoInventario.getAll(" enviomardis='F'");
			if (op.equals("I"))
				listaInventario = daoInventario.getAll(" envioindustrial='F'");

		} else
			listaInventario = daoInventario.getAll(" enviomardis='F' and envioindustrial='F' and id = " + idInventarioEnviar);
		if (listaInventario.size() > 0) {
			jsonArrayInventario = new JSONArray();
			Iterator<Inventario> i = listaInventario.iterator();

			while (i.hasNext()) {
				inventario = i.next();

				jsonInventario = new JSONObject();
				try {


					jsonInventario.put("id", 0);
					jsonInventario.put("codcliente", inventario.getCodcliente());
					jsonInventario.put("codvendedor", inventario.getCodvendedor());
					jsonInventario.put("fechainventario", inventario.getFechainventario());
					jsonInventario.put("uri", inventario.getCodigomardis());
					if(inventario.getId() != null )
						jsonInventario.put("inventariodetalles", obtieneJsonDetalleDeInventario(inventario.getId(),op));
					else
						jsonInventario.put("inventariodetalles", "");
					jsonArrayInventario.put(jsonInventario);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			jSonInventario = jsonArrayInventario.toString();
		}
		return jSonInventario;
	}

	private JSONArray obtieneJsonDetalleDeInventario(long idInvnetario,String op) {

		inventariodetalles _Inventariodetalles;
		JSONObject jsoDetalleItem;
		JSONArray jsa = null;

		JSONObject jsonObjectDevolucionesL;
		Inventario invactual=daoInventario.getById(idInvnetario);
		List<inventariodetalles> listaItemsInventariodetalles = daoinventariodetalles.getAll(" idinventario = " + idInvnetario);

		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddhhmmss");
		String fecha = df.format(date);
		if (listaItemsInventariodetalles.size() > 0) {
			jsa = new JSONArray();
			Iterator<inventariodetalles> i = listaItemsInventariodetalles.iterator();

			while (i.hasNext()) {
				_Inventariodetalles = i.next();
				jsoDetalleItem = new JSONObject();
				jsonObjectDevolucionesL= new JSONObject();
				try {

					jsoDetalleItem.put("id", 0);
					jsoDetalleItem.put("idinventario", 0);
					jsoDetalleItem.put("codproducto", _Inventariodetalles.getCodproducto());
					jsoDetalleItem.put("valor", _Inventariodetalles.getValor());
					jsoDetalleItem.put("unidad", _Inventariodetalles.getUnidad());
					jsa.put(jsoDetalleItem);

					Cliente c= daoCliente.getByKey(invactual.getCodcliente());
					Articulo art=daoArticulo.getByKey(_Inventariodetalles.getCodproducto());
					jsonObjectDevolucionesL.put("d_DEVOLUCION",invactual.getCodigounico());
					jsonObjectDevolucionesL.put("d_ORDEN",_Inventariodetalles.getId());
					jsonObjectDevolucionesL.put("d_FECHA",Integer.valueOf(fecha));
					jsonObjectDevolucionesL.put("d_FACTURA",0000);
					jsonObjectDevolucionesL.put("d_PRODUCTO",_Inventariodetalles.getCodproducto());
					jsonObjectDevolucionesL.put("d_CANTIDAD",_Inventariodetalles.getValor());
					jsonObjectDevolucionesL.put("d_PRECIO",art.getPrecio9());
					jsonObjectDevolucionesL.put("d_CLIENTE",c.getCodigoOpcional());
					jsonObjectDevolucionesL.put("d_VENDEDOR",invactual.getCodvendedor());
					jsonObjectDevolucionesL.put("d_ESTADO",0);
					jsonObjectDevolucionesL.put("d_PEDIDO_MARDIS",invactual.getCodigomardis());
					jsonArrayDevolucionesL.put(jsonObjectDevolucionesL);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		}


		return jsa;
	}

	private String obtieneJsonPagos() {
		String jSonPagos = "";
		JSONArray jsonArrayPagos = null;

		JSONObject jsonPagos;

		Pagos pagos;
		if (idPagosEnviar == -1)
			listaPagos = daoPagos.getAll("");
		else
			listaPagos = daoPagos.getAll(" Idpago = " + idPagosEnviar);
		if (listaPagos.size() > 0) {
			jsonArrayPagos = new JSONArray();
			Iterator<Pagos> i = listaPagos.iterator();

			while (i.hasNext()) {
				pagos = i.next();

				jsonPagos = new JSONObject();
				try {
					String file = pagos.getFotos();
					File mi_foto = new File(file);
					String encodeFileToBase64Binary = getStringFile(mi_foto);

					jsonPagos.put("Idpago", pagos.getIdpago());
					jsonPagos.put("CodCliente", pagos.getCodCliente());
					jsonPagos.put("ValorTotalPago", pagos.getValorTotalPago());
					jsonPagos.put("FormaPago", pagos.getFormaPago());
					jsonPagos.put("Fecha", pagos.getFecha());
					jsonPagos.put("foto", encodeFileToBase64Binary.replace("\n", ""));
					jsonPagos.put("idvendedor", pagos.getIdvendedor());
					jsonPagos.put("PagosDetalles", obtieneJsonDetalleDePagos(pagos.getIdpago()) == null ? "" : obtieneJsonDetalleDePagos(pagos.getIdpago()));
					jsonPagos.put("ChequePagos", obtieneJsonDetalleChequePagos(pagos.getIdpago()) == null ? "" : obtieneJsonDetalleChequePagos(pagos.getIdpago()));

					jsonArrayPagos.put(jsonPagos);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			jSonPagos = jsonArrayPagos.toString();
		}
		return jSonPagos;
	}

	public String getStringFile(File f) {
		InputStream inputStream = null;
		String encodedFile = "", lastVal;
		try {
			inputStream = new FileInputStream(f.getAbsolutePath());

			byte[] buffer = new byte[512];//specify the size to allow
			int bytesRead;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				output64.write(buffer, 0, bytesRead);
			}
			output64.close();
			encodedFile = output.toString();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lastVal = encodedFile;
		return lastVal;
	}




	private String obtieneJsonPedidos(String op) {
		String jSonPedidos = "";
		JSONArray jsonArrayPedidos = null;

		JSONObject jsonPedido;

		Pedido pedido;
		jsonArrayPedidosL=null;
		jsonArrayPedidosL=new JSONArray();
		if (idPedidoEnviar == -1) {
			if (op.equals("M"))
				listaPedidos = daoPedido.getAll(" transferido = 0 and enviomardis='F'");
			if (op.equals("I"))
				listaPedidos = daoPedido.getAll(" transferido = 0 and envioindustrial='F'");

		}
		else
			listaPedidos = daoPedido.getAll(" enviomardis='F' and envioindustrial='F' and _id = " + idPedidoEnviar);

		if (listaPedidos.size() > 0) {

			jsonArrayPedidos = new JSONArray();

			Iterator<Pedido> i = listaPedidos.iterator();
			while (i.hasNext()) {
				pedido = i.next();

				jsonPedido = new JSONObject();
				try {


						jsonPedido.put("id", 0);
						jsonPedido.put("codCliente", pedido.getCodCliente());
						jsonPedido.put("idVendedor", pedido.getIdVendedor());
						jsonPedido.put("fecha", pedido.getFecha());
						jsonPedido.put("totalNeto", pedido.getTotalNeto());
						jsonPedido.put("totalFinal", pedido.getTotalFinal());
						jsonPedido.put("facturar", pedido.isFacturar());
						jsonPedido.put("incluirEnReparto", pedido.isIncluirEnReparto());
						jsonPedido.put("p_PEDIDO_MARDIS", pedido.getCodpedidomardis());
						jsonPedido.put("pedidosItems", obtieneJsonDetalleDePedido(pedido.getIdPedido()));


					jsonArrayPedidos.put(jsonPedido);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			jSonPedidos = jsonArrayPedidos.toString();
		}
		return jSonPedidos;
	}

	private JSONArray obtieneJsonDetalleDePedido(long idPedido) {
		PedidoItem pedidoItem;
		JSONObject jsoPedidoItem;
		JSONArray jsa = null;

		JSONObject jsonObjectPedidoL;
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddhhmmss");
		String fecha = df.format(date);

		List<PedidoItem> listaItemsPedidos = daoPedidoItem.getAll(" idPedido = " + idPedido);
		Pedido localPedido=daoPedido.getById((int)idPedido);


		if (listaItemsPedidos.size() > 0) {
			jsa = new JSONArray();

			Iterator<PedidoItem> i = listaItemsPedidos.iterator();
			while (i.hasNext()) {
				pedidoItem = i.next();
				jsoPedidoItem = new JSONObject();
				jsonObjectPedidoL= new JSONObject();

				try {
					jsoPedidoItem.put("id",0);
					jsoPedidoItem.put("idPedido",0);
					jsoPedidoItem.put("idArticulo", pedidoItem.getIdArticulo());
					jsoPedidoItem.put("cantidad", pedidoItem.getCantidad());
					jsoPedidoItem.put("importeUnitario", pedidoItem.getImporteUnitario());
					jsoPedidoItem.put("porcDescuento", pedidoItem.getPorcDto());
					jsoPedidoItem.put("total", pedidoItem.getTotal());
					jsoPedidoItem.put("transferido", 0);
					jsoPedidoItem.put("ppago", pedidoItem.getPpago());
					jsoPedidoItem.put("nespecial", pedidoItem.getNespecial());
					jsoPedidoItem.put("formapago", pedidoItem.getFormaPago());
					jsoPedidoItem.put("unidad", pedidoItem.getUnidcajas());


					Cliente c= daoCliente.getByKey(localPedido.getCodCliente());
					Articulo art=daoArticulo.getByKey(pedidoItem.getIdArticulo());
					//armar json para envio servidor industrial
					jsonObjectPedidoL.put("P_PEDIDO",localPedido.getCodigounico());
					jsonObjectPedidoL.put("P_NUEVO_CLIENTE",localPedido.getCodCliente().equals(c.getCodigoOpcional())?"":c.getCodigoOpcional());
					jsonObjectPedidoL.put("P_ORDEN", pedidoItem.getIdPedidoItem());
					jsonObjectPedidoL.put("P_FECHA",Integer.valueOf(fecha));
					jsonObjectPedidoL.put("P_PRODUCTO",pedidoItem.getIdArticulo());
					jsonObjectPedidoL.put("P_PRECIO",art.getPrecio9());
					jsonObjectPedidoL.put("P_CANTIDAD",pedidoItem.getCantidad());
					jsonObjectPedidoL.put("P_ESTADO",0);
					jsonObjectPedidoL.put("P_FILLER","");
					jsonObjectPedidoL.put("p_CLIENTE",!localPedido.getCodCliente().equals(c.getCodigoOpcional())?c.getCodigoOpcional():localPedido.getCodCliente());
					jsonObjectPedidoL.put("p_PEDIDO_MARDIS",localPedido.getCodpedidomardis());
					jsonObjectPedidoL.put("P_VENDEDOR",Integer.valueOf(localPedido.getIdVendedor().replace("V","")));


					jsonArrayPedidosL.put(jsonObjectPedidoL);

					jsa.put(jsoPedidoItem);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		}

		return jsa;
	}


	private JSONArray obtieneJsonDetalleDePagos(long idPago) {

		PagosDetalles pagosDetalles;
		JSONObject jsoPagosDetalles;
		JSONArray jsa = null;

		List<PagosDetalles> listapagosdetalles = daoPagosDetalles.getAll(" idpago = " + idPago);
		if (listapagosdetalles.size() > 0) {
			jsa = new JSONArray();
			Iterator<PagosDetalles> i = listapagosdetalles.iterator();
			while (i.hasNext()) {
				pagosDetalles = i.next();
				jsoPagosDetalles = new JSONObject();

				try {
					if (pagosDetalles.getPagoRegistrado() > 0) {
						jsoPagosDetalles.put("idpago", pagosDetalles.getIdpago());
						jsoPagosDetalles.put("Numfactura", pagosDetalles.getNumfactura());
						jsoPagosDetalles.put("TotalFactura", pagosDetalles.getTotalFactura());
						jsoPagosDetalles.put("PagoRegistrado", pagosDetalles.getPagoRegistrado());
						jsa.put(jsoPagosDetalles);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		}
		return jsa;
	}

	private JSONArray obtieneJsonDetalleChequePagos(long idPago) {

		ChequePagos chequePagos;
		JSONObject jsoChequepagos;
		JSONArray jsa = null;

		List<ChequePagos> listachequepagos = daoChequePagos.getAll(" idpago = " + idPago);
		if (listachequepagos.size() > 0) {
			jsa = new JSONArray();
			Iterator<ChequePagos> i = listachequepagos.iterator();
			while (i.hasNext()) {
				chequePagos = i.next();
				jsoChequepagos = new JSONObject();

				try {
					if (chequePagos.getValor() > 0) {
						jsoChequepagos.put("idpago", chequePagos.getIdpago());
						jsoChequepagos.put("numerocheque", chequePagos.getNumerocheque());
						jsoChequepagos.put("banco", chequePagos.getBanco());
						jsoChequepagos.put("valor", chequePagos.getValor());
						jsoChequepagos.put("fecha", chequePagos.getFecha());
						jsa.put(jsoChequepagos);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		}
		return jsa;
	}


	@Override
	public boolean handleMessage(Message msg) {

		// RECIBO ERRORES
		String resultado = (String) msg.obj;
		if (EnvioOpcion.equals("servidor")) {
			switch (msg.arg1) {
				// recibo 0/1
				case 1:

					// Si esta Ok, muestro aviso
					if (resultado.equals("true")) {
						// Recorre la lista de pedidos y elimina el pedido y los items
						pantallaManagerEnviaPendientes.seteaValorchkEnviarPendientes(true);
						pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seCrearonLosPedidosExitosamente));
						pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
						pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);

						if (desdeCargaDePedidos) {
							pantallaManagerEnviaPendientes.cerrarDialogoSincronizacion();
							pantallaManagerEnviaPendientes.cerrarActivity();
						} else {
							pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
						}




						/*modifica para enviados a industrial*/
						for (int i = 0; i < jsonArrayPedidosL.length(); i++) {
							try {
								JSONObject jsonObject = jsonArrayPedidosL.getJSONObject(i);
								if (jsonObject.has("P_PEDIDO")) {
									String codigounico = jsonObject.getString("P_PEDIDO");
									// Aquí actualizamos estado de pedidos Industrial
									daoPedido.updatecodigoindustrial(codigounico);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						EnvioOpcion="pedidos";
						String jSonPedidosmardis = obtieneJsonPedidos("M");
						Handler h = new Handler(this);
						ThreadEnvio te = new ThreadEnvio(h, jSonPedidosmardis);
						Thread t = new Thread(te);
						t.start();


					} else {
						// Muestro toast
						pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seProdujoUnErrorAlSubirLosPedidos)+"Servidor Industrial");
						pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
						pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
						pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
					}
					break;

				// hubo error de comunicaciones
				case 2:

					pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seProdujoUnErrorDeComunicacion) + "( " + resultado + ") Servidor industrial");
					pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
					pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
					pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
					break;

			}

			return false;
		}
		if (EnvioOpcion.equals("pedidos")) {
			switch (msg.arg1) {
				// recibo 0/1
				case 1:

					// Si esta Ok, muestro aviso
					if (resultado.equals("true")) {
						// Recorre la lista de pedidos y elimina el pedido y los items
						pantallaManagerEnviaPendientes.seteaValorchkEnviarPendientes(true);
						pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seCrearonLosPedidosExitosamente));
						pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
						pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);

						if (desdeCargaDePedidos) {
							pantallaManagerEnviaPendientes.cerrarDialogoSincronizacion();
							pantallaManagerEnviaPendientes.cerrarActivity();
						} else {
							pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
						}
						/*modifica para enviados a industrial*/
						for (int i = 0; i < jsonArrayPedidosL.length(); i++) {
							try {
								JSONObject jsonObject = jsonArrayPedidosL.getJSONObject(i);
								if (jsonObject.has("P_PEDIDO")) {
									String codigounico = jsonObject.getString("P_PEDIDO");
									// Aquí actualizamos estado de pedidos Industrial
									daoPedido.updatecodigomardis(codigounico);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						//eliminaPedidos();
						//EnvioOpcion="servidor";
						/*Handler hp = new Handler(this);
						ThreadEnvioIndustrial teI = new ThreadEnvioIndustrial(hp, jsonArrayPedidosL.toString());
						Thread tI = new Thread(teI);
						tI.start();*/
					} else {
						// Muestro toast
						pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seProdujoUnErrorAlSubirLosPedidos)+"mardis");
						pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
						pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
						pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
					}
					break;

				// hubo error de comunicaciones
				case 2:

					pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seProdujoUnErrorDeComunicacion) + "( " + resultado + ")");
					pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
					pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
					pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
					break;

						}

			return false;
		}
		if (EnvioOpcion.equals("pagos")) {
			switch (msg.arg1) {
				// recibo 0/1
				case 1:

					// Si esta Ok, muestro aviso
					if (resultado.equals("true")) {
						// Recorre la lista de pedidos y elimina el pedido y los items
						pantallaManagerEnviaPendientes.seteaValorchkEnviarPagosPendientes(true);
						pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seCrearonLosPagosExitosamente));
						pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
						pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);

						if (desdeCargaDePedidos) {
							pantallaManagerEnviaPendientes.cerrarDialogoSincronizacion();
							pantallaManagerEnviaPendientes.cerrarActivity();
						} else {
							pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
						}

						eliminaPagos();
					} else {
						// Muestro toast
						pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seProdujoUnErrorAlSubirLosPedidos));
						pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
						pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
						pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
					}
					break;

				// hubo error de comunicaciones
				case 2:

					pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seProdujoUnErrorDeComunicacion) + "( " + resultado + ")");
					pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
					pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
					pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
					break;
			}
			return false;
		}
		if (EnvioOpcion.equals("devoluciones")) {
			switch (msg.arg1) {
				// recibo 0/1
				case 1:

					// Si esta Ok, muestro aviso
					if (resultado.equals("true")) {
						// Recorre la lista de pedidos y elimina el pedido y los items
						pantallaManagerEnviaPendientes.seteaValorchkEnviarPagosPendientes(true);
						pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio("Devoluciones Enviadas");
						pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
						pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);

						if (desdeCargaDePedidos) {
							pantallaManagerEnviaPendientes.cerrarDialogoSincronizacion();
							pantallaManagerEnviaPendientes.cerrarActivity();
						} else {
							pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
						}

						/*modifica para enviados a industrial*/
						for (int i = 0; i < jsonArrayDevolucionesL.length(); i++) {
							try {
								JSONObject jsonObject = jsonArrayDevolucionesL.getJSONObject(i);
								if (jsonObject.has("d_DEVOLUCION")) {
									String codigounico = jsonObject.getString("d_DEVOLUCION");
									// Aquí actualizamos estado de pedidos Industrial
									daoInventario.updatecodigoindustrial(codigounico);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						EnvioOpcion="inventario";
						String jSonInventariomardis = obtieneJsonInventario("M");
						Handler hp = new Handler(this);
						ThreadEnvioinvnetario tep = new ThreadEnvioinvnetario(hp, jSonInventariomardis);
						Thread t11 = new Thread(tep);
						t11.start();
						//eliminaInvnetario();
					} else {
						// Muestro toast
						pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio("Se produjo un error al enviar las devoluciones");
						pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
						pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
						pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
					}
					break;

				// hubo error de comunicaciones
				case 2:

					pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seProdujoUnErrorDeComunicacion) + "( " + resultado + ")");
					pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
					pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
					pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
					break;
			}
			return false;
		}
		if (EnvioOpcion.equals("inventario")) {
			switch (msg.arg1) {
				// recibo 0/1
				case 1:

					// Si esta Ok, muestro aviso
					if (resultado.equals("true")) {
						// Recorre la lista de pedidos y elimina el pedido y los items
						pantallaManagerEnviaPendientes.seteaValorchkEnviarPagosPendientes(true);
						pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio("Devoluciones Enviadas");
						pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
						pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);

						if (desdeCargaDePedidos) {
							pantallaManagerEnviaPendientes.cerrarDialogoSincronizacion();
							pantallaManagerEnviaPendientes.cerrarActivity();
						} else {
							pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
						}

						/*modifica para enviados a industrial*/
						for (int i = 0; i < jsonArrayDevolucionesL.length(); i++) {
							try {
								JSONObject jsonObject = jsonArrayDevolucionesL.getJSONObject(i);
								if (jsonObject.has("d_DEVOLUCION")) {
									String codigounico = jsonObject.getString("d_DEVOLUCION");
									// Aquí actualizamos estado de pedidos Industrial
									daoInventario.updatecodigomardis(codigounico);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						//eliminaInvnetario();
					} else {
						// Muestro toast
						pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio("Error al enviar las devoluciones mardis");
						pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
						pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
						pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
					}
					break;

				// hubo error de comunicaciones
				case 2:

					pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seProdujoUnErrorDeComunicacion) + "( " + resultado + ")");
					pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
					pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
					pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
					break;
			}
			return false;
		}
		if (EnvioOpcion.equals("visitas")) {
			switch (msg.arg1) {
				// recibo 0/1
				case 1:

					// Si esta Ok, muestro aviso
					if (resultado.equals("true")) {
						// Recorre la lista de pedidos y elimina el pedido y los items
						pantallaManagerEnviaPendientes.seteaValorchkEnviarPagosPendientes(true);
						pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio("Visita Enviado");
						pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
						pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);

						if (desdeCargaDePedidos) {
							pantallaManagerEnviaPendientes.cerrarDialogoSincronizacion();
							pantallaManagerEnviaPendientes.cerrarActivity();
						} else {
							pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
						}

						eliminavisitas();
					} else {
						// Muestro toast
						pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio("Error al enviar visitas mardis");
						pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
						pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
						pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
					}
					break;

				// hubo error de comunicaciones
				case 2:

					pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seProdujoUnErrorDeComunicacion) + "( " + resultado + ")");
					pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
					pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
					pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
					break;
			}
			return false;
		}


		return  false;

	}


	
	
	private void eliminaPedidos()
	{
		if (idPedidoEnviar != -1)
		{
			daoPedido.deleteAll("_id = " + idPedidoEnviar);
			daoPedidoItem.deleteByIdPedido(idPedidoEnviar);
		}
		else
		{
			daoPedido.deleteAll("");
			daoPedidoItem.deleteAll("");
		}
	}





	private void eliminaPagos()
	{

			daoPagos.deleteAll("");
			daoPagosDetalles.deleteAll("");
			daoChequePagos.deleteAll("");

	}
	private void eliminaInvnetario()
	{

		daoInventario.deleteAll("");
		daoinventariodetalles.deleteAll("");

	}
	private void eliminavisitas()
	{

		daoVisitasUio.deleteAll("");


	}
	public void setDesdeCargaDePedidos(boolean desdeCargaDePedidos) {
		this.desdeCargaDePedidos = desdeCargaDePedidos;
	}

	
	public void setIdPedidoEnviar(long idPedidoEnviar) 
	{
		this.idPedidoEnviar = idPedidoEnviar;
	}
	public void setIdPagosEnviar(long idPagosEnviar)
	{
		this.idPagosEnviar = idPagosEnviar;
	}
	private String obtieneJsonCliente(String codigo) {
		String jSonCliente1 = "";
		JSONObject jsonCliente;
		jsonCliente= new JSONObject();
		String aconnt="0";
		for (ConfiguracionDB da : daoConfiguracion.getAll("")
		) {
			aconnt=da.getId_cuenta();
		}
		try {
			jsonCliente.put("account", Integer.valueOf(aconnt));
			jsonCliente.put("iduser", "1");
			jsonCliente.put("option", 1);
			jsonCliente.put("_route", obtieneJsonDetalleDeRuta(codigo));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonCliente.toString();
	}
	private void ValidToken(){
		JavaRestClient tarea = new JavaRestClient(this.a);
		String date="";
		for (Token a:daoToken.getAll("")
		) {
			date= a.getFecha();
		}

		try {
			Date datetoken=new SimpleDateFormat("dd-MM-yyyy").parse(date);
			Date dateNow = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(datetoken); // Configuramos la fecha que se recibe
			calendar.add(Calendar.DAY_OF_YEAR, 5);  // numero de días a añadir, o restar en caso de días<0
			Date ExpToke=calendar.getTime();
			if(dateNow.compareTo(ExpToke)>0 ){
				User _user =new User();
				tarea.getToken2(_user,this.a);
			}

		}catch (Exception e){
			User _user =new User();
			tarea.getToken2(_user,this.a);

		}
	}
	public JSONObject obtieneJsonDetalleDeRuta(String codigo){
		Cliente cliente;
		JSONObject jsonCliente;
		cliente = daoCliente.getByKey(codigo);
		jsonCliente= new JSONObject();
		if (cliente!= null) {
			try {
				jsonCliente.put("Codigo_Encuesta", cliente.getCodigo());
				jsonCliente.put("PT_indice", cliente.getCodigo());
				jsonCliente.put("Tipo", cliente.getLocalidad());
				jsonCliente.put("local", cliente.getRazonSocial());
				jsonCliente.put("Dirección", cliente.getCalleNroPisoDpto());
				jsonCliente.put("Referencia", cliente.getReference());
				jsonCliente.put("Nombres", cliente.getNombre());
				jsonCliente.put("Apellidos", cliente.getApellido());
				jsonCliente.put("Mail", cliente.getMail().equals("")?"NA":cliente.getMail().equals(""));
				jsonCliente.put("Cédula", cliente.getCedula());
				jsonCliente.put("Celular", cliente.getCelular());
				jsonCliente.put("Telefono", cliente.getTelefono().equals("")?cliente.getCelular():cliente.getTelefono().equals(""));
				jsonCliente.put("Latitud", cliente.getLatitudeBranch());
				jsonCliente.put("Longitud", cliente.getLenghtBranch());
				jsonCliente.put("Provincia", "GUAYAS");
				jsonCliente.put("Canton", "GUAYAQUIL");
				jsonCliente.put("Parroquia", "GUAYAQUIL");
				jsonCliente.put("CLUSTER", app.getVendedorLogueado().toString());
				jsonCliente.put("Estado", "Activo");
				jsonCliente.put("RUTA", cliente.getCuit());
				jsonCliente.put("IMEI", cliente.getImeI_ID());







			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return jsonCliente;
	}
}
