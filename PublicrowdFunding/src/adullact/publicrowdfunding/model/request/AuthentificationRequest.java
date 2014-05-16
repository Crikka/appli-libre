package adullact.publicrowdfunding.model.request;

import adullact.publicrowdfunding.model.event.AuthentificationEvent;
import adullact.publicrowdfunding.model.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.Share;
import adullact.publicrowdfunding.shared.User;

/**
 * @author Ferrand
 * @brief Use this request when a user needs to be authenticate.
 */
public class AuthentificationRequest extends Request<AuthentificationRequest, AuthentificationEvent> {
	private String m_username;
	private String m_password;

	public AuthentificationRequest(String username, String password) {
		super();
		this.m_username = username;
		this.m_password = password;
	}
	
	@Override
	public void execute(AuthentificationEvent event) {
		
		ServerEmulator serverEmulator = ServerEmulator.instance();
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
		}
		
	}
	
	private void authentificateAndInitializeUser(User serverUser) {
		Share.user.defineFields(serverUser.pseudo(), serverUser.password(), serverUser.name(), serverUser.firstName());
		Share.user.authentificate();
	}

}
