package ar.com.syswork.sysmobile.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyOpenHelper extends SQLiteOpenHelper
{
	private String  dbFilePath;
	private String  dbFileName;
	private Context context;
	
	private static final int DB_VERSION = 14;
	
	public MyOpenHelper(Context context, String name) 
	{
		super(context, name, null, DB_VERSION);
	
		dbFilePath = context.getDatabasePath(name).getAbsolutePath();
		dbFilePath = dbFilePath.substring(0, dbFilePath.lastIndexOf("/")+1);
		dbFileName = name;
		this.context=context;
		
		checkDataBase(); // si no existe la db, la crea
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		Log.d("SW","entro a onCreate() de Database ");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		Log.d("SW","Hay una nueva version de la db");

	}
	
	 private void checkDataBase(){
		 
	    	SQLiteDatabase checkDB = null;
			 if(DB_VERSION==13){
				 try {
					 Log.d("SW","copiando de assets a databases");
					 copyDataBase();
				 } catch (IOException e2) {
					 Log.d("SW","excepcion del copy");
				 }
			 }
	    	try{
	    		String myPath = dbFilePath + dbFileName;
	    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);	 
	    	}catch(SQLiteException e){
	    		//database does't exist yet.
	    		try {			
	    			Log.d("SW","copiando de assets a databases");
	    			copyDataBase();
	    		} catch (IOException e2) {
	    			Log.d("SW","excepcion del copy");
	    		}
	    	}	 
	    	if(checkDB != null){
	 
	    		checkDB.close();	 
	    	}
	    }
	 
	private void copyDataBase() throws IOException
	{		 
		File f = new File(dbFilePath);
		f.mkdirs(); // creamos la carpeta "databases" si no existe
		
    	//Open your local db as the input stream
    	InputStream myInput = context.getAssets().open(dbFileName);
 
    	// Path to the just created empty db
    	String outFileName = dbFilePath + dbFileName;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0)
    	{
    		myOutput.write(buffer, 0, length);
    		Log.d("SW","escribiendo Base de Datos: "+length+" bytes");
    	} 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close(); 
    }	

}
