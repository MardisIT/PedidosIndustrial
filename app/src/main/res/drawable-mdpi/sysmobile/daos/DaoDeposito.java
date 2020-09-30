package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.Deposito;

public class DaoDeposito implements DaoInterface<Deposito>{
	
	private SQLiteDatabase db;
	private SQLiteStatement statement;
	
	public DaoDeposito(SQLiteDatabase db)
	{
		this.db=db;		
		statement = db.compileStatement("INSERT INTO depositos (idDeposito,Descripcion) VALUES(?,?)");
	}

	@Override
	public long save(Deposito deposito) {
		
		statement.clearBindings();
		statement.bindString(1, deposito.getIdDeposito());
		statement.bindString(2, deposito.getDescripcion());
		
		return statement.executeInsert();
	}

	@Override
	public void update(Deposito deposito) 
	{
		db.execSQL("UPDATE depositos SET Descripcion = '" + deposito.getDescripcion() + "' WHERE idDeposito = '" + deposito.getIdDeposito() + "'");
	}

	@Override
	public void delete(Deposito deposito) 
	{
		db.execSQL("DELETE FROM Depositos WHERE idDeposito = '" + deposito.getIdDeposito() + "'");
		
	}

	@Override
	public Deposito getByKey(String key) {
		Deposito deposito = null;		
		Cursor c;
		
		c = db.rawQuery("SELECT idDeposito,Descripcion FROM Depositos WHERE idDeposito = '" + key + "'", null);		
		
		if(c.moveToFirst())
		{
			deposito = new Deposito();
			deposito.setIdDeposito(c.getString(0));
			deposito.setDescripcion(c.getString(1));
		}
		if(!c.isClosed())
		{
			c.close();
		}

		return deposito;
	}

	@Override
	public List<Deposito> getAll(String where) {
		
		ArrayList<Deposito> lista = new ArrayList<Deposito>();
		Deposito deposito = null;

		String sql = "SELECT _id,idDeposito,Descripcion FROM Depositos ";
		
		if (!where.equals("")){
			sql = sql + " WHERE " + where;
		}	
		
		Cursor c = db.rawQuery(sql,null);
		
		if(c.moveToFirst())
		{
			do
			{
				deposito= new Deposito();
				deposito.setIdDeposito(c.getString(0));
				deposito.setDescripcion(c.getString(1));
				lista.add(deposito);
			}
			
			while(c.moveToNext());
		}
		
		if(!c.isClosed())
		{
			c.close();
		}

		return lista;
	}
	
	public void deleteAll() {
		db.execSQL("DELETE FROM Depositos");
	}

}
