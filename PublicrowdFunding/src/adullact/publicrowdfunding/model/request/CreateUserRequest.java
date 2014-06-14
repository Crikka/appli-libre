package adullact.publicrowdfunding.model.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import retrofit.http.Body;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.errorHandle.CreateUserErrorHandler;
import adullact.publicrowdfunding.model.event.CreateUserEvent;


public class CreateUserRequest extends AnonymousRequest<CreateUserRequest, CreateUserEvent, CreateUserErrorHandler> {
	private String m_username;
	private ServerUser m_serverUser;

	public CreateUserRequest(String username, String password, String name, String firstName, CreateUserEvent event) {
		super(event, new CreateUserErrorHandler());
		errorHandler().defineEvent(event);
		errorHandler().defineRequest(this);

		m_username = username;
		m_serverUser = new ServerUser();
		m_serverUser.password = password;
		m_serverUser.name = name;
		m_serverUser.firstName = firstName;

		m_service = m_restAdapter.create(CreateUserService.class);
	}

	/* Communication interface */
	private final CreateUserService m_service;
	@SuppressWarnings("unused")
	private class ServerUser {
		public String password;
		public String name;
		public String firstName;
	}
	private class ResponseServer {
		public Integer returnCode; 
	}
	private interface CreateUserService {
		@PUT("/user/{username}")
		Observable<ResponseServer> create(@Body ServerUser serverUser, @Path(value = "username") String username);
	}
	/* --------- */

	private String streamToString(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

	@Override
	public void execute() {
		m_service.create(m_serverUser, m_username).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
		.onErrorReturn(new Func1<Throwable, ResponseServer>() {

			@Override
			public ResponseServer call(Throwable arg0) {
				ResponseServer res = new ResponseServer();
				res.returnCode = 2;
				return res;
			}
			
		})
		.subscribe(new Action1<ResponseServer>() {
			
			@Override
			public void call(ResponseServer response) {
				int  returnCode = response.returnCode;
				if(errorHandler().isOk()){
					switch(returnCode){
					case 0: // Created
						event().onCreateUser();
						break;
					case 1: // Error, missing username or password
						break;
					case 2: // User exists already
						event().errorUsernameAlreadyExists(m_username);
						break;
					}
				}
			};
		});
	}
}
