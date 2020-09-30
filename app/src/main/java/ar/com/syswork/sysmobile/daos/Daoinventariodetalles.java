package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.Inventario;
import ar.com.syswork.sysmobile.entities.inventariodetalles;

public class Daoinventariodetalles implements DaoInterface<inventariodetalles>{
    private SQLiteDatabase db;
    private SQLiteStatement statement;

    DaoCliente daoCliente;
    DaoChequePagos daoChequePagos;
    DaoPagosDetalles daoPagosDetalles;
    DaoArticulo daoArticulo;

    public Daoinventariodetalles(SQLiteDatabase db)
    {
        this.db=db;
        statement = this.db.compileStatement("INSERT INTO inventariodetalles (" +
                "idinventario," +
                "codproducto," +
                "valor,unidad) VALUES(?,?,?,?)");
        daoCliente = new DaoCliente(db);
        daoChequePagos=new DaoChequePagos(db);
        daoPagosDetalles=new DaoPagosDetalles(db);
        daoArticulo= new DaoArticulo(db);
    }

    @Override
    public long save(inventariodetalles _inventariodetalles) {

        long id=0;

        try {
            statement.clearBindings();
            statement.bindLong(1, _inventariodetalles.getIdinventario());
            statement.bindString(2, _inventariodetalles.getCodproducto());
            statement.bindLong(3, _inventariodetalles.getValor());
            statement.bindString(4, _inventariodetalles.getUnidad());


            id = statement.executeInsert();


        }
        catch(SQLException e)
        {
            id = -1;
        }
        return id;
    }

    @Override
    public void update(inventariodetalles _inventariodetalles) {
        String sql;

        sql = "UPDATE inventario SET inventariodetalles = '" + _inventariodetalles.getIdinventario() + "',"
                +	" codproducto = '" + _inventariodetalles.getCodproducto() + "'"
                + " valor = '" + _inventariodetalles.getValor() + "' "
                + " unidad = '" + _inventariodetalles.getUnidad() + "' " +
                "WHERE id = " + _inventariodetalles.getId();
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
    public void delete(inventariodetalles _inventariodetalles) {
        String sql;

        sql = "DELETE FROM inventariodetalles WHERE id = " + _inventariodetalles.getId();
        try
        {
            db.execSQL(sql);
        }
        catch(SQLiteException e)
        {
            e.printStackTrace();
        }

    }
    public void deleteByIdinventario(long idinventario) {
        String sql;

        sql = "DELETE FROM inventariodetalles WHERE idinventario = " + idinventario;
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

        sql = "DELETE FROM inventariodetalles ";
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
    public inventariodetalles getByKey(String key) {
        return null;
    }

    public inventariodetalles getById(Long id) {

        String sql;
        inventariodetalles _inventariodetalles= new inventariodetalles();

        sql = "SELECT id,  idinventario,codproducto,valor,unidad FROM inventariodetalles WHERE id = " + id;
        try
        {
            Cursor c = db.rawQuery(sql,null);
            if(c.moveToFirst())
            {
                _inventariodetalles = new inventariodetalles();

                _inventariodetalles.setId(c.getLong(0));
                _inventariodetalles.setIdinventario(c.getLong(1));
                _inventariodetalles.setCodproducto(c.getString(2));
                _inventariodetalles.setValor(c.getLong(3));
                _inventariodetalles.setUnidad(c.getString(4));
                _inventariodetalles.setArticulo(daoArticulo.getByKey(c.getString(2)));


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

        return _inventariodetalles;
    }

    @Override
    public List<inventariodetalles> getAll(String where) {

        ArrayList<inventariodetalles> lista = new ArrayList<inventariodetalles>();

        String sql;
        inventariodetalles _inventariodetalles;

        sql = "SELECT id,  idinventario,codproducto,valor,unidad FROM inventariodetalles";
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
                    _inventariodetalles = new inventariodetalles();

                    _inventariodetalles.setId(c.getLong(0));
                    _inventariodetalles.setIdinventario(c.getLong(1));
                    _inventariodetalles.setCodproducto(c.getString(2));
                    _inventariodetalles.setValor(c.getLong(3));
                    _inventariodetalles.setUnidad(c.getString(4));
                    _inventariodetalles.setArticulo(daoArticulo.getByKey(c.getString(2)));
                    lista.add(_inventariodetalles);
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

        String sql = "SELECT count(id) as cant FROM inventariodetalles";
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