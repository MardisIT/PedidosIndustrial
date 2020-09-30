package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.Pagos;

public class DaoPagos implements DaoInterface<Pagos>{
    private SQLiteDatabase db;
    private SQLiteStatement statement;

    DaoCliente daoCliente;
    DaoChequePagos daoChequePagos;
    DaoPagosDetalles daoPagosDetalles;

    public DaoPagos(SQLiteDatabase db)
    {
        this.db=db;
        statement = this.db.compileStatement("INSERT INTO pagos (" +
                "CodCliente," +
                "ValorTotalPago," +
                "FormaPago," +
                "Fecha,foto,Idvendedor) VALUES(?,?,?,?,?,?)");
        daoCliente = new DaoCliente(db);
        daoChequePagos=new DaoChequePagos(db);
        daoPagosDetalles=new DaoPagosDetalles(db);
    }

    @Override
    public long save(Pagos pagos) {

        long id=0;

        try {
            statement.clearBindings();
            statement.bindString(1, pagos.getCodCliente());
            statement.bindDouble(2, pagos.getValorTotalPago());
            statement.bindString(3, pagos.getFormaPago());
            statement.bindString(4, pagos.getFecha());
            statement.bindString(5, pagos.getFotos());
            statement.bindString(6, pagos.getIdvendedor());
            id = statement.executeInsert();


        }
        catch(SQLException e)
        {
            id = -1;
        }
        return id;
    }

    @Override
    public void update(Pagos pagos) {
        String sql;

        sql = "UPDATE pagos SET CodCliente = '" + pagos.getCodCliente() + "',"
                +	" ValorTotalPago = '" + pagos.getValorTotalPago() + "'"
                + " FormaPago = '" + pagos.getFormaPago() + "',"
                + " Fecha = '" + pagos.getFecha() + "',"
                + " foto = " + pagos.getFotos()+" " +
                "WHERE Idpago = " + pagos.getIdpago();
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
    public void delete(Pagos pagos) {
        String sql;

        sql = "DELETE FROM pagos WHERE Idpago = " + pagos.getIdpago();
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

        sql = "DELETE FROM pagos ";
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
    public Pagos getByKey(String key) {
        return null;
    }

    public Pagos getById(Long id) {

        String sql;
        Pagos pagos = null;

        sql = "SELECT Idpago,CodCliente,ValorTotalPago,FormaPago,Fecha,foto,Idvendedor FROM pagos WHERE Idpago = " + id;
        try
        {
            Cursor c = db.rawQuery(sql,null);
            if(c.moveToFirst())
            {
                pagos = new Pagos();

                pagos.setIdpago(c.getLong(0));
                pagos.setCodCliente(c.getString(1));
                pagos.setValorTotalPago(c.getDouble(2));
                pagos.setFormaPago(c.getString(3));
                pagos.setFecha(c.getString(4));
                pagos.setFotos(c.getString(5));
                pagos.setIdvendedor(c.getString(6));
                pagos.setCliente(daoCliente.getByKey(c.getString(1)));
                pagos.setChequePagos(daoChequePagos.getAll("idpago="+c.getLong(0)));
                pagos.setPagosDetalles(daoPagosDetalles.getAll("idpago="+c.getLong(0)));

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

        return pagos;
    }

    @Override
    public List<Pagos> getAll(String where) {

        ArrayList<Pagos> lista = new ArrayList<Pagos>();

        String sql;
        Pagos pagos = null;

        sql = "SELECT Idpago,CodCliente,ValorTotalPago,FormaPago,Fecha,foto,Idvendedor FROM pagos";
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
                    pagos = new Pagos();

                    pagos.setIdpago(c.getLong(0));
                    pagos.setCodCliente(c.getString(1));
                    pagos.setValorTotalPago(c.getDouble(2));
                    pagos.setFormaPago(c.getString(3));
                    pagos.setFecha(c.getString(4));
                    pagos.setFotos(c.getString(5));
                    pagos.setIdvendedor(c.getString(6));
                    pagos.setCliente(daoCliente.getByKey(c.getString(1)));
                    pagos.setPagosDetalles(daoPagosDetalles.getAll("idpago="+c.getLong(0)));
                    lista.add(pagos);
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

        String sql = "SELECT count(Idpago) as cant FROM pagos";
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
