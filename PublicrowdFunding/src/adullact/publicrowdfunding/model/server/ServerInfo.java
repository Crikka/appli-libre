package adullact.publicrowdfunding.model.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.support.v4.app.TaskStackBuilder;

/**
 * @author Ferrand
 * @brief Storage class for Server information
 */
public class ServerInfo {
	/* Singleton */
	private static ServerInfo m_instance = null;
	public static ServerInfo instance() { if(m_instance == null) {m_instance = new ServerInfo();} return m_instance; }
	/* --------- */

	public static String SERVER_URL = "http://www.google.fr"; 
	// http://127.0.0.0/PublicrowFunding/PublicrowFunding/index.php
	// http://www.google.fr
	public static String PORT = "2525";
	public static AsyncTask<String, String, String> TASK = new AsyncTask<String, String, String>() {
		
		@Override
		protected String doInBackground(String... params) {
		     try
		        {
		            HttpClient httpclient = new DefaultHttpClient();
		            HttpGet method = new HttpGet(params[0]);
		            HttpResponse response = httpclient.execute(method);
		            HttpEntity entity = response.getEntity();
		            if(entity != null){
		            	System.out.println(EntityUtils.toString(entity));
		                return EntityUtils.toString(entity);
		            }
		            else{
		                return "No string.";
		            }
		         }
		         catch(Exception e){
		             return "Network problem";
		         }

		}
	};


	private ServerInfo() {}

	public boolean tryConnection() {
		TASK.execute(SERVER_URL);

		return true;
	}
}
