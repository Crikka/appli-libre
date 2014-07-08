package adullact.publicrowdfunding.model.server.request;

import retrofit.http.GET;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.ServerInfo;
import adullact.publicrowdfunding.model.server.errorHandler.AuthenticationErrorHandler;
import adullact.publicrowdfunding.model.server.event.AuthenticationEvent;
import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.Share;
import adullact.publicrowdfunding.shared.User;

/**
 * @author Ferrand
 * @brief Use this request when a user needs to be authenticate.
 */
public class AuthenticationRequest extends AuthenticatedRequest<AuthenticationRequest, AuthenticationEvent, AuthenticationErrorHandler> {
	private String m_username;
	private String m_password;

	public AuthenticationRequest(String username, String password, AuthenticationEvent event) {
		super(username, password, event, new AuthenticationErrorHandler());

		this.m_username = username;
		this.m_password = password;
	}

	public String username() {
		return m_username;
	}

	public String password() {
		return m_password;
	}

	@Override
	public void execute() {
		service().authenticate().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
		.onErrorReturn(new Func1<Throwable, ServerInfo.DetailedServerUser>() {

			@Override
			public ServerInfo.DetailedServerUser call(Throwable arg0) {
				return null;
			}
			
		})
		.subscribe(new Action1<ServerInfo.DetailedServerUser>() {

			@Override
			public void call(ServerInfo.DetailedServerUser user) {
				if(errorHandler().isOk()){
					done();
					if(user.administrator == "0") {
						Share.user = new User();
                        authenticateAndInitializeUser(user);
					}
					else {
						Share.user = new Administrator();
                        authenticateAndInitializeUser(user);
					}
					event().onAuthenticate();
				}
				else {
			    	event().errorUserNotExists(username(), password());
				}
			};
		});
	}

	private void authenticateAndInitializeUser(ServerInfo.ServerUser serverUser) {
		Share.user.defineFields(serverUser.username, m_password, serverUser.name, serverUser.firstName);
		Share.user.authentificate();
	}

}
