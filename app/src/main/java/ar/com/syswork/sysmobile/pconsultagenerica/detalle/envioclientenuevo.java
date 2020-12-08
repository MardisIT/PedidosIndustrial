package ar.com.syswork.sysmobile.pconsultagenerica.detalle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.com.syswork.sysmobile.daos.DaoCliente;
import ar.com.syswork.sysmobile.daos.DaoToken;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class envioclientenuevo extends AsyncTask<String, Void, String> {
    private AppSysMobile app;
    private DataManager dm;
    private  Activity a;
    private DaoCliente daoCliente;
    private DaoToken daoToken;
    public envioclientenuevo(Activity a) {
        this.a = a;
    }
    private ProgressDialog progreso;
    @Override
    protected void onPreExecute()
    {

    }
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;

        try {
            JsonObject postData = new JsonObject();
          //  URL url = new URL("http://dyvenpro.azurewebsites.net/api/Branch/LoadTask");
            URL url = new URL("http://dyvenpro.azurewebsites.net/api/Branch/POSTGuardarLocalesNuevoAPPPedido");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + params[1]);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, "UTF-8"));
            writer.write(params[0]);
            writer.flush();

            int code = urlConnection.getResponseCode();
            if (code != 200) {

                throw new IOException("Invalid response from server: " + code);

            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            String resultaso="";
            while ((line = rd.readLine()) != null) {
                Log.i("data", line);
                resultaso= line;
            }
            return params[0].toString()+"Ç"+resultaso;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }


    }


    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);


        try {
            if (jsonString != "") {
                try {
                    String[] vector=jsonString.split("Ç");
                    JSONObject jsonObject = new JSONObject(vector[0]);
                    JSONObject resultado=new JSONObject(vector[1]);

                    JSONObject _ruta = new JSONObject(jsonObject.getString("_route"));
                    String codelocal = _ruta.getString("Codigo_Encuesta");
                    String codigosecundario=resultado.getString("data");
                    if (codelocal != "") {
                        app = (AppSysMobile) a.getApplicationContext();
                        dm = app.getDataManager();
                        daoCliente = dm.getDaoCliente();
                        Cliente c = daoCliente.getByKey(codelocal);
                        c.setEstadoenvio("S");
                        if(codigosecundario!=null)
                            c.setCodigoOpcional(codigosecundario);
                        daoCliente.update(c);
                    }

                } catch (JSONException err) {
                    Log.d("Error", err.toString());
                    Toast.makeText(app, "resultado de ejecucion: "+jsonString, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(app, "Clientes enviados", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(app, "Error de envio cliente: "+ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }



}


