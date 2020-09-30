package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.DESCUENTO_AVENA;
import ar.com.syswork.sysmobile.entities.DESCUENTO_FORMAPAGO;

public class DaoDESCUENTO_FORMAPAGO implements DaoInterface<DESCUENTO_FORMAPAGO>  {
    private SQLiteDatabase db;
    private SQLiteStatement statement;
    private int cantRegistros = 50;

    public DaoDESCUENTO_FORMAPAGO(SQLiteDatabase db)
    {
        this.db=db;

        String sql;
        sql = "INSERT INTO DESCUENTO_FORMAPAGO (CodigoSKU,Porcentaje"
                + ") VALUES(?,?)";

        statement = db.compileStatement(sql);
    }

    @Override
    public long save(DESCUENTO_FORMAPAGO descuento_formapago) {

        statement.clearBindings();

        statement.bindString(1, descuento_formapago.getCodigoSKU());
        statement.bindDouble(2, descuento_formapago.getPorcentaje());
        return statement.executeInsert();
    }
    @Override
    public void update(DESCUENTO_FORMAPAGO descuento_formapago) {

        String sql = "UPDATE DESCUENTO_FORMAPAGO SET " +
                "CodigoSKU='" + descuento_formapago.getCodigoSKU() + "',"
                + "Porcentaje = '" + descuento_formapago.getPorcentaje() + "'"
                + " WHERE _ID  = '" + descuento_formapago.getId() +  "'" ;

        db.execSQL(sql);

    }

    @Override
    public void delete(DESCUENTO_FORMAPAGO descuento_formapago) {
        String sql = "DELETE FROM DESCUENTO_FORMAPAGO WHERE _ID = '" + descuento_formapago.getId() +  "'" ;
        db.execSQL(sql);
    }

    @Override
    public DESCUENTO_FORMAPAGO getByKey(String key) {

        DESCUENTO_FORMAPAGO descuento_formapago = null;
        Cursor c;


        c = db.rawQuery("SELECT CodigoSKU,Porcentaje FROM DESCUENTO_FORMAPAGO WHERE CodigoSKU  = '" + key + "'", null);



        if(c.moveToFirst())
        {
            descuento_formapago = new DESCUENTO_FORMAPAGO();


            descuento_formapago.setCodigoSKU(c.getString(0));

            descuento_formapago.setPorcentaje(c.getDouble(1));



        }
        if(!c.isClosed())
        {
            c.close();
        }


        return descuento_formapago;
    }

    @Override
    public List<DESCUENTO_FORMAPAGO> getAll(String where) {

        ArrayList<DESCUENTO_FORMAPAGO> lista = new ArrayList<>();
        DESCUENTO_FORMAPAGO descuento_formapago = null;

        String sql = "SELECT CodigoSKU,Porcentaje FROM DESCUENTO_FORMAPAGO" ;

        if (!where.equals("")){
            sql = sql + " WHERE " + where;
        }

        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst())
        {
            do
            {
                descuento_formapago = new DESCUENTO_FORMAPAGO();


                descuento_formapago.setCodigoSKU(c.getString(0));

                descuento_formapago.setPorcentaje(c.getDouble(1));


                lista.add(descuento_formapago);
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
        String sql = "DELETE FROM DESCUENTO_FORMAPAGO" ;
        db.execSQL(sql);
    }

}