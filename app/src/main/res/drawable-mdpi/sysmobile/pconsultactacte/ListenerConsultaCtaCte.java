package ar.com.syswork.sysmobile.pconsultactacte;

import android.view.View;
import android.view.View.OnClickListener;

import ar.com.syswork.sysmobile.R;

public class ListenerConsultaCtaCte implements OnClickListener {
	
	private LogicaConsultaCtaCte logicaConsultaCtaCte;
	private  PantallaManagerConsultaCtaCte pantallaManagerConsultaCtaCte;
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnAceptarFechaDdeHta)
		{
			logicaConsultaCtaCte.tomarParametrosFechaDesdeHasta();
		}
        switch (v.getId())
        {
            case R.id.btncancelarch:

                pantallaManagerConsultaCtaCte.cierraDialogoChequepagos();
                break;
			case R.id.btnagregarcheque:
				pantallaManagerConsultaCtaCte.AgregarchequeL();
				break;
			case R.id.btnaceptarcheque:
				logicaConsultaCtaCte.llenarlistacheque();
				break;
			case R.id.btnsalirvista:
				pantallaManagerConsultaCtaCte.cerrardialogoimagen();
				break;
			//case R.id.lvlistacheques:



        }
	}

	public void seteaListener(View v)
	{
		v.setOnClickListener(this);
	}

	
	public void seteaLogicaConsultaCtaCte(LogicaConsultaCtaCte logicaConsultaCtaCte, PantallaManagerConsultaCtaCte pantallaManagerConsultaCtaCte)
	{
		this.logicaConsultaCtaCte = logicaConsultaCtaCte;
		this.pantallaManagerConsultaCtaCte=pantallaManagerConsultaCtaCte;
	}
}
