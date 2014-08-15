package adullact.publicrowdfunding.model.server.request;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import adullact.publicrowdfunding.model.server.entities.Service;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
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
                //.setLogLevel(RestAdapter.LogLevel.FULL)
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
                .setEndpoint(SERVER_URL).build()
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

    public final static String SERVER_URL = "http://server.lucasnelaupe.fr/PublicrowFunding/PublicrowFunding/controler.php";
}
