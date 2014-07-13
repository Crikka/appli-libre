package adullact.publicrowdfunding.model.server.request;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import adullact.publicrowdfunding.model.server.ServerInfo;
import adullact.publicrowdfunding.model.server.ServerObject;
import adullact.publicrowdfunding.model.server.errorHandler.ErrorHandler;
import adullact.publicrowdfunding.model.server.event.Event;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * 
 * @author Ferrand
 *
 */
public abstract class Request
<TRequest extends Request<TRequest, TEvent, TErrorHandler>, 
TEvent extends Event<TRequest, TEvent, TErrorHandler>,
TErrorHandler extends ErrorHandler<TRequest, TEvent, TErrorHandler>> 
extends ServerObject<TRequest, TEvent, TErrorHandler> {
	private boolean m_done;
    private Service m_service;
	
	public Request(TEvent event, TErrorHandler errorHandler){
        super(event, errorHandler);


        this.m_service = new RestAdapter.Builder()
                .setRequestInterceptor(new SecurityRequestInterceptor())
                .setErrorHandler(errorHandler())
                .setEndpoint(SERVER_URL).build()
                .create(Service.class);

        this.m_done = false;
	}

    protected void defineRequestInterceptor(RequestInterceptor requestInterceptor) {
        m_service = new RestAdapter.Builder()
                .setRequestInterceptor(requestInterceptor)
                .setErrorHandler(errorHandler())
                .setEndpoint(ServerInfo.SERVER_URL).build()
                .create(Service.class);
    }

    protected Service service() {

        return m_service;
    }
	
	public boolean isDone() {
		return m_done;
	}
	
	public void done() {
		m_done = true;
	}
	
	public abstract void execute();

    public final static String SERVER_URL = "http://10.0.2.2/PublicrowFunding/PublicrowFunding/rest";

    /* Type of server response */
    protected static final class SimpleServerResponse {
        public int code;
    }
    protected static final class ServerFunding {
        public int id;
        public String value;
        public String username;
        public String projectID;
        public Date creationDate;
    }
    protected static final class ServerBookmark {
        public int id;
        public String username;
        public String projectID;
        public Date creationDate;
    }
    protected static final class ServerCommentary {
        public int id;
        public String username;
        public String projectID;
        public String title;
        public String message;
        public double mark;
        public Date creationDate;
    }
    protected static class ServerUser {
        public String username;
        public String password;
        public String mail;
        public String name;
        public String firstName;
        public String administrator;
    }
    protected static class ServerProject {
        public String id;
        public String proposedBy;
        public boolean validate;
        public String name;
        public String description;
        public String currentFunding;
        public String requestedFunding;
        public Date creationDate;
        public Date beginDate;
        public Date endDate;
        public double latitude;
        public double longitude;
    }
    protected static final class DetailedServerUser extends ServerUser {
        public ArrayList<ServerProject> projects; // All project
        public ArrayList<ServerFunding> fundedProjects;
        public ArrayList<ServerBookmark> bookmarkedProjects;
        public ArrayList<ServerCommentary> commentaries;
    }
    protected static final class DetailedServerProject extends ServerProject {
        public ArrayList<ServerFunding> fundedBy;
        public ArrayList<ServerBookmark> bookmarkedBy;
        public ArrayList<ServerCommentary> commentedBy;
    }
    /* ----------------------- */
    protected interface Service {
        // Check
        @GET("/check/authentication")
        Observable<DetailedServerUser> authenticate();
        @GET("/check/ready") // Indicate if server is ready
        Observable<SimpleServerResponse> ready();

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
        Observable<ArrayList<ServerUser>> listProjects(@QueryMap Map<String, String> filter);
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
	
}
