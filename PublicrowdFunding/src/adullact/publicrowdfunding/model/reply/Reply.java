package adullact.publicrowdfunding.model.reply;

/**
 * @author Ferrand
 */
public abstract class Reply {
	private boolean m_ok;
	
	/**
	 * @param request
	 * @brief Constructor with true for ok
	 */
	public Reply(){
		m_ok = true;
	}
	
	public void declareSucceed(){
		m_ok = true;
	}
	
	public void declareFailed(){
		m_ok = false;
	}
	
	public boolean ok() {
		return m_ok;
	}
	
}
