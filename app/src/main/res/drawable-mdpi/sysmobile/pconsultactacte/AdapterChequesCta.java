package ar.com.syswork.sysmobile.pconsultactacte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.Cheques;


public class AdapterChequesCta extends ArrayAdapter<Cheques> {

    private LayoutInflater lInflater;
    OperacionesPagos obj= new OperacionesPagos();

    public AdapterChequesCta(Context context, ArrayList<Cheques> listaCtaCte) {
        super(context,0, listaCtaCte);

        lInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        Cheques i = getItem(position);

        if (!i.getFecha().equals(""))
            return 0;

        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
try {

    View view = null;
    EnvoltorioAux envoltorio = null;
    final Cheques cheques = getItem(position);

		/* SI LA FECHA VIENE EN "", QUIERE DECIR QUE ES EL SALDO ANTERIOR O EL
		   SALDO ACTUAL, POR TANTO TENGO QUE CARGAR OTRO LAYOUT PARA EL ITEM
		*/

    if (convertView == null) {
        view = lInflater.inflate(R.layout.item_cheques, null);

        envoltorio = new EnvoltorioAux();

        envoltorio.txtnumcheque = (EditText) view.findViewById(R.id.txtnumcheque);

        envoltorio.txtbanco = (EditText) view.findViewById(R.id.txtbanco);

        envoltorio.txtvalorp = (EditText) view.findViewById(R.id.txtvalorp);

        envoltorio.txtfechacheque = (EditText) view.findViewById(R.id.txtfechacheque);

        view.setTag(envoltorio);

    } else {

        view = convertView;
        envoltorio = (EnvoltorioAux) view.getTag();

    }

    envoltorio.txtnumcheque.setText(cheques.getNumcheque());
    envoltorio.txtbanco.setText(cheques.getBanco());
    envoltorio.txtvalorp.setText(String.valueOf(cheques.getValor()));
    envoltorio.txtfechacheque.setText(cheques.getFecha());
        envoltorio.txtvalorp.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    if(Caption.getText().toString().equals("")==false)
                        cheques.setValor (Double.valueOf(Caption.getText().toString()));
                    else
                        cheques.setValor (0.0);
                }

            }

        });


        envoltorio.txtbanco.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    cheques.setBanco (Caption.getText().toString());
                }

            }

        });
        envoltorio.txtnumcheque.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    cheques.setNumcheque (Caption.getText().toString());
                }

            }

        });
    envoltorio.txtfechacheque.setOnFocusChangeListener(new View.OnFocusChangeListener() {

        public void onFocusChange(View v, boolean hasFocus) {

            if (!hasFocus) {
                final int position = v.getId();
                final EditText Caption = (EditText) v;

                cheques.setFecha (Caption.getText().toString());
            }

        }

    });


    return view;
}catch (Exception ex){
    return  null;
}
    }

    private class EnvoltorioAux {

        EditText txtnumcheque;

        EditText txtbanco;

        EditText txtvalorp;

        EditText txtfechacheque;

    }
}
