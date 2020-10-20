package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.CodigosNuevos;
import ar.com.syswork.sysmobile.entities.ConfiguracionDB;

public class DaoCodigosNuevos implements DaoInterface<CodigosNuevos> {
    private SQLiteDatabase db;
    private SQLiteStatement statement;
    private int cantRegistros = 50;

    public DaoCodigosNuevos(SQLiteDatabase db)
    {
        this.db=db;

        String sql;
        sql = "INSERT INTO CodigosNuevos (_ID,idAccount,code,estado,uri,imei_id,codeunico"
                + ") VALUES(?,?,?,?,?,?,?)";

        statement = db.compileStatement(sql);
    }

    @Override
    public long save(CodigosNuevos codigosNuevos) {

        statement.clearBindings();
        statement.bindLong(1, codigosNuevos.getID());
        statement.bindString(2, codigosNuevos.getIdAccount());
        statement.bindLong(3, codigosNuevos.getCode());
        statement.bindString(4, codigosNuevos.getEstado());
        statement.bindString(5, codigosNuevos.getUri());
        statement.bindString(6, codigosNuevos.getImei_id());
        statement.bindString(7, codigosNuevos.getCodeunico());
        return statement.executeInsert();
    }
    @Override
    public void update(CodigosNuevos codigosNuevos) {

        String sql = "UPDATE CodigosNuevos SET idAccount='" + codigosNuevos.getIdAccount() + "',"
                + "code='" + codigosNuevos.getCode() + "',"
                + "estado='" + codigosNuevos.getEstado() + "',"
                + "uri = '" + codigosNuevos.getUri() + "',"
                + "imei_id = '" + codigosNuevos.getImei_id() + "',"
                + "codeunico = '" + codigosNuevos.getCodeunico() + "'"
                + " WHERE _ID  = '" + codigosNuevos.getID() +  "'" ;

        db.execSQL(sql);

    }

    @Override
    public void delete(CodigosNuevos codigosNuevos) {
        String sql = "DELETE FROM CodigosNuevos WHERE _ID = '" + codigosNuevos.getID() +  "'" ;
        db.execSQL(sql);
    }



    @Override
    public CodigosNuevos getByKey(String key) {

        CodigosNuevos codigosNuevos = null;
        Cursor c;


        c = db.rawQuery("SELECT _ID,idAccount,code,estado,uri,imei_id,codeunico FROM CodigosNuevos WHERE _ID  = '" + key + "'", null);



        if(c.moveToFirst())
        {
            codigosNuevos = new CodigosNuevos();

            codigosNuevos.setID(c.getInt(0));
            codigosNuevos.setIdAccount(c.getString(1));
            codigosNuevos.setCode(c.getInt(2));
            codigosNuevos.setEstado(c.getString(3));
            codigosNuevos.setUri(c.getString(4));
            codigosNuevos.setImei_id(c.getString(5));
            codigosNuevos.setCodeunico(c.getString(6));


        }
        if(!c.isClosed())
        {
            c.close();
        }


        return codigosNuevos;
    }

    @Override
    public List<CodigosNuevos> getAll(String where) {

        ArrayList<CodigosNuevos> lista = new ArrayList<>();
        CodigosNuevos codigosNuevos = null;

        String sql = "SELECT _ID,idAccount,code,estado,uri,imei_id,codeunico FROM CodigosNuevos" ;

        if (!where.equals("")){
            sql = sql + " WHERE " + where;
        }

        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst())
        {
            do
            {
                codigosNuevos = new CodigosNuevos();

                codigosNuevos.setID(c.getInt(0));
                codigosNuevos.setIdAccount(c.getString(1));
                codigosNuevos.setCode(c.getInt(2));
                codigosNuevos.setEstado(c.getString(3));
                codigosNuevos.setUri(c.getString(4));
                codigosNuevos.setImei_id(c.getString(5));
                codigosNuevos.setCodeunico(c.getString(6));


                lista.add(codigosNuevos);
            }

            while(c.moveToNext());
        }

        if(!c.isClosed())
        {
            c.close();
        }

        return lista;
    }
    @Override
        public CodigosNuevos CodigosActual(){
            CodigosNuevos codigosNuevos = null;
            Cursor c,c1=null;


            c = db.rawQuery("SELECT max(code) FROM CodigosNuevos WHERE uri  = ''", null);
            if(c.moveToFirst()) {

                c1 = db.rawQuery("SELECT _ID,idAccount,code,estado,uri,imei_id,codeunico FROM CodigosNuevos WHERE code  = '" + c.getInt(0) + "'", null);
                if(c1.moveToFirst())
                {
                    codigosNuevos = new CodigosNuevos();
                    codigosNuevos.setID(c1.getInt(0));
                    codigosNuevos.setIdAccount(c1.getString(1));
                    codigosNuevos.setCode(c1.getInt(2));
                    codigosNuevos.setEstado(c1.getString(3));
                    codigosNuevos.setUri(c1.getString(4));
                    codigosNuevos.setImei_id(c1.getString(5));
                    codigosNuevos.setCodeunico(c1.getString(6));


                }
            }



            if(!c.isClosed())
            {
                c.close();
            }
            if(!c1.isClosed())
            {
                c1.close();
            }


            return codigosNuevos;
        }



    public void deleteAll() {
        String sql = "DELETE FROM CodigosNuevos" ;
        db.execSQL(sql);
    }

}

