package adullact.publicrowdfunding.model.server.request;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.ServerInfo;
import adullact.publicrowdfunding.model.server.errorHandler.CreateUserErrorHandler;
import adullact.publicrowdfunding.model.server.event.CreateUserEvent;


public class CreateUserRequest extends AnonymousRequest<CreateUserRequest, CreateUserEvent, CreateUserErrorHandler> {
	private String m_username;
	private ServerInfo.ServerUser m_serverUser;

	public CreateUserRequest(String username, String password, String name, String firstName, CreateUserEvent event) {
        super(event, new CreateUserErrorHandler());

        m_username = username;
        m_serverUser = new ServerInfo.ServerUser();
        m_serverUser.password = password;
        m_serverUser.name = name;
        m_serverUser.firstName = firstName;

    }
	
	public String username() {
		return m_username;
	}
	
	public String password() {
		return m_serverUser.password;
	}
	
	public String name() {
		return m_serverUser.name;
	}
	
	public String firstName() {
		return m_serverUser.firstName;
	}

	@Override
	public void execute() {
		service().createUser(m_serverUser).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
		.onErrorReturn(new Func1<Throwable, ServerInfo.SimpleServerResponse>() {

			@Override
			public ServerInfo.SimpleServerResponse call(Throwable arg0) {
                return null;
			}
			
		})
		.subscribe(new Action1<ServerInfo.SimpleServerResponse>() {
			
			@Override
			public void call(ServerInfo.SimpleServerResponse response) {
				int  returnCode = response.code;
				if(errorHandler().isOk()){
					switch(returnCode){
					case 0: // Created
						done();
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
