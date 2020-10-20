package ar.com.syswork.sysmobile.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ar.com.syswork.sysmobile.entities.Token;

public class DaoToken implements DaoInterface<Token>{


    private SQLiteDatabase db;
    private SQLiteStatement statement;

    public DaoToken(SQLiteDatabase db)
    {
        this.db=db;
        statement = db.compileStatement("INSERT INTO Token (token,fecha) VALUES(?,?)");
    }


    @Override
    public long save(Token token)
    {
        statement.clearBindings();
        statement.bindString(1, token.getToken());
        statement.bindString(2, token.getFecha());

        return statement.executeInsert();
    }

    @Override
    public void update(Token token) {
        db.execSQL("UPDATE Token SET token = '" + token.getToken() + "' WHERE _id = '" + token.getId() + "'");
    }

    @Override
    public void delete(Token token) {
        db.execSQL("DELETE FROM Token WHERE _id = '" + token.getId() + "'");
    }

    @Override
    public Token getByKey(String key) {

        Token token = null;
        Cursor c;

        c = db.rawQuery("SELECT _id,token,fecha FROM Token WHERE _id = '" + key + "'", null);

        if(c.moveToFirst())
        {
            token = new Token();
            token.setId(c.getInt(0));
            token.setToken(c.getString(1));
            token.setFecha(c.getString(2));
        }
        if(!c.isClosed())
        {
            c.close();
        }

        return token;
    }

    @Override
    public List<Token> getAll(String where) {

        ArrayList<Token> lista = new ArrayList<Token>();
        Token token = null;

        String sql = "SELECT _id,token,fecha FROM Token ";

        if (!where.equals("")){
            sql = sql + " WHERE " + where;
        }

        Cursor c = db.rawQuery(sql,null);

        if(c.moveToFirst())
        {
            do
            {
                token = new Token();
                token.setId(c.getInt(0));
                token.setToken(c.getString(1));
                token.setFecha(c.getString(2));
                lista.add(token);
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
    public Token CodigosActual() {
        return null;
    }


    public void deleteAll() {
        db.execSQL("DELETE FROM Token");
    }

}
