package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.Capania;


public class DaoCuenta implements DaoInterface<Capania> {
    private SQLiteDatabase db;
    private SQLiteStatement statement;
    private int cantRegistros = 50;

    public DaoCuenta(SQLiteDatabase db)
    {
        this.db=db;

        String sql;
        sql = "INSERT INTO CampaniaCuentas (idAccount,AccountNombre,IdCampania,"
                + "CampaniaNombre"
                + ") VALUES(?,?,?,?)";

        statement = db.compileStatement(sql);
    }

    @Override
    public long save(Capania capania) {

        statement.clearBindings();

        statement.bindString(1, capania.getIdAccount());
        statement.bindString(2, capania.getAccountNombre());
        statement.bindString(3, capania.getIdCampania());
        statement.bindString(4, capania.getCampaniaNombre());
        return statement.executeInsert();
    }
    @Override
    public void update(Capania capania) {

        String sql = "UPDATE CampaniaCuentas SET idAccount='" + capania.getIdAccount() + "',"
                + "AccountNombre='" + capania.getAccountNombre() + "',"
                + "IdCampania='" + capania.getIdCampania() + "',"
                + "CampaniaNombre = '" + capania.getCampaniaNombre() + "'"
                + " WHERE _ID  = '" + capania.getID() +  "'" ;

        db.execSQL(sql);

    }

    @Override
    public void delete(Capania capania) {
        String sql = "DELETE FROM CampaniaCuentas WHERE _ID = '" + capania.getID() +  "'" ;
        db.execSQL(sql);
    }

    @Override
    public Capania getByKey(String key) {

        Capania capania = null;
        Cursor c;


        c = db.rawQuery("SELECT idAccount,AccountNombre,IdCampania,CampaniaNombre FROM CLIENTES WHERE _ID  = '" + key + "'", null);



        if(c.moveToFirst())
        {
            capania = new Capania();


            capania.setIdAccount(c.getString(0));
            capania.setAccountNombre(c.getString(1));
            capania.setIdCampania(c.getString(2));
            capania.setCampaniaNombre(c.getString(3));


        }
        if(!c.isClosed())
        {
            c.close();
        }


        return capania;
    }

    @Override
    public List<Capania> getAll(String where) {

        ArrayList<Capania> lista = new ArrayList<>();
        Capania campania = null;

        String sql = "SELECT idAccount,AccountNombre,IdCampania,CampaniaNombre FROM CampaniaCuentas" ;

        if (!where.equals("")){
            sql = sql + " WHERE " + where;
        }

        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst())
        {
            do
            {
                campania= new Capania();
                campania.setIdAccount(c.getString(0));
                campania.setAccountNombre(c.getString(1));
                campania.setIdCampania(c.getString(2));
                campania.setCampaniaNombre(c.getString(3));


                lista.add(campania);
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
        String sql = "DELETE FROM CampaniaCuentas" ;
        db.execSQL(sql);
    }

}
