package ar.com.syswork.sysmobile.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import android.annotation.SuppressLint;


public class Utilidades {
	
	public static double Redondear(double numero,int digitos)
	{
	      int cifras=(int) Math.pow(10,digitos);
	      return Math.rint(numero*cifras)/cifras;
	}

	public static int obtienePorcentaje(int cienPorCiento, int parcial)
	{
		int resultado;
		
		if (cienPorCiento>0)
			resultado = parcial * 100 / cienPorCiento;
		else
			resultado = 0;
		
		return resultado;
	}

	 @SuppressLint("NewApi")
	public static String Decompress(String zipText) throws IOException 
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
	
	public static String fechaYYYYMMDDHHMMSStoDD_MM_YYYY(String fecha)
	{
		return fecha.substring(6, 8) + "/" + fecha.substring(4, 6) + "/" + fecha.substring(0, 4);
	}

	 
	 
}
