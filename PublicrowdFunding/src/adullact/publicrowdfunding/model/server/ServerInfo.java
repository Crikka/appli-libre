package adullact.publicrowdfunding.model.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

/**
 * @author Ferrand
 * @brief Storage class for Server information
 */
public class ServerInfo {
	/* Singleton */
	private static ServerInfo m_instance = null;
	public static ServerInfo instance() { if(m_instance == null) {m_instance = new ServerInfo();} return m_instance; }
	private ServerInfo(){
	}
	/* --------- */

	/* Method performer */
	private void performGet(Map<String, String> bodyRequest) {  
		HttpURLConnection httpConnection = null;
		try {
			
			httpConnection = (HttpURLConnection) new URL("http://10.0.2.2/PublicrowFunding/PublicrowFunding/rest/users/userAPI.php").openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setDoInput(true); 
			httpConnection.setDoOutput(true); 
			httpConnection.setUseCaches(false); 
			httpConnection.addRequestProperty("Accept-Charset", "UTF-8");
			httpConnection.addRequestProperty("Content-Type", "application/json");
			httpConnection.connect();

			DataOutputStream dos = new DataOutputStream (httpConnection.getOutputStream()); 

			String message = "{";
			for (Map.Entry<String, String> entry : bodyRequest.entrySet())
			{
				message += "\""+entry.getKey()+"\":\""+entry.getValue()+"\",";
			}
			message = (message.substring(0, message.length()-1)+"}");
			byte[] message_utf8 = new String(message.getBytes(), "UTF-8").getBytes();
			dos.write(message_utf8);
			dos.flush(); 
			dos.close();

			String inputLine="";   //Stores the line of text returned by the server


			inputLine = streamToString(httpConnection.getInputStream());

			System.out.println("je vois : " + inputLine);

			/*InputStream response = httpConnection.getInputStream();

			BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
			for (String line; (line = reader.readLine()) != null;) {
				//System.out.println(line);
				// ... System.out.println(line) ?
			}*/

		} // end of "try"

		catch (MalformedURLException mue) { 
		} 
		catch (IOException ioe) { 
		}
		finally {
			httpConnection.disconnect();
		}

	}  // end of postNewItem() method 
	/* ---------------- */
	
	private String streamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }


	public void connect(final String username, final String password){
		Observable<User> observable = Observable.create(new OnSubscribe<User>() {

			@Override
			public void call(Subscriber<? super User> subscriber) {
				try {

					Map<String, String> bodyRequest = new TreeMap<String, String>();

					bodyRequest.put("username", username);
					bodyRequest.put("password", password);

					performGet(bodyRequest);

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
}
