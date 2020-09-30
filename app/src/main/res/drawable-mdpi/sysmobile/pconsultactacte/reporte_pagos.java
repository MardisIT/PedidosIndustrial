package ar.com.syswork.sysmobile.pconsultactacte;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoPagos;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Pagos;
import ar.com.syswork.sysmobile.entities.PagosDetalles;
import ar.com.syswork.sysmobile.entities.Reporte;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class reporte_pagos extends ActionBarActivity {
    private AdapterReportePagos reportePagos;
    ArrayList<Reporte> reporteList= new ArrayList<>();
    private DaoPagos daoPagos;
    private AppSysMobile app;
    private DataManager dm;
    private ListView listpagos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_pagos);
        ListView lv = (ListView) findViewById(R.id.listareporte);
        app = (AppSysMobile) this.getApplication();
        dm = app.getDataManager();
        daoPagos = dm.getDaoPagos();

        List<Pagos>listapagos= new ArrayList<>();
        listapagos=daoPagos.getAll("");
        for (Pagos pagos:listapagos) {
            Reporte reporte= new Reporte();
            reporte.setNombrecliente(pagos.getCliente().getRazonSocial());
            String facturas="";
            for (PagosDetalles pagosDetalles :pagos.getPagosDetalles()
                    ) {
                facturas=facturas+"-"+pagosDetalles.getNumfactura();

            }
            reporte.setNumerofactura(facturas);
            reporte.setValorpago(pagos.getValorTotalPago());
            reporte.setFormapago(pagos.getFormaPago());
            reporteList.add(reporte);
        }
        reportePagos = new AdapterReportePagos(this.getApplication(),  reporteList);
        reportePagos.setDropDownViewResource(android.R.layout.simple_list_item_1);
        lv.setAdapter(reportePagos);
    }

}
