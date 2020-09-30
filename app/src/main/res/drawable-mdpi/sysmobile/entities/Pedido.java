package ar.com.syswork.sysmobile.entities;

public class Pedido {
	
	private long idPedido;
	private String codCliente;
	private String fecha;
	private String idVendedor;
	private double totalNeto;
	private double totalFinal;
	private boolean transferido;
	private long gpsX;
	private long gpsY;
	private boolean facturar;
	private boolean incluirEnReparto;
	
	private Cliente cliente;
	
	
	public long getIdPedido() 
	{
		return idPedido;
	}
	
	public void setIdPedido(long idPedido) 
	{
		this.idPedido = idPedido;
	}

	public String getCodCliente() 
	{
		return codCliente;
	}
	
	public void setCodCliente(String codCliente) 
	{
		this.codCliente = codCliente;
	}
	
	public String getFecha() 
	{
		return fecha;
	}
	
	public void setFecha(String fecha) 
	{
		this.fecha = fecha;
	}
	
	public String getIdVendedor() 
	{
		return idVendedor;
	}
	
	public void setIdVendedor(String idVendedor) 
	{
		this.idVendedor = idVendedor;
	}
	
	public double getTotalNeto() 
	{
		return totalNeto;
	}
	
	public void setTotalNeto(double totalNeto) 
	{
		this.totalNeto = totalNeto;
	}
	
	public double getTotalFinal() 
	{
		return totalFinal;
	}
	
	public void setTotalFinal(double totalFinal) 
	{
		this.totalFinal = totalFinal;
	}
	
	public boolean isTransferido() 
	{
		return transferido;
	}
	
	public void setTransferido(boolean transferido) 
	{
		this.transferido = transferido;
	}
	
	public long getGpsX() 
	{
		return gpsX;
	}
	
	public void setGpsX(long gpsX) 
	{
		this.gpsX = gpsX;
	}
	
	public long getGpsY() 
	{
		return gpsY;
	}
	
	public void setGpsY(long gpsY) 
	{
		this.gpsY = gpsY;
	}

	public boolean isFacturar() 
	{
		return facturar;
	}

	public void setFacturar(boolean facturar) 
	{
		this.facturar = facturar;
	}

	public Cliente getCliente() 
	{
		return cliente;
	}

	public void setCliente(Cliente cliente) 
	{
		this.cliente = cliente;
	}

	public boolean isIncluirEnReparto() 
	{
		return incluirEnReparto;
	}

	public void setIncluirEnReparto(boolean incluirEnReparto) 
	{
		this.incluirEnReparto = incluirEnReparto;
	}	
}
