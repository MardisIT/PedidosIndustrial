package ar.com.syswork.sysmobile.daos;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import ar.com.syswork.sysmobile.entities.Cliente;

public class DaoCliente implements DaoInterface<Cliente>{
	
	private SQLiteDatabase db;
	private SQLiteStatement statement;
	private int cantRegistros = 50;
	
	public DaoCliente(SQLiteDatabase db)
	{
		this.db=db;		
		
		String sql;
		sql = "INSERT INTO Clientes (Codigo,CodigoOpcional,RazonSocial,"
				+ "calleNroPisoDpto,Localidad,Cuit,"
				+ "Iva,ClaseDePrecio,PorcDto,CpteDefault,idVendedor,telefono,email,LatitudeBranch,LenghtBranch,propietario,estadoenvio" +
				",Referencia,Nombres,Apellidos,Cedula,Celular,Provincia,Canton,Parroquia,IMEI,SaldoActual,DiasPlazo,ZonaPeligrosa,ActualizaGeo,IDCLIENTE"
				+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		statement = db.compileStatement(sql);
	}

	@Override
	public long save(Cliente cliente) {
		
		statement.clearBindings();
		
		statement.bindString(1, cliente.getCodigo());
		statement.bindString(2, cliente.getCodigoOpcional());
		statement.bindString(3, cliente.getRazonSocial());
		statement.bindString(4, cliente.getCalleNroPisoDpto());
		statement.bindString(5, cliente.getLocalidad());
		statement.bindString(6, cliente.getCuit());
		statement.bindDouble(7, (double) cliente.getIva());
		statement.bindDouble(8, (double) cliente.getClaseDePrecio());
		statement.bindDouble(9, cliente.getPorcDto());
		statement.bindString(10, cliente.getCpteDefault());
		statement.bindString(11, cliente.getIdVendedor());
		statement.bindString(12, cliente.getTelefono());
		statement.bindString(13, cliente.getMail());
		statement.bindString(14, cliente.getLatitudeBranch());
		statement.bindString(15, cliente.getLenghtBranch());
		statement.bindString(16, cliente.getPropietario());
		statement.bindString(17, cliente.getEstadoenvio());

		statement.bindString(18, cliente.getReference());
		statement.bindString(19, cliente.getNombre());
		statement.bindString(20, cliente.getApellido());
		statement.bindString(21, cliente.getCedula());
		statement.bindString(22, cliente.getCelular());
		statement.bindString(23, cliente.getProvincia());
		statement.bindString(24, cliente.getCanton());
		statement.bindString(25, cliente.getParroquia());
		statement.bindString(26, cliente.getImeI_ID());
		statement.bindString(27, cliente.getSaldoActual());
		statement.bindString(28, cliente.getDiasPlazo());
		statement.bindString(29, cliente.getZonaPeligrosa());
		statement.bindLong(30, cliente.getActualizaGeo());
		statement.bindLong(31, cliente.getIDCLIENTE());
		return statement.executeInsert();
	}

	@Override
	public void update(Cliente cliente) {
		
		String sql = "UPDATE Clientes SET CodigoOpcional='" + cliente.getCodigoOpcional() + "',"
				+ "calleNroPisoDpto='" + cliente.getCalleNroPisoDpto() + "',"
				+ "Localidad = '" + cliente.getLocalidad() + "',"
				+ "Cuit= '" + cliente.getCuit() + "',"
				+ "Iva = " + cliente.getIva() + ","
				+ "ClaseDePrecio = " + cliente.getClaseDePrecio() + ","
				+ "PorcDto = " + cliente.getPorcDto() + ","
				+ "CpteDefault ='" + cliente.getCpteDefault() + "'," 
				+ "idVendedor ='" + cliente.getIdVendedor() + "'," 
				+ "telefono ='" + cliente.getTelefono() + "',"
				+ "propietario ='" + cliente.getPropietario() + "',"
				+ "LatitudeBranch ='" + cliente.getLatitudeBranch() + "',"
				+ "LenghtBranch ='" + cliente.getLenghtBranch() + "',"
				+ "estadoenvio ='" + cliente.getEstadoenvio() + "',"
				+ "ActualizaGeo =" + cliente.getActualizaGeo() + ","
				+ "email ='" + cliente.getMail() + "'"
				+ " WHERE Codigo = '" + cliente.getCodigo() +  "'" ;
		
		db.execSQL(sql);
		
	}

	@Override
	public void delete(Cliente cliente) {
		String sql = "DELETE FROM Clientes WHERE Codigo = '" + cliente.getCodigo() +  "'" ;
		db.execSQL(sql);
	}

	@Override
	public Cliente getByKey(String key) {
		
		Cliente cliente = null;		
		Cursor c;
		
				
		c = db.rawQuery("SELECT Codigo,CodigoOpcional,razonSocial,"
				+ "calleNroPisoDpto,Localidad,Cuit,"
				+ "Iva,ClaseDePrecio,PorcDto,CpteDefault,idVendedor,telefono,email ,LatitudeBranch, LenghtBranch,propietario ,estadoenvio,Referencia,Nombres,Apellidos,Cedula,Celular,Provincia,Canton,Parroquia,IMEI,SaldoActual,DiasPlazo,ZonaPeligrosa,ActualizaGeo,IDCLIENTE  FROM CLIENTES WHERE CodigoOpcional = '" + key + "'", null);
		
		
		
   		if(c.moveToFirst())
		{
			cliente = new Cliente();
			
			
			cliente.setCodigo(c.getString(0));
			cliente.setCodigoOpcional(c.getString(1));
			cliente.setRazonSocial(c.getString(2));
			cliente.setCalleNroPisoDpto(c.getString(3));
			cliente.setLocalidad(c.getString(4));
			cliente.setCuit(c.getString(5));
			cliente.setIva((byte) c.getDouble(6));
			cliente.setClaseDePrecio((byte) c.getDouble(7));
			cliente.setPorcDto(c.getDouble(8));
			cliente.setCpteDefault(c.getString(9));
			cliente.setIdVendedor(c.getString(10));
			cliente.setTelefono(c.getString(11));
			cliente.setMail(c.getString(12));
			cliente.setLatitudeBranch(c.getString(13));
			cliente.setLenghtBranch(c.getString(14));
			cliente.setPropietario(c.getString(15));
			cliente.setEstadoenvio(c.getString(16));

			cliente.setReference(c.getString(17));
			cliente.setNombre(c.getString(18));
			cliente.setApellido(c.getString(19));
			cliente.setCedula(c.getString(20));
			cliente.setCelular(c.getString(21));
			cliente.setProvincia(c.getString(22));
			cliente.setCanton(c.getString(23));
			cliente.setParroquia(c.getString(24));
			cliente.setImeI_ID(c.getString(25));
			cliente.setSaldoActual(c.getString(26));
			cliente.setDiasPlazo(c.getString(27));
			cliente.setZonaPeligrosa(c.getString(28));
			cliente.setActualizaGeo(c.getInt(29));
			cliente.setIDCLIENTE(c.getInt(30));


		}
		if(!c.isClosed())
		{
			c.close();
		}
		if(cliente==null)
		{
			c = db.rawQuery("SELECT Codigo,CodigoOpcional,razonSocial,"
					+ "calleNroPisoDpto,Localidad,Cuit,"
					+ "Iva,ClaseDePrecio,PorcDto,CpteDefault,idVendedor,telefono,email ,LatitudeBranch, LenghtBranch,propietario ,estadoenvio,Referencia,Nombres,Apellidos,Cedula,Celular,Provincia,Canton,Parroquia,IMEI,SaldoActual,DiasPlazo,ZonaPeligrosa,ActualizaGeo,IDCLIENTE  FROM CLIENTES WHERE Codigo = '" + key + "'", null);



			if(c.moveToFirst())
			{
				cliente = new Cliente();


				cliente.setCodigo(c.getString(0));
				cliente.setCodigoOpcional(c.getString(1));
				cliente.setRazonSocial(c.getString(2));
				cliente.setCalleNroPisoDpto(c.getString(3));
				cliente.setLocalidad(c.getString(4));
				cliente.setCuit(c.getString(5));
				cliente.setIva((byte) c.getDouble(6));
				cliente.setClaseDePrecio((byte) c.getDouble(7));
				cliente.setPorcDto(c.getDouble(8));
				cliente.setCpteDefault(c.getString(9));
				cliente.setIdVendedor(c.getString(10));
				cliente.setTelefono(c.getString(11));
				cliente.setMail(c.getString(12));
				cliente.setLatitudeBranch(c.getString(13));
				cliente.setLenghtBranch(c.getString(14));
				cliente.setPropietario(c.getString(15));
				cliente.setEstadoenvio(c.getString(16));

				cliente.setReference(c.getString(17));
				cliente.setNombre(c.getString(18));
				cliente.setApellido(c.getString(19));
				cliente.setCedula(c.getString(20));
				cliente.setCelular(c.getString(21));
				cliente.setProvincia(c.getString(22));
				cliente.setCanton(c.getString(23));
				cliente.setParroquia(c.getString(24));
				cliente.setImeI_ID(c.getString(25));
				cliente.setSaldoActual(c.getString(26));
				cliente.setDiasPlazo(c.getString(27));
				cliente.setZonaPeligrosa(c.getString(28));
				cliente.setActualizaGeo(c.getInt(29));
				cliente.setIDCLIENTE(c.getInt(30));

			}
			if(!c.isClosed())
			{
				c.close();
			}
		}

		return cliente;
	}

	@Override
	public List<Cliente> getAll(String where) {
		
		ArrayList<Cliente> lista = new ArrayList<Cliente>();
		Cliente cliente = null;

		String sql = "SELECT Codigo,CodigoOpcional,RazonSocial,"
				+ "calleNroPisoDpto,Localidad,Cuit,"
				+ "Iva,ClaseDePrecio,PorcDto,CpteDefault,idVendedor,telefono,email,LatitudeBranch, LenghtBranch,propietario,estadoenvio,Referencia,Nombres,Apellidos,Cedula,Celular,Provincia,Canton,Parroquia,IMEI,SaldoActual,DiasPlazo,ZonaPeligrosa,ActualizaGeo,IDCLIENTE FROM CLIENTES" ;
		
		if (!where.equals("")){
			sql = sql + " WHERE " + where;
		}	
		
		Cursor c = db.rawQuery(sql,null);
		if(c.moveToFirst())
		{
			do
			{
				cliente= new Cliente();
				cliente.setCodigo(c.getString(0));
				cliente.setCodigoOpcional(c.getString(1));
				cliente.setRazonSocial(c.getString(2));
				cliente.setCalleNroPisoDpto(c.getString(3));
				cliente.setLocalidad(c.getString(4));
				cliente.setCuit(c.getString(5));
				cliente.setIva((byte) c.getDouble(6));
				cliente.setClaseDePrecio((byte) c.getDouble(7));
				cliente.setPorcDto(c.getDouble(8));
				cliente.setCpteDefault(c.getString(9));
				cliente.setIdVendedor(c.getString(10));
				cliente.setTelefono(c.getString(11));
				cliente.setMail(c.getString(12));
				cliente.setLatitudeBranch(c.getString(13));
				cliente.setLenghtBranch(c.getString(14));
				cliente.setPropietario(c.getString(15));
				cliente.setEstadoenvio(c.getString(16));

				cliente.setReference(c.getString(17));
				cliente.setNombre(c.getString(18));
				cliente.setApellido(c.getString(19));
				cliente.setCedula(c.getString(20));
				cliente.setCelular(c.getString(21));
				cliente.setProvincia(c.getString(22));
				cliente.setCanton(c.getString(23));
				cliente.setParroquia(c.getString(24));
				cliente.setImeI_ID(c.getString(25));
				cliente.setSaldoActual(c.getString(26));
				cliente.setDiasPlazo(c.getString(27));

				cliente.setZonaPeligrosa(c.getString(28));
				cliente.setActualizaGeo(c.getInt(29));
				cliente.setIDCLIENTE(c.getInt(30));
				lista.add(cliente);
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
	public Cliente CodigosActual() {
		return null;
	}




	public List<Cliente> getAllWithLimit(String where,int limitDesde, String order) {
		
		ArrayList<Cliente> lista = new ArrayList<Cliente>();
		Cliente cliente = null;
		
		String sql = "SELECT Codigo,CodigoOpcional,RazonSocial,"
				+ "calleNroPisoDpto,Localidad,Cuit,"
				+ "Iva,ClaseDePrecio,PorcDto,CpteDefault,idVendedor,telefono,email,LatitudeBranch, LenghtBranch,propietario,estadoenvio,Referencia,Nombres,Apellidos,Cedula,Celular,Provincia,Canton,Parroquia,IMEI,SaldoActual,DiasPlazo,ZonaPeligrosa,ActualizaGeo,IDCLIENTE FROM CLIENTES" ;
		
		if (!where.equals("")){
			sql = sql + " WHERE " + where;
		}	
		
		if (!order.equals("")){
			sql = sql + " ORDER BY " + order;
		}
		
		if (!(limitDesde==-1)){
			sql = sql + " LIMIT " + limitDesde + "," + cantRegistros;
		}
				
		Cursor c = db.rawQuery(sql,null);
		
		if(c.moveToFirst())
		{
			do
			{
				cliente= new Cliente();
				cliente.setCodigo(c.getString(0));
				cliente.setCodigoOpcional(c.getString(1));
				cliente.setRazonSocial(c.getString(2));
				cliente.setCalleNroPisoDpto(c.getString(3));
				cliente.setLocalidad(c.getString(4));
				cliente.setCuit(c.getString(5));
				cliente.setIva((byte) c.getDouble(6));
				cliente.setClaseDePrecio((byte) c.getDouble(7));
				cliente.setPorcDto(c.getDouble(8));
				cliente.setCpteDefault(c.getString(9));
				cliente.setIdVendedor(c.getString(10));
				cliente.setTelefono(c.getString(11));
				cliente.setMail(c.getString(12));
				cliente.setLatitudeBranch(c.getString(13));
				cliente.setLenghtBranch(c.getString(14));
				cliente.setPropietario(c.getString(15));
				cliente.setEstadoenvio(c.getString(16));

				cliente.setReference(c.getString(17));
				cliente.setNombre(c.getString(18));
				cliente.setApellido(c.getString(19));
				cliente.setCedula(c.getString(20));
				cliente.setCelular(c.getString(21));
				cliente.setProvincia(c.getString(22));
				cliente.setCanton(c.getString(23));
				cliente.setParroquia(c.getString(24));
				cliente.setImeI_ID(c.getString(25));
				cliente.setSaldoActual(c.getString(26));
				cliente.setDiasPlazo(c.getString(27));

				cliente.setZonaPeligrosa(c.getString(28));
				cliente.setActualizaGeo(c.getInt(29));
				cliente.setIDCLIENTE(c.getInt(30));

				lista.add(cliente);
			}
			
			while(c.moveToNext());
		}
		
		if(!c.isClosed())
			c.close();
		
		
		return lista;
	}

	public  void ActualizarEstadoCliente(Cliente cliente)
	{
		String sql;
		sql="UPDATE CLIENTES SET CpteDefault = '" + cliente.getCpteDefault() + "' where Codigo='"+cliente.getCodigo()+"'";
		db.execSQL(sql);

	}
	//consulta localidades
		public List<Cliente> getAllLocalidades()
		{

			ArrayList<Cliente> lista = new ArrayList<Cliente>();
			Cliente cliente = null;

			String sql = "SELECT distinct (Localidad)  FROM CLIENTES" ;



			Cursor c = db.rawQuery(sql,null);

			if(c.moveToFirst())
			{
				do
				{
					cliente= new Cliente();
					cliente.setLocalidad(c.getString(0));
					System.out.println("LOCALIDAD "+c.getString(0));
					lista.add(cliente);
				}

				while(c.moveToNext());
			}

			if(!c.isClosed())
				c.close();


			return lista;
		}

	public void deleteAll(String where) {
		String sql = "DELETE FROM Clientes" ;
		if (!where.equals("")){
			sql = sql + " WHERE " + where;
		}

		db.execSQL(sql);
	}

	
}
