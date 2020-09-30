package ar.com.syswork.sysmobile.daos;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import ar.com.syswork.sysmobile.entities.Pedido;

public class DaoPedido implements DaoInterface<Pedido>{
	private SQLiteDatabase db;
	private SQLiteStatement statement;
	
	DaoCliente daoCliente;
	
	public DaoPedido(SQLiteDatabase db)
	{
		this.db=db;		
		statement = this.db.compileStatement("INSERT INTO pedidos (codCliente,fecha,idVendedor,totalNeto,totalFinal,transferido,gpsX,gpsY,facturar,incluirEnReparto) VALUES(?,?,?,?,?,?,?,?,?,?)");
		daoCliente = new DaoCliente(db);
	}

	@Override
	public long save(Pedido pedido) {
		
		long id=0;
		
		try {
			statement.clearBindings();
			statement.bindString(1, pedido.getCodCliente());
			statement.bindString(2, pedido.getFecha());
			statement.bindString(3, pedido.getIdVendedor());
			statement.bindDouble(4, pedido.getTotalNeto());
			statement.bindDouble(5, pedido.getTotalFinal());
			statement.bindLong(6,(pedido.isTransferido()) ? 1 : 0);
			statement.bindLong(7,pedido.getGpsX());
			statement.bindLong(8,pedido.getGpsY());
			statement.bindLong(9,pedido.isFacturar()?1:0);
			statement.bindLong(9,pedido.isIncluirEnReparto()?1:0);
			
			Log.d("SW","pedido.getGpsY()_: " + pedido.getGpsY());
			Log.d("SW","pedido.getGpsX()_: " + pedido.getGpsX());
			
			id = statement.executeInsert();
			
			
		}
		catch(SQLException e)
		{
			id = -1;
		}
		return id;
	}

	@Override
	public void update(Pedido pedido) {
		String sql;
		
		sql = "UPDATE pedidos SET codCliente = '" + pedido.getCodCliente() + "',"
								  +	" FECHA = '" + pedido.getFecha() + "'" 
								  + " idVendedor = '" + pedido.getIdVendedor() + "'," 
								  + " totalNeto = " + pedido.getTotalNeto() + "," 
								  + " totalFinal = " + pedido.getTotalFinal() + ","
								  + " transferido = " + ((pedido.isTransferido()) ? 1:0) + "," 
								  + " facturar = " + ((pedido.isFacturar()) ? 1:0) 
								  + " incluirEnReparto = " + ((pedido.isIncluirEnReparto()) ? 1:0) 
								  + " WHERE _id = " + pedido.getIdPedido();
		try
			{
				db.execSQL(sql);
			}
		catch(SQLiteException e)
			{
				e.printStackTrace();
			}
		
	}

	@Override
	public void delete(Pedido pedido) {
		String sql;
		
		sql = "DELETE FROM pedidos WHERE _id = " + pedido.getIdPedido();
		try
			{
				db.execSQL(sql);
			}
		catch(SQLiteException e)
			{
				e.printStackTrace();
			}
		
	}
	
	public void deleteAll(String where) {
		String sql;
		
		sql = "DELETE FROM pedidos ";
		if (!where.trim().equals("")){
			sql = sql + " WHERE " + where;
		}
		
		try
			{
				db.execSQL(sql);
			}
		catch(SQLiteException e)
			{
				e.printStackTrace();
			}
		
	}

	@Override
	public Pedido getByKey(String key) {
		return null;
	}
	
	public Pedido getById(int id) {

		String sql;
		Pedido pedido = null;

		sql = "SELECT _id,codCliente,fecha,idVendedor,totalNeto,totalFinal,transferido,facturar,incluirEnReparto FROM pedidos WHERE _id = " + id;
		try
			{
				Cursor c = db.rawQuery(sql,null);
				if(c.moveToFirst())
				{
					pedido = new Pedido();
		
					pedido.setIdPedido(c.getLong(0));
					pedido.setCodCliente(c.getString(1));
					pedido.setFecha(c.getString(2));
					pedido.setIdVendedor(c.getString(3));
					pedido.setTotalNeto(c.getDouble(4));
					pedido.setTotalFinal(c.getDouble(5));
					pedido.setTransferido((c.getInt(6)==1)?true:false);
					pedido.setFacturar((c.getInt(7)==1)?true:false);
					pedido.setIncluirEnReparto((c.getInt(8)==1)?true:false);
					pedido.setCliente(daoCliente.getByKey(c.getString(1)));
				}
				
				if(!c.isClosed())
				{
					c.close();
				}
			}
		catch(SQLiteException e)
			{
				e.printStackTrace();
			}

		return pedido;
	}

	@Override
	public List<Pedido> getAll(String where) {
		
		ArrayList<Pedido> lista = new ArrayList<Pedido>();
		
		String sql;
		Pedido pedido = null;
		
		sql = "SELECT _id,codCliente,fecha,idVendedor,totalNeto,totalFinal,transferido,facturar,incluirEnReparto FROM pedidos";
		if (!where.equals(""))
		{
			sql = sql + " WHERE " + where; 
		}

		try
			{
				Cursor c = db.rawQuery(sql,null);
				if(c.moveToFirst())
				{
					do
					{
						pedido = new Pedido();

						pedido.setIdPedido(c.getLong(0));
						pedido.setCodCliente(c.getString(1));
						pedido.setFecha(c.getString(2));
						pedido.setIdVendedor(c.getString(3));
						pedido.setTotalNeto(c.getDouble(4));
						pedido.setTotalFinal(c.getDouble(5));
						pedido.setTransferido((c.getInt(6)==1)?true:false);
						pedido.setFacturar((c.getInt(7)==1)?true:false);
						pedido.setIncluirEnReparto((c.getInt(7)==1)?true:false);
						pedido.setCliente(daoCliente.getByKey(c.getString(1)));
						lista.add(pedido);
					}
					
					while(c.moveToNext());
				}
				
				if(!c.isClosed())
				{
					c.close();
				}
				
			}
		catch(SQLiteException e)
			{
				e.printStackTrace();
			}
		
		return lista;
	}
	
	public int getCount()
	{
		return 	getCount("");
	}

	public int getCount(String where)
	{
		int cant=0;
		
		String sql = "SELECT count(_id) as cant FROM pedidos";
		if (!where.trim().equals(""))
		{
			sql = sql + " WHERE " + where;
		} 
		Cursor c = db.rawQuery(sql,null);
		
		if(c.moveToFirst())
			cant = c.getInt(0);
		
		if (!c.isClosed())
			c.close();

		return cant;
	}
}
