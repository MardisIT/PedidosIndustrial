package ar.com.syswork.sysmobile.daos;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.DESCUENTO_AVENA;

public class DaoDESCUENTO_AVENA implements DaoInterface<DESCUENTO_AVENA>  {
    private SQLiteDatabase db;
    private SQLiteStatement statement;
    private int cantRegistros = 50;

    public DaoDESCUENTO_AVENA(SQLiteDatabase db)
    {
        this.db=db;

        String sql;
        sql = "INSERT INTO DESCUENTO_AVENA (CodigoSKU,SKU_NOMBRE,CANTIDAD,"
                + "DESCUENTO,"+"CONDICION"
                + ") VALUES(?,?,?,?,?)";

        statement = db.compileStatement(sql);
    }

    @Override
    public long save(DESCUENTO_AVENA descuento_avena) {

        statement.clearBindings();

        statement.bindString(1, descuento_avena.getCodigoSKU());
        statement.bindString(2, descuento_avena.getSKU_NOMBRE());
        statement.bindLong(3, descuento_avena.getCANTIDAD());
        statement.bindDouble(4, descuento_avena.getDESCUENTO());
        statement.bindString(5, descuento_avena.getCONDICION());
        return statement.executeInsert();
    }
    @Override
    public void update(DESCUENTO_AVENA descuento_avena) {

        String sql = "UPDATE DESCUENTO_AVENA SET " +
                "CodigoSKU='" + descuento_avena.getCodigoSKU() + "',"
                + "SKU_NOMBRE='" + descuento_avena.getSKU_NOMBRE() + "',"
                + "CANTIDAD='" + descuento_avena.getCANTIDAD() + "',"
                + "DESCUENTO='" + descuento_avena.getDESCUENTO() + "',"
                + "CONDICION = '" + descuento_avena.getCONDICION() + "'"
                + " WHERE _ID  = '" + descuento_avena.getID() +  "'" ;

        db.execSQL(sql);

    }

    @Override
    public void delete(DESCUENTO_AVENA descuento_avena) {
        String sql = "DELETE FROM DESCUENTO_AVENA WHERE _ID = '" + descuento_avena.getID() +  "'" ;
        db.execSQL(sql);
    }

    @Override
    public DESCUENTO_AVENA getByKey(String key) {

        DESCUENTO_AVENA descuento_avena = null;
        Cursor c;


        c = db.rawQuery("SELECT CodigoSKU,SKU_NOMBRE,CANTIDAD, DESCUENTO,CONDICION FROM DESCUENTO_AVENA WHERE CodigoSKU  = '" + key + "'", null);



        if(c.moveToFirst())
        {
            descuento_avena = new DESCUENTO_AVENA();


            descuento_avena.setCodigoSKU(c.getString(0));
            descuento_avena.setSKU_NOMBRE(c.getString(1));
            descuento_avena.setCANTIDAD(c.getInt(2));
            descuento_avena.setDESCUENTO(c.getDouble(3));
            descuento_avena.setCONDICION(c.getString(4));


        }
        if(!c.isClosed())
        {
            c.close();
        }


        return descuento_avena;
    }

    @Override
    public List<DESCUENTO_AVENA> getAll(String where) {

        ArrayList<DESCUENTO_AVENA> lista = new ArrayList<>();
        DESCUENTO_AVENA descuento_avena = null;

        String sql = "SELECT CodigoSKU,SKU_NOMBRE,CANTIDAD, DESCUENTO,CONDICION FROM DESCUENTO_AVENA" ;

        if (!where.equals("")){
            sql = sql + " WHERE " + where;
        }

        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst())
        {
            do
            {
                descuento_avena = new DESCUENTO_AVENA();


                descuento_avena.setCodigoSKU(c.getString(0));
                descuento_avena.setSKU_NOMBRE(c.getString(1));
                descuento_avena.setCANTIDAD(c.getInt(2));
                descuento_avena.setDESCUENTO(c.getDouble(3));
                descuento_avena.setCONDICION(c.getString(4));


                lista.add(descuento_avena);
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
        String sql = "DELETE FROM DESCUENTO_AVENA" ;
        db.execSQL(sql);
    }

}

