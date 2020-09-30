package ar.com.syswork.sysmobile.entities;

public class Registro {
	
	private String tabla;
	private long cantidadRegistros;
	private int paginas;
	
	public String getTabla() 
	{
		return tabla;
	}
	
	public void setTabla(String tabla) 
	{
		this.tabla = tabla;
	}
	
	public long getCantidadRegistros() 
	{
		return cantidadRegistros;
	}
	
	public void setCantidadRegistros(long cantidadRegistros) 
	{
		this.cantidadRegistros = cantidadRegistros;
	}
	
	public int getPaginas() 
	{
		return paginas;
	}
	
	public void setPaginas(int paginas) 
	{
		this.paginas = paginas;
	}
	
}
