package ar.com.syswork.sysmobile.pconsultastock;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoDeposito;
import ar.com.syswork.sysmobile.entities.Deposito;
import ar.com.syswork.sysmobile.entities.ItemConsultaStock;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.Base64new;

public class LogicaConsultaStock implements Callback{
	
	private PantallaManagerConsultaStock pantallaManagerConsultaStock;
	private ArrayList<ItemConsultaStock> lista;
	private AdapterConsultaStock adapterConsultaStock ;
	private DaoDeposito daoDeposito;
	
	public LogicaConsultaStock(PantallaManagerConsultaStock pantallaManagerConsultaStock)
	{
		this.pantallaManagerConsultaStock = pantallaManagerConsultaStock;
		
		// Creo la lista
		lista = new ArrayList<ItemConsultaStock>();
		
		// Creo el Adapter
		adapterConsultaStock =  new AdapterConsultaStock(this.pantallaManagerConsultaStock.getActivity(), lista);
		
		// Obtengo el ListView
		ListView lv = (ListView) pantallaManagerConsultaStock.getActivity().findViewById(R.id.lstConsultaStock);
		
		AppSysMobile appSysMobile = (AppSysMobile) pantallaManagerConsultaStock.getActivity().getApplication();
		
		daoDeposito = appSysMobile.getDataManager().getDaoDeposito();
		
		// Seteo el Adapter
		lv.setAdapter(adapterConsultaStock);
	}
	
	public void refreshAdapter()
	{
		adapterConsultaStock.notifyDataSetChanged();
	}
	
	public void consultarStock() {
		
		pantallaManagerConsultaStock.muestraDialogoConsultaStock();
		
		Handler h = new Handler(this);
		ThreadConsultaStock tc = new ThreadConsultaStock (h,pantallaManagerConsultaStock.getIdArticulo());
        Thread t = new Thread(tc);
        
        t.start();
		
	}

	@Override
	public boolean handleMessage(Message msg) {
		
		pantallaManagerConsultaStock.cierraDialogoConsultaStock();
		String resultado = (String)  msg.obj;

		switch(msg.arg1)
		{
			case 1:
				procesaResultado(resultado);
				break;
			case 2:
				
				// Inicializo la lista
				lista.clear();
				refreshAdapter();
				pantallaManagerConsultaStock.muestraToastErrorDeConexion();
				break;
		}

		return false;
	}

	private void procesaResultado(String jsonStock)  {
		
		// Inicializo la lista
		lista.clear();
		
		try {
			jsonStock = Decompress(jsonStock);
		} catch (IOException e) {
			Log.d("SW","Error al descomprimir Consulta Stock");
			e.printStackTrace();
		}
		
		// Obtengo una lista Auxiliar en base al JsonRecibido
		ArrayList<ItemConsultaStock> tmpListaConsultaStock = new ArrayList<ItemConsultaStock>();
		tmpListaConsultaStock = parseaStock(jsonStock);
		
		if (tmpListaConsultaStock.size()>0)
		{
			Iterator<ItemConsultaStock> i = tmpListaConsultaStock.iterator();
			while (i.hasNext())
			{
				lista.add(i.next());
			}
		}
		
		refreshAdapter();
		
	}

	public  ArrayList<ItemConsultaStock> parseaStock(String jsonStock)
	{
		JSONArray arrayJson;
		JSONObject jsObject;
		Deposito deposito;
		
		ItemConsultaStock itemConsultaStock = null;
		ArrayList<ItemConsultaStock> listaConsultaStock = new ArrayList<ItemConsultaStock>();

		try
		{
			arrayJson = new JSONArray(jsonStock);
			for (int x = 0; x < arrayJson.length() ;x++)
			{
				jsObject = arrayJson.getJSONObject(x);
				
				itemConsultaStock = new ItemConsultaStock();
				itemConsultaStock.setIdDeposito(jsObject.getString("idDeposito"));
				if (itemConsultaStock.getIdDeposito().length()<=4)
				{
					deposito = daoDeposito.getByKey(itemConsultaStock.getIdDeposito());
					if (deposito!=null)
						itemConsultaStock.setDescripcion(deposito.getDescripcion());
					else
						itemConsultaStock.setDescripcion(pantallaManagerConsultaStock.getActivity().getString(R.string.deposito_no_actualizado));
				}
				else
				{
					if (itemConsultaStock.getIdDeposito().equals("@REAL"))
						itemConsultaStock.setDescripcion(pantallaManagerConsultaStock.getActivity().getString(R.string.stockActual));
					if (itemConsultaStock.getIdDeposito().equals("@COMPROMETIDO"))
						itemConsultaStock.setDescripcion(pantallaManagerConsultaStock.getActivity().getString(R.string.stockComprometido));
					if (itemConsultaStock.getIdDeposito().equals("@SUGERIDO"))
						itemConsultaStock.setDescripcion(pantallaManagerConsultaStock.getActivity().getString(R.string.stockSugerido));
					
				}
				
				itemConsultaStock.setCantidad(jsObject.getDouble("stock"));
				
				listaConsultaStock.add(itemConsultaStock);
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}		
		
		return listaConsultaStock;
		
	}
	 @SuppressLint("NewApi")
	public String Decompress(String zipText) throws IOException 
	{
		    int size = 0;
		    
		    byte[] gzipBuff = Base64new.decode(zipText, Base64new.DEFAULT);

		    ByteArrayInputStream memstream = new ByteArrayInputStream(gzipBuff, 4, gzipBuff.length - 4);
		    GZIPInputStream gzin = new GZIPInputStream(memstream);

		    final int buffSize = 8192;
		    byte[] tempBuffer = new byte[buffSize];
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    while ((size = gzin.read(tempBuffer, 0, buffSize)) != -1) 
		    {
		        baos.write(tempBuffer, 0, size);
		    }
		    byte[] buffer = baos.toByteArray();
		    baos.close();

		    return new String(buffer, "UTF-8");
	}	

}
