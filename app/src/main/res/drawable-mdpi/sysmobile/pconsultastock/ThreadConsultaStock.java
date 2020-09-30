package ar.com.syswork.sysmobile.pconsultastock;

import android.os.Handler;
import android.os.Message;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.shared.HttpManager;

public class ThreadConsultaStock implements Runnable{
	
	private Handler h;
	private String idArticulo;
	
	public ThreadConsultaStock(Handler h, String idArticulo)
	{
		this.h = h;
		this.idArticulo = idArticulo;
	}
	

	@Override
	public void run() {
		String respuesta = "";
		int tipoRespuesta = 0;
		
		boolean intentar = true;
		int tiempo = 5000;
		int vez = 0;
		
		String parametrosAdicionales="";
		
		parametrosAdicionales = "/" ;
		try {
			parametrosAdicionales = parametrosAdicionales  + URLEncoder.encode(idArticulo , "UTF-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
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
			
			HttpManager httpManager = new HttpManager(AppSysMobile.getRutaWebService() + AppSysMobile.WS_CONSULTA_STOCK + parametrosAdicionales, AppSysMobile.getPuertoWebService());
			
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
