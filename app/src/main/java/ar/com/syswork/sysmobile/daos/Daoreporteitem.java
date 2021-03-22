package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.reportecabecera;
import ar.com.syswork.sysmobile.entities.reporteitem;
import ar.com.syswork.sysmobile.reportepedidos.reportepedidosE;

public class Daoreporteitem implements DaoInterface<reporteitem>  {
    private SQLiteDatabase db;
    private SQLiteStatement statement;
    private int cantRegistros = 50;

    public Daoreporteitem(SQLiteDatabase db)
    {
        this.db=db;

        String sql;
        sql = "INSERT INTO reporteitem (codproducto,cantidad,codcabecera,unidades,precio,total) VALUES(?,?,?,?,?,?)";

        statement = db.compileStatement(sql);
    }

    @Override
    public long save(reporteitem _Reporteitem) {

        statement.clearBindings();

        statement.bindString(1, _Reporteitem.getCodproducto());
        statement.bindDouble(2, _Reporteitem.getCantidad());
        statement.bindLong(3, _Reporteitem.getCodcabecera());
        statement.bindString(4, _Reporteitem.getUnidad());
        statement.bindDouble(5, _Reporteitem.getPrecio());
        statement.bindDouble(6, _Reporteitem.getTotal());

        return statement.executeInsert();
    }
    @Override
    public void update(reporteitem _Reporteitem) {


    }

    @Override
    public void delete(reporteitem _Reporteitem) {

    }

    @Override
    public reporteitem getByKey(String key) {

        reporteitem _Reporteitem = null;
        Cursor c;


        c = db.rawQuery("SELECT _id,codproducto,cantidad,codcabecera,unidades,precio,total FROM reporteitem WHERE _id  = '" + key + "'", null);



        if(c.moveToFirst())
        {
            _Reporteitem = new reporteitem();
            _Reporteitem.set_id(c.getInt(0));
            _Reporteitem.setCodproducto(c.getString(1));
            _Reporteitem.setCantidad(c.getInt(2));
            _Reporteitem.setCodcabecera(c.getInt(3));
            _Reporteitem.setUnidad(c.getString(4));
            _Reporteitem.setPrecio(c.getDouble(5));
            _Reporteitem.setTotal(c.getDouble(6));

        }
        if(!c.isClosed())
        {
            c.close();
        }


        return _Reporteitem;
    }

    public List <reportepedidosE> reportepedidosES(){
        ArrayList<reportepedidosE> lista = new ArrayList<>();
        reportepedidosE _ReportepedidosE = null;

        String sql = "SELECT r.codproducto,a.descripcion, sum(r.cantidad),r.unidades,r.precio,sum(r.total) FROM reporteitem r  inner join articulos a on a.idArticulo=r.codproducto  INNER join reportecabecera c on c.idpedido=r.codcabecera group by r.codproducto " ;
        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst())
        {
            do
            {
                _ReportepedidosE = new reportepedidosE();


                _ReportepedidosE.setCodProductoD(c.getString(0));
                _ReportepedidosE.setDescripcionProd(c.getString(1));
                _ReportepedidosE.setCantidadpro(c.getDouble(2));
                _ReportepedidosE.setUnidades(c.getString(3));
                _ReportepedidosE.setPreciop(c.getDouble(4));
                _ReportepedidosE.setTotalp(c.getDouble(5));


                lista.add(_ReportepedidosE);
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
    public List<reporteitem> getAll(String where) {

        ArrayList<reporteitem> lista = new ArrayList<>();
        reporteitem _Reporteitem = null;

        String sql = "SELECT _id,codproducto,cantidad,codcabecera FROM reporteitem " ;

        if (!where.equals("")){
            sql = sql + " WHERE " + where;
        }

        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst())
        {
            do
            {
                _Reporteitem = new reporteitem();


                _Reporteitem.set_id(c.getInt(0));
                _Reporteitem.setCodproducto(c.getString(1));
                _Reporteitem.setCantidad(c.getInt(2));
                _Reporteitem.setCodcabecera(c.getInt(3));
                _Reporteitem.setUnidad(c.getString(4));
                _Reporteitem.setPrecio(c.getDouble(5));
                _Reporteitem.setTotal(c.getDouble(6));

                lista.add(_Reporteitem);
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
    public reporteitem CodigosActual() {
        return null;
    }


    public void deleteAll() {
        String sql = "DELETE FROM reporteitem" ;
        db.execSQL(sql);
    }
    public void deleteAllKey(String idcabecera) {
        String sql = "DELETE FROM reporteitem where codcabecera='"+idcabecera+"'" ;
        db.execSQL(sql);
    }


}

