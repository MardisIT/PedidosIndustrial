package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.Capania;
import ar.com.syswork.sysmobile.entities.ConfiguracionDB;

public class DaoConfiguracion implements DaoInterface<ConfiguracionDB> {
private SQLiteDatabase db;
private SQLiteStatement statement;
private int cantRegistros = 50;

public DaoConfiguracion(SQLiteDatabase db)
        {
        this.db=db;

        String sql;
        sql = "INSERT INTO Configuracion (Id_cuenta,Id_campania,FormaBusqueda,Estado,FechaCarga,Formularios"
        + ") VALUES(?,?,?,?,?,?)";

        statement = db.compileStatement(sql);
        }

@Override
public long save(ConfiguracionDB configuracionDB) {

        statement.clearBindings();

        statement.bindString(1, configuracionDB.getId_cuenta());
        statement.bindString(2, configuracionDB.getId_campania());
        statement.bindString(3, configuracionDB.getFormaBusqueda());
        statement.bindString(4, configuracionDB.getEstado());
        statement.bindString(5, configuracionDB.getFechaCarga());
        statement.bindString(6, configuracionDB.getFormularios());
        return statement.executeInsert();
        }
@Override
public void update(ConfiguracionDB configuracionDB) {

        String sql = "UPDATE Configuracion SET Id_cuenta='" + configuracionDB.getId_cuenta() + "',"
        + "Id_campania='" + configuracionDB.getId_campania() + "',"
        + "FormaBusqueda='" + configuracionDB.getFormaBusqueda() + "',"
        + "Estado = '" + configuracionDB.getEstado() + "'"
                + "FechaCarga = '" + configuracionDB.getFechaCarga() + "'"
                + "Formularios = '" + configuracionDB.getFormularios() + "'"
        + " WHERE _ID  = '" + configuracionDB.getId_configuracion() +  "'" ;

        db.execSQL(sql);

        }

@Override
public void delete(ConfiguracionDB configuracionDB) {
        String sql = "DELETE FROM Configuracion WHERE _ID = '" + configuracionDB.getId_configuracion() +  "'" ;
        db.execSQL(sql);
        }

public ConfiguracionDB getbyconfigurar(){
        ConfiguracionDB configuracionDB = null;
        Cursor c;


        c = db.rawQuery("SELECT Configuracion.Id_cuenta,Configuracion.Id_campania,Configuracion.FormaBusqueda,Configuracion.Estado,Configuracion.FechaCarga" +
                ",Configuracion.Formularios ,CampaniaCuentas.AccountNombre,CampaniaCuentas.CampaniaNombre" +
                "FROM Configuracion INNER JOIN CampaniaCuentas ON Configuracion.Id_campania = CampaniaCuentas.IdCampania group by Id_campania", null);


        if(c.moveToFirst())
        {
                configuracionDB = new ConfiguracionDB();


                configuracionDB.setId_cuenta(c.getString(1));
                configuracionDB.setId_campania(c.getString(2));
                configuracionDB.setFormaBusqueda(c.getString(3));
                configuracionDB.setEstado(c.getString(4));
                configuracionDB.setFechaCarga(c.getString(5));
                configuracionDB.setFormularios(c.getString(6));
                configuracionDB.setAccountNombre(c.getString(7));
                configuracionDB.setCampaniaNombre(c.getString(8));


        }
        if(!c.isClosed())
        {
                c.close();
        }


        return configuracionDB;
}

@Override
public ConfiguracionDB getByKey(String key) {

        ConfiguracionDB configuracionDB = null;
        Cursor c;


        c = db.rawQuery("SELECT Id_cuenta,Id_campania,FormaBusqueda,Estado,FechaCarga,Formularios FROM Configuracion WHERE _ID  = '" + key + "'", null);



        if(c.moveToFirst())
        {
                configuracionDB = new ConfiguracionDB();


                configuracionDB.setId_cuenta(c.getString(1));
                configuracionDB.setId_campania(c.getString(2));
                configuracionDB.setFormaBusqueda(c.getString(3));
                configuracionDB.setEstado(c.getString(4));
                configuracionDB.setFechaCarga(c.getString(5));
                configuracionDB.setFormularios(c.getString(6));


        }
        if(!c.isClosed())
        {
        c.close();
        }


        return configuracionDB;
        }

@Override
public List<ConfiguracionDB> getAll(String where) {

        ArrayList<ConfiguracionDB> lista = new ArrayList<>();
        ConfiguracionDB configuracionDB = null;

        String sql = "SELECT Id_cuenta,Id_cuenta,Id_campania,FormaBusqueda,Estado,FechaCarga,Formularios FROM Configuracion" ;

        if (!where.equals("")){
        sql = sql + " WHERE " + where;
        }

        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst())
        {
        do
        {
                configuracionDB = new ConfiguracionDB();


                configuracionDB.setId_cuenta(c.getString(1));
                configuracionDB.setId_campania(c.getString(2));
                configuracionDB.setFormaBusqueda(c.getString(3));
                configuracionDB.setEstado(c.getString(4));
                configuracionDB.setFechaCarga(c.getString(5));
                configuracionDB.setFormularios(c.getString(6));


        lista.add(configuracionDB);
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
        public ConfiguracionDB CodigosActual() {
                return null;
        }


        public void deleteAll() {
                String sql = "DELETE FROM Configuracion" ;
                db.execSQL(sql);
        }

        }
