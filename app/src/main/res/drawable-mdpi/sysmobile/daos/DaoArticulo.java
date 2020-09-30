package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.Articulo;

public class DaoArticulo implements DaoInterface<Articulo> {

	private SQLiteDatabase db;
	private SQLiteStatement statement;
	private int cantRegistros = 50;
	
	public DaoArticulo(SQLiteDatabase db)
	{
		this.db=db;		
		
		// precompilo los inserts
		String sql;
		sql = "INSERT INTO Articulos (idArticulo,descripcion,idRubro,iva,impuestosInternos,exento,"
				+ "precio1,precio2,precio3,precio4,precio5,precio6,precio7,precio8,precio9,precio10"
				+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		statement = db.compileStatement(sql);
	
	}

	
	@Override
	public long save(Articulo articulo) {
		
		statement.clearBindings();
		
		statement.bindString(1, articulo.getIdArticulo());
		statement.bindString(2, articulo.getDescripcion());
		statement.bindString(3, articulo.getIdRubro());
		statement.bindDouble(4, articulo.getIva());
		statement.bindDouble(5, articulo.getImpuestosInternos());
		// TODO Averiguar COMO MANEJO LOS BOOLEANS
		//                POR AHORA SON TODOS NO-EXENTOS 
		//statement.bindLong(6, articulo.isExento());
		statement.bindLong(6, 0);
		
		statement.bindDouble(7, articulo.getPrecio1());
		statement.bindDouble(8, articulo.getPrecio2());
		statement.bindDouble(9, articulo.getPrecio3());
		statement.bindDouble(10, articulo.getPrecio4());
		statement.bindDouble(11, articulo.getPrecio5());
		statement.bindDouble(12, articulo.getPrecio6());
		statement.bindDouble(13, articulo.getPrecio7());
		statement.bindDouble(14, articulo.getPrecio8());
		statement.bindDouble(15, articulo.getPrecio9());
		statement.bindDouble(16, articulo.getPrecio10());
		
		return statement.executeInsert();
	}

	@Override
	public void update(Articulo articulo) {
		String sql;
		
		
		sql = "UPDATE Articulos SET Descripcion = '" + articulo.getDescripcion() + "',idRubro = '" + articulo.getIdRubro() + "',"
				+ " iva = " + articulo.getIva() + ",impuestosInternos = " + articulo.getImpuestosInternos() +  ",exento = 0,"
				+ " precio1 = " + articulo.getPrecio1() + ", precio2 = " + articulo.getPrecio2() + ","
				+ " precio3 = " + articulo.getPrecio3() + ", precio4 = " + articulo.getPrecio4() + ","
				+ " precio5 = " + articulo.getPrecio5() + ", precio6 = " + articulo.getPrecio6() + ","
				+ " precio7 = " + articulo.getPrecio7() + ", precio8 = " + articulo.getPrecio8() + ","
				+ " precio9 = " + articulo.getPrecio9() + ", precio10 = " + articulo.getPrecio10() + ""
				+ " WHERE idArticulo = '" + articulo.getIdArticulo() +"'";  
		
		db.execSQL(sql);
	
		
	}

	@Override
	public void delete(Articulo articulo) {
		
		String sql = "DELETE FROM Articulos WHERE idArticulo = '" + articulo.getIdArticulo() +"'";  
		db.execSQL(sql);
		
	}

	@Override
	public Articulo getByKey(String key) {
		
		Articulo articulo = null;
		Cursor c;
		
		String sql = "SELECT  idArticulo,descripcion,idRubro,iva,impuestosInternos,exento,"
				     + "precio1,precio2,precio3,precio4,precio5,precio6,precio7,precio8,precio9,precio10 FROM ARTICULOS WHERE IDARTICULO = '" + key + "'";
				
		c = db.rawQuery(sql, null);		
						
		if(c.moveToFirst())
		{
			articulo = new Articulo();
			articulo.setIdArticulo(c.getString(0));
			articulo.setDescripcion(c.getString(1));
			articulo.setIdRubro(c.getString(2));
			articulo.setIva((byte) c.getDouble(3));
			articulo.setImpuestosInternos(c.getDouble(4));
			articulo.setExento(false);
			articulo.setPrecio1(c.getDouble(6));
			articulo.setPrecio2(c.getDouble(7));
			articulo.setPrecio3(c.getDouble(8));
			articulo.setPrecio4(c.getDouble(9));
			articulo.setPrecio5(c.getDouble(10));
			articulo.setPrecio6(c.getDouble(11));
			articulo.setPrecio7(c.getDouble(12));
			articulo.setPrecio8(c.getDouble(13));
			articulo.setPrecio9(c.getDouble(14));
			articulo.setPrecio10(c.getDouble(15));
		}
		if(!c.isClosed())
		{
			c.close();
		}
		
		return articulo;
	   
	}

	@Override
	public List<Articulo> getAll(String where) {

		ArrayList<Articulo> lista = new ArrayList<Articulo>();
		Articulo articulo = null;

		String sql = "SELECT idArticulo,descripcion,idRubro,iva,impuestosInternos,exento,"
				     + "precio1,precio2,precio3,precio4,precio5,precio6,precio7,precio8,precio9,precio10 FROM ARTICULOS";
		
		if (!where.equals("")){
			sql = sql + " WHERE " + where;
		}	
		
		Cursor c = db.rawQuery(sql,null);
						
		if(c.moveToFirst())
		{
			do
			{
				articulo = new Articulo();
				articulo.setIdArticulo(c.getString(0));
				articulo.setDescripcion(c.getString(1));
				articulo.setIdRubro(c.getString(2));
				articulo.setIva((byte) c.getDouble(3));
				articulo.setImpuestosInternos(c.getDouble(4));
				articulo.setExento(false);
				articulo.setPrecio1(c.getDouble(6));
				articulo.setPrecio2(c.getDouble(7));
				articulo.setPrecio3(c.getDouble(8));
				articulo.setPrecio4(c.getDouble(9));
				articulo.setPrecio5(c.getDouble(10));
				articulo.setPrecio6(c.getDouble(11));
				articulo.setPrecio7(c.getDouble(12));
				articulo.setPrecio8(c.getDouble(13));
				articulo.setPrecio9(c.getDouble(14));
				articulo.setPrecio10(c.getDouble(15));
				
				lista.add(articulo);
			}
			
			while(c.moveToNext());
		}
		
		if(!c.isClosed())
		{
			c.close();
		}
		
		return lista;
	}

	public List<Articulo> getAllWithLimit(String where,int limitDesde, String order) {
		
		ArrayList<Articulo> lista = new ArrayList<Articulo>();
		Articulo articulo = null;
		
		String sql = "SELECT idArticulo,descripcion,idRubro,iva,impuestosInternos,exento,"
			     + "precio1,precio2,precio3,precio4,precio5,precio6,precio7,precio8,precio9,precio10 FROM ARTICULOS";
		
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
				articulo = new Articulo();
				articulo.setIdArticulo(c.getString(0));
				articulo.setDescripcion(c.getString(1));
				articulo.setIdRubro(c.getString(2));
				articulo.setIva((byte) c.getDouble(3));
				articulo.setImpuestosInternos(c.getDouble(4));
				articulo.setExento(false);
				articulo.setPrecio1(c.getDouble(6));
				articulo.setPrecio2(c.getDouble(7));
				articulo.setPrecio3(c.getDouble(8));
				articulo.setPrecio4(c.getDouble(9));
				articulo.setPrecio5(c.getDouble(10));
				articulo.setPrecio6(c.getDouble(11));
				articulo.setPrecio7(c.getDouble(12));
				articulo.setPrecio8(c.getDouble(13));
				articulo.setPrecio9(c.getDouble(14));
				articulo.setPrecio10(c.getDouble(15));

				lista.add(articulo);
			}
			
			while(c.moveToNext());
		}
		
		if(!c.isClosed())
		{
			c.close();
		}
		
		Log.d("SW","La lista tiene " + lista.size());

		return lista;
	}


	public void deleteAll() {
		
		String sql = "DELETE FROM Articulos";  
		db.execSQL(sql);
		
	}


}
