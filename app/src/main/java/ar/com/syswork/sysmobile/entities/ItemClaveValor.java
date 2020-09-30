package ar.com.syswork.sysmobile.entities;

public class ItemClaveValor {
	
	private String clave;
	private String valorString;
	private boolean valorBoolean;
	private int tipo;
	
	public static final int TIPO_STRING = 1;
	public static final int TIPO_BOOLEAN = 2;
	
	public ItemClaveValor(String clave,String valor)
	{
		this.clave = clave;
		this.valorString=valor;
		this.tipo=ItemClaveValor.TIPO_STRING;
	}
	
	public ItemClaveValor(String clave,boolean valor)
	{
		this.clave = clave;
		this.valorBoolean=valor;
		this.tipo=ItemClaveValor.TIPO_BOOLEAN;
	}
	
	public String getClave() 
	{
		return clave;
	}
	
	public void setClave(String clave) 
	{
		this.clave = clave;
	}
	
	public String getValorString() 
	{
		return valorString;
	}
	
	public void setValorString(String valorString) 
	{
		this.valorString = valorString;
	}
	
	public boolean getValorBoolean() 
	{
		return valorBoolean;
	}
	
	public void setValorBoolean(boolean valorBoolean) 
	{
		this.valorBoolean = valorBoolean;
	}
	
	public int getTipo() 
	{
		return tipo;
	}
	
	public void setTipo(int tipo) 
	{
		this.tipo = tipo;
	}
			
}
