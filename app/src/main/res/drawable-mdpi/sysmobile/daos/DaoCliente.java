package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.Cliente;

public class DaoCliente implements DaoInterface<Cliente>{
	
	private SQLiteDatabase db;
	private SQLiteStatement statement;
	private int cantRegistros = 50;
	
	public DaoCliente(SQLiteDatabase db)
	{
		this.db=db;		
		
		String sql;
		sql = "INSERT INTO Clientes (Codigo,CodigoOpcional,RazonSocial,"
				+ "calleNroPisoDpto,Localidad,Cuit,"
				+ "Iva,ClaseDePrecio,PorcDto,CpteDefault,idVendedor,telefono,email" 
				+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		statement = db.compileStatement(sql);
	}

	@Override
	public long save(Cliente cliente) {
		
		statement.clearBindings();
		
		statement.bindString(1, cliente.getCodigo());
		statement.bindString(2, cliente.getCodigoOpcional());
		statement.bindString(3, cliente.getRazonSocial());
		statement.bindString(4, cliente.getCalleNroPisoDpto());
		statement.bindString(5, cliente.getLocalidad());
		statement.bindString(6, cliente.getCuit());
		statement.bindDouble(7, (double) cliente.getIva());
		statement.bindDouble(8, (double) cliente.getClaseDePrecio());
		statement.bindDouble(9, cliente.getPorcDto());
		statement.bindString(10, cliente.getCpteDefault());
		statement.bindString(11, cliente.getIdVendedor());
		statement.bindString(12, cliente.getTelefono());
		statement.bindString(13, cliente.getMail());
		
		return statement.executeInsert();
	}

	@Override
	public void update(Cliente cliente) {
		
		String sql = "UPDATE Clientes SET CodigoOpcional='" + cliente.getCodigoOpcional() + "'," 
				+ "RazonSocial='" + cliente.getRazonSocial() + "',"
				+ "calleNroPisoDpto='" + cliente.getCalleNroPisoDpto() + "',"
				+ "Localidad = '" + cliente.getLocalidad() + "',"
				+ "Cuit= '" + cliente.getCuit() + "',"
				+ "Iva = " + cliente.getIva() + ","
				+ "ClaseDePrecio = " + cliente.getClaseDePrecio() + ","
				+ "PorcDto = " + cliente.getPorcDto() + ","
				+ "CpteDefault ='" + cliente.getCpteDefault() + "'," 
				+ "idVendedor ='" + cliente.getIdVendedor() + "'," 
				+ "telefono ='" + cliente.getTelefono() + "'," 
				+ "email ='" + cliente.getMail() + "'" 
				+ " WHERE Codigo = '" + cliente.getCodigo() +  "'" ;
		
		db.execSQL(sql);
		
	}

	@Override
	public void delete(Cliente cliente) {
		String sql = "DELETE FROM Clientes WHERE Codigo = '" + cliente.getCodigo() +  "'" ;
		db.execSQL(sql);
	}

	@Override
	public Cliente getByKey(String key) {
		
		Cliente cliente = null;		
		Cursor c;
		
				
		c = db.rawQuery("SELECT Codigo,CodigoOpcional,RazonSocial,"
				+ "calleNroPisoDpto,Localidad,Cuit,"
				+ "Iva,ClaseDePrecio,PorcDto,CpteDefault,idVendedor,telefono,email FROM CLIENTES WHERE codigo = '" + key + "'", null);		
		
		
		
   		if(c.moveToFirst())
		{
			cliente = new Cliente();
			
			
			cliente.setCodigo(c.getString(0));
			cliente.setCodigoOpcional(c.getString(1));
			cliente.setRazonSocial(c.getString(2));
			cliente.setCalleNroPisoDpto(c.getString(3));
			cliente.setLocalidad(c.getString(4));
			cliente.setCuit(c.getString(5));
			cliente.setIva((byte) c.getDouble(6));
			cliente.setClaseDePrecio((byte) c.getDouble(7));
			cliente.setPorcDto(c.getDouble(8));
			cliente.setCpteDefault(c.getString(9));
			cliente.setIdVendedor(c.getString(10));
			cliente.setTelefono(c.getString(11));
			cliente.setMail(c.getString(12));

		}
		if(!c.isClosed())
		{
			c.close();
		}
		

		return cliente;
	}

	@Override
	public List<Cliente> getAll(String where) {
		
		ArrayList<Cliente> lista = new ArrayList<Cliente>();
		Cliente cliente = null;

		String sql = "SELECT Codigo,CodigoOpcional,RazonSocial,"
				+ "calleNroPisoDpto,Localidad,Cuit,"
				+ "Iva,ClaseDePrecio,PorcDto,CpteDefault,idVendedor,telefono,email FROM CLIENTES" ;
		
		if (!where.equals("")){
			sql = sql + " WHERE " + where;
		}	
		
		Cursor c = db.rawQuery(sql,null);
		if(c.moveToFirst())
		{
			do
			{
				cliente= new Cliente();
				cliente.setCodigo(c.getString(0));
				cliente.setCodigoOpcional(c.getString(1));
				cliente.setRazonSocial(c.getString(2));
				cliente.setCalleNroPisoDpto(c.getString(3));
				cliente.setLocalidad(c.getString(4));
				cliente.setCuit(c.getString(5));
				cliente.setIva((byte) c.getDouble(6));
				cliente.setClaseDePrecio((byte) c.getDouble(7));
				cliente.setPorcDto(c.getDouble(8));
				cliente.setCpteDefault(c.getString(9));
				cliente.setIdVendedor(c.getString(10));
				cliente.setTelefono(c.getString(11));
				cliente.setMail(c.getString(12));

				lista.add(cliente);
			}
			
			while(c.moveToNext());
		}
		
		if(!c.isClosed())
		{
			c.close();
		}

		return lista;
	}

	
	public List<Cliente> getAllWithLimit(String where,int limitDesde, String order) {
		
		ArrayList<Cliente> lista = new ArrayList<Cliente>();
		Cliente cliente = null;
		
		String sql = "SELECT Codigo,CodigoOpcional,RazonSocial,"
				+ "calleNroPisoDpto,Localidad,Cuit,"
				+ "Iva,ClaseDePrecio,PorcDto,CpteDefault,idVendedor,telefono,email FROM CLIENTES" ;
		
		if (!where.equals("")){
			sql = sql + " WHERE " + where;
		}	
		
		if (!order.equals("")){
			sql = sql + " ORDER BY " + order;
		}
		
		if (!(limitDesde==-1)){
			sql = sql + " LIMIT " + limitDesde + "," + cantRegistros;
		}
				
		Cursor c = db.rawQuery(sql,null);
		
		if(c.moveToFirst())
		{
			do
			{
				cliente= new Cliente();
				cliente.setCodigo(c.getString(0));
				cliente.setCodigoOpcional(c.getString(1));
				cliente.setRazonSocial(c.getString(2));
				cliente.setCalleNroPisoDpto(c.getString(3));
				cliente.setLocalidad(c.getString(4));
				cliente.setCuit(c.getString(5));
				cliente.setIva((byte) c.getDouble(6));
				cliente.setClaseDePrecio((byte) c.getDouble(7));
				cliente.setPorcDto(c.getDouble(8));
				cliente.setCpteDefault(c.getString(9));
				cliente.setIdVendedor(c.getString(10));
				cliente.setTelefono(c.getString(11));
				cliente.setMail(c.getString(12));

				lista.add(cliente);
			}
			
			while(c.moveToNext());
		}
		
		if(!c.isClosed())
			c.close();
		
		
		return lista;
	}
	
	//consulta localidades
		public List<Cliente> getAllLocalidades()
		{

			ArrayList<Cliente> lista = new ArrayList<Cliente>();
			Cliente cliente = null;

			String sql = "SELECT distinct (Localidad)  FROM CLIENTES" ;



			Cursor c = db.rawQuery(sql,null);

			if(c.moveToFirst())
			{
				do
				{
					cliente= new Cliente();
					cliente.setLocalidad(c.getString(0));
					System.out.println("LOCALIDAD "+c.getString(0));
					lista.add(cliente);
				}

				while(c.moveToNext());
			}

			if(!c.isClosed())
				c.close();


			return lista;
		}

	public void deleteAll() {
		String sql = "DELETE FROM Clientes" ;
		db.execSQL(sql);
	}

	
}
