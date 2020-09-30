package ar.com.syswork.sysmobile.pconsultactacte;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.os.Handler;
import android.os.Message;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.shared.HttpManager;

public class ThreadConsultaCtaCte implements  Runnable{
	
	private String cliente;
	private String fechaDesde;
	private String fechaHasta;
	private Handler h;
	
	public ThreadConsultaCtaCte(Handler h, String cliente,String fechaDesde,String fechaHasta)
	{
		this.h = h;
		this.cliente = cliente ;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
	}
	
	@Override
	public void run() {
		String respuesta = "";
		int tipoRespuesta = 0;
		
		boolean intentar = true;
		int tiempo = 5000;
		int vez = 0;
		
		String parametrosAdicionales = "/" + cliente + "/" + fechaDesde + "/" + fechaHasta;
		
		// intento una vez y si falla hago dos reintentos , pausando en el 1ro 5 segundos y en el 2do 10
		while (intentar)
		{
			
			if (vez>0){
				try 
				{
					Thread.sleep(tiempo * vez);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
			
			HttpManager httpManager = new HttpManager(AppSysMobile.getRutaWebService() + AppSysMobile.WS_CONSULTA_CTA_CTE + parametrosAdicionales, AppSysMobile.getPuertoWebService());
			
			try {
				
				respuesta = httpManager.getStrDataByGET();
				tipoRespuesta = 1;
				intentar = false;
				
			} catch (ClientProtocolException e) {
				
				respuesta  = "ClientProtocolException";
				tipoRespuesta = 2;
				
				e.printStackTrace();
				
			} catch (IOException e) {
				
				respuesta  = "IOException";
				tipoRespuesta = 2;
	
				e.printStackTrace();
			}
			
			vez++;
			if (vez==3)
			{
				intentar=false;
			}
		} 
		
		enviaMensaje (respuesta,tipoRespuesta);

	}
	
	private void enviaMensaje(String mensaje, int tipoRespuesta)
	{
		Message message = new Message();
		message.arg1 = tipoRespuesta;
		message.obj = mensaje;
		h.sendMessage(message);
	}

}
