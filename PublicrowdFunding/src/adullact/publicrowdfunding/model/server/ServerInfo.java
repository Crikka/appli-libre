package adullact.publicrowdfunding.model.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Ferrand
 * @brief Storage class for Server information
 */
public class ServerInfo {
	public final static String SERVER_URL = "http://10.0.2.2/PublicrowFunding/PublicrowFunding/rest";
	//public final static String SERVER_URL = "http://192.168.1.21/PublicrowFunding/PublicrowFunding/rest";

	
	/*
	Il faudrait vérifier la connexion 
	private boolean isNetworkAvailable() {
ConnectivityManager connectivityManager 
      = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
return activeNetworkInfo != null && activeNetworkInfo.isConnected();
}
	
	*/
	
	
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