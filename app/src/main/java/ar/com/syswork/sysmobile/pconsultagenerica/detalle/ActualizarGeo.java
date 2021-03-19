package ar.com.syswork.sysmobile.pconsultagenerica.detalle;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class ActualizarGeo extends AsyncTask<String, Void, String> {
    private AppSysMobile app;
    private DataManager dm;
    private Activity a;
    private DaoCliente daoCliente;
    private DaoToken daoToken;
    public ActualizarGeo(Activity a) {
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
            URL url = new URL("http://dyvenpro.azurewebsites.net/api/Branch/ActualizarGeoLocal?idbranch="+params[0]+"&lat="+params[1]+"&lon="+params[2]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            //urlConnection.setRequestProperty("Authorization", "Bearer " + params[1]);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, "UTF-8"));
           // writer.write(params[0]);
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
            return params[3].toString()+"Ç"+resultaso+"Ç"+params[1]+"Ç"+params[2];
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
                    String jsonObject = vector[0];
                    String resultado=vector[1];
                    String lat=vector[2];
                    String lon=vector[3];
                 if(resultado.equals("Exitoso")) {
                     app = (AppSysMobile) a.getApplicationContext();
                     dm = app.getDataManager();
                     daoCliente = dm.getDaoCliente();
                     Cliente c = daoCliente.getByKey(jsonObject);
                     c.setActualizaGeo(1);
                     c.setLatitudeBranch(lat);
                     c.setLenghtBranch(lon);

                     daoCliente.update(c);
                 }



                } catch (Exception err) {
                    Log.d("Error", err.toString());
                    Toast.makeText(app, "resultado de ejecucion: "+jsonString, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(app, "Clientes actualizado ubicación", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            //Toast.makeText(app, "Error de envio cliente: "+ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }



}

