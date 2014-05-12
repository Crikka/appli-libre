package adullact.publicrowdfunding.model.reply;

/**
 * @author Ferrand
 */
public class Reply {
	private boolean m_ok;
	
	/**
	 * @param request
	 * @brief Constructor with true for ok
	 */
	public Reply(){
		this.m_ok = true;
	}
	
	public Reply(boolean ok){
		this.m_ok = ok;
	}
	
	public boolean ok() {
		return m_ok;
	}
	
}
