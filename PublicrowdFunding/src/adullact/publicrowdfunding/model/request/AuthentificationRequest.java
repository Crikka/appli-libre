package adullact.publicrowdfunding.model.request;

import retrofit.http.GET;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.errorHandle.AuthentificationErrorHandler;
import adullact.publicrowdfunding.model.event.AuthentificationEvent;
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
		errorHandler().defineEvent(event);
		errorHandler().defineRequest(this);
		
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
		@GET("/users/UserAPI.php")
		ServerUser connect();
	}
	/* 
	/* --------- */

	
	@Override
	public void execute() {
		
		/*ServerEmulator serverEmulator = ServerEmulator.instance();
		User serverUser = serverEmulator.authentificateUser(m_username, m_password);
		if(serverUser == null){
			event.errorUserNotExists(m_username, m_password);
		}
		else{
			if(serverUser instanceof Administrator){
				Share.user = new Administrator();
				authentificateAndInitializeUser(serverUser);
				done();
				event.onAuthentificate();
				event.ifUserIsAdministrator();
			}
			else {
				authentificateAndInitializeUser(serverUser);
				done();
				event.onAuthentificate();
			}
		}*/
		
		Observable<ServerUser> obs = Observable.just(new ServerUser()).subscribeOn(Schedulers.io());
		obs.subscribe(new Action1<ServerUser>(){

			@Override
			public void call(ServerUser user) {
				System.out.println("1");
				user = m_service.connect();
				if(errorHandler().isOk()){
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
			}
			
		});
		
	}
	
	/*private String streamToString(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}*/
	
	private void authentificateAndInitializeUser(ServerUser serverUser) {
		Share.user.defineFields(serverUser.username, m_password, serverUser.name, serverUser.firstName);
		Share.user.authentificate();
	}

}
