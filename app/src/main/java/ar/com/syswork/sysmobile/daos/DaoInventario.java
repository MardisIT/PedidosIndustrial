package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.Inventario;
import ar.com.syswork.sysmobile.entities.Pagos;

public class DaoInventario implements DaoInterface<Inventario>{
    private SQLiteDatabase db;
    private SQLiteStatement statement;

    DaoCliente daoCliente;
    DaoChequePagos daoChequePagos;
    DaoPagosDetalles daoPagosDetalles;

    public DaoInventario(SQLiteDatabase db)
    {
        this.db=db;
        statement = this.db.compileStatement("INSERT INTO inventario (" +
                "codcliente," +
                "codvendedor," +
                "fechainventario) VALUES(?,?,?)");
        daoCliente = new DaoCliente(db);
        daoChequePagos=new DaoChequePagos(db);
        daoPagosDetalles=new DaoPagosDetalles(db);
    }

    @Override
    public long save(Inventario inventario) {

        long id=0;

        try {
            statement.clearBindings();
            statement.bindString(1, inventario.getCodcliente());
            statement.bindString(2, inventario.getCodvendedor());
            statement.bindString(3, inventario.getFechainventario());


            id = statement.executeInsert();


        }
        catch(SQLException e)
        {
            id = -1;
        }
        return id;
    }

    @Override
    public void update(Inventario inventario) {
        String sql;

        sql = "UPDATE inventario SET codcliente = '" + inventario.getCodcliente() + "',"
                +	" codvendedor = '" + inventario.getCodvendedor() + "'"
                + " fechainventario = '" + inventario.getFechainventario() + "' " +
                "WHERE id = " + inventario.getId();
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
    public void delete(Inventario inventario) {
        String sql;

        sql = "DELETE FROM inventario WHERE id = " + inventario.getId();
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

        sql = "DELETE FROM inventario ";
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
    public Inventario getByKey(String key) {
        return null;
    }

    public Inventario getById(Long id) {

        String sql;
        Inventario inventario = null;

        sql = "SELECT id,  codcliente,codvendedor,fechainventario FROM inventario WHERE id = " + id;
        try
        {
            Cursor c = db.rawQuery(sql,null);
            if(c.moveToFirst())
            {
                inventario = new Inventario();

                inventario.setId(c.getLong(0));
                inventario.setCodcliente(c.getString(1));
                inventario.setCodvendedor(c.getString(2));
                inventario.setFechainventario(c.getString(3));
                inventario.setCliente(daoCliente.getByKey(c.getString(2)));


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

        return inventario;
    }

    @Override
    public List<Inventario> getAll(String where) {

        ArrayList<Inventario> lista = new ArrayList<Inventario>();

        String sql;
        Inventario inventario = null;

        sql = "SELECT id,  codcliente,codvendedor,fechainventario FROM inventario";
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
                    inventario = new Inventario();

                    inventario.setId(c.getLong(0));
                    inventario.setCodcliente(c.getString(1));
                    inventario.setCodvendedor(c.getString(2));
                    inventario.setFechainventario(c.getString(3));
                    inventario.setCliente(daoCliente.getByKey(c.getString(1)));
                    lista.add(inventario);
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

        String sql = "SELECT count(id) as cant FROM inventario";
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