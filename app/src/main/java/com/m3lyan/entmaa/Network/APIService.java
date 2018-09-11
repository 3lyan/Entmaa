package com.m3lyan.entmaa.Network;

import com.m3lyan.entmaa.Model.BannersDataModel;
import com.m3lyan.entmaa.Model.BannersModel;
import com.m3lyan.entmaa.Model.HomeModel;
import com.m3lyan.entmaa.Model.QuestionsModel;
import com.m3lyan.entmaa.Model.ReplyModel;
import com.m3lyan.entmaa.Model.ReplyQModel;
import com.m3lyan.entmaa.Model.SignInModel;
import com.m3lyan.entmaa.Model.SignUpModel;
import com.m3lyan.entmaa.Model.TypesDataDetailsModel;
import com.m3lyan.entmaa.Model.TypesDetailsModel;
import com.m3lyan.entmaa.Model.TypesModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mahmoud on 5/15/2018.
 */

public interface APIService {
    @FormUrlEncoded
    @POST("signup/{lang}")
    Call<SignUpModel> signUpWrite(@Path("lang") String lang, @Field("username") String user, @Field("password") String pass, @Field("mobile") String phone, @Field("email") String email, @Field("name") String name, @Field("company_name") String compName);

    @FormUrlEncoded
    @POST("signin/{lang}")
    Call<SignInModel> signInWrite(@Path("lang") String lang,@Field("username") String user,@Field("password") String pass);

    @GET("categories/{lang}")
    Call<HomeModel> homeDataRead(@Path("lang") String lang);

    @GET("shops/{id}/{lang}")
    Call<TypesModel> TypesDataRead(@Path("id") int id,@Path("lang") String lang);

    @GET("shop/{id}/{lang}")
    Call<TypesDetailsModel> TypesDataDetailsRead(@Path("id") int id, @Path("lang") String lang);

    @GET("banners/{lang}")
    Call<BannersModel> BannersImg(@Path("lang") String lang);

    @GET("posts/{lang}")
    Call<QuestionsModel> QuestionsRead(@Path("lang") String lang);

    @GET("answers/{id}/{lang}")
    Call<ReplyQModel> ReplyRead(@Path("id") int id, @Path("lang") String lang);

    @GET("answer/{lang}")
    Call<ReplyModel> ReplyPost(@Path("lang") String lang, @Field("user_id") String userId, @Field("post_id") String postId, @Field("answer") String answer);

}
