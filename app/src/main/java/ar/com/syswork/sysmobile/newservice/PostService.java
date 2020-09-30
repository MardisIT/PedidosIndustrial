package ar.com.syswork.sysmobile.newservice;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostService {
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("api/Login/Authenticate")
    Call<String> GetTokem();

}
