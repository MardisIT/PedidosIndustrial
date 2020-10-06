package ar.com.syswork.sysmobile.reportepedidos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoCliente;
import ar.com.syswork.sysmobile.daos.Daoreporteitem;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class ReporteActivity extends AppCompatActivity {
    private AppSysMobile app;
    private DataManager dm;
    private AppSysMobile appSysmobile;
    private DataManager dataManager;
    private DaoCliente daoCliente;
    private Daoreporteitem daoreporteitem;
    private Cliente cliente;
    private ListView lstReportePedidos;

    private Activity a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportepedidos);
        lstReportePedidos=(ListView)findViewById(R.id.lstReportePedidos);
        this.a=(Activity)this;
        app = (AppSysMobile) this.a.getApplication();
        dataManager = app.getDataManager();
        daoCliente = dataManager.getDaoCliente();
        daoreporteitem=dataManager.getDaoreporteitem();
        List<reportepedidosE> _ReportepedidosES= new ArrayList<>();
        _ReportepedidosES=daoreporteitem.reportepedidosES();
        AdapterListaReportePedidos adapter = new AdapterListaReportePedidos(this.getApplicationContext(), _ReportepedidosES);
        lstReportePedidos.setAdapter(adapter);



    }
}
