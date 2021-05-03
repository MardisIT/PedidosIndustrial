package ar.com.syswork.sysmobile.industrial;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoCliente;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.entities.Pedido;
import ar.com.syswork.sysmobile.pconsultaclientes.LogicaConsultaClientes;
import ar.com.syswork.sysmobile.pconsultaclientes.PantallaManagerConsultaClientes;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private GoogleMap mapa;
    private LatLng UPV = new LatLng(39.481106, -0.340987);
    private LogicaConsultaClientes logicaConsultaClientes;
    private AppSysMobile app;
    private DataManager dm;
    private AppSysMobile appSysmobile;
    private DataManager dataManager;
    private DaoCliente daoCliente;
    private Cliente cliente;
    private Activity a;

    String lHint="";
    List<Cliente> listaTmp = new ArrayList<Cliente>();
    private PantallaManagerConsultaClientes pantallaManagerConsultaClientes;
    public void setPantallaManager(PantallaManagerConsultaClientes pantallaManagerConsultaClientes) {
        this.pantallaManagerConsultaClientes = pantallaManagerConsultaClientes;

    }
    private EditText et;
    private Spinner spnlocalidad;
    private ArrayList<Cliente> listaLocalidades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mapa);



        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
        this.a=(Activity)this;
        et = (EditText) a.findViewById(R.id.txtBuscar);
        spnlocalidad = (Spinner) a.findViewById(R.id.spnlocalidad);
        setHintParametroBusqueda("RazonSocial");
        app = (AppSysMobile) this.a.getApplication();
        dataManager = app.getDataManager();
        daoCliente = dataManager.getDaoCliente();
        listaTmp = daoCliente.getAll("");
        cargarLocalidades(spnlocalidad);
        ImageView img = (ImageView) a.findViewById(R.id.imgBuscar);
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!et.getText().toString().equals("")) {
                    if (lHint.equals(a.getString(R.string.hint_busqueda_razon_social))) {

                        listaTmp = daoCliente.getAll("RazonSocial='"+et.getText()+"'");
                        cargar();
                    }
                    if (lHint.equals(a.getString(R.string.hint_busqueda_codigo))) {
                        listaTmp = daoCliente.getAll("Codigo='"+et.getText()+"'");
                        cargar();
                    }
                } else {
                   muestraAlertaError("Error","Debe informar al menos 4 caracteres");
                }
            }
        });
    }
    public void cargar(){
        mapa.clear();
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }
    public void muestraAlertaError(String titulo ,String mensajeError){
        AlertDialog alertDialog = new AlertDialog.Builder(a).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensajeError);
        alertDialog.setIcon(R.drawable.simbolo_error);
        alertDialog.show();
    }
    public void cargarLocalidades(final Spinner spnloc) {
        List<Cliente> listaTmp = new ArrayList<Cliente>();
        listaTmp = daoCliente.getAllLocalidades();
        listaLocalidades= new ArrayList<Cliente>();
        Iterator<Cliente> i = listaTmp.iterator();


        while (i.hasNext()) {
            cliente = i.next();
            listaLocalidades.add(cliente);
        }
        llenaloc(spnloc);

    }
    public void llenaloc(Spinner spnloc)
    {
        List<String> lables = new ArrayList<String>();
        lables.add("Seleccione una Localidad");
        System.out.println("tama?o lista "+listaLocalidades.size());
        for (int i = 0; i < listaLocalidades.size(); i++)
        {
            // Get the position
            System.out.println("obtiene localidad");
            Cliente cliente = listaLocalidades.get(i);
            lables.add(cliente.getLocalidad());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(a,android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnloc.setAdapter(spinnerAdapter);


    }
    public void setHintParametroBusqueda(String queHint){

        et.setText("");

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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.getUiSettings().setZoomControlsEnabled(false);

        //mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(UPV, 15));

        for (Cliente a: listaTmp
             ) {
            if(a.getLatitudeBranch()!=null && a.getLenghtBranch()!=null) {
                UPV = new LatLng(Double.valueOf(a.LatitudeBranch.replace(",",".")), Double.valueOf(a.LenghtBranch.replace(",",".")));
                mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(UPV, 13));

                if(a.getCpteDefault().toString().equals("E"))
                    mapa.addMarker(new MarkerOptions()
                            .position(UPV)
                            .title(a.getCodigo())
                            .snippet(a.getName())
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            .anchor(0.5f, 0.5f));
                else if(a.getCpteDefault().toString().equals("V"))
                    mapa.addMarker(new MarkerOptions()
                            .position(UPV)
                            .title(a.getCodigo())
                            .snippet(a.getName())
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                            .anchor(0.5f, 0.5f));
                else
                    mapa.addMarker(new MarkerOptions()
                            .position(UPV)
                            .title(a.getCodigo())
                            .snippet(a.getName())
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .anchor(0.5f, 0.5f));


            }
        }

        mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {

                Toast.makeText(
                        app,
                        "   Punto:"+ marker.getTitle()+" \n" +
                                "Lat: " + marker.getPosition().latitude + "\n" +
                                "Lng: " + marker.getPosition().longitude + "\n" ,
                        Toast.LENGTH_SHORT).show();

                final Cliente seleccion= daoCliente.getByKey(marker.getTitle());
                 android.app.AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new android.app.AlertDialog.Builder(a);
                } else {
                    builder = new android.app.AlertDialog.Builder(a);
                }
                builder.setTitle("Iniciar Tarea");
                builder.setMessage("Local: "+seleccion.getRazonSocial()+
                        '\n'+"Propietario: "+seleccion.getPropietario()+ '\n'+"Dirección: "+seleccion.getMainStreet());
                /// David Samueza
                builder.setPositiveButton("Pedido", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        Intent i = new Intent(a,ar.com.syswork.sysmobile.visita.class);
                        i.putExtra("cliente", seleccion.getCodigo());
                        a.startActivity(i);
                        a.finish();

                      //  Intent i = new Intent(a,ar.com.syswork.sysmobile.pcargapedidos.ActivityCargaPedidos.class);
                      ///  i.putExtra("cliente", seleccion.getCodigo());
                      //  a.startActivity(i);
                       // a.finish();
                    }
                });
                builder.setNegativeButton("Como llegar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Uri intentUri = Uri.parse("geo:" + marker.getPosition().latitude + "," + marker.getPosition().longitude + "?z=16&q=" + marker.getPosition().latitude + "," + marker.getPosition().longitude + "(" + marker.getTitle() + ")");
                        Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
                        startActivity(intent);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert);
                builder.show();


                return true;
            }
        });



        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setCompassEnabled(true);

        }
    }


    public void moveCamera(View view) {
        mapa.moveCamera(CameraUpdateFactory.newLatLng(UPV));
    }

    public void animateCamera(View view) {
        mapa.animateCamera(CameraUpdateFactory.newLatLng(UPV));
    }

    public void addMarker(View view) {
        mapa.addMarker(new MarkerOptions().position(
                mapa.getCameraPosition().target));
    }

    @Override public void onMapClick(LatLng puntoPulsado) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_consulta_clientes, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.mnu_ordenar_razon_social:
                // Si ya no estaba seteado por razon social, lo cambio
                /*if (!logicaConsultaClientes.getOrderBy().equals("RazonSocial")){
                logicaConsultaClientes.cambiarCriterioBusqueda("RazonSocial");*/
                    setHintParametroBusqueda("RazonSocial");


                return true;

            case R.id.mnu_ordenar_codigo:
                // Si ya no estaba seteado por Codigo, lo cambio
               /* if (!logicaConsultaClientes.getOrderBy().equals("CodigoOpcional")){
                    logicaConsultaClientes.cambiarCriterioBusqueda("CodigoOpcional");
                }*/
                setHintParametroBusqueda("CodigoOpcional");
                return true;

            case R.id.mnu_borrar_parametros_orden:
                mapa.clear();
                listaTmp = daoCliente.getAll("");
                SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapa);
                mapFragment.getMapAsync(this);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}