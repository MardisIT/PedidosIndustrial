package ar.com.syswork.sysmobile.entities;

public class ItemMenuPrincipal {
	
	private int imagen;
	private String titulo;
	private int cantidad;
	private int accion;
	
	public int getImagen() 
	{
		return imagen;
	}
	
	public void setImagen(int imagen) 
	{
		this.imagen = imagen;
	}
	
	public String getTitulo() 
	{
		return titulo;
	}
	
	public void setTitulo(String titulo) 
	{
		this.titulo = titulo;
	}
	
	public int getAccion() 
	{
		return accion;
	}
	
	public void setAccion(int accion) 
	{
		this.accion = accion;
	}
	
	public int getCantidad() 
	{
		return cantidad;
	}
	
	public void setCantidad(int cantidad) 
	{
		this.cantidad = cantidad;
	}
}
