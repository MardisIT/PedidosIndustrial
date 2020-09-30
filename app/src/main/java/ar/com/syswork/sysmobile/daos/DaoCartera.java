package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.Cartera;
import ar.com.syswork.sysmobile.entities.PRECIO_ESCALA;

public class DaoCartera implements DaoInterface<Cartera>{
    private SQLiteDatabase db;
    private SQLiteStatement statement;
    private int cantRegistros = 50;
    public DaoCartera(SQLiteDatabase db)
    {
        this.db=db;

        String sql;
        sql = "INSERT INTO Cartera (_ID,Codcli," +
                "nombre_cliente," +
                "fe_fac," +
                "fe_des," +
                "tp_d," +
                "line," +
                "nro_docm," +
                "ti_desp," +
                "fe_vecto," +
                "corriente," +
                "vcdo1_15," +
                "vcdo16_30," +
                "vcdo31_60," +
                "vcdo61mayor," +
                "vendedor," +
                "codvendedor"
                + ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        statement = db.compileStatement(sql);
    }
    @Override
    public long save(Cartera cartera) {

        statement.clearBindings();

        statement.bindLong(1, Long.valueOf(cartera.getID()));
        statement.bindString(2, cartera.getCodcli());
        statement.bindString(3, cartera.getNombre_cliente());
        statement.bindString(4, cartera.getFe_fac());
        statement.bindString(5, cartera.getFe_des());

        statement.bindString(6, cartera.getTp_d());
        statement.bindString(7, cartera.getLine());
        statement.bindString(8, cartera.getNro_docm());
        statement.bindString(9, cartera.getTi_desp());
        statement.bindString(10, cartera.getFe_vecto());

        statement.bindDouble(11, cartera.getCorriente());
        statement.bindDouble(12, cartera.getVcdo1_15());
        statement.bindDouble(13, cartera.getVcdo16_30());
        statement.bindDouble(14, cartera.getVcdo31_60());
        statement.bindDouble(15, cartera.getVcdo61mayor());

        statement.bindString(16, cartera.getVendedor());
        statement.bindString(17, cartera.getCodvendedor());



        return statement.executeInsert();
    }
    @Override
    public void update(Cartera cartera) {

        String sql = "UPDATE cartera SET " +
                "" +
                "Codcli='" + cartera.getCodcli() + "',"
                +"nombre_cliente='" + cartera.getNombre_cliente() + "',"
                +"fe_fac='" + cartera.getFe_fac() + "',"
                +"fe_des='" + cartera.getFe_des() + "',"
                +"tp_d='" + cartera.getTp_d() + "',"
                +"line='" + cartera.getLine() + "',"
                +"nro_docm='" + cartera.getNro_docm() + "',"
                +"ti_desp='" + cartera.getTi_desp() + "',"
                +"fe_vecto='" + cartera.getFe_vecto() + "',"
                +"corriente='" + cartera.getCorriente() + "',"
                +"vcdo1_15='" + cartera.getVcdo1_15() + "',"
                +"vcdo16_30='" + cartera.getVcdo16_30()+ "',"
                +"vcdo31_60='" + cartera.getVcdo31_60() + "',"
                +"vcdo61mayor='" + cartera.getVcdo61mayor() + "',"
                +"vendedor='" +cartera.getVendedor() + "',"
                + "codvendedor = '" + cartera.getCodvendedor()+ "'"
                + " WHERE id  = '" + cartera.getID() +  "'" ;

        db.execSQL(sql);

    }

    @Override
    public void delete(Cartera cartera) {
        String sql = "DELETE FROM cartera WHERE _ID = '" + cartera.getID() +  "'" ;
        db.execSQL(sql);
    }

    @Override
    public Cartera getByKey(String key) {

        Cartera cartera = null;
        Cursor c;


        c = db.rawQuery("SELECT _ID, Codcli,nombre_cliente,fe_fac,fe_des,tp_d,line,nro_docm,ti_desp,fe_vecto,corriente,vcdo1_15,vcdo16_30,vcdo31_60,vcdo61mayor,vendedor,codvendedor FROM cartera WHERE id  = '" + key + "'", null);



        if(c.moveToFirst())
        {
            cartera = new Cartera();

            cartera.setID(c.getInt(0));
            cartera.setCodcli(c.getString(1));
            cartera.setNombre_cliente(c.getString(2));
            cartera.setFe_fac(c.getString(3));
            cartera.setFe_des(c.getString(4));
            cartera.setTp_d(c.getString(5));
            cartera.setLine(c.getString(6));
            cartera.setNro_docm(c.getString(7));
            cartera.setTi_desp(c.getString(8));
            cartera.setFe_vecto(c.getString(9));
            cartera.setCorriente(c.getDouble(10));
            cartera.setVcdo1_15(c.getDouble(11));
            cartera.setVcdo16_30(c.getDouble(12));
            cartera.setVcdo31_60(c.getDouble(13));
            cartera.setVcdo61mayor(c.getDouble(14));
            cartera.setVendedor(c.getString(15));
            cartera.setCodvendedor(c.getString(16));




        }
        if(!c.isClosed())
        {
            c.close();
        }


        return cartera;
    }

    @Override
    public List<Cartera> getAll(String where) {

        ArrayList<Cartera> lista = new ArrayList<>();
        Cartera cartera = null;

        String sql = "SELECT _id, Codcli,nombre_cliente,fe_fac,fe_des,tp_d,line,nro_docm,ti_desp,fe_vecto,corriente,vcdo1_15,vcdo16_30,vcdo31_60,vcdo61mayor,vendedor,codvendedor FROM cartera" ;

        if (!where.equals("")){
            sql = sql + " WHERE " + where;
        }

        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst())
        {
            do
            {
                cartera = new Cartera();

                cartera.setID(c.getInt(0));
                cartera.setCodcli(c.getString(1));
                cartera.setNombre_cliente(c.getString(2));
                cartera.setFe_fac(c.getString(3));
                cartera.setFe_des(c.getString(4));
                cartera.setTp_d(c.getString(5));
                cartera.setLine(c.getString(6));
                cartera.setNro_docm(c.getString(7));
                cartera.setTi_desp(c.getString(8));
                cartera.setFe_vecto(c.getString(9));
                cartera.setCorriente(c.getDouble(10));
                cartera.setVcdo1_15(c.getDouble(11));
                cartera.setVcdo16_30(c.getDouble(12));
                cartera.setVcdo31_60(c.getDouble(13));
                cartera.setVcdo61mayor(c.getDouble(14));
                cartera.setVendedor(c.getString(15));
                cartera.setCodvendedor(c.getString(16));

                lista.add(cartera);
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
        String sql = "DELETE FROM cartera" ;
        db.execSQL(sql);
    }
}
