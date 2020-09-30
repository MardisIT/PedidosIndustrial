package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.Vendedor;

public class DaoVendedor implements DaoInterface<Vendedor>{

	private SQLiteDatabase db;
	private SQLiteStatement statement;
	
	public DaoVendedor(SQLiteDatabase db)
	{
		this.db=db;		
		statement= db.compileStatement("INSERT INTO Vendedores (idVendedor,Nombre,CodigoDeValidacion) VALUES(?,?,?)");
	}

	@Override
	public long save(Vendedor vendedor) {
		
		statement.clearBindings();
		
		statement.bindString(1, vendedor.getIdVendedor());
		statement.bindString(2, vendedor.getNombre());
		statement.bindString(3, vendedor.getCodigoDeValidacion());
		
		return statement.executeInsert();
	}

	@Override
	public void update(Vendedor vendedor) {
		db.execSQL("UPDATE Vendedores SET Nombre = '" + vendedor.getNombre()+ "', CodigoDeValidacion = '" + vendedor.getCodigoDeValidacion() + "' WHERE idVendedor = '" + vendedor.getIdVendedor() + "'");
	}

	@Override
	public void delete(Vendedor vendedor) {
		db.execSQL("DELETE FROM Vendedores  WHERE idVendedor = '" + vendedor.getIdVendedor() + "'");
		
	}

	@Override
	public Vendedor getByKey(String key) {
		
		Vendedor vendedor = null;		
		Cursor c;
		
		c = db.rawQuery("SELECT idVendedor,Nombre,CodigoDeValidacion FROM Vendedores WHERE idVendedor = '" + key + "'", null);		
		
		if(c.moveToFirst())
		{
			vendedor = new Vendedor();
			vendedor.setIdVendedor(c.getString(0));
			vendedor.setNombre(c.getString(1));
			vendedor.setCodigoDeValidacion(c.getString(2));
		}
		if(!c.isClosed())
		{
			c.close();
		}

		return vendedor;

	}

	@Override
	public List<Vendedor> getAll(String where) {
		
		ArrayList<Vendedor> lista = new ArrayList<Vendedor>();
		Vendedor vendedor = null;

		String sql = "SELECT _id,idVendedor,Nombre, CodigoDeValidacion FROM Vendedores ";
		
		if (!where.equals("")){
			sql = sql + " WHERE " + where;
		}	
		
		Cursor c = db.rawQuery(sql,null);
		
		if(c.moveToFirst())
		{
			do
			{
				vendedor= new Vendedor();
				vendedor.setIdVendedor(c.getString(0));
				vendedor.setNombre(c.getString(1));
				lista.add(vendedor);
			}
			
			while(c.moveToNext());
		}
		
		if(!c.isClosed())
		{
			c.close();
		}

		return lista;
	}

	public int getCount()
	{
		int cant=0;
		
		String sql = "SELECT count(_id) as cant FROM vendedores";
		Cursor c = db.rawQuery(sql,null);
		
		if(c.moveToFirst())
			cant = c.getInt(0);
		
		if (!c.isClosed())
			c.close();
		
		return cant;
	}

	public void deleteAll() {
		db.execSQL("DELETE FROM Vendedores");
	}
}
