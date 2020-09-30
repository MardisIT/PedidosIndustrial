package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.VisitasUio;

public class DaoVisitasUio implements DaoInterface<VisitasUio>{
private SQLiteDatabase db;
private SQLiteStatement statement;
    DaoCliente daoCliente;
    public DaoVisitasUio(SQLiteDatabase db)
    {
        this.db=db;
        statement = this.db.compileStatement("INSERT INTO visitasUio (" +
                "codcliente," +
                "codvendedor," +
                "fechavisita,latitud,longitud,linkfotoexterior) VALUES(?,?,?,?,?,?)");
        daoCliente = new DaoCliente(db);

    }

    @Override
    public long save(VisitasUio visitasUio) {

        long id=0;

        try {
            statement.clearBindings();
            statement.bindString(1, visitasUio.getCodcliente());
            statement.bindString(2, visitasUio.getCodvendedor());
            statement.bindString(3, visitasUio.getFechavisita());
            statement.bindDouble(4, visitasUio.getLatitud());
            statement.bindDouble(5, visitasUio.getLongitud());
            statement.bindString(6, visitasUio.getLinkfotoexterior());



            id = statement.executeInsert();


        }
        catch(SQLException e)
        {
            id = -1;
        }
        return id;
    }

    @Override
    public void update(VisitasUio visitasUio) {
        String sql;

        sql = "UPDATE visitasUio SET codcliente = '" + visitasUio.getCodcliente() + "',"
                +	" codvendedor = '" + visitasUio.getCodvendedor() + "'"
                + " fechavisita = '" + visitasUio.getFechavisita() + "' ,"
                + " latitud = '" + visitasUio.getLatitud() + "' ,"
                + " longitud = '" + visitasUio.getLongitud() + "' "
                + " linkfotoexterior = '" + visitasUio.getLinkfotoexterior() + "' " +
                "WHERE id = " + visitasUio.getId();
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
    public void delete(VisitasUio visitasUio) {
        String sql;

        sql = "DELETE FROM visitasUio WHERE id = " + visitasUio.getId();
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

        sql = "DELETE FROM visitasUio ";
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
    public VisitasUio getByKey(String key) {
        return null;
    }

    public VisitasUio getById(Long id) {

        String sql;
        VisitasUio visitasUio = null;

        sql = "SELECT id,  codcliente,codvendedor,fechavisita,latitud,longitud,linkfotoexterior FROM VisitasUio WHERE id = " + id;
        try
        {
            Cursor c = db.rawQuery(sql,null);
            if(c.moveToFirst())
            {
                visitasUio = new VisitasUio();

                visitasUio.setId(c.getLong(0));
                visitasUio.setCodcliente(c.getString(1));
                visitasUio.setCodvendedor(c.getString(2));
                visitasUio.setFechavisita(c.getString(3));
                visitasUio.setLatitud(c.getDouble(4));
                visitasUio.setLongitud(c.getDouble(5));
                visitasUio.setLinkfotoexterior(c.getString(6));
                visitasUio.setCliente(daoCliente.getByKey(c.getString(1)));


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

        return visitasUio;
    }

    @Override
    public List<VisitasUio> getAll(String where) {

        ArrayList<VisitasUio> lista = new ArrayList<VisitasUio>();

        String sql;
        VisitasUio visitasUio = null;

        sql = "SELECT id,  codcliente,codvendedor,fechavisita,latitud,longitud,linkfotoexterior FROM visitasUio";
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
                    visitasUio = new VisitasUio();

                    visitasUio.setId(c.getLong(0));
                    visitasUio.setCodcliente(c.getString(1));
                    visitasUio.setCodvendedor(c.getString(2));
                    visitasUio.setFechavisita(c.getString(3));
                    visitasUio.setLatitud(c.getDouble(4));
                    visitasUio.setLongitud(c.getDouble(5));
                    visitasUio.setLinkfotoexterior(c.getString(6));
                    visitasUio.setCliente(daoCliente.getByKey(c.getString(1)));
                    lista.add(visitasUio);
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

        String sql = "SELECT count(id) as cant FROM visitasUio";
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