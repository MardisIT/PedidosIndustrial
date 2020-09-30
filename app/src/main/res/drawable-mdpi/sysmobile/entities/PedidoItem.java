package ar.com.syswork.sysmobile.entities;

public class PedidoItem {
	
	private long idPedidoItem;
	private long idPedido;
	private String idArticulo;
	private double cantidad;
	private double importeUnitario;
	private double porcDto;
	private double total;
	private double ppago;
	private  double nespecial;
	private  String FormaPago;
private String unidcajas;

	public String getUnidcajas() {
		return unidcajas;
	}

	public void setUnidcajas(String unidcajas) {
		this.unidcajas = unidcajas;
	}

	public String getFormaPago() {
		return FormaPago;
	}

	public void setFormaPago(String formaPago) {
		FormaPago = formaPago;
	}

	public double getPpago() {
		return ppago;
	}

	public void setPpago(double ppago) {
		this.ppago = ppago;
	}

	public double getNespecial() {
		return nespecial;
	}

	public void setNespecial(double nespecial) {
		this.nespecial = nespecial;
	}

	private boolean transferido;
	private String auxDescripcionArticulo;
	
	
	public long getIdPedidoItem() 
	{
		return idPedidoItem;
	}
	
	public void setidPedidoItem(long idPedidoItem ) 
	{
		this.idPedidoItem= idPedidoItem;
	}
	
	public long getIdPedido() 
	{
		return idPedido;
	}
	
	public void setIdPedido(long idPedido) 
	{
		this.idPedido = idPedido;
	}
	
	public String  getIdArticulo() 
	{
		return idArticulo;
	}
	
	public void setIdArticulo(String idArticulo) 
	{
		this.idArticulo = idArticulo;
	}
	
	public double getCantidad() 
	{
		return cantidad;
	}
	
	public void setCantidad(double cantidad) 
	{
		this.cantidad = cantidad;
	}
	
	public double getPorcDto() 
	{
		return porcDto;
	}
	
	public void setPorcDto(double porcDto) 
	{
		this.porcDto = porcDto;
	}
	
	public double getTotal() 
	{
		return total;
	}
	
	public void setTotal(double total) 
	{
		this.total = total;
	}
	
	public boolean isTransferido() 
	{
		return transferido;
	}
	
	public void setTransferido(boolean transferido) 
	{
		this.transferido = transferido;
	}
	
	public double getImporteUnitario() 
	{
		return importeUnitario;
	}
	
	public void setImporteUnitario(double importeUnitario) 
	{
		this.importeUnitario = importeUnitario;
	}
	
	public String getAuxDescripcionArticulo() 
	{
		return auxDescripcionArticulo;
	}
	
	public void setAuxDescripcionArticulo(String auxDescripcionArticulo) 
	{
		this.auxDescripcionArticulo = auxDescripcionArticulo;
	}
	
}
