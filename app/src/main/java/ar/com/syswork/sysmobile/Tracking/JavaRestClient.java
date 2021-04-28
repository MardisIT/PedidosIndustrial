package ar.com.syswork.sysmobile.Tracking;

import android.app.Activity;
import android.content.Context;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ar.com.syswork.sysmobile.daos.DaoConfiguracion;
import ar.com.syswork.sysmobile.daos.DaoToken;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.ConfiguracionDB;
import ar.com.syswork.sysmobile.entities.Token;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JavaRestClient {
    private AppSysMobile app;
    private DataManager dm;
    private AppSysMobile appSysmobile;
    private DataManager dataManager;
    private DaoConfiguracion daoConfiguracion;
    private ConfiguracionDB configuracionDB;
    private  Activity a;
    private DaoToken daoToken;

    public JavaRestClient(Activity a) {
        this.a = a;
    }

    /*
        public void getToken(JSONObject _user){
     try {

         Date date = new Date();
         SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
         String Token="";
         Retrofit _retrofit =new  Retrofit.Builder()
                 .baseUrl("https://mardisservice.azurewebsites.net/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         PostService service = _retrofit.create(PostService.class);
         Call<String> call=service.GetTokem(_user);
         call.enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 BaseDatosEngine usdbh = new BaseDatosEngine();
                 usdbh = usdbh.open();
                 if(!response.isSuccessful()){

                     int responseCOde=response.code();
                 }else {
                     ContentValues Objdatos = new ContentValues();
                     Objdatos.put("ID", 20);
                     Objdatos.put(EstructuraBD.CabecerasToken.token, response.body());
                     Objdatos.put(EstructuraBD.CabecerasToken.fecha, formatter.format(date));
                     usdbh.InsertToken(Objdatos);
                     usdbh.close();
                 }
             }

             @Override
             public void onFailure(Call<String> call, Throwable t) {
                 String d=t.getMessage();
             }
         });

         }catch (Exception e){
           String eS=e.getMessage();

         }
        }
    */
String apiser="https://mardisservice.azurewebsites.net/";
    public void getToken2(User _user,Activity c){
        try {



            final Date date = new Date();
            final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            app = (AppSysMobile) c.getApplicationContext();
            a=(Activity) c;
            dm = app.getDataManager();
            daoToken=dm.getDaoToken();
            final String Token="";
            Retrofit _retrofit =new  Retrofit.Builder()
                    .baseUrl(apiser)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostService service = _retrofit.create(PostService.class);
            final Call<TokenKey> call=service.GetTokem2(_user);
            call.enqueue(new Callback<TokenKey>() {
                @Override
                public void onResponse(Call<TokenKey> call, Response<TokenKey> response) {

                    if(!response.isSuccessful()){
                        int responseCOde=response.code();
                    }else {

                        ar.com.syswork.sysmobile.entities.Token token= new Token();
                        token.setFecha(formatter.format(date));
                        token.setToken(response.body().getToken());
                        daoToken.save(token);
                    }
                }

                @Override
                public void onFailure(Call<TokenKey> call, Throwable t) {
                    String d=t.getMessage();
                }
            });

        }catch (Exception e){
            String eS=e.getMessage();

        }
    }
    /*Envio de datos de Tracking
     *
     * */
    public void SetRouteBranches(List<RouteBranches> _routeBranches){
        try {

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String Token="";
            Retrofit _retrofit =new  Retrofit.Builder()
                    .client(clientHeader())
                    .baseUrl(apiser)
                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
            PostService service = _retrofit.create(PostService.class);
            Call<Post> call=service.SetRouteBranches(_routeBranches);
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    if(!response.isSuccessful()){

                        int responseCOde=response.code();
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    String d=t.getMessage();
                }
            });


        }catch (Exception e){
            String eS=e.getMessage();

        }
    }
    public void SetNewBranch(SaveNewBranchTracking _routeBranches){
        try {

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String Token="";
            Retrofit _retrofit =new  Retrofit.Builder()
                    .client(clientHeader())
                    .baseUrl(apiser)
                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
            PostService service = _retrofit.create(PostService.class);
            Call<Post> call=service.SaveNewBranchTracking(_routeBranches);
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    if(!response.isSuccessful()){

                        int responseCOde=response.code();
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    String d=t.getMessage();
                }
            });


        }catch (Exception e){
            String eS=e.getMessage();

        }
    }
    /*Envio de datos de Tracking
     *
     * */
    public void SetTracking(Tracking _tracking ){
        try {

            Retrofit _retrofit =new  Retrofit.Builder()
                    .client(clientHeader())
                    .baseUrl(apiser)
                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
            PostService service = _retrofit.create(PostService.class);
            Call<Post> call=service.SetTracking(_tracking);
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    if(!response.isSuccessful()){

                        int responseCOde=response.code();
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {

                }
            });


        }catch (Exception e){
            String eS=e.getMessage();

        }
    }


    /*Envio de datos de Tracking
     *
     * */
    public void SaveStatusBranchTracking(SaveStatusBranchTracking _tracking ){
        try {




            Retrofit _retrofit =new  Retrofit.Builder()
                    .client(clientHeader())
                    .baseUrl(apiser)
                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
            PostService service = _retrofit.create(PostService.class);
            Call<Post> call=service.SaveStatusBranchTracking(_tracking);
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    if(!response.isSuccessful()){

                        int responseCOde=response.code();
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {

                }
            });


        }catch (Exception e){
            String eS=e.getMessage();

        }
    }
    public OkHttpClient clientHeader(){

        app = (AppSysMobile) a.getApplicationContext();
        dm = app.getDataManager();
        daoToken=dm.getDaoToken();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                String  Token="";
                try {
                    for (Token a:daoToken.getAll("")
                    ) {
                        Token= a.getToken();
                    }
                }catch (Exception e){

                    Token="";
                }
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + Token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        return client;

    }
}
