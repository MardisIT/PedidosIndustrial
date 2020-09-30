package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.DESCUENTO_VOLUMEN;
import ar.com.syswork.sysmobile.entities.PRECIO_ESCALA;

public class DaoPRECIO_ESCALA implements DaoInterface<PRECIO_ESCALA>  {
    private SQLiteDatabase db;
    private SQLiteStatement statement;
    private int cantRegistros = 50;

    public DaoPRECIO_ESCALA(SQLiteDatabase db)
    {
        this.db=db;

        String sql;
        sql = "INSERT INTO PRECIO_ESCALA (CodigosSKU,SKU,UNIDADESXCAJA,UNIDADES,PRECIO_UNITARIO, UNIDADES_VALIDAR ,PRECIO2 "
                + ") VALUES(?,?,?,?,?,?,?)";

        statement = db.compileStatement(sql);
    }

    @Override
    public long save(PRECIO_ESCALA precio_escala) {

        statement.clearBindings();

        statement.bindString(1, precio_escala.getCodigosSKU());
        statement.bindString(2, precio_escala.getSKU());
        statement.bindLong(3, precio_escala.getUNIDADESXCAJA());
        statement.bindString(4, precio_escala.getUNIDADES());
        statement.bindDouble(5, precio_escala.getPRECIO_UNITARIO());
        statement.bindLong(6, precio_escala.getUNIDADES_VALIDAR());
        statement.bindDouble(7, precio_escala.getPRECIO2());
        return statement.executeInsert();
    }
    @Override
    public void update(PRECIO_ESCALA precio_escala) {

        String sql = "UPDATE PRECIO_ESCALA SET " +
                "CodigosSKU='" + precio_escala.getCodigosSKU() + "',"
                +"SKU='" + precio_escala.getSKU() + "',"
                +"UNIDADESXCAJA='" + precio_escala.getUNIDADESXCAJA() + "',"
                +"UNIDADES='" + precio_escala.getUNIDADES() + "',"
                +"PRECIO_UNITARIO='" + precio_escala.getPRECIO_UNITARIO() + "',"
                +"UNIDADES_VALIDAR='" + precio_escala.getUNIDADES_VALIDAR() + "',"
                + "PRECIO2 = '" + precio_escala.getPRECIO2() + "'"
                + " WHERE ID  = '" + precio_escala.getId() +  "'" ;

        db.execSQL(sql);

    }

    @Override
    public void delete(PRECIO_ESCALA precio_escala) {
        String sql = "DELETE FROM PRECIO_ESCALA WHERE _ID = '" + precio_escala.getId() +  "'" ;
        db.execSQL(sql);
    }

    @Override
    public PRECIO_ESCALA getByKey(String key) {

        PRECIO_ESCALA precio_escala = null;
        Cursor c;


        c = db.rawQuery("SELECT CodigosSKU,SKU,UNIDADESXCAJA,UNIDADES,PRECIO_UNITARIO,UNIDADES_VALIDAR,PRECIO2 FROM PRECIO_ESCALA WHERE CodigosSKU  = '" + key + "'", null);



        if(c.moveToFirst())
        {
            precio_escala = new PRECIO_ESCALA();


            precio_escala.setCodigosSKU(c.getString(0));
            precio_escala.setSKU(c.getString(1));
            precio_escala.setUNIDADESXCAJA(c.getInt(2));
            precio_escala.setUNIDADES(c.getString(3));
            precio_escala.setPRECIO_UNITARIO(c.getDouble(4));
            precio_escala.setUNIDADES_VALIDAR(c.getInt(5));
            precio_escala.setPRECIO2(c.getDouble(6));



        }
        if(!c.isClosed())
        {
            c.close();
        }


        return precio_escala;
    }

    @Override
    public List<PRECIO_ESCALA> getAll(String where) {

        ArrayList<PRECIO_ESCALA> lista = new ArrayList<>();
        PRECIO_ESCALA precio_escala = null;

        String sql = "SELECT CodigosSKU,SKU,UNIDADESXCAJA,UNIDADES,PRECIO_UNITARIO,UNIDADES_VALIDAR,PRECIO2 FROM PRECIO_ESCALA " ;

        if (!where.equals("")){
            sql = sql + " WHERE " + where;
        }

        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst())
        {
            do
            {
                precio_escala = new PRECIO_ESCALA();


                precio_escala.setCodigosSKU(c.getString(0));
                precio_escala.setSKU(c.getString(1));
                precio_escala.setUNIDADESXCAJA(c.getInt(2));
                precio_escala.setUNIDADES(c.getString(3));
                precio_escala.setPRECIO_UNITARIO(c.getDouble(4));
                precio_escala.setUNIDADES_VALIDAR(c.getInt(5));
                precio_escala.setPRECIO2(c.getDouble(6));

                lista.add(precio_escala);
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
        String sql = "DELETE FROM PRECIO_ESCALA" ;
        db.execSQL(sql);
    }


}
