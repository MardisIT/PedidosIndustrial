package ar.com.syswork.sysmobile.pconsultagenerica.detalle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
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
import ar.com.syswork.sysmobile.daos.DaoCodigosNuevos;
import ar.com.syswork.sysmobile.daos.DaoToken;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.entities.CodigosNuevos;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class obtenerCodigos extends AsyncTask<String, Void, String> {
    private AppSysMobile app;
    private DataManager dm;
    private String result="";
    private Activity a;
    private DaoCliente daoCliente;
    private DaoToken daoToken;
    private DaoCodigosNuevos daoCodigosNuevos;
    public obtenerCodigos(Activity a) {
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
            URL url = new URL("http://dyvenpro.azurewebsites.net/api/Order/SequeceOrder?idvendedor=1&iddevice="+AppSysMobile.WS_IMAIL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, "UTF-8"));
            writer.flush();

            int code = urlConnection.getResponseCode();
            if (code != 200) {

                throw new IOException("Invalid response from server: " + code);

            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                Log.i("data", line);
                result=line;
            }
            return result;
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
                JSONArray jsonArr = new JSONArray(jsonString);
                app = (AppSysMobile) a.getApplicationContext();
                dm = app.getDataManager();
                daoCodigosNuevos = dm.getDaoCodigosNuevos();
                daoCodigosNuevos.deleteAll();
                for (int i = 0; i < jsonArr.length(); i++)
                {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    CodigosNuevos codigosNuevos= new CodigosNuevos();
                    codigosNuevos.setID(jsonObj.getInt("id"));
                    codigosNuevos.setCode(jsonObj.getInt("code"));
                    codigosNuevos.setEstado(jsonObj.getString("estado"));
                    codigosNuevos.setUri("");
                    codigosNuevos.setIdAccount(jsonObj.getString("idsaleman"));
                    codigosNuevos.setImei_id(jsonObj.getString("imei_id"));
                    codigosNuevos.setCodeunico(jsonObj.getString("codeunico"));

                    daoCodigosNuevos.save(codigosNuevos);

                    System.out.println(jsonObj);
                }
            }
        } catch (Exception ex) {
            Toast.makeText(app, "Error al recibir códigos: "+ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }



}


