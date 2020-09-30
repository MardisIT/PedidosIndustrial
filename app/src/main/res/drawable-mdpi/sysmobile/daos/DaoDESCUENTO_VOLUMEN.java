package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.DESCUENTO_VOLUMEN;

public class DaoDESCUENTO_VOLUMEN implements DaoInterface<DESCUENTO_VOLUMEN>  {
    private SQLiteDatabase db;
    private SQLiteStatement statement;
    private int cantRegistros = 50;

    public DaoDESCUENTO_VOLUMEN(SQLiteDatabase db)
    {
        this.db=db;

        String sql;
        sql = "INSERT INTO DESCUENTO_VOLUMEN (CodigosSKU,SKU_NOMBRE,UNIDADES_DESCUENTO,UNIDADES_HASTA,DESCUENTO"
                + ") VALUES(?,?,?,?,?)";

        statement = db.compileStatement(sql);
    }

    @Override
    public long save(DESCUENTO_VOLUMEN descuento_volumen) {

        statement.clearBindings();

        statement.bindString(1, descuento_volumen.getCodigosSKU());
        statement.bindString(2, descuento_volumen.getSKU_NOMBRE());
        statement.bindLong(3, descuento_volumen.getUNIDADES_DESCUENTO());
        statement.bindString(4, descuento_volumen.getUNIDADES_HASTA());
        statement.bindDouble(5, descuento_volumen.getDESCUENTO());
        return statement.executeInsert();
    }
    @Override
    public void update(DESCUENTO_VOLUMEN descuento_volumen) {

        String sql = "UPDATE DESCUENTO_VOLUMEN SET " +
                "CodigosSKU='" + descuento_volumen.getCodigosSKU() + "',"
                +"SKU_NOMBRE='" + descuento_volumen.getSKU_NOMBRE() + "',"
                +"UNIDADES_DESCUENTO='" + descuento_volumen.getUNIDADES_DESCUENTO() + "',"
                +"UNIDADES_HASTA='" + descuento_volumen.getUNIDADES_HASTA() + "',"
                + "DESCUENTO = '" + descuento_volumen.getDESCUENTO() + "'"
                + " WHERE ID  = '" + descuento_volumen.getID() +  "'" ;

        db.execSQL(sql);

    }

    @Override
    public void delete(DESCUENTO_VOLUMEN descuento_volumen) {
        String sql = "DELETE FROM DESCUENTO_VOLUMEN WHERE _ID = '" + descuento_volumen.getID() +  "'" ;
        db.execSQL(sql);
    }

    @Override
    public DESCUENTO_VOLUMEN getByKey(String key) {

        DESCUENTO_VOLUMEN descuento_volumen = null;
        Cursor c;


        c = db.rawQuery("SELECT CodigosSKU,SKU_NOMBRE,UNIDADES_DESCUENTO,UNIDADES_HASTA,DESCUENTO FROM DESCUENTO_VOLUMEN WHERE CodigosSKU  = '" + key + "'", null);



        if(c.moveToFirst())
        {
            descuento_volumen = new DESCUENTO_VOLUMEN();


            descuento_volumen.setCodigosSKU(c.getString(0));
            descuento_volumen.setSKU_NOMBRE(c.getString(1));
            descuento_volumen.setUNIDADES_DESCUENTO(c.getInt(2));
            descuento_volumen.setUNIDADES_HASTA(c.getString(3));
            descuento_volumen.setDESCUENTO(c.getDouble(4));



        }
        if(!c.isClosed())
        {
            c.close();
        }


        return descuento_volumen;
    }

    @Override
    public List<DESCUENTO_VOLUMEN> getAll(String where) {

        ArrayList<DESCUENTO_VOLUMEN> lista = new ArrayList<>();
        DESCUENTO_VOLUMEN descuento_volumen = null;

        String sql = "SELECT CodigosSKU,SKU_NOMBRE,UNIDADES_DESCUENTO,UNIDADES_HASTA,DESCUENTO FROM DESCUENTO_VOLUMEN" ;

        if (!where.equals("")){
            sql = sql + " WHERE " + where;
        }

        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst())
        {
            do
            {
                descuento_volumen = new DESCUENTO_VOLUMEN();


                descuento_volumen.setCodigosSKU(c.getString(0));
                descuento_volumen.setSKU_NOMBRE(c.getString(1));
                descuento_volumen.setUNIDADES_DESCUENTO(c.getInt(2));
                descuento_volumen.setUNIDADES_HASTA(c.getString(3));
                descuento_volumen.setDESCUENTO(c.getDouble(4));


                lista.add(descuento_volumen);
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
        String sql = "DELETE FROM DESCUENTO_VOLUMEN" ;
        db.execSQL(sql);
    }

}