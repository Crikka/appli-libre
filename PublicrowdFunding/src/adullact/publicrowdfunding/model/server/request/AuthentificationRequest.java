package adullact.publicrowdfunding.model.server.request;

import retrofit.http.GET;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.errorHandler.AuthentificationErrorHandler;
import adullact.publicrowdfunding.model.server.event.AuthentificationEvent;
import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.Share;
import adullact.publicrowdfunding.shared.User;

/**
 * @author Ferrand
 * @brief Use this request when a user needs to be authenticate.
 */
public class AuthentificationRequest extends AuthentificatedRequest<AuthentificationRequest, AuthentificationEvent, AuthentificationErrorHandler> {
	private String m_username;
	private String m_password;

	public AuthentificationRequest(String username, String password, AuthentificationEvent event) {
		super(username, password, event, new AuthentificationErrorHandler());		

		m_service = m_restAdapter.create(AuthentificationService.class);

		this.m_username = username;
		this.m_password = password;
	}

	public String username() {
		return m_username;
	}

	public String password() {
		return m_password;
	}

	/* Communication interface */
	private final AuthentificationService m_service;
	private class ServerUser {
		public String username;
		public String name;
		public String firstName;
		public String administrator;
	}
	private interface AuthentificationService {
		@GET("/authentification")
		Observable<ServerUser> connect();
	}
	/* --------- */


	@Override
	public void execute() {
		m_service.connect().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
		.onErrorReturn(new Func1<Throwable, ServerUser>() {

			@Override
			public ServerUser call(Throwable arg0) {
				return null;
			}
			
		})
		.subscribe(new Action1<ServerUser>() {

			@Override
			public void call(ServerUser user) {
				if(errorHandler().isOk()){
					done();
					if(user.administrator == "0") {
						Share.user = new User();
						authentificateAndInitializeUser(user);
					}
					else {
						Share.user = new Administrator();
						authentificateAndInitializeUser(user);
					}
					event().onAuthentificate();
				}
				else {
			    	event().errorUserNotExists(username(), password());
				}
			};
		});
	}

	private void authentificateAndInitializeUser(ServerUser serverUser) {
		Share.user.defineFields(serverUser.username, m_password, serverUser.name, serverUser.firstName);
		Share.user.authentificate();
	}

}
