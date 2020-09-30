package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.PedidoItem;

public class DaoPedidoItem implements DaoInterface<PedidoItem>{

	private SQLiteDatabase db;
	private SQLiteStatement statement;
	
	public DaoPedidoItem(SQLiteDatabase db)
	{
		this.db=db;		
		statement = this.db.compileStatement(
				"INSERT INTO pedidosItems (idPedido,idArticulo,cantidad," +
				"importeUnitario,porcDescuento,total,transferido,ppago,nespecial,formapago,unidad) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
	}

	@Override
	public long save(PedidoItem pedidoItem) {

		long id=0;
		
		try {
			statement.clearBindings();
			statement.bindLong(1,pedidoItem.getIdPedido());
			statement.bindString(2,pedidoItem.getIdArticulo());
			statement.bindDouble(3, pedidoItem.getCantidad());
			statement.bindDouble(4, pedidoItem.getImporteUnitario());
			statement.bindDouble(5, pedidoItem.getPorcDto());
			statement.bindDouble(6, pedidoItem.getTotal());
			statement.bindLong( 7,(pedidoItem.isTransferido()) ? 1 : 0);
			statement.bindDouble(8, pedidoItem.getPpago());
			statement.bindDouble(9, pedidoItem.getNespecial());
			statement.bindString(10, pedidoItem.getFormaPago());
			statement.bindString(11, pedidoItem.getUnidcajas());
			
			id = statement.executeInsert();
		}
		catch(SQLException e)
		{
			id = -1;
		}
		return id;
	}

	@Override
	public void update(PedidoItem pedidoItem) {
		String sql;
		
		sql = "UPDATE pedidosItems SET idPedido = " + pedidoItem.getIdPedido()  +
							",idArticulo = '" + pedidoItem.getIdArticulo() + "'" +
							",cantidad = " + pedidoItem.getCantidad() + 
							",importeUnitario = " + pedidoItem.getImporteUnitario() + 
							",porcDescuento = " + pedidoItem.getPorcDto() + 
							",total = " + pedidoItem.getTotal() +
							",ppago = " + pedidoItem.getPpago() +
							",nespecial = " + pedidoItem.getNespecial() +
							",formapago = " + pedidoItem.getFormaPago() +
							",unidad = " + pedidoItem.getUnidcajas() +
							",transferido = " + ((pedidoItem.isTransferido())?1:0) +
						    " WHERE _id = " + pedidoItem.getIdPedidoItem();
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
	public void delete(PedidoItem pedidoItem) {
		String sql;
		
		sql = "DELETE FROM pedidosItems WHERE _id = " + pedidoItem.getIdPedidoItem();
		try
		{
			db.execSQL(sql);
		}
		catch(SQLiteException e)
		{
			e.printStackTrace();
		}
		
	}
	public void deleteByIdPedido(long idPedido) {
		String sql;
		
		sql = "DELETE FROM pedidosItems WHERE idPedido = " + idPedido;
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
		
		sql = "DELETE FROM pedidosItems ";
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
	public PedidoItem getByKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public PedidoItem getById(long idPedidoItem) {
		
		String sql;
		PedidoItem pedidoItem = null;
				
		sql = "SELECT _id,idPedido,idArticulo,cantidad,importeUnitario,porcDescuento,total,transferido,ppago,nespecial,formapago,unidad FROM pedidosItems WHERE _id = " + idPedidoItem;
		try
			{
				Cursor c = db.rawQuery(sql,null);
				if(c.moveToFirst())
				{
					pedidoItem = new PedidoItem();
					pedidoItem.setidPedidoItem(c.getLong(0));
					pedidoItem.setIdPedido(c.getLong(1));
					pedidoItem.setIdArticulo(c.getString(2));
					pedidoItem.setCantidad(c.getDouble(3));
					pedidoItem.setImporteUnitario(c.getDouble(4));
					pedidoItem.setPorcDto(c.getDouble(5));
					pedidoItem.setTotal(c.getDouble(6));
					pedidoItem.setTransferido((c.getInt(7)==1)?true:false);
					pedidoItem.setPpago(c.getDouble(8));
					pedidoItem.setNespecial(c.getDouble(9));
					pedidoItem.setFormaPago(c.getString(10));
					pedidoItem.setUnidcajas(c.getString(11));
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
		
		return pedidoItem;
	}

	@Override
	public List<PedidoItem> getAll(String where) {
		ArrayList<PedidoItem> lista = new ArrayList<PedidoItem>();
		
		String sql;
		PedidoItem pedidoItem = null;
		
		sql = "SELECT _id,idPedido,idArticulo,cantidad,importeUnitario,porcDescuento,total,transferido,ppago,nespecial,formapago,unidad FROM pedidosItems ";
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
						pedidoItem = new PedidoItem();
						pedidoItem.setidPedidoItem(c.getLong(0));
						pedidoItem.setIdPedido(c.getLong(1));
						pedidoItem.setIdArticulo(c.getString(2));
						pedidoItem.setCantidad(c.getDouble(3));
						pedidoItem.setImporteUnitario(c.getDouble(4));
						pedidoItem.setPorcDto(c.getDouble(5));
						pedidoItem.setTotal(c.getDouble(6));
						pedidoItem.setTransferido((c.getInt(7)==1)?true:false);
						pedidoItem.setPpago(c.getDouble(8));
						pedidoItem.setNespecial(c.getDouble(9));
						pedidoItem.setFormaPago(c.getString(10));
						pedidoItem.setUnidcajas(c.getString(11));
						
						lista.add(pedidoItem);
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
}
