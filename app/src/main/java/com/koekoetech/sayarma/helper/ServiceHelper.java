package com.koekoetech.sayarma.helper;

import android.content.Context;

import com.koekoetech.sayarma.model.ArticleModel;
import com.koekoetech.sayarma.model.ContentCommentModel;
import com.koekoetech.sayarma.model.ContentLikeModel;
import com.koekoetech.sayarma.model.LastLoginViewModel;
import com.koekoetech.sayarma.model.LoginViewModel;
import com.koekoetech.sayarma.model.MemberModel;
import com.koekoetech.sayarma.model.NotificationModel;
import com.koekoetech.sayarma.model.WomenWellnessModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ServiceHelper {

    private static ApiService gitApiInterface;
    private static Cache cache;

    public static ApiService getClient(final Context context) {
        if (gitApiInterface == null) {

            Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    int maxAge = 300; // read from cache for 5 minute
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                }
            };

            //setup cache
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            //Cache cache = new Cache(httpCacheDirectory, cacheSize);
            cache = new Cache(httpCacheDirectory, cacheSize);

            final OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
            okClientBuilder.readTimeout(5, TimeUnit.MINUTES);
            okClientBuilder.connectTimeout(5, TimeUnit.MINUTES);
            okClientBuilder.writeTimeout(5, TimeUnit.MINUTES);

            OkHttpClient okClient = okClientBuilder.build();
//            okClient.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
//            okClient.setCache(cache);


            Retrofit client = new Retrofit.Builder()
                    .baseUrl("https://thukhamainapi.azurewebsites.net/api/")
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            gitApiInterface = client.create(ApiService.class);
        }
        return gitApiInterface;
    }

    public static void removeFromCache(String url) {
        try {
            Iterator<String> it = cache.urls();
            while (it.hasNext()) {
                String next = it.next();
                if (next.contains("http://thukhamainapi.azurewebsites.net/api/" + url)) {
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface ApiService {

        @GET("Newsfeed/getArticle")
        Call<ArrayList<ArticleModel>> getArticles(@Query("userID") String userID);

        @GET("Newsfeed/getArticleByID")
        Call<ArticleModel> getArticlesByContentID(@Query("ContentID") String ContentID);

        @GET("ThuKhaMainUser/GetMemberbyPhoneNumber")
        Call<MemberModel> getMemberProfile(@Query("organizationID") String organizationID, @Query("phoneNumber") String phoneNumber);

        @POST("ThuKhaMainUser/CreateNewMember")
        Call<MemberModel> saveNewMember (@Body MemberModel memberModel);

        @GET("Notification/getNotification")
        Call<ArrayList<NotificationModel>> getNotifications();

        @GET("Notification/updateNotificationStatus")
        Call<NotificationModel> updateNotificationStatus(@Query("NotificationID") String NotificationID);

        @POST("Newsfeed/SubmitContentLike")
        Call<ContentLikeModel> like(@Body ContentLikeModel contentLikeModel);

        @POST("Newsfeed/SubmitContentComment")
        Call<ContentCommentModel> comment(@Body ContentCommentModel contentCommentModel);

        @GET("Newsfeed/deleteComment")
        Call<ContentCommentModel> deleteComment(@Query("CommentID") String CommentID, @Query("ContentID") String ContentID, @Query("UserID") String UserID);

        @POST("ThuKhaMainUser/Authenticate")
        Call<LoginViewModel> login(@Body MemberModel memberModel);

        @GET("ThuKhaMainUser/GetUserByUserName")
        Call<MemberModel> getUserDataByUsername(@Query("username") String username);

        @POST("ThuKhaMainUser/UpdateUserData")
        Call<MemberModel> updateUserData(@Body MemberModel memberModel);

        @POST("Sync/ScoreSync")
        Call<LoginViewModel> sync(@Body LoginViewModel loginViewModel);

        @GET("WomensAwareness/getWomensAwarenessAndriod")
        Call<ArrayList<WomenWellnessModel>> getWomenWellness();

        @POST("ThuKhaMainUser/LastLoginRemark")
        Call<LastLoginViewModel> setLastLoginUser(@Body LastLoginViewModel lastLoginViewModel);

    }

}
