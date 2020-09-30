package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.PagosDetalles;
import ar.com.syswork.sysmobile.entities.PedidoItem;

public class DaoPagosDetalles implements DaoInterface<PagosDetalles>{

    private SQLiteDatabase db;
    private SQLiteStatement statement;

    public DaoPagosDetalles(SQLiteDatabase db)
    {
        this.db=db;
        statement = this.db.compileStatement(
                "INSERT INTO PagosDetalles (idpago,Numfactura,TotalFactura," +
                        "PagoRegistrado) VALUES(?,?,?,?)");
    }

    @Override
    public long save(PagosDetalles pagosDetalles) {

        long id=0;

        try {
            statement.clearBindings();
            statement.bindLong(1,pagosDetalles.getIdpago());
            statement.bindString(2,pagosDetalles.getNumfactura());
            statement.bindDouble(3, pagosDetalles.getTotalFactura());
            statement.bindDouble(4, pagosDetalles.getPagoRegistrado());


            id = statement.executeInsert();
        }
        catch(SQLException e)
        {
            id = -1;
        }
        return id;
    }

    @Override
    public void update(PagosDetalles pagosDetalles) {
        String sql;

        sql = "UPDATE PagosDetalles SET idpago = " + pagosDetalles.getIdpago()  +
                ",Numfactura = '" + pagosDetalles.getNumfactura() + "'" +
                ",TotalFactura = " + pagosDetalles.getTotalFactura() +
                ",PagoRegistrado = " + pagosDetalles.getPagoRegistrado() +
                " WHERE idpagodetalle = " + pagosDetalles.getIdpagodetalle();
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
    public void delete(PagosDetalles pagosDetalles) {
        String sql;

        sql = "DELETE FROM PagosDetalles WHERE idpagodetalle = " + pagosDetalles.getIdpagodetalle();
        try
        {
            db.execSQL(sql);
        }
        catch(SQLiteException e)
        {
            e.printStackTrace();
        }

    }
    public void deleteByIdPago(long idpago) {
        String sql;

        sql = "DELETE FROM PagosDetalles WHERE idpago = " + idpago;
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

        sql = "DELETE FROM PagosDetalles ";
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
    public PagosDetalles getByKey(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    public PagosDetalles getById(long idpagodetalle) {

        String sql;
        PagosDetalles pagosDetalles = null;

        sql = "SELECT idpagodetalle,idpago,Numfactura,TotalFactura,PagoRegistrado FROM PagosDetalles WHERE idpagodetalle = " + idpagodetalle;
        try
        {
            Cursor c = db.rawQuery(sql,null);
            if(c.moveToFirst())
            {
                pagosDetalles = new PagosDetalles();
                pagosDetalles.setIdpagodetalle(c.getLong(0));
                pagosDetalles.setIdpago(c.getLong(1));
                pagosDetalles.setNumfactura(c.getString(2));
                pagosDetalles.setTotalFactura(c.getDouble(3));
                pagosDetalles.setPagoRegistrado(c.getDouble(4));

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

        return pagosDetalles;
    }
    public PagosDetalles getByIdnumero(String numero) {

        String sql;
        PagosDetalles pagosDetalles = null;

        sql = "SELECT  idpagodetalle,idpago,Numfactura,TotalFactura,PagoRegistrado FROM PagosDetalles WHERE Numfactura = '" + numero+"' LIMIT 1";
        try
        {
            Cursor c = db.rawQuery(sql,null);
            if(c.moveToFirst())
            {
                pagosDetalles = new PagosDetalles();
                pagosDetalles.setIdpagodetalle(c.getLong(0));
                pagosDetalles.setIdpago(c.getLong(1));
                pagosDetalles.setNumfactura(c.getString(2));
                pagosDetalles.setTotalFactura(c.getDouble(3));
                pagosDetalles.setPagoRegistrado(c.getDouble(4));

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

        return pagosDetalles;
    }
    @Override
    public List<PagosDetalles> getAll(String where) {
        ArrayList<PagosDetalles> lista = new ArrayList<PagosDetalles>();

        String sql;
        PagosDetalles pagosDetalles = null;

        sql = "SELECT idpagodetalle,idpago,Numfactura,TotalFactura,PagoRegistrado FROM PagosDetalles ";
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
                    pagosDetalles = new PagosDetalles();
                    pagosDetalles.setIdpagodetalle(c.getLong(0));
                    pagosDetalles.setIdpago(c.getLong(1));
                    pagosDetalles.setNumfactura(c.getString(2));
                    pagosDetalles.setTotalFactura(c.getDouble(3));
                    pagosDetalles.setPagoRegistrado(c.getDouble(4));

                    lista.add(pagosDetalles);
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
