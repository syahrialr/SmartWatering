package zexal.org.smartwatering;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by syahr on 14/04/2017.
 */

public interface RequestInterface {
    @GET("/data")
    Call<List<Data>> getJSON();
    //kalem

}
