package adullact.publicrowdfunding.model.request;

import adullact.publicrowdfunding.model.event.ModifyAccountEvent;
import adullact.publicrowdfunding.model.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Share;

public class ModifyAccountRequest extends Request<ModifyAccountRequest, ModifyAccountEvent> {
	private String m_newPseudo;
	private String m_newPassword;
	private String m_newName;
	private String m_newFirstName;

	public ModifyAccountRequest(String newPseudo, String newPassword, String newName, String newFirstName) {
		super();
		this.m_newPseudo = newPseudo;
		this.m_newPassword = newPseudo;
		this.m_newName = newName;
		this.m_newFirstName = newFirstName;
	}

	@Override
	public void execute(ModifyAccountEvent event) {
		ServerEmulator serverEmulator = ServerEmulator.instance();
		
		if(Share.user.isAuthentified()) {
			String oldPseudo = Share.user.pseudo();
			String oldPassword = Share.user.password();

			Share.user.defineFields(
					m_newPseudo == null ? Share.user.pseudo() : m_newPseudo,
							m_newPassword == null ? Share.user.password() : m_newPassword,
									m_newName == null ? Share.user.name() : m_newName, 
											m_newFirstName == null ? Share.user.firstName() : m_newFirstName);

			serverEmulator.replaceUser(oldPseudo, oldPassword, Share.user);
			done();
			event.onModifyAccount();
		}
		else {
			event.errorAuthentificationRequired();
		}
	}

}
