package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.reportecabecera;

public class Daoreportecabecera implements DaoInterface<reportecabecera>  {
    private SQLiteDatabase db;
    private SQLiteStatement statement;
    private int cantRegistros = 50;

    public Daoreportecabecera(SQLiteDatabase db)
    {
        this.db=db;

        String sql;
        sql = "INSERT INTO reportecabecera (fecha,codcliente,idpedido) VALUES(?,?,?)";

        statement = db.compileStatement(sql);
    }

    @Override
    public long save(reportecabecera _reportecabecera) {
        long id=0;

        try {
        statement.clearBindings();

        statement.bindString(1, _reportecabecera.getFecha());
        statement.bindString(2, _reportecabecera.getCodcliente());
        statement.bindLong(3, _reportecabecera.getIdpedido());

            id = statement.executeInsert();


        }
        catch(SQLException e)
        {
            id = -1;
        }
        return id;
    }
    @Override
    public void update(reportecabecera _reportecabecera) {

        String sql = "UPDATE PRECIO_ESCALA SET " +
                "fecha='" + _reportecabecera.getFecha() + "',"
                +"codcliente='" + _reportecabecera.getCodcliente() + "',"
                + " WHERE _ID  = '" + _reportecabecera.get_id() +  "'" ;

        db.execSQL(sql);

    }

    @Override
    public void delete(reportecabecera _reportecabecera) {
        String sql = "DELETE FROM reportecabecera WHERE _ID = '" + _reportecabecera.get_id() +  "'" ;
        db.execSQL(sql);
    }

    @Override
    public reportecabecera getByKey(String key) {

        reportecabecera _reportecabecera = null;
        Cursor c;


        c = db.rawQuery("SELECT _id,fecha,codcliente,idpedido FROM reportecabecera WHERE _id  = '" + key + "'", null);



        if(c.moveToFirst())
        {
            _reportecabecera = new reportecabecera();
            _reportecabecera.set_id(c.getInt(0));
            _reportecabecera.setFecha(c.getString(1));
            _reportecabecera.setCodcliente(c.getString(2));
            _reportecabecera.setIdpedido(c.getInt(3));
        }
        if(!c.isClosed())
        {
            c.close();
        }


        return _reportecabecera;
    }

    @Override
    public List<reportecabecera> getAll(String where) {

        ArrayList<reportecabecera> lista = new ArrayList<>();
        reportecabecera _reportecabecera = null;

        String sql = "SELECT _id,fecha,codcliente,idpedido FROM reportecabecera " ;

        if (!where.equals("")){
            sql = sql + " WHERE " + where;
        }

        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst())
        {
            do
            {
                _reportecabecera = new reportecabecera();


                _reportecabecera.set_id(c.getInt(0));
                _reportecabecera.setFecha(c.getString(1));
                _reportecabecera.setCodcliente(c.getString(2));
                _reportecabecera.setIdpedido(c.getInt(3));

                lista.add(_reportecabecera);
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
        String sql = "DELETE FROM reportecabecera" ;
        db.execSQL(sql);
    }


}
