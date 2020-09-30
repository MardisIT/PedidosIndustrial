package ar.com.syswork.sysmobile.pconsultagenerica.detalle;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.ItemClaveValor;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class ConsultaClaveValorGenerica {
	private int icono;
	private String titulo;
	
	private List<ItemClaveValor> listaClaveValor = new ArrayList<ItemClaveValor>();
	private Activity a;
	
	public ConsultaClaveValorGenerica(Activity a, List<ItemClaveValor> listaClaveValor)
	{
		this.listaClaveValor = listaClaveValor;
		this.a =a;
	}
	
	public void lanzarActivity()
	{
		Intent i = new Intent(a,ActivityConsultaGenericaDetalle.class);
		i.putExtra("icono_detalle", icono);
		i.putExtra("titulo", titulo);
		
		AppSysMobile app = (AppSysMobile) a.getApplication();
		app.setListaClaveValor(listaClaveValor);
		
		a.startActivity(i);
	}
	
	public int getIcono() {
		if (icono==0){
			icono = R.drawable.simbolo_interrogacion;
		}
		return icono;
	}

	public void setIcono(int icono) {
		this.icono = icono;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
}
