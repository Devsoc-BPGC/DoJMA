package com.csatimes.dojma.utilities;

import com.csatimes.dojma.models.GetPostResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Java interface to DoJMA apis.
 * Usage:
 * (1) create instance of this interface
 * <pre>
 *     {@code
 *         final DojmaApi api = new Retrofit.Builder()
 *         .baseUrl(DOJMA_API_BASE_URL)
 *         .addConverterFactory(GsonConverterFactory.create())
 *         .build()
 *         .create(DojmaApi.class);
 *     }
 * </pre>
 *
 * (2) use the instance to create asynchronous request.
 * <pre>
 *     {@code
 *          api.methodName(params).enque(callback);
 *     }
 * </pre>
 *
 * _Attention_ : one Call can be used for only single async call. But you can clone it. See
 * https://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html#clone-- for more info.
 *
 * (3) for more see https://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html
 *
 * @author Rushikesh Jogdand.
 */
public interface DojmaApi {
    @GET("get_post")
    Call<GetPostResponse> getPost(@Query("id") int id);
}
