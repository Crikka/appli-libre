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
    @GET("/check/ready") // Indicate if server is ready
    Observable<SimpleServerResponse> ready();

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
    Observable<DetailedServerProject> detailProject();
    @GET("/project/")
    Observable<ArrayList<ServerProject>> listProjects(@QueryMap Map<String, String> filter);
    @POST("/project/")
    Observable<SimpleServerResponse> createProject(@Body ServerProject serverProject);
    @PUT("/project/{projectID}")
    Observable<SimpleServerResponse> modifyProject(@Path("projectID") String projectID, @Body ServerProject serverProject);
    @DELETE("/project/{projectID}")
    Observable<SimpleServerResponse> deleteProject(@Path("projectID") String projectID);

    // Funding
    @GET("/funding/")
    Observable<ArrayList<ServerFunding>> listFunding(@QueryMap Map<String, String> filter);
    @POST("/funding/")
    Observable<SimpleServerResponse> createFunding(@Body ServerFunding serverFunding);

    // Commentaries
    @GET("/commentary/")
    Observable<ArrayList<ServerUser>> listCommentaries(@QueryMap Map<String, String> filter);
    @POST("/commentary/")
    Observable<SimpleServerResponse> createCommentary(@Body ServerCommentary serverCommentary);
    @PUT("/commentary/{commentaryID}")
    Observable<SimpleServerResponse> modifyCommentary(@Path("commentaryID") String commentaryID);
    @DELETE("/commentary/{commentaryID}")
    Observable<SimpleServerResponse> deleteCommentary(@Path("commentaryID") String commentaryID);
}
