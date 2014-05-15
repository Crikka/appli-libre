package adullact.publicrowdfunding.model.reply;

public abstract class AuthentificationRequiredReply  extends Reply {
	private boolean m_authentificationFailing;

	/**
	 * @param request
	 * @brief for fail reply
	 */
	public AuthentificationRequiredReply() {
		super();
		
		m_authentificationFailing = false;
	}
	
	@Override
	public void declareSucceed() {
		super.declareSucceed();
		
		m_authentificationFailing = false;
	}
	
	public void declareAuthentificationFailed() {
		declareFailed();
		
		m_authentificationFailing = true;
	}

	public boolean isAuthentificationFailing() {
		return m_authentificationFailing;
	}

}
