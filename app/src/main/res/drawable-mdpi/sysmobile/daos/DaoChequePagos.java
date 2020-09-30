package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.ChequePagos;


public class DaoChequePagos implements DaoInterface<ChequePagos>{

    private SQLiteDatabase db;
    private SQLiteStatement statement;

    public DaoChequePagos(SQLiteDatabase db)
    {
        this.db=db;
        statement = this.db.compileStatement("INSERT INTO ChequePagos (idpago,numerocheque,banco,valor, fecha) VALUES(?,?,?,?,?)");
    }

    @Override
    public long save(ChequePagos chequePagos) {

        long id=0;

        try {
            statement.clearBindings();
            statement.bindLong(1,chequePagos.getIdpago());
            statement.bindString(2,chequePagos.getNumerocheque());
            statement.bindString(3, chequePagos.getBanco());
            statement.bindDouble(4, chequePagos.getValor());
            statement.bindString(5, chequePagos.getFecha());


            id = statement.executeInsert();
        }
        catch(SQLException e)
        {
            id = -1;
        }
        return id;
    }

    @Override
    public void update(ChequePagos chequePagos) {
        String sql;

        sql = "UPDATE ChequePagos SET idpago = " + chequePagos.getIdpago()  +
                ",Numerocheque = '" + chequePagos.getNumerocheque() + "'" +
                ",Banco = " + chequePagos.getBanco() +
                ",Valor = " + chequePagos.getValor() +
                ",Fecha = " + chequePagos.getFecha()+" WHERE Idcheque = " + chequePagos.getIdcheque();
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
    public void delete(ChequePagos chequePagos) {
        String sql;

        sql = "DELETE FROM ChequePagos WHERE Idcheque = " + chequePagos.getIdcheque();
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

        sql = "DELETE FROM ChequePagos WHERE idpago = " + idpago;
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

        sql = "DELETE FROM ChequePagos ";
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
    public ChequePagos getByKey(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    public ChequePagos getById(long Idcheque) {

        String sql;
        ChequePagos chequePagos = null;

        sql = "SELECT Idcheque,idpago,numerocheque,banco,valor,fecha FROM ChequePagos WHERE Idcheque = " + Idcheque;
        try
        {
            Cursor c = db.rawQuery(sql,null);
            if(c.moveToFirst())
            {
                chequePagos = new ChequePagos();
                chequePagos.setIdcheque(c.getLong(0));
                chequePagos.setIdpago(c.getLong(1));
                chequePagos.setNumerocheque(c.getString(2));
                chequePagos.setBanco(c.getString(3));
                chequePagos.setValor(c.getDouble(4));
                chequePagos.setFecha(c.getString(5));

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

        return chequePagos;
    }

    @Override
    public List<ChequePagos> getAll(String where) {
        ArrayList<ChequePagos> lista = new ArrayList<ChequePagos>();

        String sql;
        ChequePagos chequePagos = null;

        sql = "SELECT Idcheque,idpago,numerocheque,banco,valor,fecha FROM ChequePagos ";
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
                    chequePagos = new ChequePagos();
                    chequePagos.setIdcheque(c.getLong(0));
                    chequePagos.setIdpago(c.getLong(1));
                    chequePagos.setNumerocheque(c.getString(2));
                    chequePagos.setBanco(c.getString(3));
                    chequePagos.setValor(c.getDouble(4));
                    chequePagos.setFecha(c.getString(5));

                    lista.add(chequePagos);
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