package ar.com.syswork.sysmobile.daos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ar.com.syswork.sysmobile.util.DialogManager;

public class DataManager {


	private static MyOpenHelper openHelper;
	private static SQLiteDatabase db ;
	
	private static DaoRubro daoRubro;
	private static DaoDeposito daoDeposito;
	private static DaoVendedor daoVendedor;
	private static DaoCliente daoCliente;
	private static DaoArticulo daoArticulo;
	private static DaoPedido daoPedido;
	private static DaoPedidoItem daoPedidoItem;
    private DialogManager utilDialogos;


	private  static  DaoCuenta daoCuenta;

	private static DaoDESCUENTO_AVENA daoDESCUENTO_avena;
	private static DaoDESCUENTO_FORMAPAGO daoDESCUENTO_formapago;
	private static DaoDESCUENTO_VOLUMEN daoDESCUENTO_volumen;
	private static DaoPRECIO_ESCALA daoPRECIO_escala;
	private  static DaoCartera daoCartera;
	private  static DaoPagos daoPagos;
	private static  DaoPagosDetalles daoPagosDetalles;
	private  static  DaoChequePagos daoChequePagos;
	private static  DaoInventario daoInventario;
	private  static  Daoinventariodetalles daoinventariodetalles;

	private static  DaoVisitasUio daoVisitasUio;


	public DataManager(Context c)
	{
	
		if (openHelper == null)
		{
			openHelper = new MyOpenHelper(c, "baseDeDatos.db3");
	        db = openHelper.getWritableDatabase();
	        
	        checkDataBaseStructure();
	        
	        setDaoRubro(new DaoRubro(db));
	        setDaoVendedor(new DaoVendedor(db));
	        setDaoCliente(new DaoCliente(db));
	        setDaoArticulo(new DaoArticulo(db));
	        setDaoPedido(new DaoPedido(db));
	        setDaoPedidoItem(new DaoPedidoItem(db));
	        setDaoDeposito(new DaoDeposito(db));
	        setDaoCuenta(new DaoCuenta(db));
	        setDaoVisitasUio(new DaoVisitasUio(db));

			setDaoDESCUENTO_avena(new DaoDESCUENTO_AVENA(db));
			setDaoDESCUENTO_formapago(new DaoDESCUENTO_FORMAPAGO(db));
			setDaoDESCUENTO_volumen(new DaoDESCUENTO_VOLUMEN(db));
			setDaoPRECIO_escala(new DaoPRECIO_ESCALA(db));
			setDaoCartera(new DaoCartera(db));
			setDaoPagos(new DaoPagos(db));
			setDaoPagosDetalles(new DaoPagosDetalles(db));
			setDaoChequePagos(new DaoChequePagos(db));
			setDaoInventario(new DaoInventario(db));
			setDaoinventariodetalles(new Daoinventariodetalles(db));


		}

	}

	public DaoDESCUENTO_AVENA getDaoDESCUENTO_avena() {
		return daoDESCUENTO_avena;
	}
	public DaoChequePagos getDaoChequePagos() {
		return daoChequePagos;
	}
	public void setDaoDESCUENTO_avena(DaoDESCUENTO_AVENA daoDESCUENTO_avena) {
		DataManager.daoDESCUENTO_avena = daoDESCUENTO_avena;
	}

	public DaoDESCUENTO_FORMAPAGO getDaoDESCUENTO_formapago() {
		return daoDESCUENTO_formapago;
	}

	public void setDaoDESCUENTO_formapago(DaoDESCUENTO_FORMAPAGO daoDESCUENTO_formapago) {
		DataManager.daoDESCUENTO_formapago = daoDESCUENTO_formapago;
	}

	public DaoDESCUENTO_VOLUMEN getDaoDESCUENTO_volumen() {
		return daoDESCUENTO_volumen;
	}

	public void setDaoDESCUENTO_volumen(DaoDESCUENTO_VOLUMEN daoDESCUENTO_volumen) {
		DataManager.daoDESCUENTO_volumen = daoDESCUENTO_volumen;
	}

	public DaoPRECIO_ESCALA getDaoPRECIO_escala() {
		return daoPRECIO_escala;
	}

	public void setDaoPRECIO_escala(DaoPRECIO_ESCALA daoPRECIO_escala) {
		DataManager.daoPRECIO_escala = daoPRECIO_escala;
	}

	public DaoCartera getDaoCartera() {
		return daoCartera;
	}
	public  DaoPagos getDaoPagos(){
	    return  daoPagos;
    }
    public  DaoPagosDetalles getDaoPagosDetalles(){
	    return daoPagosDetalles;
    }
    public  DaoInventario getDaoInventario(){
        return daoInventario;
    }
	public  DaoVisitasUio getDaoVisitasUio(){
		return daoVisitasUio;
	}
    public  Daoinventariodetalles getDaoinventariodetalles(){
        return daoinventariodetalles;
    }

	public void checkDataBaseStructure()
	{
		String sql = "";
		
		if (!checkFieldNameExists("PEDIDOS","facturar"))
		{
			sql = "ALTER TABLE PEDIDOS ADD COLUMN facturar NUMERIC";
			db.execSQL(sql);
		}
		
		if (!checkFieldNameExists("PEDIDOS","incluirEnReparto"))
		{
			sql = "ALTER TABLE PEDIDOS ADD COLUMN incluirEnReparto NUMERIC";
			db.execSQL(sql);
		}
	}

	private boolean checkFieldNameExists(String table, String fieldName)
	{
		try
		{
			Cursor c = db.rawQuery("SELECT " + fieldName + " FROM " + table + " LIMIT 1",null);
			c.close();
		}
		catch (Exception ex)
		{
			return false;
		}
		
		return true;
	}
	
	public void close()
	{
		openHelper.close();
	}

	public DaoRubro getDaoRubro() {
		return daoRubro;
	}

	private void setDaoRubro(DaoRubro daoRubro) {
		DataManager.daoRubro = daoRubro;
	}

	public DaoVendedor getDaoVendedor() {
		return daoVendedor;
	}

	private void setDaoVendedor(DaoVendedor daoVendedor) {
		DataManager.daoVendedor = daoVendedor;
	}

	public DaoCliente getDaoCliente() {
		return DataManager.daoCliente;
	}

	private void setDaoCliente(DaoCliente daoCliente) {
		DataManager.daoCliente = daoCliente;
	}

	public DaoArticulo getDaoArticulo() {
		return daoArticulo;
	}

	private void setDaoArticulo(DaoArticulo daoArticulo) {
		DataManager.daoArticulo = daoArticulo;
	}
	
	public DaoPedido getDaoPedido() {
		return daoPedido;
	}

	private void setDaoPedido(DaoPedido daoPedido) {
		DataManager.daoPedido = daoPedido;
	}

	public DaoPedidoItem getDaoPedidoItem() {
		return daoPedidoItem;
	}

	private void setDaoPedidoItem(DaoPedidoItem daoPedidoItem) {
		DataManager.daoPedidoItem = daoPedidoItem;
	}

	public DaoDeposito getDaoDeposito() {
		return daoDeposito;
	}

	public void setDaoDeposito(DaoDeposito daoDeposito) {
		DataManager.daoDeposito = daoDeposito;
	}
	public  DaoCuenta getDaoCuenta() {
		return daoCuenta;
	}

	public  void setDaoCuenta(DaoCuenta daoCuenta) {
		DataManager.daoCuenta = daoCuenta;
	}
	public  void setDaoCartera(DaoCartera daoCartera) {
		DataManager.daoCartera = daoCartera;
	}
	public  void setDaoPagos(DaoPagos daoPagos) {
		DataManager.daoPagos = daoPagos;
	}
	public  void setDaoPagosDetalles(DaoPagosDetalles daoPagosDetalles) {
		DataManager.daoPagosDetalles = daoPagosDetalles;
	}
	public  void setDaoChequePagos(DaoChequePagos daoChequePagos) {
		DataManager.daoChequePagos = daoChequePagos;
	}
	public  void setDaoInventario(DaoInventario daoInventario){
	    DataManager.daoInventario=daoInventario;
    }

	public  void setDaoVisitasUio(DaoVisitasUio daoVisitasUio){
		DataManager.daoVisitasUio=daoVisitasUio;
	}


    public  void setDaoinventariodetalles (Daoinventariodetalles daoinventariodetalles){
	    DataManager.daoinventariodetalles=daoinventariodetalles;
    }
}
