package zexal.org.smartwatering;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by syahr on 14/04/2017.
 */

public interface RequestInterface {
    @GET("/data")
    Call<List<Data>> getJSON();
    //kalem
}
