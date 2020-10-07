package ar.com.syswork.sysmobile.reportepedidos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoCliente;
import ar.com.syswork.sysmobile.daos.Daoreportecabecera;
import ar.com.syswork.sysmobile.daos.Daoreporteitem;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.entities.reportecabecera;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.Utilidades;

public class ReporteActivity extends AppCompatActivity {
    private AppSysMobile app;
    private DataManager dm;
    private AppSysMobile appSysmobile;
    private DataManager dataManager;
    private DaoCliente daoCliente;
    private Daoreporteitem daoreporteitem;
    private Daoreportecabecera daoreportecabecera;
    private Cliente cliente;
    private ListView lstReportePedidos;
    private TextView txtCantidadDePedidosReporte,txtDescTotalReporte;

    private Activity a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportepedidos);
        lstReportePedidos=(ListView)findViewById(R.id.lstReportePedidos);
        txtCantidadDePedidosReporte=(TextView)findViewById(R.id.txtCantidadDePedidosReporte);
        txtDescTotalReporte=(TextView)findViewById(R.id.txtDescTotalReporte);
        this.a=(Activity)this;
        app = (AppSysMobile) this.a.getApplication();
        dataManager = app.getDataManager();
        daoCliente = dataManager.getDaoCliente();
        daoreporteitem=dataManager.getDaoreporteitem();
        daoreportecabecera=dataManager.getDaoreportecabecera();
        List<reportepedidosE> _ReportepedidosES= new ArrayList<>();
        _ReportepedidosES=daoreporteitem.reportepedidosES();
        AdapterListaReportePedidos adapter = new AdapterListaReportePedidos(this.getApplicationContext(), _ReportepedidosES);
        lstReportePedidos.setAdapter(adapter);
        List<reportecabecera> _reportec=new ArrayList<>();
        _reportec=daoreportecabecera.getAll("");
        int totalpedido=_reportec!=null?_reportec.size():0;
        txtCantidadDePedidosReporte.setText("Items: "+_ReportepedidosES.size()+" Total Pedidos: "+totalpedido);
        Double totalsum= 0.00;
        for (reportepedidosE a:_ReportepedidosES
             ) {
            totalsum=totalsum+a.totalp;

        }
        txtDescTotalReporte.setText("Total: $ " + Double.toString(Utilidades.Redondear((totalsum),2)));




    }
}
