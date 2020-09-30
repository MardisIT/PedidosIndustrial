package ar.com.syswork.sysmobile.pconsultastock;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoArticulo;
import ar.com.syswork.sysmobile.entities.Articulo;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.DialogManager;

public class PantallaManagerConsultaStock {
	
	private Activity a;
	private AppSysMobile appSysMobile;
	private DaoArticulo daoArticulo;
	private Articulo articulo;
	private String idArticulo;
	private Dialog dialog;
	private DialogManager utlDlg;
	
	public PantallaManagerConsultaStock(Activity a)
	{
		this.a  = a;
		
		appSysMobile = (AppSysMobile) this.a.getApplication();
		daoArticulo = appSysMobile.getDataManager().getDaoArticulo();

		Intent i = this.a.getIntent();
		Bundle extras = i.getExtras();
		idArticulo = extras.getString("idArticulo");
		
		utlDlg = new DialogManager();
		
		creaDialogoConsultaStock();

	}
	
	public void seteaNombreArticulo()
	{
		articulo = daoArticulo.getByKey(this.idArticulo);
		TextView txtArticulo = (TextView) a.findViewById(R.id.txtArticulo);
		txtArticulo.setText(articulo.getDescripcion().trim());
	}

	public String getIdArticulo() {
		return idArticulo;
	}
	
	public Activity getActivity(){
		return this.a;
	}

	public void creaDialogoConsultaStock()
	{
		dialog = new Dialog(a);
		dialog.setContentView(R.layout.dialog_consulta_stock);
		dialog.setTitle(a.getString(R.string.sincronizando));
		dialog.setCancelable(false);
	}

	
	public void cierraDialogoConsultaStock() {
		dialog.dismiss();
	}

	public void muestraToastErrorDeConexion() {
		utlDlg.muestraToastGenerico(a, a.getString(R.string.huboErrorDeComunicaciones), false);
		
	}

	public void muestraDialogoConsultaStock() {
		dialog.show();
	}
}
