package in.amruthashala.momapp.retrofit;
import com.google.gson.JsonObject;

import java.util.Map;

import in.amruthashala.momapp.inputresponsemodel.AddNewProductResponse;
import in.amruthashala.momapp.requestmodel.Myloginresponse;
import in.amruthashala.momapp.requestmodel.RequestModelClass;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
/**
 * Created by varadhi on 10/3/18.
 */

public interface APIService {

    @Headers("Content-Type: application/json")
    @POST("api/v1/mom_login")
    Call<Myloginresponse> momlogin(@Body Map<String, String> body);


    @Headers("Content-Type: application/json")
    @GET("api/v1/refresh_token")
    Call<Object> refreshtoken(@Header("Authorization") String authorization);


    @GET("api/v1/get_mom_details")
    Call<Object> get_mom_profile(@Header("Authorization") String authorization, @QueryMap Map<String, String> options);


    @PUT("api/v1/update_mom_details")
    Call<Object> update_mom_profile(@Header("Authorization") String authorization,@Body JsonObject rawJson);

    @POST("api/v1/change_password")
    Call<Object> changePassword(
            @Header("Authorization") String token,
            @Body Map<String,String> map
    );

    @POST("api/v1/mom_forgot_password")
    Call<Object> forgot_password(@Body Map<String, String> body);


    @POST("api/v1/mom_login_with_otp")
    Call<Object> mom_login_with_otp(@Body Map<String, String> body);


    @Headers("Content-Type: application/json")
    @POST("api/v1/add_product")
    Call<Object> addNewProduct(@Header("Authorization") String authorization,@Body JsonObject rawJson);


    @Headers("Content-Type: application/json")
    @POST("api/v1/mom_logout")
    Call<Object> momlogout(@Header("Authorization") String authorization);


    @Headers("Content-Type: application/json")
    @GET("api/v1/get_product")
    Call<Object> getproduct(@Header("Authorization") String authorization,@Query("seller_id") String sellerid);



    @POST("api/v1/add_product_image")
    Call<Object> add_productimage(@Header("Authorization") String authorization,@Body RequestBody file);


    @POST("api/v1/apply_registration")
    Call<Object> applyregistration(@Body RequestBody file);



    @PUT("api/v1/upload_documents")
    Call<MyDocResponse> uploaddocuments(@Body RequestBody file);
}


