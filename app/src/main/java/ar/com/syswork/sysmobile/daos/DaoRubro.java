package ar.com.syswork.sysmobile.daos;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import ar.com.syswork.sysmobile.entities.Rubro;

public class DaoRubro implements DaoInterface<Rubro>{


	private SQLiteDatabase db;
	private SQLiteStatement statement;
	
	public DaoRubro(SQLiteDatabase db)
	{
		this.db=db;		
		statement = db.compileStatement("INSERT INTO Rubros (idRubro,Descripcion) VALUES(?,?)");
	}
	
	
	@Override
	public long save(Rubro rubro) 
	{
		statement.clearBindings();
		statement.bindString(1, rubro.getIdRubro());
		statement.bindString(2, rubro.getDescripcion());
		
		return statement.executeInsert();
	}

	@Override
	public void update(Rubro rubro) {
		db.execSQL("UPDATE Rubros SET Descripcion = '" + rubro.getDescripcion() + "' WHERE idRubro = '" + rubro.getIdRubro() + "'");
	}

	@Override
	public void delete(Rubro rubro) {
		db.execSQL("DELETE FROM Rubros WHERE idRubro = '" + rubro.getIdRubro() + "'");
	}

	@Override
	public Rubro getByKey(String key) {
		
		Rubro rubro = null;		
		Cursor c;
		
		c = db.rawQuery("SELECT idRubro,Descripcion FROM Rubros WHERE idRubro = '" + key + "'", null);		
		
		if(c.moveToFirst())
		{
			rubro = new Rubro();
			rubro.setIdRubro(c.getString(0));
			rubro.setDescripcion(c.getString(1));
		}
		if(!c.isClosed())
		{
			c.close();
		}

		return rubro;
	}
	
	@Override
	public List<Rubro> getAll(String where) {
		
		ArrayList<Rubro> lista = new ArrayList<Rubro>();
		Rubro rubro = null;

		String sql = "SELECT _id,idRubro,Descripcion FROM Rubros ";
		
		if (!where.equals("")){
			sql = sql + " WHERE " + where;
		}	
		
		Cursor c = db.rawQuery(sql,null);
		
		if(c.moveToFirst())
		{
			do
			{
				rubro= new Rubro();
				rubro.setIdRubro(c.getString(0));
				rubro.setDescripcion(c.getString(1));
				lista.add(rubro);
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
		db.execSQL("DELETE FROM Rubros");
	}

}
