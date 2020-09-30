package ar.com.syswork.sysmobile.entities;

public class Vendedor {
	
	private String idVendedor;
	private String nombre;
	private String codigoDeValidacion;
	
	public String getIdVendedor() 
	{
		return idVendedor;
	}
	
	public void setIdVendedor(String idVendedor) 
	{
		this.idVendedor = idVendedor;
	}
	
	public String getNombre() 
	{
		return nombre;
	}
	
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	
	public String getCodigoDeValidacion() 
	{
		return codigoDeValidacion;
	}
	
	public void setCodigoDeValidacion(String codigoDeValidacion) 
	{
		this.codigoDeValidacion = codigoDeValidacion;
	}

}
