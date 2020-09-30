package ar.com.syswork.sysmobile.pconsultactacte;

import android.os.Bundle;

import android.widget.ListView;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoPagos;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Pagos;
import ar.com.syswork.sysmobile.entities.PagosDetalles;
import ar.com.syswork.sysmobile.entities.Reporte;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class dialog_chequespagos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_chequespagos);

    }

}
