package ar.com.syswork.sysmobile.pconsultactacte;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.ChequePagos;
import ar.com.syswork.sysmobile.entities.Cheques;
import ar.com.syswork.sysmobile.util.DialogManager;

public class PantallaManagerConsultaCtaCte {

	private Activity a;
	private Dialog dialog;
	private Dialog dialogimagen;
	private Dialog dialogFechaDesdeHasta;
	private DialogManager utlDlg;
	public final Calendar c = Calendar.getInstance();
	final int mes = c.get(Calendar.MONTH);
	final int dia = c.get(Calendar.DAY_OF_MONTH);
	final int anio = c.get(Calendar.YEAR);
	private DatePicker dtpFechaDesde;
	private DatePicker dtpFechaHasta;
	private Button btnAceptarFechas;
	private  Button btncancelarpagocheque;
	private  Button btnaggregarcheque;
	private ListView listavcheque;
	private AdapterChequesCta adapterch;
	private ListenerConsultaCtaCte listenerConsultaCtaCte;
	private ArrayList<Cheques> listacheques;
	private  Button btnaceptarcheque,btnsalirvista;
	private ImageView imageView;
	public  DatePicker dtpfechaCheque;
	private static final String CERO = "0";
	private static final String DOS_PUNTOS = ":";
	private static final String BARRA = "/";

	public PantallaManagerConsultaCtaCte(final Activity a, ListenerConsultaCtaCte listenerConsultaCtaCte)
	{
		this.a = a;
		this.listenerConsultaCtaCte = listenerConsultaCtaCte;
		
		creaDialogoConsultaCtaCte();
		crearDialogoConsultaFechaDesdeHasta();
		crearDialogoChequePagos();
		crearDialogoImagen();
		utlDlg = new DialogManager();
		listavcheque.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(final AdapterView<?> act, View v, final int position, long id) {
				AlertDialog.Builder adb=new AlertDialog.Builder(a);
				adb.setTitle("Delete?");
				adb.setMessage("Are you sure you want to delete " + position);
				final int positionToRemove = position;
				adb.setNegativeButton("Cancel", null);
				adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						adapterch.remove((Cheques) act.getItemAtPosition(position));
						adapterch.notifyDataSetChanged();
					}});
				adb.show();
			}
		});
	}
	
	public void seteaNombreCliente(String nombre)
	{
		TextView tv = (TextView) a.findViewById(R.id.txtRazonSocialCliente);
		tv.setText(nombre);
	}
	
	
	public void creaDialogoConsultaCtaCte()
	{
		dialog = new Dialog(a);
		dialog.setContentView(R.layout.dialog_consulta_cta_cte);
		dialog.setTitle(a.getString(R.string.sincronizando));
		dialog.setCancelable(false);
	}

	public  void crearDialogoImagen(){
		dialogimagen= new Dialog(a);
		dialogimagen.setContentView(R.layout.dialog_vistaimagen);
		imageView=(ImageView) dialogimagen.findViewById(R.id.imagentomada);
		btnsalirvista=(Button)dialogimagen.findViewById(R.id.btnsalirvista);
		listenerConsultaCtaCte.seteaListener(btnsalirvista);
		dialogimagen.setCancelable(false);
	}
	public  void mostrardialogoimagen(){
		dialogimagen.show();
	}
	public  void cerrardialogoimagen(){
		dialogimagen.dismiss();
	}
	public void crearDialogoChequePagos(){
		dialog = new Dialog(a);
		dialog.setContentView(R.layout.dialog_chequespagos);
		dialog.setTitle(a.getString(R.string.chequespagos));
		btncancelarpagocheque=(Button) dialog.findViewById(R.id.btncancelarch);
		listavcheque=(ListView)dialog.findViewById(R.id.lvagregaCheque);
		btnaggregarcheque=(Button)dialog.findViewById(R.id.btnagregarcheque);
		btnaceptarcheque=(Button)dialog.findViewById(R.id.btnaceptarcheque);
		listenerConsultaCtaCte.seteaListener(btncancelarpagocheque);
		listenerConsultaCtaCte.seteaListener(btnaggregarcheque);
		listenerConsultaCtaCte.seteaListener(btnaceptarcheque);

		listacheques=new ArrayList<Cheques>();
		adapterch= new AdapterChequesCta(dialog.getContext(),listacheques);
		listavcheque.setAdapter(this.adapterch);
		dialog.setCancelable(false);

	}
	public void muestraDialogoConsultCtaCte()
	{
		dialog.show();
	}
	public  void  mostrarDialogoChequePagos()
	{
		dialog.show();
	}
	public void cierraDialogoConsultCtaCte()
	{
		dialog.dismiss();
	}
	public void cierraDialogoChequepagos(){
		dialog.dismiss();
	}

	public void crearDialogoConsultaFechaDesdeHasta()
	{
		dialogFechaDesdeHasta= new Dialog(a);
		dialogFechaDesdeHasta.setContentView(R.layout.dialog_consulta_fechadesdehasta);
		dialogFechaDesdeHasta.setCancelable(true);
		
		dtpFechaDesde = (DatePicker) dialogFechaDesdeHasta.findViewById(R.id.dtpFechaDesde);
		dtpFechaHasta = (DatePicker) dialogFechaDesdeHasta.findViewById(R.id.dtpFechaHasta);
		
		btnAceptarFechas = (Button) dialogFechaDesdeHasta.findViewById(R.id.btnAceptarFechaDdeHta);
		listenerConsultaCtaCte.seteaListener(btnAceptarFechas);
		
	}	
	
	public void muestraDialogoConsultaFechaDesdeHasta()
	{
		Calendar cal = Calendar.getInstance();
		dtpFechaDesde.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
		dtpFechaHasta.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
		
		dialogFechaDesdeHasta.show();
	}



	public void cierraDialogoConsultaFechaDesdeHasta()
	{
		dialogFechaDesdeHasta.dismiss();
	}
	
	public  void cargarfoto(String ruta){
		Bitmap bmImg = BitmapFactory.decodeFile(ruta);
		imageView.setImageBitmap(bmImg);

	}

	
	public void muestraToastErrorDeConexion()
	{
		utlDlg.muestraToastGenerico(this.a, a.getString(R.string.huboErrorDeComunicaciones), false);
	}
	
	public void muestraToastErrorDesdeMayorQueHasta()
	{
		utlDlg.muestraToastGenerico(this.a, a.getString(R.string.valorDesdeMayorQueHalorHasta), false);
	}
	public  void muestraerrovalidacion(String mensaje){
        utlDlg.muestraToastGenerico(this.a, mensaje, true);
    }

	
	public String obtieneFechaDesdeYYYYMMDD()
	{
		String tmpFecha = "";
		String valorLeido = "";
		
		valorLeido = dtpFechaDesde.getYear() + "";
		if (valorLeido.length()==2)
			valorLeido = "20" + valorLeido;
		
		tmpFecha = valorLeido;
		
		
		valorLeido = (dtpFechaDesde.getMonth() +1)+ "";
		if (valorLeido.length()==1)
			valorLeido = "0" + valorLeido;
		tmpFecha = tmpFecha + valorLeido;
		
		valorLeido = dtpFechaDesde.getDayOfMonth() + "";
		if (valorLeido.length()==1)
			valorLeido = "0" + valorLeido;
		tmpFecha = tmpFecha + valorLeido;

		return tmpFecha;
	}
	public String obtieneFechaHastaYYYYMMDD()
	{
		String tmpFecha = "";
		String valorLeido = "";
		
		valorLeido = dtpFechaHasta.getYear() + "";
		if (valorLeido.length()==2)
			valorLeido = "20" + valorLeido;
		
		tmpFecha = valorLeido;
		
		
		valorLeido = (dtpFechaHasta.getMonth() +1) + "";
		if (valorLeido.length()==1)
			valorLeido = "0" + valorLeido;
		tmpFecha = tmpFecha + valorLeido;
		
		valorLeido = dtpFechaHasta.getDayOfMonth() + "";
		if (valorLeido.length()==1)
			valorLeido = "0" + valorLeido;
		tmpFecha = tmpFecha + valorLeido;
		
		return tmpFecha;
	}
	public void AgregarchequeL(){
		Cheques cheques= new Cheques();
		cheques.setValor(0.00);
		cheques.setFecha("");
		cheques.setNumcheque("");
		cheques.setBanco("");

		listacheques.add(cheques);
		//adapterch.add(cheques);
		adapterch.notifyDataSetChanged();
	}

	public ArrayList<Cheques> RetornaLista(Double valor){
		if(ValidarTotal(listacheques,valor)){
			cierraDialogoChequepagos();
		}else{
			muestraerrovalidacion("El valor de cheques es menor al valor total pagado..!!!");
		}
		return  listacheques;
	}
	public void removerItemListaItems(int posicionItemSeleccionado) {
		listacheques.remove(posicionItemSeleccionado);
		adapterch.notifyDataSetChanged();

	}
	public boolean ValidarTotal(List<Cheques> chequePagos,double valor){
		double sumar=0;
        if(validardatoscheques(chequePagos)==false){
            muestraerrovalidacion("Formato de fecha invalido DD/MM/YYYY");
            return false;
        }else {
            for (Cheques x : chequePagos
                    ) {
                sumar = sumar + x.getValor();
            }
            if (sumar == valor)
                return true;
            else
                return false;
        }

	}
	public boolean validardatoscheques(List<Cheques> chequespagos){
	    try{
            for (Cheques x: chequespagos) {

                boolean isExpired=false;
                if(x.getFecha().length() < 10){
                    return  false;
                }
                else {
                    Date expiredDate = stringToDate(x.getFecha().replace("/", "-"), "dd-MM-yyyy");

                                return true;
                }
            }
	        return true;
        }catch (Exception ex){
	        return false;
        }
    }
    private Date stringToDate(String aDate,String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }
	public void cargarcheques(List<ChequePagos> chequePagos){
		listacheques.clear();
		ArrayList<Cheques> tmpListaCheque = new ArrayList<>();

		Cheques nuevoch = new Cheques();


		ArrayList<Cheques> listact= new ArrayList<>();
		for (ChequePagos c:chequePagos
				) {
			if (c.getBanco().equals("Efectivo") == false) {
				nuevoch = new Cheques();
				nuevoch.setValor(c.getValor());
				nuevoch.setBanco(c.getBanco());
				nuevoch.setNumcheque(c.getNumerocheque());
				nuevoch.setFecha(c.getFecha());
				listact.add(nuevoch);
			}
		}
		if(chequePagos==null){
			nuevoch.setValor(0);
			nuevoch.setBanco("");
			nuevoch.setNumcheque("");
			nuevoch.setFecha("");
			listact.add(nuevoch);
		}


		tmpListaCheque = listact;
		if (tmpListaCheque.size() > 0) {
			Iterator<Cheques> i = tmpListaCheque.iterator();
			while (i.hasNext()) {
				listacheques.add(i.next());
			}
		}
	}
}
