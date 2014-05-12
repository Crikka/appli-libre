package adullact.publicrowdfunding.model.request;

import adullact.publicrowdfunding.model.reply.Reply;
import adullact.publicrowdfunding.model.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Share;

public class ModifyAccountRequest extends Request {
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
	public Reply execute() {
		ServerEmulator serverEmulator = ServerEmulator.instance();
		String oldPseudo = Share.user.pseudo();
		String oldPassword = Share.user.password();

		Share.user.defineFields(
				m_newPseudo == null ? Share.user.pseudo() : m_newPseudo,
						m_newPassword == null ? Share.user.password() : m_newPassword,
								m_newName == null ? Share.user.name() : m_newName, 
										m_newFirstName == null ? Share.user.firstName() : m_newFirstName);

		serverEmulator.replaceUser(oldPseudo, oldPassword, Share.user);
		return null;
	}

}
