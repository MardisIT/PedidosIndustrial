package ar.com.syswork.sysmobile.penviapendientes;

import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpException;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;

import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.shared.HttpManager;

public class ThreadEnvioIndustrial implements Runnable {

    private String jSonPedido;
    private Handler h;

    public ThreadEnvioIndustrial(Handler h,String jSonPedido)
    {
        this.jSonPedido = jSonPedido;

        this.h = h;
    }

    @Override
    public void run() {
        String respuesta = "";
        int tipoRespuesta = 0;

        boolean intentar = true;
        int tiempo = 5;
        int vez = 0;

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

            HttpManager httpManager = new HttpManager(AppSysMobile.WS_ACCESO_GRABAR_PEDIDOS_INDUSTRIAL , AppSysMobile.getPuertoWebService());

            try {

                respuesta = httpManager.sendJsonDataByPOST(jSonPedido);
                tipoRespuesta = 1;
                intentar = false;

            } catch (ClientProtocolException e) {

                respuesta  = "ERROR: Exception ClientProtocolException";
                tipoRespuesta = 2;
                e.printStackTrace();

            } catch (IOException e) {

                respuesta  = "ERROR: IOException";
                tipoRespuesta = 2;
                e.printStackTrace();
            } catch (HttpException e) {
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
        if(mensaje==null)
            mensaje="0";
        message.obj = mensaje;
        h.sendMessage(message);
    }

}
