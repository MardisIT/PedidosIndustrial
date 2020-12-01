package ar.com.syswork.sysmobile.Tracking;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostService {
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("api/Login/Authenticate")

    Call<String> GetTokem(@Body JSONObject _user);

    @POST("api/Login/Auth")
    Call<TokenKey> GetTokem2(@Body User _user);

    @POST("api/Tracking/SaveBranchTracking")
    Call<Post> SetRouteBranches(@Body List<RouteBranches> _routeBranches);

    @POST("api/Tracking/SaveStatusPerson")
    Call<Post> SetTracking(@Body Tracking _tracking);

    @POST("api/Tracking/SaveStatusBranchTracking")
    Call<Post> SaveStatusBranchTracking(@Body SaveStatusBranchTracking _SaveStatusBranchTracking);

    @POST("api/Tracking/SaveNewBranchTracking")
    Call<Post> SaveNewBranchTracking(@Body SaveNewBranchTracking _SaveNewBranchTracking);

    //@GET("api/Tracking/SaveNewBranchTracking")


}