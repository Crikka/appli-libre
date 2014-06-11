package adullact.publicrowdfunding.model.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import retrofit.http.Body;
import retrofit.http.PUT;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.errorHandle.CreateUserErrorHandler;
import adullact.publicrowdfunding.model.event.CreateUserEvent;


public class CreateUserRequest extends AnonymousRequest<CreateUserRequest, CreateUserEvent, CreateUserErrorHandler> {
	private ServerUser m_serverUser;

	public CreateUserRequest(String username, String password, String name, String firstName, CreateUserEvent event) {
		super(event, new CreateUserErrorHandler());
		errorHandler().defineEvent(event);
		errorHandler().defineRequest(this);

		m_serverUser = new ServerUser();
		m_serverUser.username = username;
		m_serverUser.password = password;
		m_serverUser.name = name;
		m_serverUser.firstName = firstName;

		m_service = m_restAdapter.create(CreateUserService.class);
	}

	/* Communication interface */
	private final CreateUserService m_service;
	@SuppressWarnings("unused")
	private class ServerUser {
		public String username;
		public String password;
		public String name;
		public String firstName;
	}
	private class ResponseServer {
		public Integer returnCode; 
	}
	private interface CreateUserService {
		@PUT("/users/UserAPI.php")
		Observable<ResponseServer> create(@Body ServerUser serverUser);
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
		m_service.create(m_serverUser).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseServer>() {
			
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
						event().errorUsernameAlreadyExists(m_serverUser.username);
						break;
					}
				}
			};
		});
	}
}
