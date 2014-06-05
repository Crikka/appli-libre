package adullact.publicrowdfunding.model.server;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.shared.User;
import android.app.Service;
import android.os.AsyncTask;

/**
 * @author Ferrand
 * @brief Storage class for Server information
 */
public class ServerInfo {
	/* Singleton */
	private static ServerInfo m_instance = null;
	public static ServerInfo instance() { if(m_instance == null) {m_instance = new ServerInfo();} return m_instance; }
	private ServerInfo(){
		class MyErrorHandler implements ErrorHandler {
			@Override public Throwable handleError(RetrofitError cause) {
				Response r = cause.getResponse();
				System.out.println(cause.getMessage());
				if (r != null && r.getStatus() == 401) {
					//return new UnauthorizedException(cause);
				}
				return cause;
			}
		}
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://10.0.2.2/PublicrowFunding/PublicrowFunding/index.php").setErrorHandler(new MyErrorHandler()).build();
		m_service = restAdapter.create(ServerService.class);
	}
	/* --------- */

	/* Communication interface */
	private final ServerService m_service;
	private class ServerUser {
		public String name;
	}
	private interface ServerService {
		@GET("/users/{username}")
		User connect(@Path("username") String username);
		@GET("/")
		ServerUser test();
	}
	/* ----------------------- */

	public void connectry(){
		Observable<ServerUser> observable = Observable.create(new OnSubscribe<ServerUser>() {

			@Override
			public void call(Subscriber<? super ServerUser> subscriber) {
				try {
					ServerUser user = m_service.test();
					System.out.println(user.name);
					subscriber.onNext(user);
					subscriber.onCompleted();
				} catch (Exception exception) {
					subscriber.onError(exception);
				}
			}
			
		}).subscribeOn(Schedulers.io());
		observable.subscribe();
	}
}
