package adullact.publicrowdfunding.model.server.request;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.ServerInfo;
import adullact.publicrowdfunding.model.server.errorHandler.CreateUserErrorHandler;
import adullact.publicrowdfunding.model.server.event.CreateUserEvent;


public class CreateUserRequest extends AnonymousRequest<CreateUserRequest, CreateUserEvent, CreateUserErrorHandler> {
	private String m_username;
	private ServerUser m_serverUser;

	public CreateUserRequest(String username, String password, String name, String firstName, CreateUserEvent event) {
        super(event, new CreateUserErrorHandler());

        m_username = username;
        m_serverUser = new ServerUser();
        m_serverUser.username = username;
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
        service().createUser(m_serverUser).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
		.onErrorReturn(new Func1<Throwable, SimpleServerResponse>() {

			@Override
			public SimpleServerResponse call(Throwable arg0) {
                return null;
			}

		})
		.subscribe(new Action1<SimpleServerResponse>() {

			@Override
			public void call(SimpleServerResponse response) {
                if (response == null) {
                    errorHandler().manageCallback();
                    return;
                }

				switch(response.code){
					case 0: // Created
						done();
						event().onCreateUser();
						break;
					case 1: // Error, missing username or password
                        event().errorUsernameAlreadyExists(m_username);
                        break;
				}
			}
		});
	}
}
