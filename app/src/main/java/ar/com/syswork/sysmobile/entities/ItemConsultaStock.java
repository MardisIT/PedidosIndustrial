package ar.com.syswork.sysmobile.entities;

public class ItemConsultaStock {
	
	private String idDeposito;
	private String descripcion;
	private double cantidad ;
	
	public String getIdDeposito() 
	{
		return idDeposito;
	}
	
	public void setIdDeposito(String idDeposito) 
	{
		this.idDeposito = idDeposito;
	}
	
	public double getCantidad() 
	{
		return cantidad;
	}
	
	public void setCantidad(double cantidad) 
	{
		this.cantidad = cantidad;
	}

	public String getDescripcion() 
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}
	
}
