package adullact.publicrowdfunding.model.server.entities;

import java.util.ArrayList;
import java.util.Map;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by Ferrand on 21/07/2014.
 */
public interface Service {
    // Check
    @GET("/authentication/")
    Observable<SimpleServerResponse> authenticate();
    @POST("/registration/{pseudo}")
    Observable<SimpleServerResponse> register(@Body ServerAccount serverAccount, @Path("pseudo") String pseudo);

    // Accounts
    @GET("/account/{name}")
    Observable<ServerAccount> detailAccount(@Path("name") String name);
    @GET("/account/")
    Observable<ArrayList<ServerAccount>> listAccount(@QueryMap Map<String, String> filter);
    @POST("/account/")
    Observable<SimpleServerResponse> createAccount(@Body ServerAccount serverAccount);
    @PUT("/account/{name}")
    Observable<SimpleServerResponse> modifyAccount(@Body ServerAccount serverAccount, @Path("name") String name);
    @DELETE("/account/{name}")
    Observable<SimpleServerResponse> deleteAccount(@Path("name") String name);

    // Users
    @GET("/user/{username}")
    Observable<DetailedServerUser> detailUser(@Path("username") String username);
    @GET("/user/")
    Observable<ArrayList<ServerUser>> listUsers(@QueryMap Map<String, String> filter);
    @POST("/user/")
    Observable<SimpleServerResponse> createUser(@Body ServerUser serverUser);
    @PUT("/user/{username}")
    Observable<SimpleServerResponse> modifyUser(@Body ServerUser serverUser, @Path("username") String username);
    @DELETE("/user/{username}")
    Observable<SimpleServerResponse> deleteUser(@Path("username") String username);

    // Projects
    @GET("/project/{projectID}")
    Observable<DetailedServerProject> detailProject(@Path("projectID") String projectID);
    @GET("/project/")
    Observable<ArrayList<ServerProject>> listProjects(@QueryMap Map<String, String> filter);
    @POST("/project/")
    Observable<SimpleServerResponse> createProject(@Body ServerProject serverProject);
    @PUT("/project/{projectID}")
    Observable<SimpleServerResponse> modifyProject(@Path("projectID") String projectID, @Body ServerProject serverProject);
    @DELETE("/project/{projectID}")
    Observable<SimpleServerResponse> deleteProject(@Path("projectID") String projectID);

    // Funding
    @GET("/funding/{id}")
    Observable<ServerFunding> detailFunding(@Path("id") String id);
    @GET("/funding/")
    Observable<ArrayList<ServerFunding>> listFunding(@QueryMap Map<String, String> filter);
    @POST("/funding/")
    Observable<SimpleServerResponse> createFunding(@Body ServerFunding serverFunding);
    @PUT("/funding/{id}")
    Observable<SimpleServerResponse> modifyFunding(@Path("id") String id, @Body ServerFunding serverFunding);
    @DELETE("/funding/{id}")
    Observable<SimpleServerResponse> deleteFunding(@Path("id") String id);

    // Commentaries
    @GET("/commentary/")
    Observable<ArrayList<ServerCommentary>> listCommentaries(@QueryMap Map<String, String> filter);
    @GET("/commentary/{id}")
    Observable<ServerCommentary> detailCommentary(@Path("id") String id);
    @POST("/commentary/")
    Observable<SimpleServerResponse> createCommentary(@Body ServerCommentary serverCommentary);
    @PUT("/commentary/{commentaryID}")
    Observable<SimpleServerResponse> modifyCommentary(@Path("commentaryID") String commentaryID, @Body ServerCommentary serverCommentary);
    @DELETE("/commentary/{commentaryID}")
    Observable<SimpleServerResponse> deleteCommentary(@Path("commentaryID") String commentaryID);

    // Bookmarks
    @GET("/bookmark/")
    Observable<ArrayList<ServerBookmark>> listBookmarks(@QueryMap Map<String, String> filter);
    @GET("/bookmark/{id}")
    Observable<ServerBookmark> detailBookmark(@Path("id") String id);
    @POST("/bookmark/")
    Observable<SimpleServerResponse> createBookmark(@Body ServerBookmark serverBookmark);
    @PUT("/bookmark/{id}")
    Observable<SimpleServerResponse> modifyBookmark(@Path("id") String id, @Body ServerBookmark serverBookmark);
    @DELETE("/bookmark/{id}")
    Observable<SimpleServerResponse> deleteBookmark(@Path("id") String id);
}
