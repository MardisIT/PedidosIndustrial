package ar.com.syswork.sysmobile.pcargainventario;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoArticulo;
import ar.com.syswork.sysmobile.daos.DaoCliente;
import ar.com.syswork.sysmobile.daos.DaoCodigosNuevos;
import ar.com.syswork.sysmobile.daos.DaoInventario;
import ar.com.syswork.sysmobile.daos.Daoinventariodetalles;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Articulo;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.entities.CodigosNuevos;
import ar.com.syswork.sysmobile.entities.Inventario;
import ar.com.syswork.sysmobile.entities.inventariodetalles;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.AlertManager;
import ar.com.syswork.sysmobile.util.DialogManager;
import ar.com.syswork.sysmobile.util.IAlertResult;
import ar.com.syswork.sysmobile.util.Utilidades;

public class LogicaCargaInventario  implements IAlertResult {
    private Activity a;
    private PantallaManagerCargaInventario pantallaManagerCargaInventario;
    private ArrayList<inventariodetalles> listainventariodetalles;
    private DialogManager utilDialogos;

    private AppSysMobile app;

    private DataManager dataManager;

    private DaoCliente daoCliente;
    private DaoArticulo daoArticulo;
    private DaoInventario daoInventario;
    private Daoinventariodetalles daoinventariodetalles;
    private AdapterCargaInventario adapterCargaInventario;

    private Cliente cliente;
    private String codigoProductoActual;
    private String codigoVendedor;
    private double importeTotalPedido;
    private int claseDePrecioSeleccionada;

    private String textoLecturaCodigoDeBarras;
    private String textoMarketNoInstalado;
    private DaoCodigosNuevos daoCodigosNuevos;

    private String elCodigoInformadoNoEsCorrecto;
    private String debeInformarElCodigoDelProducto;
    private String debeInformarLaCantidad;
    private String noHaCargadoProductos;
    private String ocurrioUnErrorAlGrabar;
    private String pedidoGrabadoConExito;

    private boolean reintentarObtenerIntentZxing;
    private Intent intentScan ;
    //private String targetAppPackage;
    private static final String BS_PACKAGE = "com.google.zxing.client.android";
    public LogicaCargaInventario(Activity a,PantallaManagerCargaInventario pantallaManagerCargaInventario)
    {
        this.a = a;
        this.pantallaManagerCargaInventario = pantallaManagerCargaInventario;

        app = (AppSysMobile) this.a.getApplication();

        dataManager = app.getDataManager();
        daoCliente = dataManager.getDaoCliente();
        daoArticulo = dataManager.getDaoArticulo();
        daoInventario = dataManager.getDaoInventario();
        daoinventariodetalles = dataManager.getDaoinventariodetalles();
daoCodigosNuevos=dataManager.getDaoCodigosNuevos();



        listainventariodetalles = new ArrayList<inventariodetalles>();


        //Creo el Adapter
        this.adapterCargaInventario= new AdapterCargaInventario(this.a,listainventariodetalles,this);

        codigoVendedor = app.getVendedorLogueado();

        utilDialogos = new DialogManager ();

        intentScan = getZxingIntent(this.a);
        //targetAppPackage = findTargetAppPackage(intentScan);

        textoLecturaCodigoDeBarras = this.a.getString(R.string.textoLecturaCodigoDeBarras);
        textoMarketNoInstalado = this.a.getString(R.string.textoMarketNoInstalado);
        elCodigoInformadoNoEsCorrecto= this.a.getString(R.string.elCodigoInformadoNoEsCorrecto);
        debeInformarElCodigoDelProducto= this.a.getString(R.string.debeInformarElCodigoDelProducto);
        debeInformarLaCantidad = this.a.getString(R.string.debeInformarLaCantidad);
        noHaCargadoProductos = this.a.getString(R.string.noHaCargadoProductos);
        ocurrioUnErrorAlGrabar = this.a.getString(R.string.ocurrioUnErrorAlGrabar);
        pedidoGrabadoConExito= this.a.getString(R.string.invnetarioGrabadoConExito);

        reintentarObtenerIntentZxing = false;
    }
    public void vaciaLista()
    {
        this.listainventariodetalles.clear();
    }

    public AdapterCargaInventario getAdapterCargaInventario() {
        return this.adapterCargaInventario;
    }

    public void notificarCambiosAdapter(){
        this.adapterCargaInventario.notifyDataSetChanged();
    }

    public void inicializaDatos(String codigoCliente)
    {
        cliente = daoCliente.getByKey(codigoCliente);
        pantallaManagerCargaInventario.setDatosCliente(cliente);
        pantallaManagerCargaInventario.setCantidadItems(0);
        pantallaManagerCargaInventario.setImporteTotalInventario(0);
        claseDePrecioSeleccionada = cliente.getClaseDePrecio();
    }

    public boolean validaCodigoArticulo() {

        String valor = pantallaManagerCargaInventario.getCodigoIntroducido();
        Articulo articulo;

        if (valor.equals("")){
            utilDialogos.muestraToastGenerico(a, debeInformarElCodigoDelProducto, false);
            return false;
        }

        articulo = daoArticulo.getByKey(valor);
        if (articulo==null){
            utilDialogos.muestraToastGenerico(a, elCodigoInformadoNoEsCorrecto, false);
            return false;
        }




        // El Articulo esta OK, lo seteo como el articulo actual
        setCodigoProductoActual(articulo.getIdArticulo());
        pantallaManagerCargaInventario.mostrarDialogoSolicitaCantidad(articulo.getIdArticulo(),articulo.getDescripcion());

        return true;
    }

    public boolean validaCantidadIntroducida() {
        try{



            String valor = pantallaManagerCargaInventario.getCantidadIntroducida();
            String unidadmedida=pantallaManagerCargaInventario.getUnidadesIntroducida();

            if (valor.equals("")){
                utilDialogos.muestraToastGenerico(a, debeInformarLaCantidad , false);
                return false;
            }










            Articulo articulo = daoArticulo.getByKey(getCodigoProductoActual().toString().trim());

            // Agrego a la lista y cierro
            inventariodetalles _inventariodetalles = new inventariodetalles();

            _inventariodetalles.setId(-1);
            _inventariodetalles.setId(-1);

            _inventariodetalles.setCodproducto(articulo.getIdArticulo());
            _inventariodetalles.setValor(Long.valueOf(valor));
            _inventariodetalles .setArticulo(articulo);
            _inventariodetalles.setUnidad(unidadmedida);

            listainventariodetalles.add(_inventariodetalles);
            this.notificarCambiosAdapter();
            pantallaManagerCargaInventario.cerrarDialogoSolicitaCantidad();
            pantallaManagerCargaInventario.setCodigoIntroducido("");
            pantallaManagerCargaInventario.setCantidadItems(listainventariodetalles.size());
            totalizarPedido();
            return true;
        }catch (Exception ex){
            Log.d("sw",ex.getMessage());
            return  false;
        }
    }

    public String getCodigoProductoActual() {
        return codigoProductoActual;
    }
    public void setCodigoProductoActual(String codigoProductoActual) {
        this.codigoProductoActual = codigoProductoActual;
    }

    public void consultarArticulos() {
        pantallaManagerCargaInventario.lanzarActivityConsultaArticulos();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    public void grabarInventario(boolean enviarAutomaticamente,boolean facturar,long _idinventarioAEliminar,boolean incluirEnReparto)
    {
        long idInventario;
        long idInventariodetalle;

        if (listainventariodetalles.size() == 0)
        {
            utilDialogos.muestraToastGenerico(a, noHaCargadoProductos, false);
            return;
        }

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

        String fecha = df.format(date);

        if (_idinventarioAEliminar>0)
        {
            Inventario tmpinventario = daoInventario.getById(_idinventarioAEliminar);
            daoInventario.delete(tmpinventario);

            daoinventariodetalles.deleteByIdinventario(_idinventarioAEliminar);
        }
        CodigosNuevos codigosNuevos= new CodigosNuevos();
        codigosNuevos= daoCodigosNuevos.CodigosActual();

        Inventario inventario = new Inventario();
        inventario.setCodcliente(cliente.getCodigo());
        inventario.setFechainventario(fecha);
        inventario.setCodvendedor(codigoVendedor);
        UUID idOne = UUID.randomUUID();
        inventario.setCodigomardis(idOne.toString());
        inventario.setEnviomardis("F");
        inventario.setEnvioindustrial("F");
        inventario.setCodigounico(codigosNuevos.getCodeunico());
        idInventario = daoInventario.save(inventario);
        codigosNuevos.setUri("E");
        daoCodigosNuevos.update(codigosNuevos);
        inventariodetalles _inventariodetalle = null;

        if (idInventario!=-1)
        {
            Iterator<inventariodetalles> i = listainventariodetalles.iterator();
            while (i.hasNext())
            {
                _inventariodetalle = i.next();

                _inventariodetalle.setIdinventario(idInventario);
                idInventariodetalle = daoinventariodetalles.save(_inventariodetalle);

                if (idInventariodetalle==-1)
                {
                    utilDialogos.muestraToastGenerico(a, ocurrioUnErrorAlGrabar, false);

                    // Si ocurrio un error elimino
                    daoInventario.delete(inventario);
                    daoinventariodetalles.deleteByIdinventario(idInventario);

                    return;
                }
            }
            utilDialogos.muestraToastGenerico(a, pedidoGrabadoConExito, false);

            Intent intent = new Intent(AppSysMobile.INTENT_FILTER_CAMBIOS_LISTA_PEDIDOS);
            a.sendBroadcast(intent);


                pantallaManagerCargaInventario.finalizaActivityCargaInventario();

        }
        else
        {
            utilDialogos.muestraToastGenerico(a, ocurrioUnErrorAlGrabar, false);
            return;
        }


    }

    public void removerItemListaItems(int posicionItemSeleccionado) {
        listainventariodetalles.remove(posicionItemSeleccionado);
        notificarCambiosAdapter();
        pantallaManagerCargaInventario.setCantidadItems(listainventariodetalles.size());
        totalizarPedido();
    }

    public void totalizarPedido()
    {
        double total=0;
        inventariodetalles _inventariodetalles;

        Iterator<inventariodetalles> i = listainventariodetalles.iterator();
        while (i.hasNext())
        {
            _inventariodetalles = i.next();
            total = total + _inventariodetalles.getValor();
        }

        importeTotalPedido = total;
        pantallaManagerCargaInventario.setImporteTotalInventario(Utilidades.Redondear(total,2));
    }



    public void scanArticulo()
    {


        if (reintentarObtenerIntentZxing)
        {
            intentScan = getZxingIntent(this.a);
            Log.d("sw","reintentarObtenerIntentZxing ");
        }

        if (intentScan == null)
        {
            Log.d("sw","intentScan es NULO" );
            mostrarDialogoDescarga();
            reintentarObtenerIntentZxing = true;
        }
        else
        {
            Log.d("sw","intentScan NO ES NULO" );
            reintentarObtenerIntentZxing = false;
            intentScan.putExtra("PROMPT_MESSAGE", textoLecturaCodigoDeBarras);
            a.startActivityForResult(intentScan, AppSysMobile.DESDE_SCANNER);
        }

    }
    private void mostrarDialogoDescarga()
    {
        pantallaManagerCargaInventario.muestraAlertaDescargaBarcodeScanner(this);
    }
    public static Intent getZxingIntent(Context context) {

        Intent zxingIntent = new Intent(BS_PACKAGE + ".SCAN");

        final PackageManager pm = context.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(zxingIntent,0);

        for (int i = 0; i < activityList.size(); i++)
        {
            ResolveInfo app = activityList.get(i);
            // PARA QUE SEA LA DE ZXING BARCODE TIENE QUE LLAMARSE com.google.zxing.client.android.CaptureActivity"
            Log.d("sw","app.activityInfo.name " + app.activityInfo.name.toString());

            if (app.activityInfo.name.contains("zxing"))
            {
                zxingIntent.setClassName(app.activityInfo.packageName,app.activityInfo.name);
                return zxingIntent;
            }
        }
        //return zxingIntent;
        return null;
    }

    @Override
    public void onAlertResult(int idAlert, int which)
    {
        if (idAlert == 0)
        {
            switch (which)
            {
                case AlertManager.BUTTON_POSITIVE :
                    Uri uri = Uri.parse("market://details?id=" + BS_PACKAGE);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    try
                    {
                        a.startActivity(intent);
                    }
                    catch (ActivityNotFoundException anfe)
                    {
                        Toast.makeText(a, textoMarketNoInstalado, Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }


    public void cargaDatosInventario(long _idinventario)
    {

        Log.d("SW","_idPedido: " +_idinventario);

        Inventario inventario = daoInventario.getById(_idinventario);

        String codCliente = inventario.getCodcliente();
        inicializaDatos(codCliente);

        inventariodetalles _inventariodetalles;

        listainventariodetalles.clear();
        List<inventariodetalles> lstTemp = daoinventariodetalles.getAll(" idinventario = " + inventario.getId());
        Iterator<inventariodetalles> i = lstTemp.iterator();
        while (i.hasNext())
        {
            _inventariodetalles = i.next();


            listainventariodetalles.add(_inventariodetalles);
        }

        notificarCambiosAdapter();

        totalizarPedido();
    }







}

