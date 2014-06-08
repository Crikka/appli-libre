package adullact.publicrowdfunding.model.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.shared.User;
import android.app.Service;
import android.os.AsyncTask;
import android.util.Base64;

/**
 * @author Ferrand
 * @brief Storage class for Server information
 */
public class ServerInfo {
	/* Singleton */
	private static ServerInfo m_instance = null;
	public static ServerInfo instance() { if(m_instance == null) {m_instance = new ServerInfo();} return m_instance; }
	private ServerInfo(){
		RestAdapter restAdapter = new RestAdapter.Builder()
		.setRequestInterceptor(new AuthentificationRequestInterceptor("pierre", "123456"))
		.setEndpoint("http://10.0.2.2/PublicrowFunding/PublicrowFunding")
		.build();

		m_service = restAdapter.create(ServerService.class);

	}

	protected class AuthentificationRequestInterceptor implements RequestInterceptor {

		private String username;
		private String password;

		public AuthentificationRequestInterceptor(String username, String password) {
			this.username = username;
			this.password = password;
		}

		@Override
		public void intercept(RequestFacade requestFacade) {
			final String userAndPassword = username + ":" + password;
			final String encodedUserAndPassword = "Basic " + Base64.encodeToString(userAndPassword.getBytes(), 0);
			requestFacade.addHeader("Authorization", encodedUserAndPassword);
		}

	}


	/* Communication interface */
	private final ServerService m_service;
	private class ServerUser {
		public String name;
		public String password;
	}
	private interface ServerService {
		@GET("/")
		ServerUser connect();
	}
	/* 
	/* --------- */

	public void connect(final String username, final String password){
		Observable<User> observable = Observable.create(new OnSubscribe<User>() {

			@Override
			public void call(Subscriber<? super User> subscriber) {
				try {

					System.out.println("je passe là");
					ServerUser user = m_service.connect();
					System.out.println(user.name);
					System.out.println(user.password);
					System.out.println("je passe ici");

					/*	JSONObject jsonReceived = new JSONObject(EntityUtils.toString(entity));
						if(jsonReceived.length() == 0) {
							// Authentification incorrect
						}
						else {      	
							User user = new User();
							user.defineFields(
									jsonReceived.getString("username"),
									password,
									jsonReceived.getString("name"),
									jsonReceived.getString("firstname")
									);

							System.out.println(user.name());
							System.out.println(user.firstName());
						}*/
					//subscriber.onNext(user);
					subscriber.onCompleted();
				}

				catch (Exception exception) {
					subscriber.onError(exception);
				}
			}

		}).subscribeOn(Schedulers.io());
		observable.subscribe();
	}

	private String streamToString(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}
}