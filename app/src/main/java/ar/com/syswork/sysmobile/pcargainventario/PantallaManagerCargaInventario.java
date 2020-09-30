package ar.com.syswork.sysmobile.pcargainventario;

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
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.AlertManager;
import ar.com.syswork.sysmobile.util.DialogManager;
import ar.com.syswork.sysmobile.util.IAlertResult;


public class PantallaManagerCargaInventario {
    private Activity a;
    private ListenerCargaInventario listenerCargaInventario;
    private Dialog dialog;
    private Dialog dialogClaseDePrecio;

    // Se usan en el Dialog
    private TextView txtCodArticulo;
    private TextView txtDescArticulo;
    private Button btnAgregar;
    private Button btnCancelarAgregar;

    private EditText edtCantidadDlg;

    // Estan en la activity de Carga de Inventario
    private TextView txtCliente;
    private TextView txtCantidadItems;
    private TextView txtImporteInventario;
    private EditText edtCodigoProducto;
    private  Spinner cmbformapago;
    private  Spinner cmbunidades;


    private String strCantidadItems = "";
    private String strImporteInventario = "";

    private  String strProntopago="";
    private  String strNegoEspescial="";

    private String textoTituloInstalarBarcodeScanner;
    private String textoMensajeInstalarBarcodeScanner;
    private String strIncluirEnReparto;
    private String strSi;
    private String strNo;
    private String strAceptar;

    private String haCargadoItemsEnElInventarioRealmenteDeseaSalir;
    private String avisoAlOperador;
    private String strDebeSeleccionarLaClaseDePrecio;
    public PantallaManagerCargaInventario(Activity a, ListenerCargaInventario listenerCargaInventario)
    {
        this.a = a;
        this.listenerCargaInventario = listenerCargaInventario;

        creaDialogoSolicitaCantidad();



        txtCliente = (TextView) a.findViewById(R.id.txtCliente);

        txtCantidadItems = (TextView) a.findViewById(R.id.txtCantidadItems);

        txtImporteInventario = (TextView) a.findViewById(R.id.txtImporteInventario);

        edtCodigoProducto = (EditText) a.findViewById(R.id.edtCodProducto);

        strCantidadItems = this.a.getString(R.string.cantidad_de_items);
        strImporteInventario = this.a.getString(R.string.importe_total_pedido);
        strIncluirEnReparto = this.a.getString(R.string.incluirEnReparto);

        TextView tmpTextView;


        tmpTextView = (TextView) a.findViewById(R.id.txtDescCantidad);
        tmpTextView.setText(this.a.getString(R.string.abreviatura_cantidad));







        textoTituloInstalarBarcodeScanner = this.a.getString(R.string.textoTituloInstalarBarcodeScanner);
        textoMensajeInstalarBarcodeScanner = this.a.getString(R.string.textoMensajeInstalarBarcodeScanner )	;
        strSi = this.a.getString(R.string.SI);
        strNo = this.a.getString(R.string.NO);
        strAceptar = this.a.getString(R.string.aceptar);

        avisoAlOperador = this.a.getString(R.string.avisoAlOperador);
        haCargadoItemsEnElInventarioRealmenteDeseaSalir = this.a.getString(R.string.haCargadoItemsEnElPedidoRealmenteDeseaSalir);
        strDebeSeleccionarLaClaseDePrecio = this.a.getString(R.string.debeSeleccionarLaClaseDePrecio);
    }
    public void creaDialogoSolicitaCantidad()
    {
        List<String> unidades= new ArrayList<>();
        unidades.add("Unidades");
        unidades.add("Cajas");


        dialog = new Dialog(a);
        dialog.setContentView(R.layout.dialog_solicitar_cantidad_inventario);
        dialog.setTitle(a.getString(R.string.ingrese_la_cantidad));
        dialog.setCancelable(false);

        txtCodArticulo = (TextView) dialog.findViewById(R.id.txtCodArticulo);
        txtDescArticulo = (TextView) dialog.findViewById(R.id.txtDescArticulo);
        cmbunidades=(Spinner)dialog.findViewById(R.id.cmbunidad);
        ArrayAdapter<String> adaptadoru;
        adaptadoru = new ArrayAdapter<String>(a, R.layout.support_simple_spinner_dropdown_item, unidades);
        cmbunidades.setAdapter(adaptadoru);

        btnAgregar = (Button) dialog.findViewById(R.id.btnAgregar);
        btnCancelarAgregar = (Button) dialog.findViewById(R.id.btnCancelarAgregar);

        edtCantidadDlg = (EditText) dialog.findViewById(R.id.edtCantidadDlg);

    }





    public void seteaListener() {

        ImageButton imbOk = (ImageButton) a.findViewById(R.id.imgBtnOk);
        imbOk.setOnClickListener(listenerCargaInventario);

        ImageButton imbBuscar = (ImageButton) a.findViewById(R.id.imgBtnBuscar);
        imbBuscar.setOnClickListener(listenerCargaInventario);

        ImageButton imbScan = (ImageButton) a.findViewById(R.id.imgBtnScan);
        imbScan.setOnClickListener(listenerCargaInventario);

        btnAgregar.setOnClickListener(listenerCargaInventario);
        btnCancelarAgregar.setOnClickListener(listenerCargaInventario);


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
        i = new Intent(a,ar.com.syswork.sysmobile.pconsultaarticulos.ActivityConsultaArticulos.class);
        i.putExtra("origenDeLaConsulta", AppSysMobile.DESDE_CARGA_DE_PEDIDOS);
        a.startActivityForResult(i, AppSysMobile.DESDE_CARGA_DE_PEDIDOS);
    }

    public void consultarStock(String idArticulo)
    {
        Intent i = new Intent(a,ar.com.syswork.sysmobile.pconsultastock.ActivityConsultaStock.class);
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

    public void setImporteTotalInventario(double importe)
    {
        txtImporteInventario.setText(strImporteInventario + " $ " + importe);
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

    public void finalizaActivityCargaInventario() {
        a.finish();
    }

    public void solicitaConfirmacionDeSalida()
    {
        DialogManager utlDlg = new DialogManager();
        utlDlg.muestraToastGenerico(a, a.getString(R.string.confirmacionSalir), false);
    }
    public void muestraAlertaDescargaBarcodeScanner(LogicaCargaInventario logicaCargaInventario) {

       AlertManager am = new AlertManager(this.a ,logicaCargaInventario,0);
        am.setTitle(textoTituloInstalarBarcodeScanner);
        am.setMessage(textoMensajeInstalarBarcodeScanner);
        am.setPositiveButton(strSi);
        am.setNegativeButton(strNo);
        am.ShowAlert();

    }

    public void muestraAlertaCancelarInventario() {

        AlertManager am = new AlertManager(this.a ,(IAlertResult) this.a ,R.id.mnu_cancelar_pedido);

        am.setTitle(avisoAlOperador);
        am.setMessage(haCargadoItemsEnElInventarioRealmenteDeseaSalir);
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



}

