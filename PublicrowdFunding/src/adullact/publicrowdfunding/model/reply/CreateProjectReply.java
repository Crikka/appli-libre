package adullact.publicrowdfunding.model.reply;


public class CreateProjectReply extends AuthentificationRequiredReply {
	private boolean m_alreadyInDatabase;

	/**
	 * @param request
	 * @brief for fail reply
	 */
	public CreateProjectReply() {
		super();
		
		this.m_alreadyInDatabase = false;
	}
	
	@Override
	public void declareSucceed() {
		super.declareSucceed();
		
		m_alreadyInDatabase = true;
	}
	
	public void declareAlreadyInDatabase() {
		declareFailed();
		
		m_alreadyInDatabase = false;
	}
	
	public boolean isAlreadyInDatabase() {
		return m_alreadyInDatabase;
	}

}